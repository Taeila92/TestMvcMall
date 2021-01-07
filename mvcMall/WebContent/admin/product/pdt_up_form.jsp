<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");

ArrayList<CataBigInfo> cataBigList = (ArrayList<CataBigInfo>)request.getAttribute("cataBigList");
ArrayList<CataSmallInfo> cataSmallList = (ArrayList<CataSmallInfo>)request.getAttribute("cataSmallList");
ArrayList<BrandInfo> brandList = (ArrayList<BrandInfo>)request.getAttribute("brandList");
PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");

int cpage = Integer.parseInt(request.getParameter("cpage"));
int psize = Integer.parseInt(request.getParameter("psize"));

// 검색조건 및 정렬조건 쿼리스트링을 받음
String id, isview, schtype, keyword, sdate, edate, bcata, scata, sprice, eprice, stock, ord;
id		= request.getParameter("id");		isview	= request.getParameter("isview");
schtype = request.getParameter("schtype");	keyword = request.getParameter("keyword");
sdate	= request.getParameter("sdate");	edate	= request.getParameter("edate");
bcata	= request.getParameter("bcata");	scata	= request.getParameter("scata");
sprice	= request.getParameter("sprice");	eprice	= request.getParameter("eprice");
stock	= request.getParameter("stock");	ord 	= request.getParameter("ord");

String args = "?cpage=" + cpage + "&psize=" + psize;
if (isview != null && !isview.equals(""))	args += "&isview=" + isview;
if (sdate != null && !sdate.equals(""))		args += "&sdate=" + sdate;
if (edate != null && !edate.equals(""))		args += "&edate=" + edate;
if (bcata != null && !bcata.equals(""))		args += "&bcata=" + bcata;
if (scata != null && !scata.equals(""))		args += "&scata=" + scata;
if (sprice != null && !sprice.equals(""))	args += "&sprice=" + sprice;
if (eprice != null && !eprice.equals(""))	args += "&eprice=" + eprice;
if (stock != null && !stock.equals(""))		args += "&stock=" + stock;
if (keyword != null && !keyword.equals(""))
	args += "&schtype=" + schtype + "&keyword=" + keyword;
if (ord != null && !ord.equals(""))			args += "&ord=" + ord;

int csIdx = pdtInfo.getCs_idx();	// 소분류 idx
String cbIdx = (csIdx + "").substring(0, 2);	// 대분류 idx(소분류의 앞 두자리 숫자값)
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#pdtUpForm th, #pdtUpForm td { border-bottom:1px solid black; height:30px; }
</style>
<script>
<%
String scName = null;
int bc = 0, sc = 0;
for (int i = 0, j = 1 ; i < cataSmallList.size() ; i++, j++) {
	if (bc != cataSmallList.get(i).getCb_idx()) {
		j = 1;
%>
var arr<%=cataSmallList.get(i).getCb_idx()%> = new Array();
arr<%=cataSmallList.get(i).getCb_idx()%>[0] = new Option("", "소분류 선택");
<%
	}
	bc = cataSmallList.get(i).getCb_idx();
	sc = cataSmallList.get(i).getCs_idx();
	scName = cataSmallList.get(i).getCs_name();
%>
arr<%=bc%>[<%=j%>] = new Option("<%=sc%>", "<%=scName%>");
<%
}
%>

function setCategory(obj, target) {
	var x = obj.value;
	for (var m = target.options.length - 1 ; m > 0 ; m--) {
		target.options[m] = null;
	}
	if (x != "") {
		var selectedArray = eval("arr" + x);
		for (var i = 0 ; i < selectedArray.length ; i++) {
			target.options[i] = new Option(selectedArray[i].value, selectedArray[i].text);
		}
		target.options[0].selected = true;
	}
}
</script>
</head>
<body>
<h2>상품 등록 폼</h2>
<form name="frmPdt" action="pdt_up_proc.pdta" method="post" enctype="multipart/form-data">
<input type="hidden" name="args" value="<%=args %>" />
<input type="hidden" name="id" value="<%=pdtInfo.getPl_id() %>" />
<input type="hidden" name="oldImg1" value="<%=pdtInfo.getPl_img1() %>" />
<input type="hidden" name="oldImg2" value="<%=(pdtInfo.getPl_img2() == null) ? "" : pdtInfo.getPl_img2() %>" />
<input type="hidden" name="oldImg3" value="<%=(pdtInfo.getPl_img3() == null) ? "" : pdtInfo.getPl_img3() %>" />
<input type="hidden" name="oldDesc" value="<%=pdtInfo.getPl_desc() %>" />
<table width="800" cellpadding="5" id="pdtUpForm">
<tr>
<th width="150">대분류</th>
<td width="250">
	<select name="bCata" onchange="setCategory(this, this.form.sCata);">
		<option value="">대분류 선택</option>
