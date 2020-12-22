package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FreeProcSvc {
// 게시글에 대한 실제 작업을 처리하는 클래스
	public boolean freeInsert(FreeInfo freeInfo) throws Exception {
	// 게시글 등록을 위한 메소드
		boolean isSuccess = false;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);

		int result = freeDao.freeInsert(freeInfo);
		if (result > 0) {
		// 글등록하는 쿼리를 실행했을 때 등록된 레코드가 있으면
			commit(conn);	// 쿼리를 실제로 적용시킴
			isSuccess = true;
		} else {
		// 글등록하는 쿼리를 실행했을 때 등록된 레코드가 없으면
			rollback(conn);	// 실행했던 모든 쿼리를 무효화 시킴
		}

		close(conn);
		return isSuccess;
	}
	public boolean freeUpdate(FreeInfo freeInfo) throws Exception {
	// 게시글 수정을 위한 메소드
		boolean isSuccess = false;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);

		int result = freeDao.freeUpdate(freeInfo);
		if (result > 0) {
			commit(conn);
			isSuccess = true;
		} else {
			rollback(conn);
		}

		close(conn);
		return isSuccess;
	}
	public boolean freeDelete(FreeInfo freeInfo) throws Exception {
	// 게시글 삭제를 위한 메소드
		boolean isSuccess = false;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);

		int result = freeDao.freeDelete(freeInfo);
		if (result > 0) {
			commit(conn);
			isSuccess = true;
		} else {
			rollback(conn);
		}
		close(conn);
		return isSuccess;
	}
}
