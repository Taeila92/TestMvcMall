package action;

import javax.servlet.http.*;
import vo.ActionForward;

public class FreePwdAction implements Action {
// 비회원 글 수정이나 삭제시 비밀번호를 받는 폼으로 이동시키는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	// 게시글 암호 입력 폼으로 이동하는 객체 생성 후 리턴하는 메소드
		ActionForward forward = new ActionForward();
		forward.setPath("/bbs/free_pwd.jsp");
		// 암호 입력 폼으로 이동하기만 하면 되므로 ~Svc 클래스를 호출할 필요가 없음
		return forward;
	}
}
