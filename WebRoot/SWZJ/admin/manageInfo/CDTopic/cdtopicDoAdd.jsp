<%if(session.getAttribute("user") == null){response.sendRedirect("/CSMS/login.jsp");return;}%>
<%@ page language="java" import="java.util.*,JZW.*,java.util.Date" pageEncoding="utf-8"%>

<%
	CDTopic cdt = new CDTopic();
    //获取上一个页面传递过来的数据
	request.setCharacterEncoding("UTF-8");
    String cdtopic_number = request.getParameter("cdtopic_number");
    if(cdt.isExist_number(cdtopic_number)){
    	out.print("<script>alert(\"新增课题信息失败！\\n编号 "+cdtopic_number+" 已存在！\");window.history.back(-1);</script>");
    }else{
	    String cdtopic_name = request.getParameter("cdtopic_name");
	    String cdtopic_keyword = request.getParameter("cdtopic_keyword");
	    String cdtopic_technology = request.getParameter("cdtopic_technology");
	    Integer teacher_id = Integer.valueOf(request.getParameter("teacher_id"));
	    Date created_at = new Date();
		
		if(cdt.createCDTopic(cdtopic_number, cdtopic_name, cdtopic_keyword, cdtopic_technology, teacher_id, created_at)){
			new Teacher().refreshCDTopicCountByID(teacher_id);//刷新教师拥有的课题总数
			session.setAttribute("message", cdtopic_name+" 的信息添加成功！");
		}else{
			session.setAttribute("message", "遇到未知错误！可能是服务器正在维护或者其他未知原因！ "+cdtopic_name+" 的信息添加失败！");
		}
		out.print("<script>window.location.href = \"/CSMS/SWZJ/admin/manageInfo/CDTopic/cdtopicInfo.jsp\";</script>");
    }
%>
