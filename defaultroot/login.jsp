<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>欢迎登录泰申管理系统</title>
	<link href="./_css/newLogin.css" rel="stylesheet" type="text/css" />
	<link href="../_css/newLogin.css" rel="stylesheet" type="text/css" />
	<script src="_js/common.js" type="text/javascript"></script>
	<script language="JavaScript">
		if (self!=top){
		   top.location=self.location;
		 }	
			
		 function submitForm(){
		   document.forms[0].submit();    
		 }
		 
		 function  resetForm(){
		   document.forms[0].userNo.value="";
		   document.forms[0].userPassword.value="";
		   document.forms[0].rand.value="";   
		 }

	</script>
	</head>
	<body onload="document.forms[0].userNo.focus();">
    <form id="uf"
			action="<%=request.getContextPath()%>/user/user.do?thisAction=login"
			method="post">
      <div id="div05">
        <div id="divTitle"> 泰申综合业务管理系统</div>
        <div id="divUser"> <span>登录账号：</span>
          <div class="bg0">
            <div class="spanUser"></div>
            <input name="userNo" />
          </div>
        </div>
        <div id="divPassword"> <span>登录密码：</span>
          <div class="bg0">
            <div class="spanPassworld"></div>
            <input name="userPassword" type="password" value="" />
          </div>
        </div>
        <div id="divCode"> <span>&nbsp;&nbsp;&nbsp;验证码：</span>
          <input name="rand" title="请输入右侧验证码"  maxlength="4" class="colorblue" onkeydown="if(event.keyCode==13){submitForm();}" />
          &nbsp;
          <html:img page="/servlet/com.neza.base.NumberImage" align="absmiddle"
                height="21" width="64" />
        </div>
        <div id="divSubmit">
          <input name="label" type="button" class="btnLogin" onclick="submitForm();" />
          <input name="label" type="button" class="btnReset" onclick="resetForm();" />
          <div id="divMsg">
            <c:if test="${err eq 'randError'}">验证码错误!</c:if>
            <c:if test="${err eq 'passError'}">登录密码错误！</c:if>
            <c:if test="${err eq 'nameError'}">登录账号错误！</c:if>
            <c:if test="${err eq 'statusError'}">您的账号已经被停用！请联系管理员！</c:if>
          </div>
        </div>
      </div>
    </form>
    <script type="text/javascript" src="http://js.tongji.linezing.com/2155048/tongji.js"></script><noscript><a href="http://www.linezing.com"><img src="http://img.tongji.linezing.com/2155048/tongji.gif"/></a></noscript>
</body>
</html>
