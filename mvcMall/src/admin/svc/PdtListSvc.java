package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class PdtListSvc {
	public int getPdtCount(String where) {
	// 검색된 상품의  전체 개수를 리턴하는 메소드
		int rcnt = 0;	// 전체 레코드 개수를 저장할 변수
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		rcnt = pdtDao.getPdtCount(where);
		close(conn);

		return rcnt;
	}
	
	public ArrayList<PdtInfo> getPdtList(
		String where, String orderby, int cpage, int psize) {
		ArrayList<PdtInfo> pdtList = null;
		// 상품 목록을 담을 ArrayList 선언
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		pdtList = pdtDao.getPdtList(where, orderby, cpage, psize);
		close(conn);

		return pdtList;
	}
}
