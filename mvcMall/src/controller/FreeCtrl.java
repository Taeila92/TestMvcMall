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
		request.setCharacterEncoding("utf-8");
		String requestUri = request.getRequestURI();
		// URI(도메인과 쿼리스트링을 뺀 주소 문자열) : /mvcMall/brd_list.free
		String contextPath = request.getContextPath();
		// URI에서 파일명 부분을 제외한 문자열 : /mvcMall
		String command = requestUri.substring(contextPath.length());
		// requestUri에서 contextPath를 뺀 문자열 : /brd_list.free
		
		ActionForward forward = null;
		Action action = null;
		
		switch (command) {
		case "/brd_list.free" :	// 게시판 목록 보기
			action = new FreeListAction();
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
		}catch(Exception e){e.printStackTrace();}
		
		if(forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
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
