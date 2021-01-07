<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
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

PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");

String plstock = String.valueOf(pdtInfo.getPl_stock());
if (plstock.equals("-1"))	plstock = "무제한";

String opt = "";
if (pdtInfo.getPl_opt() != null && !pdtInfo.getPl_opt().equals("")) {
	if (pdtInfo.getPl_opt().indexOf(':') > -1) {
		String[] arrOpt = pdtInfo.getPl_opt().split(":");
		for (int i = 0 ; i < arrOpt.length ; i++) {
			opt += arrOpt[i].substring(0, arrOpt[i].indexOf(',')) + "옵션 : ";
			opt += arrOpt[i].substring(arrOpt[i].indexOf(',') + 1).replace(",", ", ") + "<br />";
		}
		opt = opt.substring(0, opt.length() - 6);
	} else {
		opt = pdtInfo.getPl_opt().substring(0, pdtInfo.getPl_opt().indexOf(',')) + "옵션 : ";
		opt += pdtInfo.getPl_opt().substring(pdtInfo.getPl_opt().indexOf(',') + 1).replace(",", ", ") + "<br />";
	}
} else	opt = "옵션 없음";

float rate = (float)pdtInfo.getPl_discount() / 100;
int dcPrice = Math.round(pdtInfo.getPl_price() - (pdtInfo.getPl_price() * rate));
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
<td width="*"><!-- 상품명, 옵션, 가격, 재고량, 할인율, 등록일, 원가, 판매량, 관리자 번호 -->
	<table width="100%" cellpadding="8">
	<tr>
	<td width="100">분류</td>
	<td width="*"><%=pdtInfo.getCb_name() + " - " + pdtInfo.getCs_name() %></td>
	</tr>
	<tr><td>상품명</td><td><%=pdtInfo.getPl_name() %></td></tr>
	<tr><td>브랜드</td><td><%=pdtInfo.getBl_name() %></td></tr>
	<tr><td>원산지</td><td><%=pdtInfo.getPl_orig() %></td></tr>
	<tr><td>가격</td><td><%=pdtInfo.getPl_price() %></td></tr>
	<tr><td>원가</td><td><%=pdtInfo.getPl_cost() %></td></tr>
	<tr><td>할인율</td><td><%=pdtInfo.getPl_discount() %>% (할인가격 : <%=dcPrice %>)</td></tr>
	<tr><td>재고량</td><td><%=plstock %></td></tr>
	<tr><td>판매량</td><td><%=pdtInfo.getPl_salecnt() %></td></tr>
	<tr><td>옵션</td><td><%=opt %></td></tr>
	<tr><td>등록일</td><td><%=pdtInfo.getPl_date() %></td></tr>
	<tr><td>등록자</td><td><%=pdtInfo.getAl_idx() %></td></tr>
	</table>
</td>
</tr>
<tr><td colspan="2" align="center"><hr width="100%" /></td></tr>
<tr><td colspan="2" align="center">
	<img src="/mvcMall/product/pdt_img/<%=pdtInfo.getPl_desc() %>" width="780" />
</td></tr>
<tr><td colspan="2" align="center"><hr width="100%" /></td></tr>
<tr><td colspan="2" align="center">
	<input type="button" value="목록" onclick="location.href='pdt_list.pdta<%=args %>';" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="수정" onclick="location.href='pdt_up_form.pdta<%=args %>&id=<%=id %>';" />
<% if (pdtInfo.getPl_salecnt() == 0) {	// 판매된 적이 없을 경우에만 삭제 가능 %>
<script>
function notCool(id) {
	if (confirm("정말 삭제하시겠습니까?\n삭제하면 복구가 불가능 합니다.")) {
		location.href="pdt_del_proc.pdta?id=" + id;
	}
}
</script>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="삭제" onclick="notCool('<%=id %>');" />
<% } %>
</td></tr>
</table>
<br /><br />
</body>
</html>
