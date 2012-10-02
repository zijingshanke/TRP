using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using System.Text.RegularExpressions;
using Fdays.Model.Order;
using System.Xml;
using Fdays.Common;
using Fdays.BLL.Order;
using Fdays.BLL.FSCache;
using Fdays.Config.Base;

namespace Fdays.BLL.Com.Share
{
    public static class BParsePnrInfo
    {
        //正则表达式集：
        //有效PNR

        static string gRegPassenger = @"\b(\d\.[\w \s/\(\)]+?)+ \s*?[A-Z0-9]{5}\b";
        //static string gRegPassenger = @"\b(\d\.[\w \s/\(\)]+?)+ \s*?[A-Z0-9]{6}\b";

        static string gRegFlight = @"\d\.\s+[A-Z0-9]{2}[0-9]{3,4}.*\b";
        static string gRegTicket = @"([0-9]{13})|[0-9]{3}-[0-9]{10}";
        //无效PNR
        static string gRegPassengerCancel = @"X(\d\.[\w /\(\)\d]+?)+ \s*?[A-Z0-9]{5}\b";
        static string gRegFlightCancel = @"\d\.\s+[A-Z0-9]{2}[0-9]{3,4}.*\b";
        //票价
        static string fareReg = @"[\s]FARE:[A-Z]{3}[\d]{1,9}.00";
        //机建税
        static string airTaxReg = @"[\s]TAX:[A-Z]{3}[\d]{1,3}.00";
        //燃油税
        static string oilTaxReg = @"[\s]YQ:[A-Z]{3}[\d]{1,3}.00";

        /// <summary>
        /// VIP城市
        /// </summary>
        public static string VipCity
        {
            get;
            set;
        }
        /// <summary>
        /// 是否是VIP用户
        /// </summary>
        public static int IsVip
        {
            get;
            set;
        }
        /// <summary>
        /// 是否匹配政策：0不匹配，1匹配
        /// </summary>
        public static int IsMatchingPolicy
        {
            get;
            set;
        }

        public static decimal Rebate
        {
            get;
            set;
        }


        /// <summary>
        /// 通过字符串解析，在正常订单导入PNR时，生成实体集合，用于保存。
        /// </summary>
        ///<param name="PNRInfo">编码信息</param>
        ///<param name="mo_order">订单实体</param>
        /// <returns></returns>
        public static void ParseFlightInfo(string PNRInfo, MO_order mo_order)
        {
            int ptype = 1;
            string Type = "";
            string regPassList = "";
            string regPassReplce = "";
            string regFlight = "";

            if (PNRInfo.IndexOf("*THIS PNR WAS ENTIRELY CANCELLED*") < 0)
            {
                Type = "Standard";
                regPassList = gRegPassenger;
                regPassReplce = @"\d\.";
                regFlight = gRegFlight;
            }
            else
            {
                Type = "Cancel";
                regPassList = gRegPassengerCancel;
                regPassReplce = @"X\d\.";
                regFlight = gRegFlightCancel;
            }

            PNRInfo = PNRInfo.Replace("*", "");


            Match MM = new Regex(regPassList).Match(PNRInfo);
            string passengers = MM.Value;

            //获取大小PNR。
            string pnr = passengers.Substring(passengers.Length - 5, 5);
            //string pnr = passengers.Substring(passengers.Length - 6, 6);
            //设置PAT价格到订单中，如果有
            SetPATPrice(mo_order, PNRInfo);

            mo_order.BkPNR = pnr;
            mo_order.BigPNR = new Regex(@"\d{1,2}\.RMK CA/([A-Z0-9]{5})\b").Match(PNRInfo).Groups[1].Value;

            if (string.IsNullOrEmpty(mo_order.BigPNR.Trim()))
            {
                mo_order.BigPNR = new Regex(@"-CA-([A-Z0-9]{5})").Match(PNRInfo).Groups[1].Value;
            }
            //解析航班舱位信息
            Dictionary<int, MO_cabins> dicCabin = ParseCabin(mo_order, regFlight, PNRInfo);
            //解析乘客信息
            ParsePassenger(mo_order, passengers, PNRInfo, regPassReplce, Type, out ptype);

            if (IsMatchingPolicy == 1)
            {
                if (ptype == 2)
                {
                    MatchingEnfantPolicy(dicCabin);
                }
                else
                {
                    SetCabinInfo(dicCabin, mo_order);
                }
            }

            //设置订单信息
            SetOrderInfo(mo_order, dicCabin, ptype);

            //解析票号
            MatchCollection matchColl = new Regex(gRegTicket).Matches(PNRInfo);
            int count = 0;
            if (matchColl.Count > 0)
            {
                foreach (MO_passenger mo_passenger in mo_order.DicPassenger.Values)
                {
                    mo_passenger.TicketNo = matchColl[count].Value;
                    mo_order.TicketNos = mo_passenger.TicketNo + ",";
                    count++;
                }
            }
            SetSecondPassenger(mo_order);

            mo_order.TicketNos = (!string.IsNullOrEmpty(mo_order.TicketNos) ? mo_order.TicketNos.TrimEnd(',') : mo_order.TicketNos);
        }

