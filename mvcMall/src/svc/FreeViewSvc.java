package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import java.util.*;
import dao.*;
import vo.*;

public class FreeViewSvc {
// 게시글 보기의 비즈니스 로직을 처리하는 클래스
	public FreeInfo getArticle (int idx) {
		FreeInfo article = null;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		
		int result =freeDao.setCountUp(idx);	// 조회수 증가
		if(result > 0)	commit(conn);
		else 			rollback(conn);
		
		article = freeDao.getArticle(idx);	// 보여줄 게시글 받기
		close(conn);
		return article;
	}
}
