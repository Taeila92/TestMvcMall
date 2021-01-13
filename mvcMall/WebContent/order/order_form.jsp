<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
String kind = request.getParameter("kind");
ArrayList<CartInfo> pdtList = (ArrayList<CartInfo>)request.getAttribute("pdtList");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.box{ border:1px solid black; width:600px; padding:10px; margin-bottom:10px;}
</style>
</head>
<body>
<h2>상품 주문서 작성</h2>
<form name="frmOrder" action="order_proc.ord" method="post">
<h3>구매할 상품</h3>
<div class="box">
<%
int total = 0;	// 총 구매가격을 저장할 변수
if (pdtList != null && pdtList.size() > 0) {	// 구매할 상품이 있으면
	for (int i = 0 ; i < pdtList.size() ; i++) {
		CartInfo crt = pdtList.get(i);
		
		String option = "해당 상품은 옵션이 없음";
		String opts = crt.getPl_opt();	// 상품이 가지는옵션
		String opt = crt.getCl_opt();	// 사용자가 선택한 옵션
		if (opts != null && !opts.equals("")) {		// 해당 상품에 옵션이 있으면
			String[] arrOpt = opts.split(":");		// 옵션의 종류를 배열로 생성
			String[] arrChoose = opt.split(",");	// 선택한 옵션값을 배열로 생성
			option ="";
			for (int j = 0 ; j < arrOpt.length ; j++) {
				String[] arrTmp = arrOpt[j].split(",");
				option += " / " + arrTmp[0] + " : " + arrChoose[j];
			}
			option = option.substring(3);
		}
%>
	<img src="/mvcMall/product/pdt_img/<%=pdtList.get(i).getPl_img1() %>" width="50" height="50" align="absmiddle" />
	<%=crt.getPl_name() %><br/><%=option%><br/>
<%
		total += crt.getPrice();
	}
} else { // 구매할 상품이 없으면
%>
<script>
	alert("잘못된 경로로 들어오셨습니다.");
	history.back();
</script>
<%	
}
%>
</div>
<h3>구매자 정보</h3>
<div class="box">
</div>
<h3>배송지 정보</h3>
<div class="box">
</div>
<h3>결제 정보</h3>
<div class="box">
</div>
<div style="width:600px; margin:20px; text-align:center;">
	<input type="submit" value="상품 구매"/>&nbsp;&nbsp;&nbsp;
	<input type="button" value="취소" onclick="history.back();"/>
</div>
<input type="hidden" name="total" value="<%=total %>"/>
</form>
</body>
</html>