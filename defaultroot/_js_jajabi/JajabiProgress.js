
//source为发起会话人的++比号；destination为接收会话者的++比号；
function JJBTalkOnline(source, destination) {

    var obj = document.getElementById('bbjObject');
    if (obj == null) {
        var bbjObjectStr = '<object id="bbjObject" name="bbjObject"  classid="clsid:6CE9F6A0-3900-479E-B35F-15D37D5B6275"  width="160px;" height="20px;" codebase="http://fdays4.cncsz.net:8080/OcxAutoUpdate/++bTalkOcx_1.0.cab"></object>';
        document.body.innerHTML += bbjObjectStr;
        obj = document.getElementById('bbjObject');
    }
    try {
        //标题彼此会话时将显示为名称
        obj.SetTalkInfo(source, destination, "你正在与" + destination + "聊天——来自兑换商城", "");
    }
    catch (ex) {
        var msg = '兑换商城提示您：您尚未下载最新版++比即时聊天组件，请<a href="http://server.bbjbbj.com:8080/OcxAutoUpdate/++bTalkOcx_1.0.exe"><b style="color:Blue">下载</b></a>组件后再点击。';
        ymPrompt.alert({
            message: msg,
            width: 280,
            height: 140,
            titleBar: false
        });
    }
}