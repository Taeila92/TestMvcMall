package action;

import javax.servlet.http.*;
import vo.*;

public class FreePwdAction implements Action {
	// 비회원글 수정이나 삭제시 비밀번호를 받는 폼으로 이동시키는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		ActionForward forward = new ActionForward();
		forward.setPath("/bbs/free_pwd.jsp");
		return forward;
	}
}
