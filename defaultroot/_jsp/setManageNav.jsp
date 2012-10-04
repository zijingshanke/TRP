<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<c:choose>
	<c:when test="${param.orderType==91}">
		<c:set var="title" value="正常订单管理" />
		<c:choose>
			<c:when test="${param.moreStatus=='1'}">
				<c:set var="subtitle" value="待处理新订单" />
				<script>setSelectedMenu("ulEditNormal",1);</script>
			</c:when>
			<c:when test="${param.moreStatus=='2,7,8'}">
				<c:set var="subtitle" value="待确认支付订单" />
				<script>setSelectedMenu("ulEditNormal",2);</script>
			</c:when>
			<c:when test="${param.moreStatus=='3'}">
				<c:set var="subtitle" value="等待出票订单" />
				<script>setSelectedMenu("ulEditNormal",3);</script>
			</c:when>
			<c:when test="${param.moreStatus=='5'}">
				<c:set var="subtitle" value="完成出票订单" />
				<script>setSelectedMenu("ulEditNormal",4);</script>
			</c:when>
			<c:when test="${param.moreStatus=='4'}">
				<c:set var="subtitle" value="取消待退款订单" />
				<script>setSelectedMenu("ulEditNormal",5);</script>
			</c:when>
			<c:when test="${param.moreStatus=='6'}">
				<c:set var="subtitle" value="取消已退款订单" />
				<script>setSelectedMenu("ulEditNormal",7);</script>
			</c:when>
			<c:otherwise>
				<c:set var="subtitle" value="正常订单管理" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${param.orderType==92}">
		<c:set var="title" value="改签订单管理" />
		<c:choose>
			<c:when test="${param.moreStatus=='39,46'}">
				<c:set var="subtitle" value="待审核新订单" />
				<script>setSelectedMenu("ulEditUmbuchen",1);</script>
			</c:when>
			<c:when test="${param.moreStatus=='40,41,42'}">
				<c:set var="subtitle" value="已审待支付订单" />
				<script>setSelectedMenu("ulEditUmbuchen",2);</script>
			</c:when>
			<c:when test="${param.moreStatus=='43'}">
				<c:set var="subtitle" value="已付待确认订单" />
				<script>setSelectedMenu("ulEditUmbuchen",3);</script>
			</c:when>
			<c:when test="${param.moreStatus=='45'}">
				<c:set var="subtitle" value="完成改签订单" />
				<script>setSelectedMenu("ulEditUmbuchen",4);</script>
			</c:when>
			<c:when test="${param.moreStatus=='44'}">
				<c:set var="subtitle" value="改签不通过订单" />
				<script>setSelectedMenu("ulEditUmbuchen",5);</script>
			</c:when>
			<c:otherwise>
				<c:set var="subtitle" value="改签订单管理" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${param.orderType==93}">
		<c:set var="title" value="退废订单管理" />
		<c:choose>
			<c:when test="${param.moreStatus=='19,29,20,30,24,25,34,35'}">
				<c:set var="subtitle" value="待审核新订单" />
				<script>setSelectedMenu("ulEditRetire",1);</script>
			</c:when>
			<c:when test="${param.moreStatus=='21,31'}">
				<c:set var="subtitle" value="已审待退款订单" />
				<script>setSelectedMenu("ulEditRetire",2);</script>
			</c:when>
			<c:when test="${param.moreStatus=='22,32'}">
				<c:set var="subtitle" value="完成退款订单" />
				<script>setSelectedMenu("ulEditRetire",3);</script>
			</c:when>
			<c:when test="${param.moreStatus=='23,33'}">
				<c:set var="subtitle" value="审核不通过订单" />
				<script>setSelectedMenu("ulEditRetire",4);</script>
			</c:when>
			<c:otherwise>
				<c:set var="subtitle" value="退废订单管理" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>
<c:import url="../_jsp/mainTitle.jsp" charEncoding="UTF-8" >
		            <c:param name="title1" value="票务管理"/>
		            <c:param name="title2" value="${title}"/>
		            <c:param name="title3" value="${subtitle}"/>
</c:import>
