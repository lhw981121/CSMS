<%if(session.getAttribute("user") == null){response.sendRedirect("/CSMS/login.jsp");return;}%>
<%@ page language="java" import="java.util.*,JZW.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<!-- 头部 -->
<%@include file="/CommonView/head.jsp" %>

<style>
form input {
    appearance: none;
    outline: 0;
    border: 1px solid rgba(255, 255, 255, 0.4);
    background-color: rgba(255, 255, 255, 0.2);
    border-radius: 3px;
    padding: 10px 15px;
    margin: 0 auto 10px auto;
    display: block;
    text-align: center;
    -webkit-transition-duration: 0.25s;
    transition-duration: 0.25s;
}
form input:hover {
    background-color: rgba(255, 255, 255, 0.4);
}
form input:focus {
    background-color: white;
    width: 450px;
    color: #2B3743;
}
</style>

<!-- 表单验证 -->
<script>
	function checkAll(){
		var user_phone = checkUser_phone();
		var user_phoneCode = checkPhoneCode();
		if(user_phone&&user_phoneCode){
			return true;
		}else{  
			return false;  
		}
	} 
</script>

<!-- 手机号验证JS -->
<script src="/CSMS/ValidateJS/User/user_phone.js"></script>

</head>

<body>
<div id="wrapper"><!-- WRAPPER -->
<!-- 导航栏 -->
<%@include file="/CommonView/navbar.jsp" %>
<!-- 左侧边栏 -->
<%@include file="/CommonView/userLeftSidebar.jsp" %>

<!-- 内容区域 -->
<div class="main">
<!-- MAIN CONTENT -->
<div class="main-content">
<%
String user_phone = request.getParameter("user_phone")==null?"":request.getParameter("user_phone");
String sendCode = request.getParameter("sendCode")==null?"":request.getParameter("sendCode");
String phoneCode="";
int result = 0;
if(sendCode.length()!=0){
	if(user.isExist_phone(user_phone)){
		result=2;
	}else{
		phoneCode = User.getPhoneCode();
		session.setAttribute("phonecode",phoneCode);
		if(user.sendPhoneCode(user_phone, phoneCode))	result=1;
	}
}
%>
<div class="container">
	<div class="col-md-8 col-md-offset-2" style="padding: 100px 70px 0 70px;">
		<div class="panel panel-default" style="text-align:center; vertical-align:middel;">
			<div class="panel-heading">
           		<h3 class="panel-title">手机号绑定</h3>
        	</div>
			<div class="panel-body">
				<form class="form-horizontal" role="form" method="post" action="validatePhone.jsp?operation=bindPhone&id=<%=user.getID() %>" onsubmit="return checkAll()">
					<br>
					<div class="form-group" id="user_phone_class">
						<%if(user.getPhone()!=null&&user.getPhone().length()!=0){%>
							<h3>您已绑定手机号&ensp;<%=user.getPhoneHide() %></h3>
						<%}else{ %>
							<label for="user_phone" class="control-label"><a class="text-danger"></a>手&ensp;机&ensp;号</label><hr>
							<input type="text" class="form-control" id="user_phone" name="user_phone" 
							placeholder="请输入手机号" value="<%=user_phone %>" autocomplete="off" 
							oninput="Inputing(document.getElementById('user_phone_span'),document.getElementById('user_phone_class'))">
							<span id="user_phone_span"></span>
						<%} %>
					</div>
					
					<%if(user.getPhone().length()==0){%>
					<br>	
					<div class="form-group" id="phonecode_class">
						<label for="phonecode" class="col-sm-2 control-label"><a class="text-danger"></a>验证码</label>
						<div class="col-md-10 col-sm-10 col-lg-10">
							<div class="input-group">
								<input type="text" class="form-control" id="phonecode" name="phonecode" disabled="disabled" onkeypress="if(event.keyCode==13) {phoneBtn.click();return false;}"
								placeholder="填写手机短信验证码" value="" autocomplete="off"
								oninput="Inputing(document.getElementById('phonecode_span'),document.getElementById('phonecode_class'))">
								<span class="input-group-btn"><button type="button" class="btn btn-primary" id="phonecodeBtn">免费获取验证码</button></span>
							</div>
							<span id="phonecode_span"></span>
						</div>
					</div>
					<%} %>
					
					<br>
					<div class="form-group">
						<%if(user.getPhone()!=null&&user.getPhone().length()!=0){%>
						<a href="unbindPhone.jsp" title="转到手机号解绑页面" type="button" class="btn btn-primary">手机号解绑</a>
						<%}else{ %>
						<button type="submit" id="phoneBtn" title="绑定手机号" class="btn btn-primary">绑定手机号</button>
						<%} %>
					</div>
					
				</form>
           	</div>
		</div>
	</div>
