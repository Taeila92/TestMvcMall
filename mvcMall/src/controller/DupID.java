package controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import svc.*;

@WebServlet("/dupID")
public class DupID extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DupID() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid");
		PrintWriter out = response.getWriter();
		try {
			DupIDSvc dupIDSvc = new DupIDSvc();
			int chkPoint = dupIDSvc.chkDupID(uid);
			out.println(chkPoint);
		} catch (Exception e) {
		// 중복체크 쿼리 실행시 문제가 발생하면
			e.printStackTrace();
			out.println(1);
			// 중복된 값이 하나 있다고 출력시킴
		}
	}
}
