package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class FreeViewAction implements Action {
// 하나의 게시글을 볼 때 연결시켜주는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));	// 글번호
		int cpage = Integer.parseInt(request.getParameter("cpage"));// 현재 페이지번호
		// 글번호와 페이지번호는 필수이므로 받으면서 바로 정수형으로 변환시킴

		String schtype = request.getParameter("schtype");	// 검색조건
		String keyword = request.getParameter("keyword");	// 검색어
		
		FreeViewSvc freeViewSvc = new FreeViewSvc();
		FreeInfo article = freeViewSvc.getArticle(idx);
		// 지정한 게시글을 FreeInfo형 인스턴스로 받아옴
		
		ActionForward forward = new ActionForward();
		request.setAttribute("article", article);
		// 이동할 페이지에 request에 게시물 데이터를 담아 넘겨줌(dispatch방식이므로 request를 넘길수 있음)
		forward.setPath("/bbs/free_view.jsp");
		// 이동할 페이지 지정
		return forward;
	}
}