</div>

</div>
<!-- END MAIN CONTENT -->
</div>
<!-- END 内容区域 -->

<!-- 页尾 -->
<%@include file="/CommonView/foot.jsp" %>
</div><!-- END WRAPPER -->
<!-- Javascript -->
<%@include file="/CommonView/javaScript.jsp" %>
<script>
//获取验证码倒计时（邮箱）
var countdown = 120;
<%if(sendCode.length()!=0){%>
	window.history.pushState(null,"","bindPhone.jsp")
	var obj = $("#phonecodeBtn");
	document.getElementById("phonecode_class").className = "form-group";
	<%if(result==1){%>  
		document.getElementById("phonecode_span").innerHTML = "<label class=\"control-label text-success left\" for=\"phonecode\">验证码已成功发送到您的手机！如长时间未收到请重新发送。</label>";  
		$("#phonecode").attr('disabled', false);
		settime(obj);
	<%}else if(result==0){%>
		document.getElementById("phonecode_span").innerHTML = "<label class=\"control-label text-danger left\" for=\"phonecode\">验证码发送失败！原因可能是发送过于频繁或次数超限。</label>";  
		settime(obj);
	<%}else if(result==2){%>
		var field = document.getElementById("user_phone").value;
    	var spanNode = document.getElementById("user_phone_span");
        spanNode.innerHTML = "<label class=\"control-label text-danger\" for=\"user_phone\">该手机号已被绑定！</label>";  
        document.getElementById("user_phone_class").className = "form-group has-error";
	<%}%>
<%}%>
$('#phonecodeBtn').on('click',function(){
	var user_phone = checkUser_phone();
	if(user_phone==false)	return;
	var phone = $("#user_phone").val();
	window.location.href="?sendCode=1&user_phone="+phone;
})
function settime(obj) { //发送验证码倒计时
if (countdown == 0) {
	obj.attr('disabled', false);
	//obj.removeattr("disabled");
	obj.html("免费获取验证码");
	countdown = 120;
	return;
} else {
	obj.attr('disabled', true);
	obj.html("重新发送(" + countdown + ")");
	countdown--;
}
setTimeout(function () {settime(obj)}, 1000)
}
</script>

<!-- 手机验证码验证JS -->
<script>
function checkPhoneCode(){
    var field = document.getElementById("phonecode").value;
    var spanNode = document.getElementById("phonecode_span");
	if(field.length == 0){
        spanNode.innerHTML = "<label class=\"control-label text-danger\" for=\"phonecode\">验证码不能为空！</label>";  
        document.getElementById("phonecode_class").className = "form-group has-error";    
        return false;
	}else{
		var phonecode = '${phonecode}';
		if(phonecode!=null&&field!=phonecode){
			spanNode.innerHTML = "<label class=\"control-label text-danger\" for=\"phonecode\">验证码错误！</label>";  
	        document.getElementById("phonecode_class").className = "form-group has-error";   
	        return false;
		}else{
			spanNode.innerHTML = "";
	        document.getElementById("phonecode_class").className = "form-group";   
	        return true;
		}
	}
}
</script>

</body>
</html>
