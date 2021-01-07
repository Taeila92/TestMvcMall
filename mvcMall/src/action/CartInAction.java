package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartInAction implements Action {
// 장바구니에 선택한 상품정보를 저장하는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String plid = request.getParameter("id");		// 상품아이디
		String cnt = request.getParameter("cnt");		// 구매 수량
		String price = request.getParameter("price");	// 실구매가
		int optCnt = 0;
		String optValue = "";
		if (request.getParameter("optCnt") != null) {
			optCnt = Integer.parseInt(request.getParameter("optCnt"));	// 옵션의 개수
			for (int i = 0 ; i < optCnt ; i++) {
				optValue += "," + request.getParameter("opt" + i);
			}
			optValue = optValue.substring(1);
		}

		String buyer, isMember = "n";
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if (loginMember == null) {	// 비회원이면
			buyer = session.getId();
		} else {	// 회원일 경우
			buyer = loginMember.getMlid();
			isMember = "y";
		}
		
		CartInfo cart = new CartInfo();
		cart.setCl_buyer(buyer);
		cart.setCl_ismember(isMember);
		cart.setPl_id(plid);
		cart.setCl_opt(optValue);
		cart.setCl_cnt(Integer.parseInt(cnt));
		
		CartInSvc cartInSvc = new CartInSvc();
		int result = cartInSvc.cartInsert(cart);
		if (result == 0) {	// 카트등록에 실패했으면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('장바구니에 담기가 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		ActionForward forward = new ActionForward();
		forward.setPath("cart_list.ord" + request.getParameter("args"));	// 이동할 URL 지정
		forward.setRedirect(true);
		return forward;
	}
}
