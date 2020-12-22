<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%
request.setCharacterEncoding("utf-8");
String wtype = request.getParameter("wtype");
int idx = Integer.parseInt(request.getParameter("idx"));
// 글번호로 반드시 받아와야 하는 값이며, 정수형으로 형변환이 가능한 값임
int cpage = Integer.parseInt(request.getParameter("cpage"));
String args = "?cpage=" + cpage;
String schtype = request.getParameter("schtype");
String keyword = request.getParameter("keyword");
if (keyword != null && !keyword.equals("")) {
	args += "&schtype=" + schtype + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
}

String caption = null, link = null;
// wtype의 값에 따라 지정될 버튼의 캡션과 폼태그의 action속성값을 저장할 변수
if (wtype.equals("up")) {
	caption = "수정";
	link = "brd_form.free" + args + "&wtype=up";
} else if (wtype.equals("del")) {
	caption = "삭제";
	link = "brd_proc.free" + args + "&wtype=del";
} else {
	out.println("<script>");
	out.println("alert('잘못된 경로로 들어오셨습니다.');");
	out.println("location.href='index.jsp';");
	out.println("</script>");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>비밀번호 입력 폼</h2>
<form name="frmPwd" action="<%=link %>" method="post">
<input type="hidden" name="ismember" value="n" /><!-- 비회원 글 체크값 -->
<input type="hidden" name="idx" value="<%=idx %>" />
<table cellpadding="5">
<tr><th>비밀번호</th><td><input type="password" name="pwd" /></td></tr>
<tr><td colspan="2" align="center">
	<input type="submit" value="<%=caption %>" />
	<input type="button" value="돌아 가기" onclick="history.back();" />
</td></tr>
</table>
</form>
</body>
</html>
