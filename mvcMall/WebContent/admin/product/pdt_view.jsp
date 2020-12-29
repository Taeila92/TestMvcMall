<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");

int cpage = Integer.parseInt(request.getParameter("cpage"));
int psize = Integer.parseInt(request.getParameter("psize"));

// 검색조건 및 정렬조건 쿼리스트링을 받음
String id, isview, schtype, keyword, sdate, edate, bcata, scata, sprice, eprice, stock, ord;
id 		= request.getParameter("id");		isview	= request.getParameter("isview");
schtype	= request.getParameter("schtype");	keyword	= request.getParameter("keyword");
sdate	= request.getParameter("sdate");	edate	= request.getParameter("edate");
bcata	= request.getParameter("bcata");	scata	= request.getParameter("scata");
sprice	= request.getParameter("sprice");	eprice	= request.getParameter("eprice");
stock	= request.getParameter("stock");	ord		= request.getParameter("ord");

String args = "&cpage=" + cpage + "&psize=" + psize;
if (isview != null)	args += "&isview=" + isview;	if (sdate != null)	args += "&sdate=" + sdate;
if (edate != null)	args += "&edate=" + edate;		if (bcata != null)	args += "&bcata=" + bcata;
if (scata != null)	args += "&scata=" + scata;		if (sprice != null)	args += "&sprice=" + sprice;
if (eprice != null)	args += "&eprice=" + eprice;	if (stock != null)	args += "&stock=" + stock;
if (keyword != null && !keyword.equals(""))	args += "&schtype=" + schtype + "&keyword=" + keyword;
if (ord != null)		args += "&ord=" + ord;

PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="jquery-3.5.1.js"></script>
<script>
	$(document).ready(function(){
		$("#img1").click(function(){
			$("#bigImg").attr("src", $(this).attr("src"));
		});
		$("#img2").click(function(){
			$("#bigImg").attr("src", $(this).attr("src"));
		});
		$("#img3").click(function(){
			$("#bigImg").attr("src", $(this).attr("src"));
		});
	});
</script>
</head>
<body>
<h2>상품 상세보기 화면</h2>
<table width="800" cellpadding="5">
<tr>
<td width="50%" align="center" valign="middle">
	<table width="100%">
		<tr><td align="center" valign="middle">
			<img id="bigImg"src="/mvcMall/product/pdt_img/<%=pdtInfo.getPl_img1() %>" width="380" />
		</td></tr>
		<tr><td align="center" valign="middle" id="">
			<img id="img1" src="/mvcMall/product/pdt_img/<%=pdtInfo.getPl_img1() %>" width="100" />
<%if (pdtInfo.getPl_img2() != null && !pdtInfo.getPl_img2().equals("")){ %>	
			<img id="img2" src="/mvcMall/product/pdt_img/<%=pdtInfo.getPl_img2() %>" width="100" /><%} %>	
<%if (pdtInfo.getPl_img3() != null && !pdtInfo.getPl_img3().equals("")){ %>	
			<img id="img3" src="/mvcMall/product/pdt_img/<%=pdtInfo.getPl_img3() %>" width="100" /><%} %>					
			</td>
		</tr>
	</table>
</td>
<td width="*">
</td>
</tr>
</table>
</body>
</html>