<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>泰申管理系统-票务政策管理</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
        var si;
        var res = "";
        function tt() {
            var obj = document.getElementById("myobj");
			//获取结果
            res = obj.GetResult();
            //alert(res);
            if (res != "" && res != undefined) {
                var div = document.getElementById("result");
                div.innerHTML = res;
                alert(res);
                window.clearInterval(si);
            }
        }

        function getPolicyByFlight(carrier,flight_code,flight_class,start_point,end_point,boarding_time) {
            var obj = document.getElementById("myobj");
               
			//传条件参数
			//"<tsms><service>getPolicyByFlight</service ><carrier>CA</carrier>" +
			//"<flight_code>CA1234</flight_code><flight_class>Y</flight_class ><start_point>zuh</start_point>" +
			//"<end_point>pek</end_point><boarding_time>20100916</boarding_time></tsms>"

            obj.SetQueryParameter("<tsms><service>getPolicyByFlight</service ><carrier>"+carrier+"</carrier>" +
				"<flight_code>"+flight_code+"</flight_code><flight_class>"+flight_class+"</flight_class ><start_point>"+
				start_point+"</start_point>" +"<end_point>"+end_point+"</end_point><boarding_time>"+
				boarding_time+"</boarding_time></tsms>");
				
            si = setInterval("tt()", 5000);
        }
    </script>
	</head>
	<body>
		<div id="mainContainer">
			<input type="button" value="获取最高返点"
				onclick="getPolicyByFlight('CA','CA1480','Y','ZUH','PEK','20100916');" />
			结果：
			<span id="result"> </span>

			<object id="myobj"
				classid="clsid:F71856C2-1268-4dc7-9F16-AAD193A27DE2" width="1200"
				height="700" codebase="Activex.cab">
			</object>
		</div>
	</body>
</html>
