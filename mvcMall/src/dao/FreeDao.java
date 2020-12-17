package dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;


public class FreeDao {

	private static FreeDao freeDao;
	private Connection conn;
	
	private FreeDao() {}
	
	public static FreeDao getInstance() {
		if(freeDao == null) {
			freeDao = new FreeDao();
		}
		return freeDao;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getArticleCount(String where) {
	// 검색된 게시글의 총 개수를 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		int result = 0;
		
		try {
			sql ="select count(*) from t_free_list where fl_status ='a' " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) result = rs.getInt(1);
		}catch(Exception e){
			System.out.println("getArticleCount() 오류");
		}finally {
			close(rs); close(stmt);
		}
		return result;
	}
	
	public ArrayList<FreeInfo> getArticleList(String where, int cpage, int limit) {
	// 게시글 목록을 ArrayList형태로 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		ArrayList<FreeInfo> articleList = new ArrayList<FreeInfo>();
		// 검색 결과를 저장할 ArrayList객체
		FreeInfo freeInfo = null;	// articleList에 담을 레코드를 저장할 인스턴스
		int snum = (cpage - 1) * limit; // limit명령에서 데이터를 가져올 시작 인덱스 번호
		
		try {
			sql = "select * from t_free_list where fl_status = 'a' " + 
				where + " order by fl_idx desc limit " + snum + ", " + limit;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				freeInfo = new FreeInfo();
				// 하나의 레코드(게시글)을 저장할 인스턴스 생성
				freeInfo.setFl_idx(rs.getInt("fl_idx"));
				freeInfo.setFl_reply(rs.getInt("fl_reply"));
				freeInfo.setFl_read(rs.getInt("fl_read"));
				freeInfo.setFl_good(rs.getInt("fl_good"));
				freeInfo.setFl_bad(rs.getInt("fl_bad"));
				freeInfo.setFl_ismember(rs.getString("fl_ismember"));
				freeInfo.setFl_writer(rs.getString("fl_writer"));
				freeInfo.setFl_pwd(rs.getString("fl_pwd"));
				freeInfo.setFl_title(rs.getString("fl_title"));
				freeInfo.setFl_content(rs.getString("fl_content"));
				freeInfo.setFl_date(rs.getString("fl_date"));
				freeInfo.setFl_status(rs.getString("fl_status"));
				freeInfo.setFl_ip(rs.getString("fl_ip"));
				// 받아온 레코드들로 freeInfo인스턴스에 변수값을 넣음
				
				articleList.add(freeInfo);
				// 생성된 FreeInfo형 인스턴스를 articleList 하나씩 저장
			}
		}catch(Exception e){
			System.out.println("getArticleList() 오류");
		}finally {
			close(rs); close(stmt);
		}
		return articleList;
	}
	
	public int setCountUp(int idx) {
		// 게시글 조회수 증가 메소드
		Statement stmt = null;
		String sql = null;
		int result = 0;
		
		try {
			sql = "update t_free_list set fl_read = fl_read + 1 where fl_idx = " + idx;
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
		}catch(Exception e){
			System.out.println("setCountUp() 오류");
		}finally {
			close(stmt);
		}
		return result;
	}
	
	public FreeInfo getArticle(int idx) {
		// 지정된 게시물의 데이터를 FreeInfo형 인스턴스로 리턴하는 메소드
		FreeInfo article = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			sql = "select * from t_free_list where fl_status = 'a' and fl_idx = " + idx;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				article = new FreeInfo();
				article.setFl_idx(rs.getInt("fl_idx"));
				article.setFl_reply(rs.getInt("fl_reply"));
				article.setFl_read(rs.getInt("fl_read"));
				article.setFl_good(rs.getInt("fl_good"));
				article.setFl_bad(rs.getInt("fl_bad"));
				article.setFl_ismember(rs.getString("fl_ismember"));
				article.setFl_writer(rs.getString("fl_writer"));
				article.setFl_pwd(rs.getString("fl_pwd"));
				article.setFl_title(rs.getString("fl_title"));
				article.setFl_content(rs.getString("fl_content"));
				article.setFl_date(rs.getString("fl_date"));
				article.setFl_status(rs.getString("fl_status"));
				article.setFl_ip(rs.getString("fl_ip"));
				// 받아온 레코드들로 article인스턴스에 변수값을 넣음
			}
		}catch(Exception e){
			System.out.println("getArticle() 오류");
		}finally {
			close(rs); close(stmt);
		}
		return article;
	}	

	public int freeInsert(FreeInfo freeInfo) {
	// 게시글 등록 메소드
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int idx = 1, result = 0;
		String sql = null;
		
		try {
			sql = "select max(fl_idx) + 1 from t_free_list";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) idx = rs.getInt(1);
			// 등록할 게시글의 새로운 글번호 생성
			
			sql = "insert into t_free_list (fl_idx, fl_ismember, fl_writer, fl_pwd, fl_title, " + 
					"fl_content, fl_ip) values (?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setString(2, freeInfo.getFl_ismember());
			pstmt.setString(3, freeInfo.getFl_writer());
			pstmt.setString(4, freeInfo.getFl_pwd());
			pstmt.setString(5, freeInfo.getFl_title());
			pstmt.setString(6, freeInfo.getFl_content());
			pstmt.setString(7, freeInfo.getFl_ip());
			
			result = pstmt.executeUpdate();
			// 새로운 게시글 등록
		}catch(Exception e){
			System.out.println("freeInsert() 오류");
		}finally {
			close(rs); close(pstmt);
		}
		return result;
	}	
	public FreeInfo getArticleUp(int idx, String mem, String uid, String pwd) {
	// 수정글에 대한 권한을 체크하는 메소드
		FreeInfo article = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			String where = " and fl_ismember = " ;
			if(mem.equals("n")) {
				where += "'n' and fl_pwd = '" + pwd + "'";
			} else {
				where += "'y' and fl_writer = '" + uid + "'";				
			}
			sql = "select * from t_free_list where and fl_status = 'a'  fl_idx = " +
				idx  + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				article = new FreeInfo();
				article.setFl_idx(rs.getInt("fl_idx"));
				article.setFl_reply(rs.getInt("fl_reply"));
				article.setFl_read(rs.getInt("fl_read"));
				article.setFl_good(rs.getInt("fl_good"));
				article.setFl_bad(rs.getInt("fl_bad"));
				article.setFl_ismember(rs.getString("fl_ismember"));
				article.setFl_writer(rs.getString("fl_writer"));
				article.setFl_pwd(rs.getString("fl_pwd"));
				article.setFl_title(rs.getString("fl_title"));
				article.setFl_content(rs.getString("fl_content"));
				article.setFl_date(rs.getString("fl_date"));
				article.setFl_status(rs.getString("fl_status"));
				article.setFl_ip(rs.getString("fl_ip"));
				// 받아온 레코드들로 article인스턴스에 변수값을 넣음
			}
		}catch(Exception e){
			System.out.println("getArticleUp() 오류");
		}finally {
			close(rs); close(stmt);
		}
		return article;
	}
}