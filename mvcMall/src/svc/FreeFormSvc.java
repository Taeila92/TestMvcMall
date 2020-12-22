package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FreeFormSvc {
	public FreeInfo getArticleUp(int idx, String ismember, String uid, String pwd) {
	// 수정할 글에 대한 권한이 있을 경우 해당 데이터를 가져와 리턴할 메소드
		FreeInfo article = null;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);

		article = freeDao.getArticleUp(idx, ismember, uid, pwd);
		// 수정할 글에 대한 권한이 있을 경우 해당 데이터를 가져옴

		close(conn);
		return article;
	}
}
