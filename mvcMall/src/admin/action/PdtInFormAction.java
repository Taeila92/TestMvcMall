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
		// ��з�, �Һз�, �귣�� ����� �������� ���� SvcŬ����
		ArrayList<CataBigInfo> cataBigList = pdtInFormSvc.getCataBigList();		// ��з����
		ArrayList<CataSmallInfo> cataSmallList = pdtInFormSvc.getCataSmallList();	// �Һз����
		ArrayList<BrandInfo> brandList = pdtInFormSvc.getBrandList();			// �귣����
		
		if (cataBigList != null && cataSmallList != null && brandList != null) {
			request.setAttribute("cataBigList", cataBigList);
			request.setAttribute("cataSmallList", cataSmallList);
			request.setAttribute("brandList", brandList);
			// ��� ������ ������ �з���� �귣�� ����� request��ü�� �Ӽ����� ����
		} else {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�߸��� ��η� �����̽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		forward.setPath("/admin/product/pdt_in_form.jsp");	// �̵��� URL ����
		return forward;
	}
}