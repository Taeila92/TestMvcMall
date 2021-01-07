<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
ArrayList<CataBigInfo> cataBigList = (ArrayList<CataBigInfo>)request.getAttribute("cataBigList");
ArrayList<CataSmallInfo> cataSmallList = (ArrayList<CataSmallInfo>)request.getAttribute("cataSmallList");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");

String isview, schtype, keyword, sdate, edate, bcata, scata, sprice, eprice, stock, ord;
isview =	pageInfo.getIsview();	// 게시여부(전체게시, 게시상품, 미게시상품)
schtype =	pageInfo.getSchtype();	// 검색조건(상품코드, 상품명)
keyword =	pageInfo.getKeyword();	// 검색어
sdate =		pageInfo.getSdate();	// 등록일 검색 시작일
edate =		pageInfo.getEdate();	// 등록일 검색 종료일
bcata =		pageInfo.getBcata();	// 대분류
scata =		pageInfo.getScata();	// 소분류
sprice =	pageInfo.getSprice();	// 가격대 시작 가격
eprice =	pageInfo.getEprice();	// 가격대 종료 가격
stock =		pageInfo.getStock();	// 재고량(이상)
ord =		pageInfo.getOrd();		// 정렬조건

String args = "", schArgs = "";
if (isview != null)		schArgs += "&isview=" + isview;		else	isview = "";
if (sdate != null)		schArgs += "&sdate=" + sdate;		else	sdate = "";
if (edate != null)		schArgs += "&edate=" + edate;		else	edate = "";
if (bcata != null)		schArgs += "&bcata=" + bcata;		else	bcata = "";
if (scata != null)		schArgs += "&scata=" + scata;		else	scata = "";
if (sprice != null)		schArgs += "&sprice=" + sprice;		else	sprice = "";
if (eprice != null)		schArgs += "&eprice=" + eprice;		else	eprice = "";
if (stock != null)		schArgs += "&stock=" + stock;		else	stock = "";
if (keyword != null && !keyword.equals("")) {
	schArgs += "&schtype=" + schtype + "&keyword=" + keyword;
} else {
	schtype = "";	keyword = "";
}

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
.date { width:80px; }
.pr { width:50px; }
.pdtBox3 { width:266px; height:250px; border:1px solid black; }
.pdtBox4 { width:195px; height:200px; border:1px solid black; }
</style>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script>
$(function() {
	$.datepicker.regional['ko'] = {
		closeText: '닫기', prevText: '이전달', nextText: '다음달', currentText: '오늘',
		monthNames: ['1월','2월','3월','4월','5월','6월', '7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월', '7월','8월','9월','10월','11월','12월'],
		dayNames: ['일','월','화','수','목','금','토'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		buttonImageOnly: true, weekHeader: 'Wk', dateFormat: 'yy-mm-dd', firstDay: 0,
		isRTL: false, duration:200, showAnim:'show', showMonthAfterYear: false
	};
	$.datepicker.setDefaults($.datepicker.regional['ko']);

	$( "#sdate" ).datepicker({
		changeMonth: true, dateFormat:"yy-mm-dd", onClose: function( selectedDate ) {}
	});
	$( "#edate" ).datepicker({
		changeMonth: true, dateFormat:"yy-mm-dd", onClose: function( selectedDate ) {}
	});
});
</script>
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
	bc = cataSmallList.get(i).getCb_idx();	// 대분류 idx를 bc에 저장
	sc = cataSmallList.get(i).getCs_idx();	// 소분류 idx를 sc에 저장
	scName = cataSmallList.get(i).getCs_name();	// 대분류명을 scName에 저장
%>
arr<%=bc%>[<%=j%>] = new Option("<%=sc%>", "<%=scName%>");
<%
}
%>

function setCategory(obj, target) {
	var x = obj.value;	// 대분류에서 선택한 값을 x에 담음

	for (var m = target.options.length - 1 ; m > 0 ; m--) {
		target.options[m] = null;
	}

	if (x != "") {
		var selectedArray = eval("arr" + x);	// 보여줄 배열 지정
		for (var i = 0 ; i < selectedArray.length ; i++) {
			target.options[i] = new Option(selectedArray[i].value, selectedArray[i].text);
		}
		target.options[0].selected = true;
	}
}
</script>
</head>
<body>
<h2>상품 목록 화면</h2>
<form name="frmSch" action="" method="get">
<table width="800" cellpadding="5">
<tr>
<th width="15%">분류선택</th>
<td width="35%">
	<select name="bcata" onchange="setCategory(this, this.form.scata);">
		<option value="" <% if (bcata.equals("")) { %>selected="selected"<% } %>>대분류 선택</option>
