package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FreeViewSvc {
// 게시글 보기의 비즈니스 로직을 처리하는 클래스
	public FreeInfo getArticle(int idx) {
	// 인수로 받아온 idx에 해당하는 게시물 하나의 정보를 추출하여 FreeInfo형 인스턴스로 리턴하는 메소드
		FreeInfo article = null;	// 리턴할 게시물 정보를 담을 인스턴스 선언
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);

		int result = freeDao.setCountUp(idx);	// 조회수 증가
		if (result > 0)	commit(conn);
		else			rollback(conn);
		// 조회수 증가는 게시판 운영에 큰 영향을 끼치지 않으므로 rollback 되더라도 계속 작업을 이어감

		article = freeDao.getArticle(idx);	// 보여줄 게시글 받기

		close(conn);
		return article;
	}
}
