<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
ArrayList<CataBigInfo> cataBigList = (ArrayList<CataBigInfo>)request.getAttribute("cataBigList");
ArrayList<CataSmallInfo> cataSmallList = (ArrayList<CataSmallInfo>)request.getAttribute("cataSmallList");
ArrayList<BrandInfo> brandList = (ArrayList<BrandInfo>)request.getAttribute("brandList");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");

String keyword, bcata, scata, brand, sprice, eprice, ord;
keyword =	pageInfo.getKeyword();	// 검색어
bcata =		pageInfo.getBcata();	// 대분류
scata =		pageInfo.getScata();	// 소분류
brand =		pageInfo.getBrand();	// 브랜드
sprice =	pageInfo.getSprice();	// 가격대 시작 가격
eprice =	pageInfo.getEprice();	// 가격대 종료 가격
ord =		pageInfo.getOrd();		// 정렬조건

String args = "", schArgs = "";
if (bcata != null)		schArgs += "&bcata=" + bcata;		else	bcata = "";
if (scata != null)		schArgs += "&scata=" + scata;		else	scata = "";
if (sprice != null)		schArgs += "&sprice=" + sprice;		else	sprice = "";
if (eprice != null)		schArgs += "&eprice=" + eprice;		else	eprice = "";
if (keyword != null)	schArgs += "&keyword=" + keyword;	else	keyword = "";

if (ord != null)		schArgs += "&ord=" + ord;			else	ord = "";

int cpage	= pageInfo.getCpage();	// 현재 페이지 번호
int pcnt	= pageInfo.getPcnt();	// 전체 페이지 수
int psize	= pageInfo.getPsize();	// 페이지 크기
int bsize	= pageInfo.getBsize();	// 블록 페이지 개수
int spage	= pageInfo.getSpage();	// 블록 시작 페이지 번호
int epage	= pageInfo.getEpage();	// 블록 종료 페이지 번호
int rcnt	= pageInfo.getRcnt();	// 검색된 게시물 개수
schArgs = "&psize=" + psize + schArgs;
args = "&cpage=" + cpage + schArgs;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
td { font-size:11; }
.pr { width:50px; }
.pdtBox3 { width:266px; height:265px; border:1px solid black; }
.pdtBox4 { width:195px; height:210px; border:1px solid black; }
</style>
</head>
<body>
<h2>상품 목록 화면</h2>
<form name="frmSch" action="" method="get">
<table width="800" cellpadding="5">
<tr width="15%">
<th>가격대</th>
<td>
	<input type="text" name="sprice" class="pr" value="<%=sprice%>"/>원 이상
	<input type="text" name="eprice" class="pr" value="<%=eprice%>"/>원 이하
</td>
<th width="15%">상품명</th>
<td>
	<input type="text" name="keyword" class="date"/>
</td>
</tr>
<tr>
<th>정렬방식</th>
<td>
	<select name="ord">
		<option value="" <% if (ord.equals("")) { %>selected="selected"<% } %>>상품아이디(오름차순)</option>
		<option value="namea" <% if (ord.equals("namea")) { %>selected="selected"<% } %>>상품명(오름차순)</option>
		<option value="pricea" <% if (ord.equals("pricea")) { %>selected="selected"<% } %>>낮은 가격순(오름차순)</option>
		<option value="priced" <% if (ord.equals("priced")) { %>selected="selected"<% } %>>높은 가격순(내림차순)</option>
		<option value="datea" <% if (ord.equals("datea")) { %>selected="selected"<% } %>>오래된 등록일순(오름차순)</option>
		<option value="dated" <% if (ord.equals("dated")) { %>selected="selected"<% } %>>최근 등록일순(내림차순)</option>
		<option value="salecntd" <% if (ord.equals("salecntd")) { %>selected="selected"<% } %>>많이 팔린순(내림차순)</option>
		<option value="reviewd" <% if (ord.equals("reviewd")) { %>selected="selected"<% } %>>리뷰 개수순(내림차순)</option>
	</select>