<% for (int i = 0 ; i < cataBigList.size() ; i++) { %>
		<option value="<%=cataBigList.get(i).getCb_idx()%>" 
		<% if (bcata.equals(cataBigList.get(i).getCb_idx() + "")) { %>selected="selected"<% } %>>
		<%=cataBigList.get(i).getCb_name()%></option>
<% } %>
	</select>&nbsp;
	<select name="scata">
		<option value="" <% if (scata.equals("")) { %>selected="selected"<% } %>>소분류 선택</option>
<%
if (!bcata.equals("")) {	// 대분류를 이용하여 검색한 상태이면(소분류도 보여줘야 함)
	for (int i = 0 ; i < cataSmallList.size() ; i++) {
		if (bcata.equals((cataSmallList.get(i).getCs_idx() + "").substring(0, 2))) {
		// 현재 선택된 대분류에 속한 소분류들만 보여줌
%>
	<option value="<%=cataSmallList.get(i).getCs_idx()%>" 
	<% if (scata.equals(cataSmallList.get(i).getCs_idx() + "")) { %>selected="selected"<% } %>>
	<%=cataSmallList.get(i).getCs_name()%></option>
<%
		}
	}
}
%>
	</select>
</td>
<th width="15%">등록기간</th>
<td>
	<input type="text" name="sdate" id="sdate" class="date" value="<%=sdate%>"/> 이후&nbsp;
	<input type="text" name="edate" id="edate" class="date" value="<%=edate%>"/> 이전
</td>
</tr>
<tr>
<th>가격대</th>
<td>
	<input type="text" name="sprice" class="pr" value="<%=sprice%>"/>원 이상
	<input type="text" name="eprice" class="pr" value="<%=eprice%>"/>원 이하
</td>
<th>검색어</th>
<td colspan="3">
	<select name="schtype">
		<option value="" <% if (schtype.equals("")) { %>selected="selected"<% } %>>검색조건</option>
		<option value="id" <% if (schtype.equals("id")) { %>selected="selected"<% } %>>상품 아이디</option>
		<option value="name" <% if (schtype.equals("name")) { %>selected="selected"<% } %>>상품 이름</option>
	</select>
	<input type="text" name="keyword" class="date"/>
</td>
</tr>
<tr>
<th>재고량</th>
<td>
	<select name="stock">
		<option value="" <% if (stock.equals("")) { %>selected="selected"<% } %>>재고량 선택</option>
		<option value="-1" <% if (stock.equals("-1")) { %>selected="selected"<% } %>>무한대</option>
<% for (int i = 0 ; i < 100 ; i++) { %>
		<option value="<%=i%>" <% if (stock.equals(i + "")) { %>selected="selected"<% } %>><%=i%></option>
<% } %>
	</select>개 이상
</td>
<th>게시여부</th>
<td>
	<input type="radio" name="isview" value="" <% if (isview.equals("")) { %>checked="checked"<% } %> />전체 보기
	&nbsp;<input type="radio" name="isview" value="y" <% if (isview.equals("y")) { %>checked="checked"<% } %> />게시중
	&nbsp;<input type="radio" name="isview" value="n" <% if (isview.equals("n")) { %>checked="checked"<% } %> />미게시
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
		<option value="10" <% if (psize == 10) { %>selected="selected"<% } %>>10 개</option>
		<option value="24" <% if (psize == 24) { %>selected="selected"<% } %>>24 개</option>
	</select> 씩 보여주기
</td>
</tr>
<tr><td colspan="4" align="center">
	<input type="submit" value="상품 검색" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="reset" value="조건 초기화" />
</td></tr>
</table>
</form>
<br /><br />
<table width="800" cellpadding="5">
<%
int max = 3;	// 한 행에서 보여줄 상품의 최대 개수
switch (psize) {
	case 8 :	max = 4;	break;
	case 10 :	max = 1;	break;
}

