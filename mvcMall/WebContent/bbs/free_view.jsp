<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
FreeInfo article = (FreeInfo)request.getAttribute("article");
if (article == null) {
// 저장된 게시물이 없으면
	out.println("<script>");
	out.println("alert('잘못된 경로로 들어오셨습니다.');");
	out.println("history.back();");
	out.println("</script>");
}

String uid = null;
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
if (loginMember != null)	uid = loginMember.getMlid();
// 현재 로그인한 상태이면 로그인 아이디를 uid에 저장

int idx = Integer.parseInt(request.getParameter("idx"));
int cpage = Integer.parseInt(request.getParameter("cpage"));
String schtype = request.getParameter("schtype");
String keyword = request.getParameter("keyword");
String args = "?cpage=" + cpage;
if (schtype != null && keyword != null && !keyword.equals("")) {
	args += "&schtype=" + schtype + "&keyword=" + keyword;
	// 검색어가 있으면 검색어를 쿼리스트링으로 가져감
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>자유 게시판 글보기 화면</h2>
<table cellpadding="5">
<tr>
<th>작성자</th><td><%=article.getFl_writer() %></td>
<th>조회수</th><td><%=article.getFl_read() %></td>
</tr>
<tr>
<th>작성일</th><td><%=article.getFl_date() %></td>
<th>좋아/싫어</th><td><%=article.getFl_good() + " / " + article.getFl_bad() %></td>
</tr>
<tr><th>제목</th><td colspan="3"><%=article.getFl_title() %></td></tr>
<tr><th>내용</th>
<td colspan="3"><%=article.getFl_content().replace("\r\n", "<br />") %></td></tr>
<tr><td colspan="4" align="center">
	<input type="button" value="목록으로" onclick="location.href='brd_list.free<%=args %>';" />
<%
boolean isPms = false;	// 수정 및 삭제 권한이 있는지 여부를 저장할 변수
String link1 = null, link2 = null;
// 각각 수정과 삭제시 이동할 url을 저장할 변수로 회원과 비회원의 경우 url이 달라짐

if (article.getFl_ismember().equals("n")) {	// 비회원이 입력한 글이면
	isPms = true;	// 비회원 글은 암호를 입력하기 때문에 모든 사용자가 권한이 있는 걸로 취급
	link1 = "location.href='brd_pwd.free" + args + "&wtype=up&idx=" + idx + "';";
	link2 = "location.href='brd_pwd.free" + args + "&wtype=del&idx=" + idx + "';";
} else if (uid != null && uid.equals(article.getFl_writer())) {
// 현재 로그인한 회원과 작성자가 같으면
	isPms = true;	// 현재 로그인한 회원의 글이므로 모든 권한을 가짐
	link1 = "location.href='brd_form.free" + args + "&wtype=up&idx=" + idx + 
		"&ismember=" + article.getFl_ismember() + "';";
	// 로그인한 아이디로 검사했으므로 바로 수정폼으로 이동
	link2 = "notCool(" + idx + ");";
	// 삭제는 물러본 후 실행하도록 자바스크립트 함수를 연결
}

if (isPms) {	// 수정 및 삭제 권한이 있거나 비회원 글이면
	if (article.getFl_ismember().equals("y")) {
	// 회원이 등록한 글일 경우(현재 글이 로그인한 회원의 글이면)
%>
<script>
function notCool(idx) {
	if (confirm("정말 삭제하시겠습니까?")) {
		location.href="brd_proc.free?wtype=del&idx=" + idx + 
				"&ismember=<%= article.getFl_ismember() %>";
	}
}
</script>
<%
	}
%>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="수정" onclick="<%=link1 %>" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="삭제" onclick="<%=link2 %>" />
<% } %>
</td></tr>
</table>
</body>
</html>
