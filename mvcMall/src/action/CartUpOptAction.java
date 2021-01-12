package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartUpOptAction implements Action {
	// 장바구니에 특정 상품의 옵션을 변경하는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String idx = request.getParameter("idx");	// 장바구니 아이디
		String pid = request.getParameter("pid");	// 상품 아이디
		String cnt = request.getParameter("cnt");	// 상품 아이디
		String opt = request.getParameter("opt");	// 변경할 옵션(들) - 없을 수도 있음(빈 문자열 들어감)
		String buyer, isMember = "n";	// 회원여부와 구매자 아이디로 where절에서 조건으로 사용됨
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

		CartUpOptSvc cartUpOptSvc = new CartUpOptSvc();
		int result = cartUpOptSvc.cartOptUpdate(opt, cnt, idx, pid, buyer, isMember);
		out.println(result);
		out.close();

		return null;
	}
}
