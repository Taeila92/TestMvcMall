package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CartUpOptSvc {
	public int cartOptUpdate(String opt, String cnt, String idx, String pid, String buyer, String isMember) {
		Connection conn = getConnection();
		OrdDao ordDao = OrdDao.getInstance();
		ordDao.setConnection(conn);
		int result = ordDao.cartOptUpdate(opt, cnt, idx, pid, buyer, isMember);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
}
