package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import action.*;
import vo.*;


@WebServlet("*.free")
//url이 뭐든 마지막 다섯글자가 '.free'인 url을 받아 옴
public class FreeCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FreeCtrl() {
        super();
    }

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// 사용자의 요청이 get이든 post이든 모두 처리하는 메소드
	// 자유게시판에 대한 모든 요청을 처리함
		request.setCharacterEncoding("utf-8");
		String requestUri = request.getRequestURI();
		// URI(도메인과 쿼리스트링을 뺀 주소 문자열) : /mvcMall/brd_list.free
		String contextPath = request.getContextPath();
		// URI에서 파일명 부분을 제외한 문자열 : /mvcMall
		String command = requestUri.substring(contextPath.length());
		// requestUri에서 contextPath를 뺀 문자열 : /brd_list.free
		
		ActionForward forward = null;
		Action action = null;
		// 인터페이스 action형으로 인스턴스 선언(인터페이스이므로 생성은 불가)
		
		// 사용자의 요청 종류에 따라 각각 다른 action을 취함
		switch (command) {
		case "/brd_list.free" :	// 게시판 목록 보기
			action = new FreeListAction();
			// 인터페이스이지만 상속받은 상위클래스처럼 동작하여 인스턴스를 생성
			break;
		case "/brd_form.free" :	// 게시글 등록/수정 폼
			action = new FreeFormAction();
			break;
		case "/brd_view.free" :	// 게시글 보기
			action = new FreeViewAction();
			break;
		case "/brd_pwd.free" :	// 비밀번호 입력 폼 처리
			action = new FreePwdAction();
			break;
		case "/brd_proc.free" :	// 게시글 처리
			action = new FreeProcAction();
			break;
		}

		try {
			forward = action.execute(request, response);
			// action 형 인스턴스 execute()메소드는 오버라이딩 되어 있으므로 하위클래스에 해당하는
			// Free~Action 클래스의 execute()메소드가 실행됨
			
		}catch(Exception e){e.printStackTrace();}
		
		if(forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
				// RequestDispatcher를 통해 페이지를 이동시킴
				// RequestDispatcher를 통해 페이지를 이동시키면 이동한 페이지의 URL이 변하지 않고, 이동한 페이지로 현재 가지고 있는 
				// request와 response객체를 그대로 넘겨줌
			}
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}
