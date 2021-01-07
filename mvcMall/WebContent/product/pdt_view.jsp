<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
//로그인 되어 있을 경우 로그인 정보(현재 로그인 한 회원의 정보)를 받아옴

request.setCharacterEncoding("utf-8");
int cpage = Integer.parseInt(request.getParameter("cpage"));
int psize = Integer.parseInt(request.getParameter("psize"));

// 검색조건 및 정렬조건 쿼리스트링을 받음
String id, keyword, bcata, scata, brand, sprice, eprice, ord;
id		= request.getParameter("id");		keyword = request.getParameter("keyword");
bcata	= request.getParameter("bcata");	scata	= request.getParameter("scata");
brand	= request.getParameter("brand");	sprice	= request.getParameter("sprice");
eprice	= request.getParameter("eprice");	ord 	= request.getParameter("ord");

String args = "?cpage=" + cpage + "&psize=" + psize;
if (bcata != null && !bcata.equals(""))		args += "&bcata=" + bcata;
if (scata != null && !scata.equals(""))		args += "&scata=" + scata;
if (brand != null && !brand.equals(""))		args += "&brand=" + brand;
if (sprice != null && !sprice.equals(""))	args += "&sprice=" + sprice;
if (eprice != null && !eprice.equals(""))	args += "&eprice=" + eprice;
if (keyword != null && !keyword.equals(""))	args += "&keyword=" + keyword;
if (ord != null && !ord.equals(""))			args += "&ord=" + ord;

PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");

String soldout = "";
if (pdtInfo.getPl_stock() == 0)
	soldout = " <img src='/mvcMall/images/soldout.png' width='80' align='absmiddle' />";

String plstock = String.valueOf(pdtInfo.getPl_stock());
if (plstock.equals("-1"))	plstock = "무제한";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#thImg img { margin:10px; }
</style>
<script>
function showImg(imgName) {
	var bigImg = document.getElementById("bigImg");
	bigImg.src = "/mvcMall/product/pdt_img/" + imgName;
}

function goCart() {	// 장바구니에 담기 버튼 클릭시
	var frm = document.frmPdt;
	frm.action = "cart_in.ord";
	frm.submit();
}

function goDirect() {	// 바로 구매하기 버튼 클릭시
	var frm = document.frmPdt;
<%
if (loginMember == null) {	// 로그인을 하지 않은 상태일 경우
	session.setAttribute("url", "ord_form.ord");
%>
	frm.action = "login_form.jsp";
<% } else {	// 로그인을 한 상태일 경우 %>
	frm.action = "ord_form.ord";
<% } %>
	frm.submit();
}
</script>
</head>
<body>
<h2>상품 상세보기 화면</h2>
<table width="800" cellpadding="5">
<tr>
<td width="40%" align="center" valign="middle">
	<table width="100%">
	<tr><td align="center" valign="middle">
		<img src="/mvcMall/product/pdt_img/<%=pdtInfo.getPl_img1() %>" width="300" id="bigImg" />
	</td></tr>
	<tr><td align="center" valign="middle" id="thImg">
		<img src="/mvcMall/product/pdt_img/<%=pdtInfo.getPl_img1() %>" width="80" onclick="showImg('<%=pdtInfo.getPl_img1() %>');" />
<% if (pdtInfo.getPl_img2() != null && !pdtInfo.getPl_img2().equals("")) { %>
		<img src="/mvcMall/product/pdt_img/<%=pdtInfo.getPl_img2() %>" width="80" onclick="showImg('<%=pdtInfo.getPl_img2() %>');" /><% } %>
<% if (pdtInfo.getPl_img3() != null && !pdtInfo.getPl_img3().equals("")) { %>
		<img src="/mvcMall/product/pdt_img/<%=pdtInfo.getPl_img3() %>" width="80" onclick="showImg('<%=pdtInfo.getPl_img3() %>');" /><% } %>
	</td></tr>
	</table>
</td>
<td width="*" valign="top">
	<table width="100%" cellpadding="8">
	<tr>
	<td width="100">분류</td>
	<td width="*"><%=pdtInfo.getCb_name() + " - " + pdtInfo.getCs_name() %></td>
	</tr>
	<tr><td>상품명</td><td><%=pdtInfo.getPl_name() + soldout %></td></tr>
	<tr><td>브랜드</td><td><%=pdtInfo.getBl_name() %></td></tr>
	<tr><td>원산지</td><td><%=pdtInfo.getPl_orig() %></td></tr>
	<tr><td>가격</td><td><%=pdtInfo.getPl_price() %></td></tr>
<%
int price = pdtInfo.getPl_price();	// 실 구매가
if (pdtInfo.getPl_discount() > 0) {
	float rate = (float)pdtInfo.getPl_discount() / 100;
	price = Math.round(pdtInfo.getPl_price() - (pdtInfo.getPl_price() * rate));
%>
	<tr><td>할인율</td><td><%=pdtInfo.getPl_discount() %>% (할인가격 : <%=price %>)</td></tr>
<% } %>
	<form name="frmPdt" action="" method="post">
	<input type="hidden" name="id" value="<%=id %>" />
	<input type="hidden" name="args" value="<%=args %>" />
	<input type="hidden" name="price" value="<%=price %>" />
	<tr><td>수량</td>
	<td>
<%
String dis = "";
int max = pdtInfo.getPl_stock();
if (max == -1)		max = 100;
else if (max == 0) {
	dis = " disabled=\"disabled\"";		max = 1;
}
%>
		<select name="cnt" <%=dis %>>
<% for (int i = 1 ; i <= max ; i++) { %>
			<option value="<%=i%>"><%=i%></option>
<% } %>
		</select>
	</td>
	</tr>
<%
if (pdtInfo.getPl_opt() != null && !pdtInfo.getPl_opt().equals("")) {
// 현 상품에 선택할 옵션이 있으면
	String[] arrOpt = pdtInfo.getPl_opt().split(":");
%>
	<input type="hidden" name="optCnt" value="<%=arrOpt.length %>" />
<%
	for (int i = 0 ; i < arrOpt.length ; i++) {
		String[] arrTmp = arrOpt[i].split(",");
		out.println("<tr><td>" + arrTmp[0] + "</td><td>");
		out.println("<select name='opt" + i + "' " + dis + ">");
		for (int j = 1 ; j < arrTmp.length ; j++) {
			out.println("<option value='" + arrTmp[j] + "'>" + arrTmp[j] + "</option>");
		}
		out.println("</select></td></tr>");
	}
}
%>
	<tr><td colspan="2" align="center">
		<input type="button" value="장바구니에 담기" onclick="goCart();" <%=dis%> />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="바로 구매하기" onclick="goDirect();" <%=dis%> />
	</td></tr>
	</form>
	</table>
</td>
</tr>
<tr><td colspan="2" align="center"><hr width="100%" /></td></tr>
<tr><td colspan="2" align="center">
	<img src="/mvcMall/product/pdt_img/<%=pdtInfo.getPl_desc() %>" width="780" />
</td></tr>
<tr><td colspan="2" align="center"><hr width="100%" /></td></tr>
<tr><td colspan="2" align="center">
	<input type="button" value="목록" onclick="location.href='pdt_list.pdt<%=args %>';" />
</td></tr>
</table>
<br /><br />
</body>
</html>
