﻿<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>泰申管理系统-票务管理-导航</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script>
		function showUL(ulId){
			var selectedUL=document.getElementById(ulId);
			if(selectedUL.style.display==""){
				selectedUL.style.display="none";
			}else{
				selectedUL.style.display="";
			}		
		}
	</script>
	</head>
	<body>
		<div id="mainContainer">
			<div class="fixedSideBar"></div>
			<div id="sideBar">
				<div class="sideBarItem webAdmin">
                    <c:check code="sb01-sb03">
					<span class="title"><a href="#" onclick="showUL('ulAddB2B')">B2B订单录入</a>
					</span>

					<ul id="ulAddB2B" class="contents" style="display: none">
						<c:check code="sb01">
							<li>
								<a href="addTradingOrder.jsp" target="mainFrame">B2B正常订单录入</a>
							</li>
						</c:check>
						<c:check code="sb02">
							<li>
								<a href="addRetireTradingOrder.jsp" target="mainFrame">B2B退废订单录入</a>
							</li>
						</c:check>
						<c:check code="sb03">
							<li>
								<a href="addWaitAgreeUmbuchenOrder.jsp" target="mainFrame">B2B改签订单录入</a>
							</li>
						</c:check>
					</ul>
					</c:check>
					<c:check code="sb06-sb09">
					<span class="title"><a href="#" onclick="showUL('ulAddB2C')">B2C订单录入</a>
					</span>
					<ul class="contents" id="ulAddB2C" style="display: none">
						<c:check code="sb06">
							<li>
								<a href="addB2CTradingOrder.jsp" target="mainFrame">B2C订单录入</a>
							</li>
						</c:check>
						<c:check code="sb07">
							<li>
								<a href="addRetireTradingOrder.jsp" target="mainFrame">B2C退废订单录入</a>
							</li>
						</c:check>
						<c:check code="sb08">
							<li>
								<a href="addWaitAgreeUmbuchenOrder.jsp" target="mainFrame">B2C改签订单录入</a>
							</li>
						</c:check>
						<c:check code="sb09">
							<li>
								<a
									href="listAirTicketOrder.do?thisAction=listWaitRecoveryTicketOrders"
									target="mainFrame">B2C应收账款</a>
							</li>
						</c:check>

					</ul>
					</c:check>
					<c:check code="sb11-sb12">
					<span class="title"><a href="#"
						onclick="showUL('ulAddTeam')">团队订单录入</a> </span>
					<ul class="contents" id="ulAddTeam" style="display: none">
						<c:check code="sb11">
							<li>
								<a href="addTeamTradingOrder.jsp" target="mainFrame">团队销售订单录入</a>
							</li>
						</c:check>
						<c:check code="sb12">
							<li>
								<a href="addTeamRefundTradingOrder_Team.jsp" target="mainFrame">团队退废订单录入</a>
							</li>

						</c:check>
					</ul>
					</c:check>
					<c:check code="sb16-sb17,sb30-sb49">
					<span class="title"><a href="#"
						onclick="showUL('ulEditNormal')">正常订单管理</a> </span>
					<ul class="contents" id="ulEditNormal" style="display: none">

	                        <c:check code="sb16-sb17">
							<li>
								<a href="listAirTicketOrder.do?thisAction=listNewAirTicketOrder"
									target="mainFrame">待处理新订单</a>
							</li>
		                    </c:check>
				            <c:check code="sb43-sb46">
							<li>
								<a href="listAirTicketOrder.do?thisAction=listWaitPayOrder"
									target="mainFrame">待确认支付订单</a>
							</li>
                            </c:check>
                            <c:check code="sb47-sb48">
							<li>
								<a href="listAirTicketOrder.do?thisAction=listWaitDrawOrder"
									target="mainFrame">等待出票订单</a>
							</li>
                            </c:check>
							<c:check code="sb30,sb49">
							<li>
								<a href="listAirTicketOrder.do?thisAction=listDrawSuccessOrder"
									target="mainFrame">完成出票订单</a>
							</li>
	                        </c:check>
							<c:check code="sb31">
							<li>
								<a href="listAirTicketOrder.do?thisAction=listConfirmRefundment"
									target="mainFrame">取消待退款订单</a>
							</li>
                            </c:check>
							<c:check code="sb32-sb33">
							<li>
								<a href="listAirTicketOrder.do?thisAction=listRefundmentEnd"
									target="mainFrame">取消已退款订单</a>
							</li>
                            </c:check>
					</ul>
					</c:check>
					<c:check code="sb51-sb55">
					<span class="title"><a href="#"
						onclick="showUL('ulEditRetire')">退废订单管理</a> </span>
					<ul class="contents" id="ulEditRetire" style="display: none">

						<c:check code="sb51">
							<li>
								<a
									href="listAirTicketOrder.do?thisAction=listWaitAgreeRetireOrder"
									target="mainFrame">待审核新订单</a>
							</li>
						</c:check>
						<c:check code="sb52-sb53">
							<li>
								<a
									href="listAirTicketOrder.do?thisAction=listConfirmRetireOrder"
									target="mainFrame">已审待退款订单</a>
							</li>
						</c:check>
						<c:check code="sb54-sb55">
							<li>
								<a
									href="listAirTicketOrder.do?thisAction=listSuccessRetireOrder"
									target="mainFrame">完成退款订单</a>
							</li>
						</c:check>
						
							<li>
								<a href="listAirTicketOrder.do?thisAction=listnoPassRetireOrder"
									target="mainFrame">审核不通过订单</a>
							</li>
						

					</ul>
					</c:check>
					<c:check code="sb61-sb68">
					<span class="title"><a href="#"
						onclick="showUL('ulEditUmbuchen')">改签订单管理</a> </span>
					<ul class="contents" id="ulEditUmbuchen" style="display: none">

						<c:check code="sb61-sb62">
							<li>
								<a
									href="listAirTicketOrder.do?thisAction=listWaitAgreeUmbuchenOrder"
									target="mainFrame">待审核新订单</a>
							</li>
						</c:check>
						<c:check code="sb63">
							<li>
								<a href="listAirTicketOrder.do?thisAction=listLoadUmbuchenOrder"
									target="mainFrame">已审待支付订单</a>
							</li>
						</c:check>
						<c:check code="sb64">
							<li>
								<a
									href="listAirTicketOrder.do?thisAction=listConfirmUmbuchenOrder"
									target="mainFrame">已付待确认订单</a>
							</li>
						</c:check>
						<c:check code="sb65-sb66">
							<li>
								<a
									href="listAirTicketOrder.do?thisAction=listSuccessUmbuchenOrder"
									target="mainFrame">完成改签订单</a>
							</li>
						</c:check>
						
							<li>
								<a
									href="listAirTicketOrder.do?thisAction=listnoPassUmbuchenOrder"
									target="mainFrame">改签不通过订单</a>
							</li>
						
					</ul>
					</c:check>
					<c:check code="sb71-sb79">
					<span class="title"><a href="#"
						onclick="showUL('ulEditTeam')">团队订单管理</a> </span>
					<ul class="contents" id="ulEditTeam" style="display: none">
						<c:check code="sb71,sb72">
							<li>
								<a
									href="listAirTicketOrder.do?thisAction=listTeamNewAirticketOrder"
									target="mainFrame">待处理新订单</a>
							</li>
						</c:check>
						<c:check code="sb73">
							<li>
								<a
									href="listAirTicketOrder.do?thisAction=listTeamForpayAirticketOrder"
									target="mainFrame">待申请支付订单</a>

							</li>
						</c:check>
						<c:check code="sb73">
							<li>
								<a
									href="listAirTicketOrder.do?thisAction=listTeamAppAirticketOrder"
									target="mainFrame">待确认支付订单</a>
							</li>
						</c:check>
						<c:check code="sb74">
							<li>
								<a
									href="listAirTicketOrder.do?thisAction=listTeamWaitOutAirticketOrder"
									target="mainFrame">等待出票订单</a>
							</li>
						</c:check>
						<c:check code="sb75,sb76">
							<li>
								<a
									href="listAirTicketOrder.do?thisAction=listTeamOverAirticketOrder"
									target="mainFrame">完成出票订单</a>
							</li>
						</c:check>
						<c:check code="sb77">
						<li>
							<a
								href="listAirTicketOrder.do?thisAction=listTeamNewRefundAirticketOrde"
								target="mainFrame">待审核退废订单</a>
						</li>
						</c:check>
						<c:check code="sb78">
						<li>
							<a
								href="listAirTicketOrder.do?thisAction=listTeamWaitRefundAirticketOrder"
								target="mainFrame">已审待退款订单</a>
						</li>
						</c:check>
						<c:check code="sb79">
						<li>
							<a
								href="listAirTicketOrder.do?thisAction=listTeamOverRefundAirticketOrder"
								target="mainFrame">完成退款订单</a>
						</li>
						</c:check>
					</ul>
					</c:check>
					<c:check code="sb81,sb82,sb85,sb86">
					<span class="title"><a href="#"
						onclick="showUL('ulEditComposite')">综合订单管理</a> </span>
					<ul class="contents" id="ulEditComposite" style="display: none">
						<c:check code="sb81,sb82">
						<li>
							<a href="listAirTicketOrder.do?thisAction=list"
								target="mainFrame">散票订单管理</a>
						</li>
						</c:check>
						<c:check code="sb85,sb86">
							<li>
								<a
									href="listAirTicketOrder.do?thisAction=getTempAirticketOrderByticketType"
									target="mainFrame">团队订单管理</a>
							</li>
						</c:check>
					</ul>
					</c:check>
				</div>
			</div>
			<div class="closeSiseBar">
				<span class="btn"></span>
			</div>
		</div>
	</body>
</html>