package action;

import javax.servlet.http.*; // �޾� ��reqeust�� response�� ���� import
import java.io.PrintWriter;
import svc.*;
import vo.*;

public class FreeFormAction implements Action{
// ���� �Խ��� �� ��� �� ���� �������� �̵��� ���� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");
		ActionForward forward = new ActionForward();
		if(wtype.equals("up")) {	// �� �����̸�			
			HttpSession session = request.getSession();
			// JSP�� �ƴϹǷ� HttpSession�ν��Ͻ��� ���� ������ ��
			MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
			String uid = null;
			if(loginMember != null) uid = loginMember.getMlid();
			// ���� �α����� ȸ���� ���̵� uid�� ����
			
			int idx = Integer.parseInt(request.getParameter("idx"));
			// �۹�ȣ�� ������ ����ȯ ��Ŵ
			String pwd = request.getParameter("pwd");
			String mem = request.getParameter("mem");
			
			FreeFormSvc freeFormSvc = new FreeFormSvc();
			FreeInfo article = freeFormSvc.getArticleUp(idx, mem, uid, pwd);
			if(article != null) {
				request.setAttribute("article", article);
				// ������ �Խñ� �����͸� request��ü�� ����
			} else {
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('��й�ȣ�� Ʋ�Ƚ��ϴ�.')");
				out.println("history.back();");
				out.println("</script>");
			}
		} 
		forward.setPath("/bbs/free_form.jsp");
		// �̵��� URL ����
		
		return forward;
	}
}