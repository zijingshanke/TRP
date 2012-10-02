package com.fdays.tsms.policy.action;

import java.sql.Timestamp;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.PolicyAfter;
import com.fdays.tsms.policy.PolicyAfterUtil;
import com.fdays.tsms.policy.biz.AirlinePolicyAfterBiz;
import com.fdays.tsms.policy.biz.PolicyAfterBiz;
import com.fdays.tsms.right.UserRightInfo;

import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;
import com.neza.mail.MailUtil;
import com.neza.security.MyX509Certificate;

public class PolicyAfterAction extends BaseAction {
	
	private PolicyAfterBiz policyAfterBiz;
	private AirlinePolicyAfterBiz airlinePolicyAfterBiz;

	
	//新增操作
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		
		String forwardPage = "";
		PolicyAfter policyAfter = (PolicyAfter) form;
		Inform inf = new Inform();
		if(!check(policyAfter)){
			inf.setMessage("增加后返政策信息出错！错误信息：字段格式出错");
			inf.setForwardPage("/policy/policyAfterList.do?thisAction=listPolicyAfter&airlinePolicyAfterId="
					+policyAfter.getAirlinePolicyAfterId());
			request.setAttribute("inf", inf);
			forwardPage = "inform";
			return (mapping.findForward(forwardPage));
		}
		if(!isCheckDate(policyAfter.getBeginDate(),policyAfter.getEndDate())){
			inf.setMessage("增加后返政策信息出错！错误信息：结束日期不能早于起始日期");
			inf.setForwardPage("/policy/policyAfterList.do?thisAction=listPolicyAfter&airlinePolicyAfterId="
					+policyAfter.getAirlinePolicyAfterId());
			request.setAttribute("inf", inf);
			forwardPage = "inform";
			return (mapping.findForward(forwardPage));
		}
		
