<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
if (loginMember==null){
%>
<a href="login_form.jsp">로그인</a>
<%	
}else{
%>
<%=loginMember.getMlid() +"(" + loginMember.getMlname() +")" %>님 환영합니다.<br/>
현재 <%=loginMember.getMlpoint() %>p 보유중 입니다.<br/>
<a href="logout">로그아웃</a>
<%	
}
%>
<hr/>
<a href="brd_list.free">자유 게시판</a><br/>
<a href="brd_form.free?wtype=in">자유 게시판 글 등록</a>
</body>
</html>