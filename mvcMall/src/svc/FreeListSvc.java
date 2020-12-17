package svc;

import db.JdbcUtil.*;
import java.util.*;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.*;
import dao.*;
import vo.*;


public class FreeListSvc {
// �Խñ� ��� ������ ����Ͻ� ������ ó���ϴ� Ŭ����
	public int getArticleCount(String where) {
	// �Խñ��� ��ü ������ �����ϴ� �޼ҵ�
		int rcnt = 0;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		rcnt = freeDao.getArticleCount(where);
		close(conn);
		
		return rcnt;
	}
	public ArrayList<FreeInfo> getArticleList(String where, int cpage, int limit) {
	// �Խñ� ����� ArrayList�� �����ϸ�, �ݵ�� FreeInfo�� �ν��Ͻ��� ����Ǿ�� ��
		ArrayList<FreeInfo> articleList = null;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		articleList = freeDao.getArticleList(where, cpage, limit);
		close(conn);
		return articleList;
	}
}
