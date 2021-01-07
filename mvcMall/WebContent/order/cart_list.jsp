<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
ArrayList<CartInfo> cartList = (ArrayList<CartInfo>)request.getAttribute("cartList");

int cpage = 0, psize = 0;
if(request.getParameter("cpage") != null)
	cpage = Integer.parseInt(request.getParameter("cpage"));
if(request.getParameter("psize") != null)
	psize = Integer.parseInt(request.getParameter("psize"));

String args = "";
if (cpage > 0 && psize > 0)		args = "?cpage=" + cpage + "&psize=" + psize;
String id, keyword, bcata, scata, brand, sprice, eprice, ord;
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
<script>
function notCool(idx){
	if (confirm("해당 상품을 장바구니에서 삭제하시겠습니까?")){
		location	
	}
}

function chkAll(all){
	var arrChk = document.frmCart.chk;
	// 폼 (frmCart) 안에 chk라는 이름의 컨트롤이 여러개있으므로 배열로 변환하여 받아옴
	for(var i = 0 ; i < arrChk.length ; i++){
		arrChk[i].checked = all.checked;
	}
}
</script>
</head>
<body>
<form name="frmCart" action="ord_form.rod" method="post">
<table width="700" cellpadding="5">      
<tr>
<th width="5%"><input type="checkbox" checked="checked" id="checkAll" onclick="chkAll(this);"/></th>
<th width="*%">상품</th><th width="25%">옵션</th>
<th width="10%">수량</th><th width="10%">가격</th>
<th width="10%">삭제</th>
</tr>
<%
if (cartList != null && cartList.size() > 0) {	// 장바구니에 데이터가 들어 있으면
	for (int i = 0; i < cartList.size(); i ++) {	
		String lnk = "<a href='pdt_view.pdt" + args + "&id=" + cartList.get(i).getPl_id() + "'>";
		int max = cartList.get(i).getPl_stock();
		String msg = "";
		if (max == -1)		max = 100;	// 수량 선택 최대값으로 재고량이 무제한인 상품의 최대값
		else if (cartList.get(i).getPl_stock() < cartList.get(i).getCl_cnt()) {
			msg = "선택하신 구매수량이 재고량을 초과하였으므로 재고량만큼만 가져가슈.";
		}
%>
<tr>
<td><input type="checkbox" name="chk" value="<%=cartList.get(i).getCl_idx() %>" checked="checked" /></td>
<td align="left">
	<%=lnk%><img src="/mvcMall/product/pdt_img/<%=cartList.get(i).getPl_img1() %>" width="50" />
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
				out.println("<select name='opt" + j + "'>");
				for (int k = 1 ; k < arrTmp.length ; k++) {
					String slt = "";
					if(arrChoose[j].equals(arrTmp[k])) slt ="selected='selected'";
					out.println("<option value='" + arrTmp[k] + "'" + slt + ">" + arrTmp[k] + "</option>");
				}
				out.println("</select><br/>");
			}
		} else {	// 해당 상품에 옵션이 없으면
			out.println("옵션없음");
		}
%>
</td>
<td>
	<select name="cnt">
<% 		for (int j = 1; j <= max; j++) { %>
	<option value="<%=j%>" <% if (j == cartList.get(i).getCl_cnt()) { %>selected<% } %>><%=j%></option>
<%		} %>
	</select>
</td>
<td><%=cartList.get(i).getPrice() * cartList.get(i).getCl_cnt() %></td>
<td>
	<input type="button" value="삭제" onclick="notCool(<%=cartList.get(i).getCl_idx() %>)" />
</td>
</tr>
<%
	}
%>
<tr><th colspan="5" align="right">
	총구매가격
<%
} else {	// 장바구니에 데이터가 없으면
	out.println("<tr><td colspan='5'>장바구니가 비었습니다.</td></tr>");
}
%>
</table>
</form>
</body>
</html>