        #region 解析出乘客信息的方法
        /// <summary>
        /// Function:解析乘客信息的方法
        /// </summary>
        /// <param name="mo_order">订单实体</param>
        /// <param name="passengers">乘客姓名字符串</param>
        /// <param name="pnrInfo">黑屏信息</param>
        /// <param name="regPassReplce">乘客姓名解析正则表达式字符串</param>
        /// <param name="type">编码类型：Standard OR Cancel</param>
        /// <param name="ptype">输出参数，乘客类型：1成人；2儿童</param>
        private static void ParsePassenger(MO_order mo_order, string passengers, string pnrInfo, string regPassReplce, string type, out int ptype)
        {
            //乘客实体数据字典
            Dictionary<string, MO_passenger> dicPassenger = new Dictionary<string, MO_passenger>();
            passengers = passengers.Substring(0, passengers.Length - 5);
            passengers = Regex.Replace(passengers, regPassReplce, "*");
            ptype = 1;
            string[] passengerList = passengers.Split("*".ToCharArray());

            for (int i = 1; i < passengerList.Length; i++)
            {
                MO_passenger mo_passenger = new MO_passenger();
                mo_passenger.PName = passengerList[i].Trim();
                if (type == "Cancel")
                {
                    mo_passenger.PName = mo_passenger.PName.Substring(0, mo_passenger.PName.Length - 5);
                }
                mo_passenger.CrdNo = "";
                mo_passenger.PType = getPType(mo_passenger.PName, pnrInfo);
                ptype = mo_passenger.PType;
                mo_passenger.CrdType = 1;
                mo_passenger.OrderNo = mo_order.OrderNo;
                mo_passenger.TenorType = 1;
                mo_passenger.PState = 1;

                mo_order.Passengers += mo_passenger.PName + ",";
                mo_order.PassengerNum += 1;

                if (!dicPassenger.ContainsKey(mo_passenger.PName))
                {
                    dicPassenger.Add(mo_passenger.PName, mo_passenger);
                }
            }
            mo_order.Passengers = (!string.IsNullOrEmpty(mo_order.Passengers) ? mo_order.Passengers.TrimEnd(',') : mo_order.Passengers);
            mo_order.DicPassenger = dicPassenger;

        }
        #endregion

        #region 设置第二程乘客信息
        /// <summary>
        /// Function:设置第二程乘客信息的方法
        /// </summary>
        /// <param name="mo_order"></param>
        private static void SetSecondPassenger(MO_order mo_order)
        {
            if (mo_order.SailType != 1)
            {
                Dictionary<string, MO_passenger>.ValueCollection passengerList = mo_order.DicPassenger.Values;
                Array arr = mo_order.DicPassenger.Values.ToArray<MO_passenger>();
                foreach (MO_passenger mo_passenger in arr)
                {
                    MO_passenger mo_passenger1 = new MO_passenger();
                    mo_passenger1.CrdType = mo_passenger.CrdType;
                    mo_passenger1.CrdNo = mo_passenger.CrdNo;
                    mo_passenger1.PName = mo_passenger.PName;
                    mo_passenger1.PType = mo_passenger.PType;
                    mo_passenger1.TenorType = mo_order.SailType;
                    mo_passenger1.PState = 1;
                    mo_passenger1.TicketNo = mo_passenger.TicketNo;
                    if (!mo_order.DicPassenger.ContainsKey(mo_passenger.PName + "2"))
                    {
                        mo_order.DicPassenger.Add(mo_passenger.PName + "2", mo_passenger1);
                    }
                }
            }
        }
        #endregion

