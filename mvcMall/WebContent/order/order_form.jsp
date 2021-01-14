<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
String kind = request.getParameter("kind");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<CartInfo> pdtList = (ArrayList<CartInfo>)request.getAttribute("pdtList");

String name = "", p1 = "", p2 = "", p3 = "", e1 = "", e2 = "",zip = "", addr1 = "", addr2 = "";
if (loginMember != null){	// 로그인한 회원일 경우
	name = loginMember.getMlname();
	String[] addPhone = loginMember.getMlphone().split("-");
	p1 = addPhone[0]; p2 = addPhone[1]; p3 = addPhone[2];
	String [] addEmail = loginMember.getMlemail().split("@");
	e1 = addEmail[0]; e2 = addEmail[1];
	zip = loginMember.getMazip();
	addr1 = loginMember.getMaaddr1();
	addr2 = loginMember.getMaaddr2();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.box{ border:1px solid black; width:600px; padding:10px; margin-bottom:10px;}
</style>
<script>
function sameChk(chk){
	var frm = document.frmOrder;
	if(chk == 'n'){ //구매자 정보와 다를 경우
		frm.rname.value="";		frm.rp1.value="010";	frm.rp2.value="";
		frm.rp3.value="";		frm.re1.value="";		frm.re2.value="";
		frm.rzip.value="";		frm.raddr1.value="";	frm.raddr2.value="";
	
	} else { //구매자 정보와 같을 경우
		frm.rname.value="<%=name%>";
		frm.rp1.value= "<%=p1%>";
		frm.rp2.value= "<%=p2%>";
		frm.rp3.value= "<%=p3%>";
		frm.re1.value= "<%=e1%>";
		frm.re2.value= "<%=e2%>";
		frm.rzip.value= "<%=zip%>";
		frm.raddr1.value= "<%=addr1%>";
		frm.raddr2.value= "<%=addr2%>";
	}
}
</script>
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
	<img src="/mvcMall/product/pdt_img/<%=pdtList.get(i).getPl_img1() %>" width="50" height="50" align="absmiddle" style="margin:3px 0;"/>
	<%=crt.getPl_name() %> <%=option + " / " + crt.getCl_cnt() + "ea / " + crt.getPrice() %><br/>
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
<%if(loginMember != null) {	// 로그인한 회원일경우 %>
	이름 : <%=name %><br/>
	전화번호 : <%=loginMember.getMlphone() %><br/>
	이메일 : <%=loginMember.getMlemail() %>
<%} else { 	// 비회원일 경우%>
	이름 : <input type="text" name="bname" /><br/>
	전화번호 : <select name="bp1" />
				<option value="010">010</option>
				<option value="011">011</option>
				<option value="017">017</option>
				<option value="016">016</option>
			</select> - 
			<input type="text" name="bp2" /> - 
			<input type="text" name="bp3" /><br/>
	이메일 : <input type="text" name="be1" /> @ 
			<select name="be2">
				<option value="">도메인선택</option>
				<option value="naver.com">네이버</option>
				<option value="nate.com">네이트</option>
				<option value="gmail.com">지메일</option>
				<option value="testMall.com">테스트몰</option>
			</select>
<%} %>
</div>
<h3>배송지 정보</h3>
구매자 정보와 <input type="radio" name="isSame" value="y" onclick="sameChk('y');" /> 같습니다.
 <input type="radio" name="isSame" value="n" checked="checked" onclick="sameChk('n');" /> 같지않습니다.
<div class="box">
	이름 : <input type="text" name="rname" /><br/>
	전화번호 : <select name="rp1" />
				<option value="010">010</option>
				<option value="011">011</option>
				<option value="017">017</option>
				<option value="016">016</option>
			</select> - 
			<input type="text" name="rp2" style="width:15px;" /> - 
			<input type="text" name="rp3" style="width:15px;" /><br/>
	이메일 : <input type="text" name="re1" /> @ 
			<select name="re2">
				<option value="">도메인선택</option>
				<option value="naver.com">네이버</option>
				<option value="nate.com">네이트</option>
				<option value="gmail.com">지메일</option>
				<option value="testMall.com">테스트몰</option>
			</select><br/>
	우편번호 : 	<input type="text" name="rzip" /><br/>
	주소 : 	<input type="text" name="raddr1" /> <input type="text" name="raddr2" />
</div>
<h3>결제 정보</h3>
<div class="box">
	<input type="radio" name="payment" value="a"/> 카드결제<br/>
	<input type="radio" name="payment" value="b"/> 계좌이체<br/>
	<input type="radio" name="payment" value="c"/> 무통장 입금
	<p style="text-align:right;">총 결제 금액 : <%=total %> 원</p>
</div>
<div style="width:600px; margin:20px; text-align:center;">
	<input type="submit" value="상품 구매"/>&nbsp;&nbsp;&nbsp;
	<input type="button" value="취소" onclick="history.back();"/>
</div>
<input type="hidden" name="total" value="<%=total %>"/>
</form>
</body>
</html>