<% for (int i = 0 ; i < cataBigList.size() ; i++) { %>
		<option value="<%=cataBigList.get(i).getCb_idx()%>" 
		<% if ((cataBigList.get(i).getCb_idx() + "").equals(cbIdx)) { %>
		selected="selected"<% } %>><%=cataBigList.get(i).getCb_name()%></option>
<% } %>
	</select>
</td>
<th width="150">소분류</th>
<td width="250">
	<select name="sCata">
		<option value="">소분류 선택</option>
<%
for (int i = 0 ; i < cataSmallList.size() ; i++) {
	if (cbIdx.equals((cataSmallList.get(i).getCs_idx() + "").substring(0, 2))) {
%>
		<option value="<%=cataSmallList.get(i).getCs_idx()%>" 
		<% if (csIdx == cataSmallList.get(i).getCs_idx()) { %>selected="selected"<% } %>>
		<%=cataSmallList.get(i).getCs_name()%></option>
<%
	}
}
%>
	</select>
</td>
</tr>
<tr>
<th>브랜드</th>
<td>
	<select name="brand">
		<option value="">브랜드 선택</option>
<% for (int i = 0 ; i < brandList.size() ; i++) { %>
		<option value="<%=brandList.get(i).getBl_idx()%>" 
		<% if (brandList.get(i).getBl_idx() == pdtInfo.getBl_idx()) { %>selected="selected"<% } %>>
		<%=brandList.get(i).getBl_name()%></option>
<% } %>
	</select>
</td>
<th>원산지</th><td><input type="text" name="orig" value="<%=pdtInfo.getPl_orig() %>" /></td>
</tr>
<tr>
<th>상품명</th><td><input type="text" name="name" value="<%=pdtInfo.getPl_name() %>" /></td>
<th>가격</th><td><input type="text" name="price" value="<%=pdtInfo.getPl_price() %>" /></td>
</tr>
<tr>
<th>원가</th><td><input type="text" name="cost" value="<%=pdtInfo.getPl_cost() %>" /></td>
<th>할인율</th><td><input type="text" name="discount" value="<%=pdtInfo.getPl_discount() %>" /></td>
</tr>
<tr>
<th>옵션</th>
<td colspan="3"><input type="text" name="opt" value="<%=pdtInfo.getPl_opt() %>" /> 
(Size,S,M,L,XL,XXL:Color,RED,BLUE,BLACK)</td>
</tr>
<tr>
<th>이미지1</th><td><input type="file" name="img1" /><br />
<%=(pdtInfo.getPl_img1() == null) ? "이미지 없음" : pdtInfo.getPl_img1() %></td>
<th>이미지2</th><td><input type="file" name="img2" /><br />
<%=(pdtInfo.getPl_img2() == null) ? "이미지 없음" : pdtInfo.getPl_img2() %></td>
</tr>
<tr>
<th>이미지3</th><td><input type="file" name="img3" /><br />
<%=(pdtInfo.getPl_img3() == null) ? "이미지 없음" : pdtInfo.getPl_img3() %></td>
<th>설명이미지</th><td><input type="file" name="desc" /><br />
<%=(pdtInfo.getPl_desc() == null) ? "이미지 없음" : pdtInfo.getPl_desc() %></td>
</tr>
<tr>
<th>재고</th>
<td>
	<select name="stock">
		<option value="-1" <% if (pdtInfo.getPl_stock() == -1) { %>selected="selected"<% } %>>무제한</option>
<% for (int i = 0 ; i <= 100 ; i++) { %>
		<option value="<%=i%>" <% if (pdtInfo.getPl_stock() == i) { %>selected="selected"<% } %>><%=i%></option>
<% } %>
	</select> EA
</td>
<th>게시여부</th>
<td>
	<input type="radio" name="view" value="y" <% if (pdtInfo.getPl_view().equals("y")) { %>checked="checked"<% } %> />상품 게시
	<input type="radio" name="view" value="n" <% if (pdtInfo.getPl_view().equals("n")) { %>checked="checked"<% } %> />상품 미게시
</td>
</tr>
<tr><td colspan="4" align="center">
	<input type="button" value="목록으로" onclick="location.href='pdt_list.pdta<%=args %>';" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="submit" value="상품 수정" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="reset" value="다시 입력" />
</td></tr>
</table>
</form>
</body>
</html>
