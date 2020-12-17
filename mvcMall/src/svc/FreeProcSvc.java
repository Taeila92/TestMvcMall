package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import javax.servlet.http.HttpSession;
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
		if(result >0) {
			commit(conn);
			isSuccess = true;
		}else {
			rollback(conn);
			
		}
		close(conn);
		
		return isSuccess;
	}
	public boolean freeUpdate(FreeInfo freeInfo) throws Exception {
	// 게시글 수정을 위한 메소드
		boolean isSuccess = false;
		return isSuccess;		
	}
	public boolean freeDelete(FreeInfo freeInfo) throws Exception {
	// 게시글 삭제을 위한 메소드
		boolean isSuccess = false;
		return isSuccess;		
	}
}
