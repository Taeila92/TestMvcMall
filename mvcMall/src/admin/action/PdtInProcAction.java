package admin.action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import admin.svc.*;
import vo.*;

public class PdtInProcAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		PdtInProcSvc pdtInProcSvc = new PdtInProcSvc();
		request.setCharacterEncoding("utf-8");

		String uploadPath = "D:/zoom/jsp/work/mvcMall/WebContent/product/pdt_img";
		// 파일을 저장할 실제 위치를 구함
		int maxSize = 5 * 1024 * 1024;		// 업로드 최대 용량으로 5MB로 지정
		String sCata = "", brand = "", orig = "", name = "", opt = "", view = "";
		String img1 = "", img2 = "", img3 = "", desc = "";
		String price = "0", cost = "0", discount = "0", stock = "0";

		MultipartRequest multi = new MultipartRequest(
			request, 	// request객체로 multi로 데이터들을 받기 위함
			uploadPath, // 서버에 실제로 파일이 저장될 위치 지정
			maxSize, 	// 한 번에 업로드할 수 있는 최대크기(byte단위)
			"utf-8", 	// 파일의 인코딩 방식(utf-8, euc-kr, ksc-5601 등)
			new DefaultFileRenamePolicy());	// 파일 이름의 중복 처리
		
		sCata = multi.getParameter("sCata");	brand = multi.getParameter("brand");
		orig = multi.getParameter("orig");		name = multi.getParameter("name");
		opt = multi.getParameter("opt");		view = multi.getParameter("view");
		price = multi.getParameter("price");
		if (price == null || price.equals(""))	price = "0";
		cost = multi.getParameter("cost");
		if (cost == null || cost.equals(""))	cost = "0";
		stock = multi.getParameter("stock");
		if (stock == null || stock.equals(""))	stock = "0";
		discount = multi.getParameter("discount");
		if (discount == null || discount.equals(""))	discount = "0";
		// 등록할 상품에 대해 받아 옴

		Enumeration files = multi.getFileNames();	// 업로드할 파일 이름들을 Enumeration형으로 받아 옴
		while (files.hasMoreElements()) {
			String f = (String)files.nextElement();
			switch (f) {
				case "img1" : img1 = multi.getFilesystemName(f);	break;
				case "img2" : img2 = multi.getFilesystemName(f);	break;
				case "img3" : img3 = multi.getFilesystemName(f);	break;
				case "desc" : desc = multi.getFilesystemName(f);	break;
			}
		}

		PdtInfo pdt = new PdtInfo();
		pdt.setCs_idx(Integer.parseInt(sCata));		pdt.setBl_idx(Integer.parseInt(brand));
		pdt.setPl_price(Integer.parseInt(price));	pdt.setPl_cost(Integer.parseInt(cost));
		pdt.setPl_stock(Integer.parseInt(stock));	pdt.setPl_discount(Integer.parseInt(discount));
		pdt.setPl_orig(orig);	pdt.setPl_name(name);	pdt.setPl_opt(opt);		pdt.setPl_view(view);
		pdt.setPl_img1(img1);	pdt.setPl_img2(img2);	pdt.setPl_img3(img3);	pdt.setPl_desc(desc);
		// 등록할 상품정보를 PdtInfo형 인스턴스 pdt에 담음
		
		boolean isSuccess = pdtInProcSvc.pdtInsert(pdt);
		if (!isSuccess) {	// 상품등록에 실패했으면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('상품 등록이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		forward.setPath("pdt_list.pdta");
		forward.setRedirect(true);
		return forward;
	}
}
