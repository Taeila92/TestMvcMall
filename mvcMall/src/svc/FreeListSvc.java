package svc;

import db.JdbcUtil.*;
import java.util.*;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.*;
import dao.*;
import vo.*;


public class FreeListSvc {
// 게시글 목록 보기의 비즈니스 로직을 처리하는 클래스
	public int getArticleCount(String where) {
	// 게시글의 전체 개수를 리턴하는 메소드
		int rcnt = 0;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		rcnt = freeDao.getArticleCount(where);
		close(conn);
		
		return rcnt;
	}
	public ArrayList<FreeInfo> getArticleList(String where, int cpage, int limit) {
	// 게시글 목록을 ArrayList로 리턴하며, 반드시 FreeInfo형 인스턴스만 저장되어야 함
		ArrayList<FreeInfo> articleList = null;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		articleList = freeDao.getArticleList(where, cpage, limit);
		close(conn);
		return articleList;
	}
}
