<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
ArrayList<CartInfo> cartList = (ArrayList<CartInfo>)request.getAttribute("cartList");

int cpage = 1, psize = 12;
if (request.getParameter("cpage") != null)
	cpage = Integer.parseInt(request.getParameter("cpage"));
if (request.getParameter("psize") != null)
	psize = Integer.parseInt(request.getParameter("psize"));
String args = "?cpage=" + cpage + "&psize=" + psize;
String keyword, bcata, scata, brand, sprice, eprice, ord;
keyword = request.getParameter("keyword");	bcata	= request.getParameter("bcata");
scata	= request.getParameter("scata");	brand	= request.getParameter("brand");
sprice	= request.getParameter("sprice");	eprice	= request.getParameter("eprice");
ord 	= request.getParameter("ord");

if (bcata != null && !bcata.equals(""))		args += "&bcata=" + bcata;
if (scata != null && !scata.equals(""))		args += "&scata=" + scata;
if (brand != null && !brand.equals(""))		args += "&brand=" + brand;
if (sprice != null && !sprice.equals(""))	args += "&sprice=" + sprice;
if (eprice != null && !eprice.equals(""))	args += "&eprice=" + eprice;
if (keyword != null && !keyword.equals(""))	args += "&keyword=" + keyword;
if (ord != null && !ord.equals(""))			args += "&ord=" + ord;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#cartTable th { border-bottom:3px black double; }
#cartTable td { border-bottom:1px black solid; }
</style>
<script src="jquery-3.5.1.js"></script>
<script>
function chOptCnt(idx, pid, oIdx, len) {
// 상품의 옵션과 수량을 변경시키는 함수
// idx : t_cart_list테이블의 PK로 where 절에서 조건으로 사용할 매개변수
// pid : 상품 아이디로 기존의 장바구니내 상품과 비교하기 위한 매개변수
// oIdx : cartList의 인덱스번호로 옵션과 수량 컨트롤의 이름을 구하기 위한 매개변수
// len : 옵션 컨트롤의 이름은 idx만으로는 unique하지 않으므로 옵션의 개수를 받아 이름을 구함
	var frm = document.frmCart;
	var opt = "";	// 변경할 옵션 값(들)을 저장할 변수(값1,값2,값3,...)
	var cnt = 1;	// 변경할 수량 값을 저장할 변수
	if (len > 0) {	// 변경할 옵션이 있으면
		for (var i = 0 ; i < len ; i++) {
			opt += "," + eval("frm.opt" + oIdx + i + ".value");
		}
		opt = opt.substring(1);
	}
	cnt = eval("frm.cnt" + oIdx + ".value");

	$.ajax({
		type : "POST", 
		url : "/mvcMall/cart_up.ord", 
		data : { "idx" : idx, "pid" : pid, "opt" : opt, "cnt" : cnt }, 
		success : function(chkRst) {
			if(chkRst == 0)		alert("선택한 상품 수정에 실패했습니다.\n다시 시도해 주십시오.");
			else				location.reload();
		}
	});
}

function notCool(idx) {
	if (confirm("해당 상품을 장바구니에서 삭제하시겠습니까?")) {
		$.ajax({
			type : "POST", 
			url : "/mvcMall/cart_del.ord", 
			data : { "idx" : idx }, 
			success : function(chkRst) {
				if(chkRst == 0)		alert("선택한 상품 삭제에 실패했습니다.\n다시 시도해 주십시오.");
				else				location.reload();
			}
		});
	}
}