        #region 解析航班舱位信息的方法
        /// <summary>
        /// Function:解析舱位信息的方法
        /// </summary>
        /// <param name="mo_order">订单实体</param>
        /// <param name="regFlight">解析舱位的正则表达式字符串</param>
        /// <param name="pnrInfo">编码信息字符串</param>
        private static Dictionary<int, MO_cabins> ParseCabin(MO_order mo_order, string regFlight, string pnrInfo)
        { //航程
            Dictionary<int, MO_cabins> dicCabin = new Dictionary<int, MO_cabins>();
            MatchCollection matchColl = new Regex(regFlight).Matches(pnrInfo);
            BShare bshare = new BShare();
            int index = 1;
            foreach (Match match in matchColl)
            {
                string FlightLine = match.Value;

                FlightLine = Regex.Replace(FlightLine, @"(?=[\w]{7})" + DateTime.Now.ToString("yy") + @"(?<=[\w]{6})", " ");

                for (int i = 0; i < 10; i++)
                {
                    FlightLine = FlightLine.Replace("  ", " ");
                }
                string[] flightDetails = FlightLine.Split(" ".ToCharArray());

                MO_cabins mo_cabin = new MO_cabins();
                mo_cabin.CabinCode = flightDetails[2];
                if (mo_cabin.CabinCode == "Y")
                {
                    mo_cabin.Discount = 100;
                }
                mo_cabin.FromCityCode = flightDetails[4].Substring(0, 3);
                mo_cabin.ToCityCode = flightDetails[4].Substring(3, 3);
                CacheAirProxy cacheAirProxy = new CacheAirProxy();
                mo_cabin.FromCityName = cacheAirProxy.GetAirportName(mo_cabin.FromCityCode);
                mo_cabin.ToCityName = cacheAirProxy.GetAirportName(mo_cabin.ToCityCode);
                mo_cabin.FlightNo = flightDetails[1].Replace("*", "");
                mo_cabin.InitialPoint = Rebate;
                mo_cabin.Rebates = Rebate;
                mo_cabin.Poundage = 1;
                string FlightDate = flightDetails[3].Substring(2, 5) + DateTime.Now.Year.ToString();

                DateTime dtTemp = Convert.ToDateTime(FlightDate);
                if (dtTemp.Month < DateTime.Now.Month)
                {
                    dtTemp.AddYears(1);
                }

                FlightDate = dtTemp.ToString("yyyy-MM-dd");
                setCurrYear(ref FlightDate);
                mo_cabin.FlightDate = FlightDate;
                string startTime = flightDetails[6].Substring(0, 2) + ":" + flightDetails[6].Substring(2, 2);
                string arrival = flightDetails[7].Substring(0, 2) + ":" + flightDetails[7].Substring(2, 2);

                mo_cabin.Departure = startTime;
                mo_cabin.Arrival = arrival;
                mo_cabin.CarrierCode = mo_cabin.FlightNo.Substring(0, 2);      //获取承运人.
                if (!dicCabin.ContainsKey(index))
                {
                    dicCabin.Add(index, mo_cabin);
                }
                index++;
                if (index > 2)
                {
                    if (dicCabin[1].ToCityCode == dicCabin[2].FromCityCode && dicCabin[1].FromCityCode == dicCabin[2].ToCityCode)
                    {
                        dicCabin[2].CabinYPrice = dicCabin[1].CabinYPrice;
                        mo_order.SailType = 2;
                    }
                    else
                    {
                        mo_order.SailType = 3;
                    }
                    break;
                }
            }
            return dicCabin;
        }
        #endregion

