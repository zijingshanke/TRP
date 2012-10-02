<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>本票通业务</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		table{
			background-color: black;
		}
		tr{
			background-color: white;
		}
		input{
			width: 150px;
		}
	</style>
	<script type="text/javascript">
		function back(){
			window.history.back();
		}
		function check(formId){
			var form = document.getElementById(formId);
			var pnr = form.pnr.value;
			var bigpnr = form.bigPnr.value;
			var air = form.air.value;
			var b2buser = form.b2bUser.value;
			var b2bpwd = form.b2bPwd.value;
			if(pnr.length != 5){
				alert("请输入5位的PNR");
				return;
			}
			if(bigpnr==1 && air.length != 2){
				alert("请输入2位的航空公司代码");
				return;
			}
			if(b2buser.length < 1){
				alert("请输B2B入用户名");
				return;
			}
			if(b2bpwd.length < 1){
				alert("请输入B2B密码");
				return;
			}
			form.submit();
		}
	</script>
  </head>
  <body>
  	<br>
    	<a href="airticket/alidz.do?thisAction=isRunning">点击查看一本通是否正在运行</a>
    <br><br>
    <hr width="280" align="left" size="1" color="blue">
     <form id="queryPrice" action="airticket/alidz.do?thisAction=queryPriceByPnr" method="post">
   		<table  cellpadding="0" cellspacing="1px">
   			<tr><td colspan="2" align="center">查询本电政策和价格</td></tr>
   			<tr>
   				<td>请输入PNR:</td>
   				<td><input name="pnr" type="text"></td>
   			</tr>
   			<tr>
   				<td>请确定编码类型:</td>
   				<td>
   					<select name="bigPnr">
   						<option value="1">大编码</option>
   						<option value="0">小编码</option>
   					</select>
   				</td>
   			</tr>
   			<tr>
   				<td>请输入航空公司编码:</td>
   				<td><input name="air" type="text"></td>
   			</tr>
   			<tr>
   				<td>请输入b2b用户名:</td>
   				<td><input name="b2bUser" type="text"></td>
   			</tr>
   			<tr>
   				<td>请输入b2b密码:</td>
   				<td><input name="b2bPwd" type="password"></td>
   			</tr>
   			<tr>
   				<td colspan="2" align="center"><input type="button" value="确定" style="width:50px" onclick="check('queryPrice')"></td>
   			</tr>
   		</table>
    </form>
    <hr width="280" align="left" size="1" color="blue">
     <form id="queryOrder" action="airticket/alidz.do?thisAction=queryOrder" method="post">
   		<table cellpadding="0" cellspacing="1">
   			<tr><td colspan="2" align="center">查询本电订单状态</td></tr>
   			<tr>
   				<td>请输入PNR:</td>
   				<td><input name="pnr" type="text"></td>
   			</tr>
   			<tr>
   				<td>请确定编码类型:</td>
   				<td>
   					<select name="bigPnr">
   						<option value="1">大编码</option>
   						<option value="0">小编码</option>
   					</select>
   				</td>
   			</tr>
   			<tr>
   				<td>请输入航空公司编码:</td>
   				<td><input name="air" type="text"></td>
   			</tr>
   			<tr>
   				<td>请输入b2b用户名:</td>
   				<td><input name="b2bUser" type="text"></td>
   			</tr>
   			<tr>
   				<td>请输入b2b密码:</td>
   				<td><input name="b2bPwd" type="password"></td>
   			</tr>
   			<tr>
   				<td colspan="2" align="center"><input type="button" value="确定" style="width:50px" onclick="check('queryOrder')"></td>
   			</tr>
   		</table>
    </form>
     <hr width="280" align="left" size="1" color="blue">
     <form id="order" action="airticket/alidz.do?thisAction=order" method="post">
   		<table cellpadding="0" cellspacing="1">
   			<tr>
   				<td colspan="2" align="center">导入本电系统</td>
   			</tr>
   			<tr>
   				<td>请输入PNR:</td>
   				<td><input name="pnr" type="text"></td>
   			</tr>
   			<tr>
   				<td>请确定编码类型:</td>
   				<td>
   					<select name="bigPnr">
   						<option value="1">大编码</option>
   						<option value="0">小编码</option>
   					</select>
   				</td>
   			</tr>
   			<tr>
   				<td>请输入航空公司编码:</td>
   				<td><input name="air" type="text"></td>
   			</tr>
   			<tr>
   				<td>请输入b2b用户名:</td>
   				<td><input name="b2bUser" type="text"></td>
   			</tr>
   			<tr>
   				<td>请输入b2b密码:</td>
   				<td><input name="b2bPwd" type="password"></td>
   			</tr>
   			<tr>
	   			<td>请确定支付方式:</td>
	   			<td>
	   				<select name="autoPayFlag">
   						<option value="1">自动</option>
   						<option value="0">手动</option>
   					</select>
   				</td>
   			</tr>
   			<tr>
   				<td colspan="2" align="center"><input type="button" value="确定" style="width:50px" onclick="check('order')"></td>
   			</tr>
   		</table>
    </form>
  </body>
</html>
