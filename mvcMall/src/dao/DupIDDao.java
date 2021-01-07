package dao;

import static db.JdbcUtil.*;
import java.sql.*;

public class DupIDDao {
	// 로그인 관련 쿼리를 만들어 실행시키는 클래스
		private static DupIDDao dupIDDao;
		private Connection conn;

		private DupIDDao() {}

		public static DupIDDao getInstance() {
			if (dupIDDao == null) {
				dupIDDao = new DupIDDao();
			}
			return dupIDDao;
		}
		public void setConnection(Connection conn) {
			this.conn = conn;
		}
		public int chkDupID(String uid) {
			Statement stmt = null;
			ResultSet rs = null;
			int chkPoint = 0;

			try {
				stmt = conn.createStatement();
				String sql = "select count(*) from t_member_list where ml_id = '" + uid + "' ";
				// 사용자가 입력한 ID와 동일한 값이 있는지 여부를 검사할 쿼리
				rs = stmt.executeQuery(sql);
				if (rs.next())	chkPoint = rs.getInt(1);
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					close(rs);		close(stmt);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}

			return chkPoint;
		}
}
