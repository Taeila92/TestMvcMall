package action;

import java.io.PrintWriter;	// 등록 실패시 자바스크립트 사용을 위한 import
import javax.servlet.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

public class FreeProcAction implements Action{
// 게시글 등록 처리 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ActionForward forward = null;
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");
		FreeInfo freeInfo = new FreeInfo();
		// 사용자가 입력한 데이터들을 저장할 인스턴스
		if (wtype.equals("in") || wtype.equals("up")) {
		// 글 등록이나 수정일 경우 게시글 데이터들을 받아옴
			freeInfo.setFl_writer(request.getParameter("writer"));
			freeInfo.setFl_pwd(request.getParameter("pwd"));
			freeInfo.setFl_title(request.getParameter("title"));
			freeInfo.setFl_content(request.getParameter("content"));
		}
		if(wtype.equals("del") || wtype.equals("up")) {
		// 글 삭제나 수정일 경우 게시글 번호를 받아옴
			int idx = Integer.parseInt(request.getParameter("idx"));
			freeInfo.setFl_idx(idx);
		}
		
		FreeProcSvc freeProcSvc = new FreeProcSvc();
		boolean isSuccess = false;
		String link = null;
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if(wtype.equals("in")) {
			freeInfo.setFl_ismember("n");
			if(loginMember != null)	{	// 회원 글등록일 경우
				freeInfo.setFl_ismember("y");
				freeInfo.setFl_writer(loginMember.getMlid());
			}
			freeInfo.setFl_ip(request.getRemoteAddr());
			isSuccess = freeProcSvc.freeInsert(freeInfo);	// 글등록
			link = "brd_list.free";
		} else if (wtype.equals("up")) {
			link = "brd_view.free";
		} else {
			link = "brd_list.free";			
		}
		
		if(isSuccess) {
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath(link);
		}
		
		return forward;
	}
}
