<%if(session.getAttribute("user") == null){response.sendRedirect("/CSMS/login.jsp");return;}%>
<%@ page language="java" import="java.util.*,JZW.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<!-- 头部 -->
<%@include file="/CommonView/head.jsp" %>

</head>

<body>
<div id="wrapper"><!-- WRAPPER -->
<!-- 导航栏 -->
<%@include file="/CommonView/navbar.jsp" %>
<!-- 左侧边栏 -->
<%@include file="/CommonView/messageLeftSidebar.jsp" %>

<!-- 内容区域 -->
<div class="main">
<!-- MAIN CONTENT -->
<div class="main-content">

<!-- INFO TIP -->
<%@include file="/CommonView/infoTip.jsp" %>
<!-- END INFO TIP -->

	<%
		Message mes = new Message();
		
		int recordCount = mes.queryByCondition(0, 1, 0, user.getID(), "").size();   	//记录总数
		int pageSize = request.getParameter("selectPages")==null ? 10 : Integer.parseInt(request.getParameter("selectPages")); //每页记录数
		int start=1;           					//显示开始页
		int end=10;            					//显示结束页
		int pageCount = recordCount%pageSize==0 ? (recordCount/pageSize==0?1:recordCount/pageSize) : recordCount/pageSize+1;	//计算总页数
		int Page = request.getParameter("page")==null ? 1 : Integer.parseInt(request.getParameter("page"));		//获取当前页面的页码
		
		Page = Page>pageCount ? pageCount : Page;		//页码大于最大页码的情况
		Page = Page<1 ? 1 : Page;						//页码小于1的情况
		
		List<Message> historyMesList = mes.cutPageData(Page, pageSize, 0, 1, 0, user.getID(), "");
		request.setAttribute("historyMesList", historyMesList);
		request.setAttribute("startFloor", (Page-1)*pageSize);
     %>
	<div class="container-fluid">
		<div class="row">
		
			<div class="col-md-12">
				<div class="panel">
			        <div class="panel-heading" >
			            <h3 class="panel-title">${lan.equals("en")?"History Message":"已读消息"}</h3>
			            <div class="right">
			                <a href="sendMessage.jsp"><span class="label label-primary"><i class="fa fa-comments"></i>&nbsp;${lan.equals("en")?"Send Message":"发送消息"}</span></a>
			            </div>
			        </div>
					<div class="panel-body" style="text-align:center;font-size:24px">
						${lan.equals("en")?"Here are all the messages you've read":"这里是你所有的已读消息"}
					</div>
				</div>
			</div>
	
		
			<c:forEach var="mes" items="${historyMesList}" varStatus="floor">
			<div class="col-md-12">
				<div class="panel">
					<div class="panel-heading">
			        	<h3 class="panel-title">${lan.equals("en")?"Summary":"概述"}:${mes.getSummary() }</h3>
						<div class="text-center">
			                <h3 style="margin-top:-20px;" class="panel-title">${lan.equals("en")?"Sender":"发送者"}:${mes.getSender() }</h3>
			            </div>
						<div class="right">
							<a href="deleteMessageDo.jsp?id=${mes.getID() }" onclick="return confirm('${lan.equals('en')?'Are you sure you want to delete this message?':'确定要删除这条消息吗？'}');">
							<span class="label label-danger"><i class="fa fa-window-close"></i>&nbsp;${lan.equals("en")?"Delete Message":"删除消息"}</span></a>
							<button type="button" class="btn-toggle-collapse"><i class="lnr lnr-chevron-up"></i></button>
							<button type="button" class="btn-remove"><i class="lnr lnr-cross"></i></button>
						</div>
					</div>
					<div class="panel-body">
						<p style="text-indent: 2em;">${mes.getContent() }</p>
					</div>
					<div class="panel-footer">
						<span>#${floor.count+startFloor}</span>
						<span style="margin-left:50px;">${lan.equals("en")?"Identifier":"编号"}:${mes.getIden() }</span>
						<div class="text-center" style="margin-top:-27px;">
							<a type="button" href="markUnreadMessageDo.jsp?id=${mes.getID() }" class="btn btn-info">${lan.equals("en")?"Mark as Unread":"标为未读"}</a>
			            </div>
						<div class="text-right" style="margin-top:-27px;">
			            	<span>${lan.equals("en")?"Sending time":"发送时间"}:${mes.getCreated() }</span>
			            </div>
					</div>
				</div>
			</div>
			</c:forEach>
			<c:if test="${historyMesList.size()==0 }">
				<div class="col-md-12">
					<div class="panel">
						<div class="panel-heading">
							<div class="right">
								<button type="button" class="btn-toggle-collapse"><i class="lnr lnr-chevron-up"></i></button>
								<button type="button" class="btn-remove"><i class="lnr lnr-cross"></i></button>
							</div>
						</div>
						<div class="panel-body">
							<p style="text-align:center;font-size:20px">${lan.equals("en")?"You have no message that you have read!!!":"你没有已读消息！！！"}</p>
						</div>
					</div>
				</div>
			</c:if>
			
		</div>
		
	<!-- 选择页码 -->
	<%@include file="/CommonView/selectPages.jsp" %>
	<!-- 分页 -->
	<%@include file="/CommonView/pagination.jsp" %>
	
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

</body>
</html>