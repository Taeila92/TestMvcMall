package controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import svc.*;	// 다른 클래스를 호출하기 위해 import 됨
import vo.*;	// 메소드의 실행결과인 인스턴스를 받기 위해 import됨

@WebServlet("/login")	// URL에서 클래스명 대신 사용할 매핑시킨 별칭
public class LoginCtrl extends HttpServlet {
// 로그인 창에서 입력한 아이디와 비밀번호를 받아 로그인 작업을 시작하는 서블릿
	private static final long serialVersionUID = 1L;
    public LoginCtrl() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 사용자의 요청으로 받은 데이터들의 인코딩방식을 유니코드로 지정함
		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");
		// 사용자가 입력한 값이 들어있는 컨트롤(uid, pwd)의 값을 각각  uid와 pwd로 받음
		// 로그인에 필요한 아이디와 비밀번호를 문자열로 받아 옴
		LoginSvc loginSvc = new LoginSvc();
		// LoginSvc 형인스턴스 loginSvc를 선언 및 생성
		// 로그인에 필요한 작업을 위해 로그인 기능의 메소드를 가진 인스턴스 생성
		MemberInfo loginMember = loginSvc.getLoginMember(uid, pwd);
		// 사용자에게 받은 아이디와 비밀번호를 인수로하는 loginSvc 인스턴스의 
		// getLoginMember()메소드를 실행하고 그 결과를 loginMember에 담음
		// loginMember에는 로그인에 성공한 회원의 데이터가 담겨있음
		// 단, 로그인 실패시 null값이 들어옴
		if (loginMember != null) {	// 로그인 성공시
			HttpSession session = request.getSession();
			// jsp와 다르게 서블릿에서는 세션을 사용하기 위해 직접 세션 인스턴스를 생성해야 함
			session.setAttribute("loginMember", loginMember);
			// 로그인 상태를 유지하기 위해 세션 속성으로 저장
			response.sendRedirect("index.jsp");
			// 서버에서 index.jsp로 응답을 보냄
		}else {	// 로그인 실패시
			response.setContentType("text/html;charset=utf-8");
			// 응답하는 페이지의 타입을 text나 html로 지정 - 유니코드
			PrintWriter out = response.getWriter();
			// PrintWriter 형 인스턴스 out을 생성  (사용자에게 응답하는 객체)
			
			out.println("<script>");
			out.println("alert('로그인에 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}
	}
}
