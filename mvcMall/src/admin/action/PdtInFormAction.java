package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class PdtInFormAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		
		PdtInFormSvc pdtInFormSvc = new PdtInFormSvc();
		// 대분류, 소분류, 브랜드 목록을 가져오기 위한 Svc클래스
		ArrayList<CataBigInfo> cataBigList = pdtInFormSvc.getCataBigList();		// 대분류목록
		ArrayList<CataSmallInfo> cataSmallList = pdtInFormSvc.getCataSmallList();	// 소분류목록
		ArrayList<BrandInfo> brandList = pdtInFormSvc.getBrandList();			// 브랜드목록
		
		if (cataBigList != null && cataSmallList != null && brandList != null) {
			request.setAttribute("cataBigList", cataBigList);
			request.setAttribute("cataSmallList", cataSmallList);
			request.setAttribute("brandList", brandList);
			// 등록 폼에서 보여줄 분류들과 브랜드 목록을 request객체에 속성으로 담음
		} else {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('잘못된 경로로 들어오셨습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		forward.setPath("/admin/product/pdt_in_form.jsp");	// 이동할 URL 지정
		return forward;
	}
}
