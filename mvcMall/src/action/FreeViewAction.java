package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class FreeViewAction implements Action {
//하나의 게시글을 볼때 연결시켜주는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		String schtype = request.getParameter("schtype");
		String keyword = request.getParameter("keyword");
		
		FreeViewSvc freeViewSvc = new FreeViewSvc();
		FreeInfo article = freeViewSvc.getArticle(idx);
		// 지정한 게시글을 FreeInfo형 인스턴스로 받아옴
		
		ActionForward forward = new ActionForward();
		request.setAttribute("article", article);
		forward.setPath("/bbs/free_view.jsp");
		return forward;
	}
}