        #region 匹配儿童政策
        /// <summary>
        /// Function:匹配儿童政策的方法
        /// </summary>
        /// <param name="dicCabin"></param>
        private static void MatchingEnfantPolicy(Dictionary<int, MO_cabins> dicCabin)
        {
            BFlightInfo bflightInfo = new BFlightInfo();
            bflightInfo.MatchingEnfantPolicy(dicCabin[1]);
            if (dicCabin.Count > 1)
            {
                bflightInfo.MatchingEnfantPolicy(dicCabin[2]);
            }
        }
        #endregion

        #region 设置航班信息
        private static void SetOrderInfo(MO_order mo_order, Dictionary<int, MO_cabins> dicCabin, int ptype)
        {
            mo_order.DicOrderInfo = new Dictionary<int, MO_orderInfo>();
            SetPriceByIbe(dicCabin, mo_order);
            // SetCabinPriceOrTax(mo_order, dicCabin);

            mo_order.DicOrderInfo.Add(1, BCreateOrder.GetOrderInfo(dicCabin[1], 1, mo_order.OrderNo, 1, 0, 1, 0));
            //如果有多程 
            if (mo_order.SailType != 1)
            {
                mo_order.DicOrderInfo.Add(2, BCreateOrder.GetOrderInfo(dicCabin[2], 0, mo_order.OrderNo, mo_order.SailType, 0, ptype, 0));
            }
            mo_order.DicOperateInfo = new Dictionary<int, MO_operateInfo>();
            mo_order.DicOperateInfo.Add(1, new MO_operateInfo(mo_order.OrderNo, mo_order.BkUserID, mo_order.BkMan, 1, "提交升舱换开订单！", 1, DateTime.Now));

            //订单相关信息
            mo_order.VoyageKind = 1;
            mo_order.BkDeparts = dicCabin[1].FlightDate + " " + dicCabin[1].Departure;
            mo_order.BkDeparts += (mo_order.SailType != 1 ? "," + dicCabin[2].FlightDate + " " + dicCabin[2].Departure : string.Empty);

            mo_order.BkTime = DateTime.Now;
            mo_order.EIItem = dicCabin[1].Remark;
            mo_order.FlightNos = dicCabin[1].FlightNo;
            mo_order.FlightNos += (mo_order.SailType != 1 ? "," + dicCabin[2].FlightNo : string.Empty);
            mo_order.IsLock = 0;
            mo_order.OrderKind = 1;
            mo_order.OrderState = 11;
            //如果订单中已经有价格
            if (mo_order.SingleMarketAMT > 0)
            {
                mo_order.MarketAMT = (mo_order.SingleMarketAMT + mo_order.SingleAliTax + mo_order.SingleOilTax) * mo_order.PassengerNum;
            }
            else
            {
                mo_order.MarketAMT = (dicCabin[1].Price + dicCabin[1].AirportTax + dicCabin[1].FuelFee) * mo_order.PassengerNum;
                mo_order.MarketAMT += (mo_order.SailType != 1 ? (dicCabin[2].Price + dicCabin[2].AirportTax + dicCabin[2].FuelFee) * mo_order.PassengerNum : 0);
            }
            mo_order.PayState = 0;
            mo_order.Segment = dicCabin[1].FromCityName + "-" + dicCabin[1].ToCityName;
            mo_order.Segment += (mo_order.SailType != 1 ? ("," + dicCabin[2].FromCityName + "-" + dicCabin[2].ToCityName) : string.Empty);
            mo_order.Sources = 2;

            //设置采购商实际支付金额
            BCreateOrder.SetStockerAMT(mo_order);
            mo_order.Supplier = dicCabin[1].PolicySupplierName;
            //计算平台扣费后的供应实收款金额
            BCreateOrder.SetSupplierAMT(mo_order);
            mo_order.SupplierID = dicCabin[1].PoliySupplierId;
            mo_order.TicketKind = (dicCabin[1].IsNormalPolicy == 1 ? 2 : 1);
            mo_order.TicketType = dicCabin[1].TicketType;
            mo_order.AgioType = dicCabin[1].AgioType;
            mo_order.LimitPayMode = dicCabin[1].LimitPayMode;
        }
        #endregion


