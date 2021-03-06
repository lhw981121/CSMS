<%if(session.getAttribute("user") == null){response.sendRedirect("/CSMS/login.jsp");return;}%>
<%@ page language="java" import="java.util.*,JZW.*,java.net.URLDecoder" pageEncoding="utf-8"%>

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
<%@include file="/CommonView/adminLeftSidebar.jsp" %>

<!-- 内容区域 -->
<div class="main">
<!-- MAIN CONTENT -->
<div class="main-content">

<!-- INFO TIP -->
<%@include file="/CommonView/infoTip.jsp" %>
<!-- END INFO TIP -->

    <div class="panel">
        <div class="panel-heading" >
            <h3 class="panel-title">教师信息管理</h3>
            <div class="right">
                <a href="/CSMS/SWZJ/admin/manageInfo/Teacher/teacherAdd.jsp"><span class="label label-primary"><i class="fa fa-plus-square"></i>&nbsp;新增教师</span></a>
            </div>
        </div>
		<div class="panel-heading">
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-2 col-sm-2 col-lg-2">
						<h3 class="panel-title">信息查询</h3>
					</div>
					<% String teacher_position = request.getParameter("teacher_position")== null ? "" : request.getParameter("teacher_position"); //教师职称 %>
					<div class="col-md-5 col-sm-5 col-lg-5">
				        <form class="form-inline" id="searchPosition" role="form" method="get" action="">
				            <div class="form-group right">
				            	<span>职称查询:</span>
				                <select title="选择职称" id="teacher_position" name="teacher_position" class="form-control field">
				                	<option value = "">不限职称</option>
			                        <option value = "助教">助教</option>
			                        <option value = "讲师">讲师</option>
			                        <option value = "副教授">副教授</option>
			                        <option value = "教授">教授</option>
			                        <option value = "博士生导师">博士生导师</option>
				                </select>
				                <span class="form-group-btn"><a onclick="searchPosition()" class="btn btn-primary">查询</a></span>
				            </div>
				            <script type="text/javascript">
								function searchPosition() {
									document.getElementById("searchPosition").submit();
								}
							</script>
				        </form>
				    </div>
				    <% String queryStr = request.getParameter("queryStr")== null ? "" : request.getParameter("queryStr"); //搜索字段 %>
			        <div class="col-md-5 col-sm-5 col-lg-5">
						<form role="form" class="form-horizontal" method="get" id="searchTeacher" action="">
							<div class="input-group">
								<input class="form-control" name="queryStr" type="text" id="queryStr" value="<%=queryStr%>" placeholder="教师编号、姓名或关键字">
								<input type="hidden" name="teacher_position" value="<%=request.getParameter("teacher_position")== null ? "" : request.getParameter("teacher_position")%>">
								<input type="hidden" name="selectPages" value="<%=request.getParameter("selectPages")==null ? 10 : Integer.parseInt(request.getParameter("selectPages"))%>">
								<span class="input-group-btn"><a onclick="return searchTeacher()" class="btn btn-primary">搜索</a></span>
							</div>
							<script type="text/javascript">
								function searchTeacher() {
									if(document.getElementById("queryStr").value.length != 0){
										document.getElementById("searchTeacher").submit();
										return true;
									}else{
										return false;
									}
								}
							</script>
						</form>
					</div>
				</div>
			</div>
		</div>
        
		<div class="panel-body">
			<table class="table table-hover">
				<thead>
					<tr>
					<th>操作管理</th>
					<th>编号</th>
					<th>姓名</th>
					<th>职称</th>
					<th>拥有课题数量</th>
					<!-- <th>添加时间</th>
					<th>修改时间</th> -->
					</tr>
				</thead>
				<tbody>
				<%
					Teacher tea=new Teacher();
					List<Teacher> cutList = tea.queryByCondition(0,queryStr);
					Iterator<Teacher> teaList = cutList.iterator();
					if(teacher_position.length()!=0){
						while(teaList.hasNext()){
							Teacher t = teaList.next();
							if(!teacher_position.equals(t.getPosition()))	teaList.remove();
						}
					}
					int recordCount = cutList.size();
					
					/* tea.refreshCDTopicCountOfAll();//刷新所有教师的拥有课题数量 */
					/* int recordCount = tea.queryByCondition(0,queryStr).size();   		//记录总数 */
					int pageSize = request.getParameter("selectPages")==null ? 10 : Integer.parseInt(request.getParameter("selectPages")); //每页记录数
					int start=1;           					//显示开始页
					int end=10;            					//显示结束页
					int pageCount = recordCount%pageSize==0 ? (recordCount/pageSize==0?1:recordCount/pageSize) : recordCount/pageSize+1;	//计算总页数
					int Page = request.getParameter("page")==null ? 1 : Integer.parseInt(request.getParameter("page"));		//获取当前页面的页码
					
					Page = Page>pageCount ? pageCount : Page;		//页码大于最大页码的情况
					Page = Page<1 ? 1 : Page;						//页码小于1的情况

					/* List<Teacher> cutList = tea.cutPageData(Page,pageSize,0,queryStr); */
					
					int count = recordCount;
		        	int head = Page*pageSize-pageSize;
		        	int foot;
		        	if(head == (count/pageSize)*pageSize) {
		        		foot = head+count%pageSize;
		        		cutList =  cutList.subList(head, foot);
		        	}else {
		        		foot = head+pageSize;
		        		cutList =  cutList.subList(head, foot);
		        	}
					for(Teacher teacher:cutList) {
						out.print("<tr>");
						out.print("<td>");
						out.print("<a href=\"/CSMS/SWZJ/admin/manageInfo/Teacher/teacherDetail.jsp?id="+teacher.getID()+"\">详情</a>&ensp;");
						out.print("<a href=\"/CSMS/SWZJ/admin/manageInfo/Teacher/teacherAmend.jsp?id="+teacher.getID()+"\">修改</a>&ensp;");
						out.print("<a href=\"/CSMS/SWZJ/admin/manageInfo/Teacher/teacherDoDelete.jsp?id="+teacher.getID()+"\" onclick=\"if (confirm('确定要删除这条教师信息吗？') == false) return false;\">删除</a>");
						out.print("</td>");
						out.print("<td>"+teacher.getNum()+"</td>");
						out.print("<td>"+teacher.getName()+"</td>");
						out.print("<td>"+teacher.getPosition()+"</td>");
						out.print("<td>"+teacher.getCDTcount()+"</td>");
						/* out.print("<td>"+teacher.getCreated()+"</td>");
						out.print("<td>"+teacher.getUpdated()+"</td>"); */
					}
				%>
				</tbody>
			</table>
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
<!-- 选中职称 -->
<script>
	$("#teacher_position option").each(function() {
        if($(this).val()=='<%= teacher_position %>'){
        	$(this).prop('selected',true);
       	}
    });
</script>
<!-- END 选中职称 -->


</body>
</html>
