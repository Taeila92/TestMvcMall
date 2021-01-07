<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
String id = request.getParameter("id");
String cnt = request.getParameter("cnt");
String args = request.getParameter("args");
String optValue = "";	// 선택한 옵션들을 담을 변수
int optCnt = 0;			// 옵션의 개수를 담을 변수
if (request.getParameter("optCnt") != null) {	// 옵션이 있으면
	optCnt = Integer.parseInt(request.getParameter("optCnt"));
	for (int i = 0 ; i < optCnt ; i++) {
		optValue += "," + request.getParameter("opt" + i);
	}
	optValue = optValue.substring(1);
	// 선택한 옵션들을 쉼표를 구분자로 하여 문자열로 만듦
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
<% if (id != null && cnt != null) { %>
function goDirect() {
// 로그인 없이 비회원인 상태로 구매할 때 구매폼으로 이동시키는 함수
	var frm = document.frmLogin;
	frm.action = "ord_form.ord";
	frm.submit();
}
<% } %>
</script>
</head>
<body>
<h2>로그인 폼</h2>
<form name="frmLogin" action="login" method="post">
<input type="hidden" name="id" value="<%=id %>" />
<input type="hidden" name="cnt" value="<%=cnt %>" />
<input type="hidden" name="args" value="<%=args %>" />
<input type="hidden" name="optValue" value="<%=optValue %>" />
<table cellpadding="5">
<tr><th>아이디</th><td><input type="text" name="uid" /></td></tr>
<tr><th>비밀번호</th><td><input type="password" name="pwd" /></td></tr>
<tr><td colspan="2" align="center">
	<input type="submit" value="로그인" />
	<input type="reset" value="다시 입력" />
</td></tr>
<% if (id != null && cnt != null) { %>
<tr><td colspan="2" align="center">
	<input type="button" value="비회원 구매하기" onclick="goDirect();" />
</td></tr>
<% } %>
</table>
</form>
</body>
</html>