</td>
<th>페이지 크기</th>
<td>
	<select name="psize">
		<option value="12" <% if (psize == 12) { %>selected="selected"<% } %>>12 개</option>
		<option value="6" <% if (psize == 6) { %>selected="selected"<% } %>>6 개</option>
		<option value="8" <% if (psize == 8) { %>selected="selected"<% } %>>8 개</option>
		<option value="24" <% if (psize == 24) { %>selected="selected"<% } %>>24 개</option>
	</select> 씩 보여주기
</td>
</tr>
<tr>
<td colspan="4" align="center">
	<input type="submit" value="상품 검색" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="reset" value="조건 초기화" />
</td>
</tr>
</table>
</form>
<br /><br />
<table width="800" cellpadding="5">
<%
int max = 3;	// 한 행에서 보여줄 상품의 최대 개수
if (psize == 8) max = 4;

if (pdtList != null && rcnt > 0) {	// 검색결과가 있으면
	String lnk = "", price="", soldout = "";
	for (int i = 0 ; i < pdtList.size() && i < psize ; i++) {
		lnk = "<a href='pdt_view.pdt?id=" + pdtList.get(i).getPl_id() + args + "'>";
		if (i % max == 0)	out.println("<tr align=\"center\">");
		price = pdtList.get(i).getPl_price() + "";
		if (pdtList.get(i).getPl_discount() > 0){ // 할인율이 있으면
			float rate = (float)pdtList.get(i).getPl_discount() / 100;
			int dcPrice = Math.round(pdtList.get(i).getPl_price() - (pdtList.get(i).getPl_price() * rate));
			price = "<del>" + price + "</del><br/>할인가 : " + dcPrice;
		}
		soldout = "";
		if (pdtList.get(i).getPl_stock() == 0){
			soldout = " <img src=\"/mvcMall/images/soldout.png\" width='50' align='absmiddle' />";
		}
%>
<td>
	<div class="pdtBox<%=max%>">
		<%=lnk %><img src="/mvcMall/product/pdt_img/<%=pdtList.get(i).getPl_img1() %>" width="<%=max == 3 ? 250 : 190 %>" height="<%=max == 3 ? 200 : 140 %>" /></a><br />
		<%=lnk + pdtList.get(i).getPl_name() %></a><%= soldout %><br />
		판매가 : <%= price %>
	</div>
</td>
<%
		if (i % max == max - 1)	out.println("</tr>");
	}
} else {
	out.println("<tr><td align='center'>검색결과가 없습니다.</td></tr>");
}
%>
</table>
<br />
<table width="800" cellpadding="5">
<tr>
<td align="center">
<%
if (rcnt > 0) {	// 검색결과 상품들이 있을 경우에만 페이징을 함
	if (cpage == 1) {
		out.println("[<<]&nbsp;&nbsp;[<]&nbsp;&nbsp;");
	} else {
		out.print("<a href='pdt_list.pdt?cpage=1" + schArgs + "'>");
		out.println("[<<]</a>&nbsp;&nbsp;");
		out.print("<a href='pdt_list.pdt?cpage=" + (cpage - 1) + schArgs + "'>");
		out.println("[<]</a>&nbsp;&nbsp;");
	}

	for (int i = 1, j = spage ; i <= bsize && j <= pcnt ; i++, j++) {
		if (cpage == j) {
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
		} else {
			out.print("&nbsp;<a href='pdt_list.pdt?cpage=" + j + schArgs + "'>");
			out.println(j + "</a>&nbsp;");
		}
	}

	if (cpage == pcnt) {
		out.println("&nbsp;&nbsp;[>]&nbsp;&nbsp;[>>]");
	} else {
		out.print("&nbsp;&nbsp;<a href='pdt_list.pdt?cpage=" + (cpage + 1) + schArgs + "'>");
		out.println("[>]</a>");
		out.print("&nbsp;&nbsp;<a href='pdt_list.pdt?cpage=" + pcnt + schArgs + "'>");
		out.println("[>>]</a>");
	}
}
%>
</td>
</tr>
</table>
</body>
</html>