function chkAll(all) {
	var arrChk = document.frmCart.chk;
	// 폼(frmCart) 안에 chk라는 이름의 컨트롤이 여러 개 있으므로 배열로 변환하여 받아 옴
	for (var i = 0 ; i < arrChk.length ; i++) {
		arrChk[i].checked = all.checked;
	}
}
</script>
</head>
<body>
<h2>장바구니 화면</h2>
<form name="frmCart" action="ord_form.ord" method="post">
<table width="700" cellpadding="5" cellspacing="0" id="cartTable">
<tr>
<th width="5%"><input type="checkbox" checked="checked" onclick="chkAll(this);" /></th>
<th width="*">상품</th><th width="25%">옵션</th>
<th width="10%">수량</th><th width="10%">가격</th><th width="10%">삭제</th>
</tr>
<%
if (cartList != null && cartList.size() > 0) {	// 장바구니에 데이터가 들어 있으면
	int total = 0;	// 총 구매가격을 저장할 변수
	for (int i = 0 ; i < cartList.size() ; i++) {
		String lnk = "<a href='pdt_view.pdt" + args + "&id=" + cartList.get(i).getPl_id() + "'>";
		int max = cartList.get(i).getPl_stock();
		String msg = "";
		if (max == -1)		max = 100;	// 수량 선택 최대값으로 재고량이 무제한인 상품의 최대값
		else if (cartList.get(i).getPl_stock() < cartList.get(i).getCl_cnt()) {
			msg = "선택하신 구매수량이 재고량을 초과 하였으므로 재고량만큼만 가져가슈.";
		}
%>
<tr align="center">
<td><input type="checkbox" name="chk" value="<%=cartList.get(i).getCl_idx()%>" checked="checked" /></td>
<td align="left">
	<%=lnk%><img src="/mvcMall/product/pdt_img/<%=cartList.get(i).getPl_img1() %>" width="50" height="50" align="absmiddle" />
	<%=cartList.get(i).getPl_name() %></a>
</td>
<td>
<%
		String opt = cartList.get(i).getCl_opt();	// 사용자가 선택한 옵션(들)
		String opts = cartList.get(i).getPl_opt();	// 상품이 가지는 원래 옵션(선택할 수 있게 하기 위해)
		if (opts != null && !opts.equals("")) {		// 해당 상품에 옵션이 있으면
			String[] arrOpt = opts.split(":");		// 옵션의 종류를 배열로 생성
			String[] arrChoose = opt.split(",");	// 선택한 옵션값을 배열로 생성
			for (int j = 0 ; j < arrOpt.length ; j++) {
				String[] arrTmp = arrOpt[j].split(",");
				out.println(arrTmp[0] + " : ");
				out.println("<select name='opt" + i + j + "' onchange='chOptCnt(" + cartList.get(i).getCl_idx() + ", \"" + cartList.get(i).getPl_id() + "\", " + i + ", " + arrOpt.length + ");'>");
				for (int k = 1 ; k < arrTmp.length ; k++) {
					String slt = "";
					if (arrChoose[j].equals(arrTmp[k]))	slt = " selected='selected'";
					out.println("<option value='" + arrTmp[k] + "'" + slt + ">" + arrTmp[k] + "</option>");
				}
				out.println("</select><br />");
			}
		} else {	// 해당 상품에 옵션이 없으면
			out.println("옵션없음");
		}
%>
</td>
<td>
	<select name="cnt<%=i%>">
<%		for (int j = 1 ; j <= max ; j++) { %>
		<option value="<%=j%>" <% if (j == cartList.get(i).getCl_cnt()) { %>selected<% } %>><%=j%></option>
<%		} %>
	</select>
</td>
<td><%=cartList.get(i).getPrice() * cartList.get(i).getCl_cnt() %></td>
<td><input type="button" value="삭제" onclick="notCool(<%=cartList.get(i).getCl_idx()%>);" /></td>
</tr>
<%
		total += cartList.get(i).getPrice() * cartList.get(i).getCl_cnt();
	}
%>
</table>
<table width="700" cellpadding="25" cellspacing="0">
<tr><td align="right">총 구매가격 : <span id="total"><%=total %></span> 원</td></tr>
<%
} else {	// 장바구니에 데이터가 없으면
	out.println("<tr><td colspan='5'>장바구니가 비었습니다.</td></tr>");
}
%>
</table>
</form>
</body>
</html>
