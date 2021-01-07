package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartListAction implements Action {
// 장바구니에서 보여줄 해당 사용자의 장바구니 목록을 가져오는 클래스
		public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			request.setCharacterEncoding("utf-8");
			ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();

			String where = "";
			HttpSession session = request.getSession();
			MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
			if (loginMember == null) {	// 비회원이면
				where = " and c.cl_buyer = '" + session.getId() + "' and c.cl_ismember = 'n' ";
			} else {	// 회원일 경우
				where = " and c.cl_buyer = '" + loginMember.getMlid() + "' and c.cl_ismember = 'y' ";
			}
			CartListSvc cartListSvc = new CartListSvc();
			cartList = cartListSvc.getCartList(where);
			request.setAttribute("cartList", cartList);
			
			ActionForward forward = new ActionForward();
			forward.setPath("/order/cart_list.jsp");	// 이동할 URL 지정
			return forward;
		}
}