		try {
			PolicyAfter tempPolicyAfter = new PolicyAfter();
			UserRightInfo uri = new UserRightInfo();
			uri = (UserRightInfo) request.getSession().getAttribute("URI");//session属性里的User的userName作为操作人
			tempPolicyAfter.setFlightCode(policyAfter.getFlightCode().trim());
			tempPolicyAfter.setFlightCodeExcept(policyAfter.getFlightCodeExcept().trim());
			tempPolicyAfter.setStartEnd(policyAfter.getStartEnd().trim());
			tempPolicyAfter.setFlightClass(policyAfter.getFlightClass().trim());
			tempPolicyAfter.setFlightClassExcept(policyAfter.getFlightClassExcept().trim());

			tempPolicyAfter.setFlightCode(policyAfter.getFlightCode());
			tempPolicyAfter.setFlightCodeExcept(policyAfter.getFlightCodeExcept());
			tempPolicyAfter.setStartEnd(policyAfter.getStartEnd());
			tempPolicyAfter.setStartEndExcept(policyAfter.getStartEndExcept());
			tempPolicyAfter.setFlightClass(policyAfter.getFlightClass());
			tempPolicyAfter.setFlightClassExcept(policyAfter.getFlightClassExcept());
			tempPolicyAfter.setDiscount(policyAfter.getDiscount());
			tempPolicyAfter.setRate(policyAfter.getRate());
			tempPolicyAfter.setTravelType(policyAfter.getTravelType());
			tempPolicyAfter.setTicketType(policyAfter.getTicketType());
			tempPolicyAfter.setQuota(policyAfter.getQuota());
			tempPolicyAfter.setBeginDate(policyAfter.getBeginDate());
			tempPolicyAfter.setEndDate(policyAfter.getEndDate());
			tempPolicyAfter.setTicketNum(policyAfter.getTicketNum());
			tempPolicyAfter.setMemo(policyAfter.getMemo());
			tempPolicyAfter.setUserName(uri.getUser().getUserName());
			tempPolicyAfter.setStatus(policyAfter.getStatus());
			tempPolicyAfter.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			AirlinePolicyAfter apa = new AirlinePolicyAfter();
			apa = airlinePolicyAfterBiz.getAirlinePolicyAfterById(policyAfter.getAirlinePolicyAfterId());
			tempPolicyAfter.setAirlinePolicyAfter(apa);
			policyAfterBiz.saveOrUpdate(tempPolicyAfter);
			inf.setMessage("成功增加后返政策信息！");
			inf.setForwardPage("/policy/policyAfterList.do?thisAction=listPolicyAfter&airlinePolicyAfterId="
					+policyAfter.getAirlinePolicyAfterId());
		} catch (Exception ex) {
			
			inf.setMessage("增加后返政策信息出错！错误信息：" + ex.getMessage());
			inf.setBack(true);
		}
		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	//修改操作
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		PolicyAfter policyAfter = (PolicyAfter) form;
		Inform inf = new Inform();
		if(!check(policyAfter)){
			inf.setMessage("修改后返政策信息出错！错误信息：字段格式出错");
			inf.setForwardPage("/policy/policyAfterList.do?thisAction=listPolicyAfter&airlinePolicyAfterId="
					+policyAfter.getAirlinePolicyAfterId());
			request.setAttribute("inf", inf);
			forwardPage = "inform";
			return (mapping.findForward(forwardPage));
		}
		if(!isCheckDate(policyAfter.getBeginDate(),policyAfter.getEndDate())){
			inf.setMessage("修改后返政策信息出错！错误信息：结束日期不能早于起始日期");
			inf.setForwardPage("/policy/policyAfterList.do?thisAction=listPolicyAfter&airlinePolicyAfterId="
					+policyAfter.getAirlinePolicyAfterId());
			request.setAttribute("inf", inf);
			forwardPage = "inform";
			return (mapping.findForward(forwardPage));
		}
		try {
			PolicyAfter tempPolicyAfter = new PolicyAfter();
			UserRightInfo uri = new UserRightInfo();
			uri = (UserRightInfo) request.getSession().getAttribute("URI");//session属性里的User的userName作为操作人

			tempPolicyAfter.setFlightCode(policyAfter.getFlightCode().trim());
			tempPolicyAfter.setFlightCodeExcept(policyAfter.getFlightCodeExcept().trim());
			tempPolicyAfter.setStartEnd(policyAfter.getStartEnd().trim());
			tempPolicyAfter.setFlightClass(policyAfter.getFlightClass().trim());
			tempPolicyAfter.setFlightClassExcept(policyAfter.getFlightClassExcept().trim());

			
			tempPolicyAfter.setFlightCode(policyAfter.getFlightCode());
			tempPolicyAfter.setFlightCodeExcept(policyAfter.getFlightCodeExcept());
			tempPolicyAfter.setStartEnd(policyAfter.getStartEnd());
			tempPolicyAfter.setStartEndExcept(policyAfter.getStartEndExcept());
			tempPolicyAfter.setFlightClass(policyAfter.getFlightClass());
			tempPolicyAfter.setFlightClassExcept(policyAfter.getFlightClassExcept());

			tempPolicyAfter.setDiscount(policyAfter.getDiscount());
			tempPolicyAfter.setRate(policyAfter.getRate());
			tempPolicyAfter.setTravelType(policyAfter.getTravelType());
			tempPolicyAfter.setTicketType(policyAfter.getTicketType());
			tempPolicyAfter.setQuota(policyAfter.getQuota());
			tempPolicyAfter.setBeginDate(policyAfter.getBeginDate());
			tempPolicyAfter.setEndDate(policyAfter.getEndDate());
			tempPolicyAfter.setTicketNum(policyAfter.getTicketNum());
			tempPolicyAfter.setUserName(uri.getUser().getUserName());
			tempPolicyAfter.setMemo(policyAfter.getMemo());
			tempPolicyAfter.setStatus(policyAfter.getStatus());
			tempPolicyAfter.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			AirlinePolicyAfter apa = new AirlinePolicyAfter();
			apa = airlinePolicyAfterBiz.getAirlinePolicyAfterById(policyAfter.getAirlinePolicyAfterId());
			tempPolicyAfter.setAirlinePolicyAfter(apa);
			tempPolicyAfter.setId(policyAfter.getId());
			inf.setMessage("成功修改后返政策信息！");
			policyAfterBiz.update(tempPolicyAfter);
			inf.setForwardPage("/policy/policyAfterList.do?thisAction=listPolicyAfter&airlinePolicyAfterId="
					+policyAfter.getAirlinePolicyAfterId());
			
		} catch (Exception ex) {
			inf.setMessage("修改后返政策信息出错！错误信息：" + ex.getMessage());
			inf.setBack(true);
		}
		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		PolicyAfter policyAfter = (PolicyAfter) form;
		long id = policyAfter.getId();
		if (id > 0) {
			PolicyAfter pa = (PolicyAfter) policyAfterBiz.getPolicyAfterById(id);
			pa.setThisAction("view");
			request.setAttribute("policyAfter", pa);
		}
		forwardPage = "viewPolicyAfter";
		return (mapping.findForward(forwardPage));
	}
	/**
	 * 检查各字段格式是否符合要求
	 * @param policyAfter
	 * @return
	 */
	private boolean check(PolicyAfter policyAfter){
		String flightCode = policyAfter.getFlightCode().trim();
		String flightClass = policyAfter.getFlightClass().trim();
		String flightCodeExcept = policyAfter.getFlightCodeExcept().trim();
		String flightClassExcept = policyAfter.getFlightClassExcept().trim();
		String startEnd = policyAfter.getStartEnd().trim();
		if(flightCode != null && !"".equals(flightCode)){
			if(!PolicyAfterUtil.isFlightCode(flightCode)){
				return false;
			}
		}
		if(flightCodeExcept != null && !"".equals(flightCodeExcept)){
			if(!PolicyAfterUtil.isFlightCode(flightCodeExcept)){
				return false;
			}
		}
		if(flightClass != null && !"".equals(flightClass)){
			if(!PolicyAfterUtil.isFlightClass(flightClass)){
				return false;
			}
		}
		if(flightClassExcept != null && !"".equals(flightClassExcept)){
			if(!PolicyAfterUtil.isFlightClass(flightClassExcept)){
				return false;
			}
		}
		if(startEnd != null && !"".equals(startEnd)){
			System.out.println(startEnd);
			if(!PolicyAfterUtil.isFlightPoint(startEnd)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 开始日期是否早于结束日期
	 * @param begin
	 * @param end
	 * @return
	 */
	private boolean isCheckDate(Timestamp begin,Timestamp end){
		if(begin == null || end == null){
			return true;
		}
		if(begin.compareTo(end) <= 0){
			return true;
		}
		return false;
	}
	
	//----------------------------set get-------------------------//

	public void setPolicyAfterBiz(PolicyAfterBiz policyAfterBiz) {
		this.policyAfterBiz = policyAfterBiz;
	}


	public void setAirlinePolicyAfterBiz(AirlinePolicyAfterBiz airlinePolicyAfterBiz) {
		this.airlinePolicyAfterBiz = airlinePolicyAfterBiz;
	}

	public ActionForward test(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	{
		String keyStorePath = "E:\\project\\fdpay\\common\\mykeystore";
		keyStorePath = "/opt/IBM/CertRoot/common/mykeystore";
		
		String keypass = "123456";
		String trustStorePath = "E:\\project\\fdpay\\common\\qmTrust";
		trustStorePath = "/opt/IBM/CertRoot/common/qmTrust";
		String trustpass = "changeit";
		String keyAlgorithm = "IBMX509";
		String trustAlgorithm = "IBMPKIX";
		String provider = "IBMJSSE";
		//keyAlgorithm = "SUNX509";
		//trustAlgorithm = "SUNX509";
		//provider = "SunJSSE";

		Protocol protocol = MyX509Certificate.getProtocol(keyStorePath, keypass,
		    trustStorePath, trustpass, keyAlgorithm, trustAlgorithm, provider);

		try
		{

			HashMap<String, String> params = new HashMap<String, String>();
			params.put("$RealyName$", "Lige");

			String strURL = "https://qm.qmpay.com/system/patternEmail.do";
			String str = MailUtil.sslSend("change password", "0002", "276628@qq.com",
			    params,protocol);

		//	String str = MailUtil.send("change password", "0002", "276628@qq.com",
		//	    params);
			
			
			System.out.println(str);
			Inform inf=new Inform();
			inf.setMessage(str);
			request.setAttribute("inf",inf);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			Inform inf=new Inform();
			inf.setMessage(ex.getMessage());
			request.setAttribute("inf",inf);
		}
		
		return mapping.findForward("inform");
	}
	

}