        #region 设置舱位的价格及税费
        /// <summary>
        /// 
        /// </summary>
        /// <param name="mo_order"></param>
        /// <param name="dicCabin"></param>
        private static void SetCabinPriceOrTax(MO_order mo_order, Dictionary<int, MO_cabins> dicCabin)
        {
            if (mo_order.SailType == 1 && mo_order.SingleMarketAMT > 0)
            {
                dicCabin[1].Price = (int)mo_order.SingleMarketAMT;
                dicCabin[1].AirportTax = (int)mo_order.SingleAliTax;
                dicCabin[1].FuelFee = (int)mo_order.SingleOilTax;
                mo_order.SingleMarketAMT = 0;
                mo_order.SingleAliTax = 0;
                mo_order.SingleOilTax = 0;
            }
            else
            {
                BFlightInfo bflightInfo = new BFlightInfo();
                foreach (MO_cabins mo_cabin in dicCabin.Values)
                {
                    BParseGDS.SetYCabin(mo_cabin);
                    mo_cabin.Price = bflightInfo.ComputeCabinPrice(mo_cabin.CabinYPrice, mo_cabin.Discount);
                }


            }
        }
        #endregion

        #region 调IBE接口获取价格
        private static void SetPriceByIbe(Dictionary<int, MO_cabins> dicCabin, MO_order mo_order)
        {
            if ((mo_order.SailType == 1 && mo_order.SingleMarketAMT > 0)||IsMatchingPolicy==0)
            {
                dicCabin[1].Price = (int)mo_order.SingleMarketAMT;
                dicCabin[1].AirportTax = (int)mo_order.SingleAliTax;
                dicCabin[1].FuelFee = (int)mo_order.SingleOilTax;
                mo_order.SingleMarketAMT = 0;
                mo_order.SingleAliTax = 0;
                mo_order.SingleOilTax = 0;
            }
            else
            {
                try
                {
                    BFlightInfo bflightInfo = new BFlightInfo();
                    XmlDocument xmlDoc = new XmlDocument();
                    foreach (MO_cabins mo_cabin in dicCabin.Values)
                    {
                        int yprice = new BOrder().GetYCabinPrice(mo_cabin.CarrierCode, mo_cabin.FromCityCode, mo_cabin.ToCityCode, mo_cabin.FlightDate);

                        string ibeurl = "http://" + BaseConfig.RewriteIBEURL + "/SendIbe.asmx/";
                        string[] parm = { ibeurl, mo_cabin.FromCityCode, mo_cabin.ToCityCode, mo_cabin.FlightDate, mo_cabin.CarrierCode };
                        string url = string.Format("{0}SendDC?departure={1}&destination={2}&depart={3}&carrier={4}", parm);
                        xmlDoc.Load(url);

                        string strXML = xmlDoc.InnerXml;
                        strXML = strXML.Replace("*", "");
                        //string regExpression = @"<flight>" + flightNo + @"</flight>[\s\S]*?<fcn>(\d{1,4})</fcn>[\s\S]*?<fcny>(\d{1,4})[\s\S]*?</FlightNO>";
                        string regExpression = @"<flight>" + mo_cabin.FlightNo + @"</flight>[\s\S]*?<fcn>(\d{1,4})</fcn>[\s\S]*?<fyq>(\d{1,4})</fyq>[\s\S]*?<fcny>(\d{1,4})[\s\S]*?</FlightNO>";
                        Match MM = new Regex(regExpression).Match(strXML);

                        mo_cabin.CabinYPrice = (yprice > 0 ? yprice : getIntValue(MM.Groups[3].Value));
                        mo_cabin.AirportTax = getIntValue(MM.Groups[1].Value);
                        mo_cabin.FuelFee = getIntValue(MM.Groups[2].Value);
                        mo_cabin.Price = bflightInfo.ComputeCabinPrice(mo_cabin.CabinYPrice, mo_cabin.Discount);
                    }

                }
                catch (Exception ex)
                {
                }
            }


        }
        #endregion

