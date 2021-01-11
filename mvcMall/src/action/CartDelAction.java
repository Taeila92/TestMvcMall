package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartDelAction implements Action {
// 장바구니에 선택한 상품정보를 저장하는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String idx = request.getParameter("idx");		// 장바구니 아이디(들)
		
		String buyer, isMember = "n";
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if (loginMember == null) {	// 비회원이면
			buyer = session.getId();
		} else {	// 회원일 경우
			buyer = loginMember.getMlid();
			isMember = "y";
		}

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		CartDelSvc cartDelSvc = new CartDelSvc();
		int result = cartDelSvc.cartDelete(idx, buyer, isMember);
		out.println(result);
		out.close();
		return null; 
	}
}
