<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
String name = request.getParameter("name");
String brith = request.getParameter("brith");
String file = request.getParameter("file");
String file2 = request.getParameter("file2");
String file3 = request.getParameter("file3");
String file4 = request.getParameter("file4");
String file_old = request.getParameter("file_old");
String file2_old = request.getParameter("file2_old");
String file3_old = request.getParameter("file3_old");
String file4_old = request.getParameter("file4_old");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>파일 업로드 결과</h3>
<table cellpadding="5">
<tr><td>이름</td><td><%=name %></td></tr>
<tr><td>생년월일</td><td><%=brith %></td></tr>
<tr><td>증명사진</td>
<td><a href="file_download.jsp?file=<%=file %>"><%=file_old %></a></td>
</tr>
<tr><td>이력서</td>
<td><a href="file_download.jsp?file=<%=file2 %>"><%=file2_old %></a></td>
</tr>
<tr><td>증빙서류1</td>
<td><a href="file_download.jsp?file=<%=file3 %>"><%=file3_old %></a></td>
</tr>
<tr><td>증빙서류2</td>
<td><a href="file_download.jsp?file=<%=file4 %>"><%=file4_old %></a></td>
</tr>
</table>
</body>
</html>