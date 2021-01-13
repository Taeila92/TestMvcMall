package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class OrdFormSvc {
	public ArrayList<CartInfo> getOrdFrmPdtList(String where, String kind) {
		ArrayList<CartInfo> pdtList = null;
		Connection conn = getConnection();
		OrdDao ordDao = OrdDao.getInstance();
		ordDao.setConnection(conn);
		pdtList = ordDao.getOrdFrmPdtList(where, kind);
		close(conn);

		return pdtList;
	}

}
