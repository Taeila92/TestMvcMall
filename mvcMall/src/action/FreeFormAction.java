package action;

import javax.servlet.http.*; // 받아 온reqeust와 response를 위해 import
import java.io.PrintWriter;
import svc.*;
import vo.*;

public class FreeFormAction implements Action{
// 자유 게시판 글 등록 및 수정 폼으로의 이동을 위한 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");
		ActionForward forward = new ActionForward();
		if(wtype.equals("up")) {	// 글 수정이면			
			HttpSession session = request.getSession();
			// JSP가 아니므로 HttpSession인스턴스를 직접 만들어야 함
			MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
			String uid = null;
			if(loginMember != null) uid = loginMember.getMlid();
			// 현재 로그인한 회원의 아이디를 uid에 저장
			
			int idx = Integer.parseInt(request.getParameter("idx"));
			// 글번호를 정수로 형변환 시킴
			String pwd = request.getParameter("pwd");
			String mem = request.getParameter("mem");
			
			FreeFormSvc freeFormSvc = new FreeFormSvc();
			FreeInfo article = freeFormSvc.getArticleUp(idx, mem, uid, pwd);
			if(article != null) {
				request.setAttribute("article", article);
				// 수정할 게시글 데이터를 request객체에 담음
			} else {
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('비밀번호가 틀렸습니다.')");
				out.println("history.back();");
				out.println("</script>");
			}
		} 
		forward.setPath("/bbs/free_form.jsp");
		// 이동할 URL 지정
		
		return forward;
	}
}