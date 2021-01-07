package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class PdtViewSvc {
	public PdtInfo getPdtInfo(String id) {
		PdtInfo pdtInfo = null;	// 리턴할 상품정보를 담을 인스턴스 선언
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);

		pdtInfo = pdtDao.getPdtInfo(id);	// 보여줄 상품정보 받기

		close(conn);
		return pdtInfo;
	}
}
