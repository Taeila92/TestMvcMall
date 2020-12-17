package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class FreeListAction implements Action {
// 게시글 목록을 받아와 연결하는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ArrayList<FreeInfo> articleList = new ArrayList<FreeInfo>();
		// 게시글 목록을 저장할 ArrayList객체로 <freeInfo>형 인스턴스만 저장
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, limit = 10;
		// 현재 페이지 번호와 한페이지에서 보여줄 게시글 개수를 저장하는 변수
		if(request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		// cpage값을 받아 왔으면 정수형으로 형변환하여 사용
		}
		String schtype = request.getParameter("schtype");
		// 검색 조건으로 작성자, 제목, 내용, 제목 + 내용
		String keyword = request.getParameter("keyword");
		// 검색어 
		
		String where = "";
		if(schtype != null && keyword != null && !keyword.equals("")) {
		// 검색조건과 검색어가 있으면
			if (schtype.equals("tc")) {	// 검색 조건이 '제목+내용' 이면
				where = " and (fl_title like '%" + keyword + "%' " + 
					" or fl_content like '%" + keyword + "%') ";
			} else {	// 검색조건이 제목 또는 내용 또는 작성자 이면
				where = " and fl_" + schtype + " like '%" + keyword + "%' ";
			}
		}
		FreeListSvc freeListSvc = new FreeListSvc();
		int rcnt = freeListSvc.getArticleCount(where);
		// 총 레코드 개수를 받아옴(전체 페이지수를 계산하기 위해)
		
		articleList = freeListSvc.getArticleList(where, cpage, limit);
		// 목록화면에서 보여줄 게시글 목록을 ArrayList형으로 받아옴
		
		int pcnt = rcnt / limit;
		if(rcnt % limit > 0 ) pcnt++;
		//전체 페이지수 구함
		
		int spage = (cpage - 1) / limit * limit + 1;
		// 블록 시작페이지 번호
		int epage = spage + limit - 1;
		if (epage < pcnt) epage = pcnt;
		//블록 종료페이지 번호
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCpage(cpage);	// 현재 페이지 번호
		pageInfo.setRcnt(rcnt);		// 전체 게시글 수
		pageInfo.setPcnt(pcnt);		// 전체 페이지 수
		pageInfo.setSpage(spage);	// 블록 시작 페이지 번호
		pageInfo.setEpage(epage);	// 블록 종료 페이지 번호
		pageInfo.setSchtype(schtype);	// 검색조건
		pageInfo.setKeyword(keyword);	// 검색어
		// 페이징 관련 정보들과 검색조건을 PageInfo 인스턴스에 저장
		

		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("articleList", articleList);
		//free_list.jsp로 request를 통해 전달해줄 객체 저장
		
		ActionForward forward = new ActionForward();
		forward.setPath("/bbs/free_list.jsp");
		return forward;
	}
}