        /// <summary>
        /// Function:设置舱位信息，并匹配政策
        /// </summary>
        /// <param name="dicCabin"></param>
        /// <param name="mo_order"></param>
        public static void SetCabinInfo(Dictionary<int, MO_cabins> dicCabin, MO_order mo_order)
        {
            BFlightInfo bflightInfo = new BFlightInfo();
            bflightInfo.userId = mo_order.BkUserID;
            bflightInfo.stockCompId = mo_order.StockerID;
            bflightInfo.flightKind = mo_order.SailType;
            bflightInfo.vipCity = VipCity;
            bflightInfo.isVIP = IsVip;
            bflightInfo.supplierCompId = mo_order.SupplierID;

            Fdays.Model.FSCache.MO_cabinAgioInfo mo_cabinAgionInfo = new Fdays.BLL.FSCache.CacheCabinProxy().GetCabinByCarrierOrCode(dicCabin[1].CarrierCode, dicCabin[1].CabinCode);
            bool isNewCabin = new BFlightInfo().CheckeIsNewCabin(dicCabin[1].FlightDate, dicCabin[1].CarrierCode);
            //如果获取不到舱位信息退出
            if (object.Equals(mo_cabinAgionInfo, null))
            {
                return;
            }
            //如果是特殊舱位并且不是单程编码退出
            if (mo_cabinAgionInfo.IsSpecialCabin == 1 && mo_order.SailType != 1)
            {
                return;
            }
            if (dicCabin[1].AirportTax <= 0)
            {
                dicCabin[1].AirportTax = 50;
            }
            if (mo_cabinAgionInfo.IsSpecialCabin == 0)
            {
                dicCabin[1].Remark = mo_cabinAgionInfo.QianZhu;
                if (isNewCabin)
                {
                    if (mo_cabinAgionInfo.Agio2 > 0)
                    {
                        dicCabin[1].Discount = mo_cabinAgionInfo.Agio2;
                    }
                }
                else
                {
                    dicCabin[1].Discount = mo_cabinAgionInfo.Agio;
                }
                dicCabin[1].NewDiscount = dicCabin[1].Discount;
                if (dicCabin[1].Price <= 0)
                {
                    dicCabin[1].Price = bflightInfo.ComputeCabinPrice(dicCabin[1].CabinYPrice, dicCabin[1].Discount);
                }
                if (mo_order.SailType == 3)
                {
                    bflightInfo.MatchingSysPolicy(dicCabin[1]);
                }
                else
                {

                    bflightInfo.MatchingPolicyNew(dicCabin[1], "go", 0);

                }
            }
            else//如果是特价舱位
            {
                if (dicCabin[1].Price <= 0)
                {
                    return;
                }
                mo_order.TicketKind = 2;
                Dictionary<string, string> dicTeJiaPolicy = bflightInfo.GetSppolicy(dicCabin[1], 1);
                bflightInfo.MatchingTeJiaPolicy(dicCabin[1], dicTeJiaPolicy);
                if (dicCabin[1].Rebates <= 0)
                {
                    throw new VSException("对不起，该特价舱位供应商票号已经用完，无法生成订单！");
                }
            }
            if (mo_order.SailType != 1)
            {
                if (dicCabin[2].AirportTax <= 0)
                {
                    dicCabin[2].AirportTax = 50;
                }
                Fdays.Model.FSCache.MO_cabinAgioInfo mo_cabinAgionInfo2 = new Fdays.BLL.FSCache.CacheCabinProxy().GetCabinByCarrierOrCode(dicCabin[1].CarrierCode, dicCabin[2].CabinCode);
                //如果第二程舱位为空，或者是特殊舱位退出
                if (object.Equals(mo_cabinAgionInfo2, null) || mo_cabinAgionInfo2.IsSpecialCabin == 1)
                {
                    return;
                }

                dicCabin[2].Remark = mo_cabinAgionInfo2.QianZhu;
                if (isNewCabin)
                {
                    dicCabin[2].Discount = mo_cabinAgionInfo2.Agio2;
                }
                else
                {
                    dicCabin[2].Discount = mo_cabinAgionInfo2.Agio;
                }
                dicCabin[2].NewDiscount = dicCabin[2].Discount;
                dicCabin[1].Price = (dicCabin[1].Price <= 0 ? bflightInfo.ComputeCabinPrice(dicCabin[1].CabinYPrice, dicCabin[1].Discount) : dicCabin[1].Price);
                dicCabin[2].Price = bflightInfo.ComputeCabinPrice(dicCabin[2].CabinYPrice, dicCabin[2].Discount);

                if (mo_order.SailType == 3)
                {
                    bflightInfo.MatchingSysPolicy(dicCabin[2]);
                }
                else
                {

                    bflightInfo.MatchingPolicyNew(dicCabin[2], "back");

                }
                if (dicCabin[2].PolicyId != dicCabin[1].PolicyId)
                {
                    bflightInfo.MatchingSysPolicy(dicCabin[1]);
                    bflightInfo.MatchingSysPolicy(dicCabin[2]);
                }
                //如果设置了优惠则重新计算红包金额
                if (dicCabin[1].AnnexPoint < 0)
                {
                    decimal bribe = Math.Truncate(mo_order.SingleMarketAMT * Math.Abs(dicCabin[1].AnnexPoint) / 100);
                    dicCabin[1].Bribe = (int)bribe;
                    dicCabin[2].Bribe = (int)bribe;
                }
            }
        }

