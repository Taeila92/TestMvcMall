package action;

import javax.servlet.http.*;
import vo.*;

public class FreePwdAction implements Action {
	// ��ȸ���� �����̳� ������ ��й�ȣ�� �޴� ������ �̵���Ű�� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		ActionForward forward = new ActionForward();
		forward.setPath("/bbs/free_pwd.jsp");
		return forward;
	}
}
