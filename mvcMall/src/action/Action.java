package action;

import javax.servlet.http.*;
import vo.ActionForward;

// 여러 기능에서 사용되는 포워딩 정보를 동일한 메소드로 처리하게 하기 위한 인터페이스
public interface Action {
	ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
