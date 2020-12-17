<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
ArrayList<FreeInfo> articleList = (ArrayList<FreeInfo>)request.getAttribute("articleList");
// 화면에서 보여줄 게시글들의 목록

PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
// 페이징에 필요한 각종 데이터들을 담은 인스턴스
String schtype = null, keyword = null, schargs = "", args = "";
if (pageInfo.getSchtype() == null || pageInfo.getKeyword() == null){
	schtype = "";	keyword = "";
} else {
	schtype = pageInfo.getSchtype();
	keyword = pageInfo.getKeyword();
	if (keyword != null && !keyword.equals("")){
		schargs = "&schtype=" + schtype + "&keyword=" + keyword;
		// 검색어가 있을 경우 검색관련 데이터를 쿼리 스트링으로 지정(패이징할때 사용할 링크)
	}
}
int cpage = pageInfo.getCpage();	// 현재 페이지번호
int pcnt = pageInfo.getPcnt();		// 전체 페이지수
int spage = pageInfo.getSpage();	// 블록 시작 페이지 번호
int epage = pageInfo.getEpage();	// 블록 종료 페이지 번호
int rcnt = pageInfo.getRcnt();		// 검색된 게시물 개수

args = "&cpage=" + cpage + schargs;	// 목록에서 개시글을 보러갈때 사용할 링크
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#brdList tr { height:40px; }
#brdList td, #brdList th { border-bottom:1px black solid; }
a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:black; text-decoration:none; }
a:hover { color:green; text-decoration:underline; font-weight:bold;}
a:active { color:#f00; text-decoration:none; }	
a:focus { color:#f00; text-decoration:underline; }

</style>
</head>
<body>
<h2>자유 게시판 목록 화면</h2>
<form name="frmSch" method="get">
<table width="700" cellpadding="5">
<tr><td align="right">
	<select name="schtype">
		<option value="title" <% if (schtype.equals("title")) { %>
			selected="selected"<% } %>>제목</option>
		<option value="content" <% if (schtype.equals("content")) { %>
			selected="selected"<% } %>>내용</option>
		<option value="tc" <% if (schtype.equals("tc")) { %>
			selected="selected"<% } %>>제목+내용</option>
		<option value="writer" <% if (schtype.equals("writer")) { %>
			selected="selected"<% } %>>작성자</option>
	</select>
	<input type="text" name="keyword" value="<%=keyword %>" />
	<input type="submit" value="검색" />
</td></tr>
</table>
</form>
<table width="700" cellpadding="5" cellspacing="0" id="brdList">
<tr>
<th width="8%">번호</th><th width="*">제목</th><th width="15%">작성자</th>
<th width="15%">작성일</th><th width="8%">조회</th>
</tr>
<%
if (articleList != null && rcnt > 0){	// 검색결과가 있으면
	int seq = rcnt - (10 * (cpage - 1));
	String title = "", reply = "", lnk = "";
	for (int i = 0 ; i < articleList.size() ; i++){
		title = articleList.get(i).getFl_title();
		lnk = "<a href='brd_view.free?idx=" + articleList.get(i).getFl_idx() + args +
				"' title='" + title + "'>";
		if (title.length() > 26)
			title = title.substring(0, 24) + "...";

		reply = "";
		if (articleList.get(i).getFl_reply() > 0)
			reply = " [" + articleList.get(i).getFl_reply() + "]";
%>
<tr align="center" onmouseover="this.style.background='#efefef';" onmouseout="this.style.background='';">
<td><%= seq-- %></td>
<td align="left"><%=lnk + title + "</a>" + reply%></td>
<td><%=articleList.get(i).getFl_writer() %></td>
<td><%=articleList.get(i).getFl_date().substring(2,10) %></td>
<td><%=articleList.get(i).getFl_read() %></td>
</tr>
<%
	}
} else { // 검색결과가 없으면
	out.println("<tr align='center'><td colspan='5'>");
	out.println("검색 결과가 없습니다.</td></tr>");
}
%>
</table>
<br />
<table width="700" cellpadding="5">
<tr>
<td width="*">
<%
if (rcnt > 0) {
	pcnt = rcnt / 10;
	if (rcnt % 10 > 0)	pcnt++;
	// 전체 페이지수 = 전체게시물수 / 페이지크기 -> 나머지가 있으면 1증가

	if (cpage == 1) {
		out.println("[<<]&nbsp;&nbsp;[<]&nbsp;&nbsp;");
	} else {
		out.print("<a href='brd_list.free?cpage=1" + schargs + "'>");
		out.println("[<<]</a>&nbsp;&nbsp;");
		out.print("<a href='brd_list.free?cpage=" + (cpage - 1) + schargs + "'>");
		out.println("[<]</a>&nbsp;&nbsp;");
	}

	for (int i = 1, j = spage ; i <= 10 && j <= pcnt ; i++, j++) {
		if (cpage == j) {
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
		} else {
			out.print("&nbsp;<a href='brd_list.free?cpage=" + j + schargs + "'>");
			out.println(j + "</a>&nbsp;");
		}
	}

	if (cpage == pcnt) {
		out.println("&nbsp;&nbsp;[>]&nbsp;&nbsp;[>>]");
	} else {
		out.print("&nbsp;&nbsp;<a href='brd_list.free?cpage=" + (cpage + 1) + schargs + "'>");
		out.println("[>]</a>");
		out.print("&nbsp;&nbsp;<a href='brd_list.free?cpage=" + pcnt + schargs + "'>");
		out.println("[>>]</a>");
	}
}
%>
</td>
<td width="10%">
	<input type="button" value="글 등록" onclick="location.href='brd_form.free?wtype=in';" />
</td>
</tr>
</table>
</body>
</html>