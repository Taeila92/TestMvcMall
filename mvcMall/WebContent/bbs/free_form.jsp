<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
// 로그인 되어 있을 경우 로그인 정보(현재 로그인한 회원의 정보)를 받아옴
request.setCharacterEncoding("utf-8");
String wtype = request.getParameter("wtype");
String msg = "수정", writer = "", title = "", content = "";
if (wtype.equals("in")){
	msg = "등록";
}else if (wtype.equals("up")){
	FreeInfo article = (FreeInfo)request.getAttribute("article");
	out.println(article);
/*	writer = article.getFl_writer();
	title = article.getFl_title();
	content = article.getFl_content(); */
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>자유 게시판 글<%=msg %> 폼</h2>
<form name="frmFree" action="brd_proc.free" method="post">
<input type="hidden" name="wtype" value="<%=wtype %>" />
<table cellpadding="5">
<%
if (wtype.equals("in")) {
// 글 등록상황이면
%>
<tr>
<%	if (loginMember == null) {	// 비회원일 경우 %>
<th>작성자</th><td><input type="text" name="writer" /></td>
<th>비밀번호</th><td><input type="password" name="pwd" /></td>
<%	} else {	// 로그인 한 회원일 경우 %>
<th>작성자</th><td><%=loginMember.getMlid() %></td>
<% } %>
</tr>
<%
} else {
// 글 수정상황이면
	out.println("<tr><th>작성자</th><td>" + writer + "</td></tr>");
}
%>
<tr>
<th>제목</th>
<td colspan="3"><input type="text" name="title" size="60" value="<%= title %>" /></td>
</tr>
<tr>
<th>내용</th>
<td colspan="3"><textarea name="content" rows="10" cols="60"><%= content %></textarea></td>
</tr>
<tr><td colspan="4" align="center">
	<input type="submit" value="글<%= msg %>" />
	<input type="reset" value="다시 입력" />
</td></tr>
</table>
</form>
</body>
</html>