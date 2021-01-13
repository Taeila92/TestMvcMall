package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class OrdFormAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String kind = request.getParameter("kind");
		// ��ٱ��� �Ǵ� �ٷ� ���Ÿ� �����ϴ� ������(cart : ��ٱ��ϸ� ���� ����, direct : �ٷα���)
		ArrayList<CartInfo> pdtList = new ArrayList<CartInfo>();
		// �����Ϸ��� ��ǰ(��)�� ���� ArrayList
		String where = "";
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if(kind.equals("cart")) {
			if (loginMember == null) {	// ��ȸ���̸�
				where = " and c.cl_buyer = '" + session.getId() + "' and c.cl_ismember = 'n' ";
			} else {	// ȸ���� ���
				where = " and c.cl_buyer = '" + loginMember.getMlid() + "' and c.cl_ismember = 'y' ";
			}
		}
		OrdFormSvc ordFormSvc = new OrdFormSvc();
		pdtList = ordFormSvc.getOrdFrmPdtList(where, kind);
		request.setAttribute("pdtList", pdtList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/order/order_form.jsp");	// �̵��� URL ����
		return forward;
	}
}