if (pdtList != null && rcnt > 0) {	// 검색결과가 있으면
	if (max == 1) {		// 한 행에서 보여줄 상품의 최대 개수가 1이면 게시판 형식으로 보여줌
%>
<tr>
<th width="7%">번호</th><th width="10%">상품분류<br />소분류</th>
<th width="15%">이미지</th><th width="*">상품아이디<br />상품명</th>
<th width="10%">가격<br />원가</th><th width="15%">등록일<br />게시</th>
</tr>
<%
		int seq = rcnt - (10 * (cpage - 1));	// 현재 페이지에서의 시작번호
		String lnk = "";
		for (int i = 0 ; i < pdtList.size() && i < psize ; i++) {
			lnk = "<a href='pdt_view.pdta?id=" + pdtList.get(i).getPl_id() + args + "'>";
%>
<tr align="center">
<td><%=seq-- %></td>
<td><%=pdtList.get(i).getCb_name() + "<br />" + pdtList.get(i).getCs_name() %></td>
<td><img src="/mvcMall/product/pdt_img/<%=pdtList.get(i).getPl_img1() %>" width="100" height="100" /></td>
<td align="left"><%=lnk + pdtList.get(i).getPl_id() + "<br />" + lnk + pdtList.get(i).getPl_name()%></a></td>
<td><%=pdtList.get(i).getPl_price() + "<br />" + pdtList.get(i).getPl_cost() %></td>
<td><%=pdtList.get(i).getPl_date().substring(0, 10) %><br />
<%=pdtList.get(i).getPl_view().equals("y") ? "게시" : "미게시" %></td>
</tr>
<%
		}
	} else {	// 한 행에서 보여줄 상품의 최대 개수가 3이나 4이면
		String lnk = "";
		for (int i = 0 ; i < pdtList.size() && i < psize ; i++) {
			lnk = "<a href='pdt_view.pdta?id=" + pdtList.get(i).getPl_id() + args + "'>";
			if (i % max == 0)	out.println("<tr align=\"center\">");
%>
<td>
	<div class="pdtBox<%=max%>">
		<%=lnk %><img src="/mvcMall/product/pdt_img/<%=pdtList.get(i).getPl_img1() %>" width="<%=max == 3 ? 250 : 190 %>" height="<%=max == 3 ? 200 : 140 %>" /></a><br />
		<%=lnk + pdtList.get(i).getPl_name() %></a><br />
		판매가 : <%=pdtList.get(i).getPl_price() %><br />
		할인가 : 
	</div>
</td>
<%
			if (i % max == max - 1)	out.println("</tr>");
		}
	}
} else {
	out.println("<tr><td align='center'>검색결과가 없습니다.</td></tr>");
}
%>
</table>
<br />
<table width="800" cellpadding="5">
<tr>
<td width="*">
<%
if (rcnt > 0) {	// 검색결과 상품들이 있을 경우에만 페이징을 함
	if (cpage == 1) {
		out.println("[<<]&nbsp;&nbsp;[<]&nbsp;&nbsp;");
	} else {
		out.print("<a href='pdt_list.pdta?cpage=1" + schArgs + "'>");
		out.println("[<<]</a>&nbsp;&nbsp;");
		out.print("<a href='pdt_list.pdta?cpage=" + (cpage - 1) + schArgs + "'>");
		out.println("[<]</a>&nbsp;&nbsp;");
	}

	for (int i = 1, j = spage ; i <= bsize && j <= pcnt ; i++, j++) {
		if (cpage == j) {
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
		} else {
			out.print("&nbsp;<a href='pdt_list.pdta?cpage=" + j + schArgs + "'>");
			out.println(j + "</a>&nbsp;");
		}
	}

	if (cpage == pcnt) {
		out.println("&nbsp;&nbsp;[>]&nbsp;&nbsp;[>>]");
	} else {
		out.print("&nbsp;&nbsp;<a href='pdt_list.pdta?cpage=" + (cpage + 1) + schArgs + "'>");
		out.println("[>]</a>");
		out.print("&nbsp;&nbsp;<a href='pdt_list.pdta?cpage=" + pcnt + schArgs + "'>");
		out.println("[>>]</a>");
	}
}
%>
</td>
<td width="10%">
	<input type="button" value="상품 등록" onclick="location.href='pdt_in_form.pdta';" />
</td>
</tr>
</table>
</body>
</html>