        /// <summary>
        /// Function:设置PAT信息的价格到订单中
        /// </summary>
        /// <param name="mo_order"></param>
        /// <param name="pnrInfo"></param>
        public static void SetPATPrice(MO_order mo_order, string pnrInfo)
        {
            int minFare = 0;
            int maxFare = 0;
            int price = 0;
            string fare = string.Empty;
            MatchCollection matchColl = new Regex(fareReg).Matches(pnrInfo);
            if (matchColl.Count > 0)
            {
                foreach (Match match in matchColl)
                {
                    fare = Replace(match.Value, "[A-Z]|[:]", "").Trim();
                    price = getIntValue(fare);
                    if (minFare == 0 || price < minFare)
                    {
                        minFare = price;
                    }
                    else
                    {
                        maxFare = price;
                    }
                }
                maxFare = (maxFare == 0 ? minFare : maxFare);
                mo_order.SingleMarketAMT = minFare;
                mo_order.MaxSingleMarketAmt = maxFare;
            }
            matchColl = new Regex(airTaxReg).Matches(pnrInfo);
            if (matchColl.Count > 0)
            {
                string airTax = Replace(matchColl[0].Value, "[A-Z]|[:]", "").Trim();
                mo_order.SingleAliTax = getIntValue(airTax);
            }
            matchColl = new Regex(oilTaxReg).Matches(pnrInfo);
            if (matchColl.Count > 0)
            {
                string oilTax = Replace(matchColl[0].Value, "[A-Z]|[:]", "").Trim();
                mo_order.SingleOilTax = getIntValue(oilTax);
            }
            //如果是没有做PAT的信息，检查是否有添加了价格到编码信息中
            if (mo_order.SingleMarketAMT <= 0)
            {
                //12.FN/A/FCNY860.00/SCNY860.00/C3.00/XCNY90.00/TCNY50.00CN/TCNY40.00YQ/           ACNY950.00           

                string regText = @"FCNY[\d]{1,9}.00";
                Match match = new Regex(regText).Match(pnrInfo);
                if (object.Equals(match, null) || string.IsNullOrEmpty(match.Value))
                {
                    regText = @"SCNY[\d]{1,9}.00";
                    match = new Regex(regText).Match(pnrInfo);
                }
                if (!object.Equals(match, null) && !string.IsNullOrEmpty(match.Value))
                {
                    fare = Replace(match.Value, "[A-Z]", "");
                    mo_order.SingleMarketAMT = getDecimalValue(fare);
                    mo_order.MaxSingleMarketAmt = mo_order.SingleMarketAMT;
                }
                regText = @"TCNY[\d]{1,9}.00CN";
                match = new Regex(regText).Match(pnrInfo);
                if (!object.Equals(match, null) && !string.IsNullOrEmpty(match.Value))
                {
                    fare = Replace(match.Value, "[A-Z]", "");
                    mo_order.SingleAliTax = getDecimalValue(fare);
                }
                regText = @"TCNY[\d]{1,9}.00YQ";
                match = new Regex(regText).Match(pnrInfo);
                if (!object.Equals(match, null) && !string.IsNullOrEmpty(match.Value))
                {
                    fare = Replace(match.Value, "[A-Z]", "");
                    mo_order.SingleOilTax = getDecimalValue(fare);
                }
            }
        }


