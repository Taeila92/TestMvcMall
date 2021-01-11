package controller;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.ord")
public class OrdCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public OrdCtrl() {
        super();
    }

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// 사용자의 요청이 get이든 post이든 모두 처리하는 메소드
		request.setCharacterEncoding("utf-8");
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());

		ActionForward forward = null;
		Action action = null;

		switch (command) {
			case "/cart_in.ord" :			// 장바구니 등록 기능
				action = new CartInAction();		break;
			case "/cart_list.ord" :			// 장바구니 화면
				action = new CartListAction();		break;
			case "/cart_del.ord" :			// 장바구니 삭제 기능
				action = new CartDelAction();		break;
			case "/cart_up.ord" :			// 장바구니 수정 기능
				action = new CartUpAction();		break;
		}

		try {
			forward = action.execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = 
					request.getRequestDispatcher(forward.getPath());
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
