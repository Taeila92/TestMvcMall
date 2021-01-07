package admin.action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import admin.svc.*;
import vo.*;

public class PdtDelAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PdtDelProcSvc pdtDelProcSvc = new PdtDelProcSvc();
		String id = request.getParameter("id");
		// ������ ���̵� �ش��ϴ� ��ǰ�� �������� PdtInfo�� �ν��Ͻ��� �޾� ��
		boolean isSuccess = pdtDelProcSvc.pdtDelete(id);
		if (!isSuccess) {	// ��ǰ��Ͽ� ����������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��ǰ ������ �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		ActionForward forward = new ActionForward();
		forward.setPath("pdt_list.pdta");
		forward.setRedirect(true);
		return forward;
	}
}
