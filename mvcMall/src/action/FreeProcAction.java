package action;

import java.io.PrintWriter;	// 등록 실패시 자바스크립트 사용을 위한 import
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
import svc.*;
import vo.*;

public class FreeProcAction implements Action {
// 게시글 등록 처리 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");
		FreeInfo freeInfo = new FreeInfo();
		// 사용자가 입력한 데이터(게시글)들을 저장할 인스턴스

		freeInfo.setFl_ismember(request.getParameter("ismember"));
		if (wtype.equals("in") || wtype.equals("up")) {
		// 글등록이나 수정일 경우 게시글 데이터들을 받아옴
			freeInfo.setFl_writer(request.getParameter("writer"));
			freeInfo.setFl_pwd(request.getParameter("pwd"));
			freeInfo.setFl_title(request.getParameter("title"));
			freeInfo.setFl_content(request.getParameter("content"));
			// 받아 온 데이터들을 하나의 게시물을 저장하는 freeInfo 인스턴스에 setter를 이용하여 담음
		}

		if (wtype.equals("del") || wtype.equals("up")) {
		// 글삭제나 수정일 경우 게시글 번호를 받아옴
			int idx = Integer.parseInt(request.getParameter("idx"));
			freeInfo.setFl_idx(idx);
		}

		FreeProcSvc freeProcSvc = new FreeProcSvc();
		// 비즈니스 로직을 처리할 서비스 클래스의 인스턴스 생성
		boolean isSuccess = false;	// 쿼리(등록, 수정, 삭제) 성공여부를 저장할 변수
		String link = null;			// 작업(등록, 수정, 삭제) 후 이동할 URL을 저장할 변수
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		// 등록시 작성자, 수정/삭제 시 권한 체크용으로 로그인 정보를 담음

		if (wtype.equals("in")) {
			freeInfo.setFl_ismember("n");	// 일단 비회원 글로 저장
			if (loginMember != null) {	// 회원 글등록일 경우
				freeInfo.setFl_ismember("y");	// 로그인 한 경우 회원글로 변경
				freeInfo.setFl_writer(loginMember.getMlid());	// 로그인한 아이디를 작성자로 지정
			}
			freeInfo.setFl_ip(request.getRemoteAddr());	// 등록자 IP주소 지정
			isSuccess = freeProcSvc.freeInsert(freeInfo);
			link = "brd_list.free";

		} else if (wtype.equals("up")) {
			isSuccess = freeProcSvc.freeUpdate(freeInfo);
			int cpage = Integer.parseInt(request.getParameter("cpage"));
			String args = "?cpage=" + cpage;
			String schtype = request.getParameter("schtype");
			String keyword = request.getParameter("keyword");
			if (keyword != null && !keyword.equals("")) {
				args += "&schtype=" + schtype + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}
			link = "brd_view.free" + args + "&idx=" + freeInfo.getFl_idx();

		} else {
			String pwd = request.getParameter("pwd");
			freeInfo.setFl_pwd(pwd);
			// 사용자가 입력한 암호를 가져가 조건으로 활용(단, 비회원 글에만 사용됨)
			if (loginMember != null && freeInfo.getFl_ismember().equals("y")) {
			// 로그인 중인 회원이면서 회원이 입력한 글일 경우
				freeInfo.setFl_writer(loginMember.getMlid());
				// freeInfo 인스턴스의 writer 값을 현재 로그인한 회원의 아이디로 저장
			}
			isSuccess = freeProcSvc.freeDelete(freeInfo);
			if (!isSuccess) {	// 삭제 실패시
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				if (freeInfo.getFl_ismember().equals("y")) {
					out.println("alert('잘못된 경로로 들어오셨습니다.');");
				} else {
					out.println("alert('비밀번호가 틀렸습니다.');");
				}
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
			link = "brd_list.free";
		}

		if (isSuccess) {	// 성공시
			forward = new ActionForward();
			forward.setRedirect(true);	// 이동방식을 redirect로 하겠다는 의미
			forward.setPath(link);
		}

		return forward;
	}
}