        /// <summary>
        /// 得到乘客类型，根据乘客姓名。
        /// </summary>
        /// <param name="pName"></param>
        /// <returns></returns>
        public static int getPType(string pName, string PNRInfo)
        {
            if (pName.Length > 5)
            {
                if (pName.Substring(pName.Length - 3, 3) == "CHD" || pName.IndexOf("CHD(") > 0)
                {
                    return 2;
                }
            }
            if (PNRInfo.IndexOf("SSR CHLD") > 0)
            {
                return 2;
            }

            return 1;
        }

        /// <summary>
        /// Function:根据航班日期获取当前年份的方法
        /// </summary>
        /// <param name="FlightDate"></param>
        private static void setCurrYear(ref string FlightDate)
        {
            DateTime date1 = Convert.ToDateTime(FlightDate);
            if (date1.Month < DateTime.Now.Month)
            {
                date1 = date1.AddYears(1);
            }
            FlightDate = date1.ToString("yyyy-MM-dd");
        }

        /// <summary>
        /// Function:通过舱位信息判断行程类型的方法
        /// </summary>
        /// <param name="dicCabins"></param>
        /// <returns></returns>
        public static int GetSailType(Dictionary<int, MO_cabins> dicCabins)
        {
            int stailType = 1;
            if (dicCabins.Count > 1)
            {
                if (dicCabins[1].FromCityCode == dicCabins[2].ToCityCode && dicCabins[1].ToCityCode == dicCabins[2].FromCityCode)
                {
                    stailType = 2;
                }
                else
                {
                    stailType = 3;
                }
            }
            return stailType;
        }

        #region 数据类型转换

        public static int getIntValue(string strValue)
        {
            try
            {
                int v = 0;
                if (!string.IsNullOrEmpty(strValue))
                {
                    v = (int)decimal.Parse(strValue);
                }
                return v;
            }
            catch (Exception)
            {
                return 0;
            }
        }

        public static decimal getDecimalValue(string strValue)
        {
            try
            {
                decimal v = 0;
                if (!string.IsNullOrEmpty(strValue))
                {
                    decimal.TryParse(strValue, out v);
                }
                return v;
            }
            catch (Exception)
            {
                return 0;
            }
        }

        public static float getFloatValue(string strValue)
        {
            try
            {
                float v = 0;
                if (!string.IsNullOrEmpty(strValue))
                {
                    float.TryParse(strValue, out v);
                }
                return v;
            }
            catch (Exception)
            {
                return 0;
            }
        }

        public static DateTime getDateTimeValue(string strValue)
        {
            DateTime v = new DateTime();
            try
            {
                if (!string.IsNullOrEmpty(strValue))
                {
                    v = DateTime.Parse(strValue);
                }
            }
            catch
            {
                v = DateTime.Parse("1900-01-01 00:00:00");
            }
            return v;
        }

        public static string Replace(string source, string expression, string replaceValue)
        {
            if (string.IsNullOrEmpty(source))
            {
                return source;
            }
            return Regex.Replace(source, expression, replaceValue);
        }

        #endregion
    }
}
