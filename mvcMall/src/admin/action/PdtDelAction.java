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
		// 지정한 아이디에 해당하는 상품의 정보들을 PdtInfo형 인스턴스로 받아 옴
		boolean isSuccess = pdtDelProcSvc.pdtDelete(id);
		if (!isSuccess) {	// 상품등록에 실패했으면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('상품 삭제에 실패했습니다.');");
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
