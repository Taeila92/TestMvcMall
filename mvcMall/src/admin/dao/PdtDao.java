package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class PdtDao {
	private static PdtDao pdtDao;
	private Connection conn;

	private PdtDao() {}
	public static PdtDao getInstance() {
		if (pdtDao == null) {
			pdtDao = new PdtDao();
		}
		return pdtDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public ArrayList<CataBigInfo> getCataBigList(){
	// DB에서 대분류 목록을 받아 리턴하는 메소드
		ArrayList<CataBigInfo> cataBigList =  new ArrayList<CataBigInfo>();
		CataBigInfo bigInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			sql ="select * from t_cata_big";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				bigInfo = new CataBigInfo();
				// cataBigList에 담을 CataBigInfo형 인스턴스 생성
				bigInfo.setCb_idx(rs.getInt("cb_idx"));
				bigInfo.setCb_name(rs.getString("cb_name"));
				bigInfo.setCb_date(rs.getString("cb_date"));
				
				cataBigList.add(bigInfo);
				// 리턴할 ArrayList에 생성한 CataBigInfo형 인스턴스 저장
			}
		} catch(Exception e) {
			System.out.println("getCataBigList() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return cataBigList;		
	}
	
	public ArrayList<CataSmallInfo> getCataSmallList(){
		// DB에서 대분류 목록을 받아 리턴하는 메소드
			ArrayList<CataSmallInfo> cataSmallList =  new ArrayList<CataSmallInfo>();
			CataSmallInfo smallInfo = null;
			Statement stmt = null;
			ResultSet rs = null;
			String sql = null;

			try {
				sql ="select * from t_cata_small order by cb_idx, cs_idx";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					smallInfo = new CataSmallInfo();
					// cataBigList에 담을 CataBigInfo형 인스턴스 생성
					smallInfo.setCs_idx(rs.getInt("cs_idx"));
					smallInfo.setCb_idx(rs.getInt("cb_idx"));
					smallInfo.setCs_name(rs.getString("cs_name"));
					smallInfo.setCs_date(rs.getString("cs_date"));
					
					cataSmallList.add(smallInfo);
					// 리턴할 ArrayList에 생성한 CataBigInfo형 인스턴스 저장
				}
			} catch(Exception e) {
				System.out.println("getCataSmallList() 오류");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}
			return cataSmallList;		
		}

	public ArrayList<BrandInfo> getBrandList(){
	// DB에서 대분류 목록을 받아 리턴하는 메소드
		ArrayList<BrandInfo> brandList =  new ArrayList<BrandInfo>();
		BrandInfo brandInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			sql ="select * from t_brand_list";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				brandInfo = new BrandInfo();
				brandInfo.setBl_idx(rs.getInt("bl_idx"));
				brandInfo.setBl_name(rs.getString("bl_name"));
				brandInfo.setBl_date(rs.getString("bl_date"));
				
				brandList.add(brandInfo);
				// 리턴할 ArrayList에 생성한 CataBigInfo형 인스턴스 저장
			}
		} catch(Exception e) {
			System.out.println("getBrandList() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return brandList;
	}
	public int pdtInsert(PdtInfo pdt) {
	// 상품 등록 처리를 위한 메소드
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		// 등록할 게시글의 번호를 얻기 위한 ResultSet
		String sql = null, plid = pdt.getCs_idx() + "pdt001";

		try {
			
			sql = "select max(right(pl_id, 3)) from t_product_list where cs_idx = " + pdt.getCs_idx();
			// 해당 소분류에서 가장 큰 값을 가진 상품아이디 마지막 세자리를 잘라옴
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int n = 1;			
				if (rs.getString(1) != null) n = Integer.parseInt(rs.getString(1)) + 1;
				if (n < 10) 		plid = pdt.getCs_idx() + "pdt00" + n;
				else if (n < 100) 	plid = pdt.getCs_idx() + "pdt0" + n;
				else 				plid = pdt.getCs_idx() + "pdt" + n;
			
				sql = "insert into t_product_list (pl_id, cs_idx, bl_idx, pl_orig, " + 
						"pl_name, pl_price, pl_cost, pl_discount, pl_opt, pl_img1, pl_img2, " +
						"pl_img3, pl_desc, pl_stock, pl_deliv, pl_view, al_idx) values " +
						"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, plid);
				pstmt.setInt(2, pdt.getCs_idx());
				pstmt.setInt(3, pdt.getBl_idx());
				pstmt.setString(4, pdt.getPl_orig());
				pstmt.setString(5, pdt.getPl_name());
				pstmt.setInt(6, pdt.getPl_price());
				pstmt.setInt(7, pdt.getPl_cost());
				pstmt.setInt(8, pdt.getPl_discount());
				pstmt.setString(9, pdt.getPl_opt());
				pstmt.setString(10, pdt.getPl_img1());
				pstmt.setString(11, pdt.getPl_img2());
				pstmt.setString(12, pdt.getPl_img3());
				pstmt.setString(13, pdt.getPl_desc());
				pstmt.setInt(14, pdt.getPl_stock());
				pstmt.setString(15, "");
				pstmt.setString(16, pdt.getPl_view());
				pstmt.setInt(17, 1);
				
				result = pstmt.executeUpdate();
				
			}
		} catch(Exception e) {
			System.out.println("pdtInsert() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(pstmt);
		}

		return result;
	}
	public int getPdtCount(String where) {
		// 조건을 받아와 조건에 맞는 상품들의 총 개수를 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		int rcnt = 0;
		
		try {
			sql = "select count(*) from t_product_list a, t_cata_big b, t_cata_small c " + 
					" where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	rcnt = rs.getInt(1);
			// 검색된 상품의 개수를 result에 담음
		} catch(Exception e) {
			System.out.println("getPdtCount() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return rcnt;
	}
	public ArrayList<PdtInfo> getPdtList(
			String where, String orderby, int cpage, int psize) {
		// 검색조건과 정렬조건을 받아와 조건에 맞는 상품들을 정렬하여 그목록을  ArrayList<PdtInfo>형태로 리턴하는 메소드
			Statement stmt = null;
			ResultSet rs = null;
			String sql = null;
			ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
			// 검색 결과를 저장할 ArrayList객체
			PdtInfo pdtInfo = null;// 하나의 상품정보를 저장한 후 pdtList에 저장될 인스턴스 
			int snum = (cpage - 1) * psize;		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호

			try {
				sql ="select a.*, b.cb_name, c.cs_name from t_product_list a, t_cata_big b, t_cata_small c " + 
						" where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx " + 
						where + orderby +" limit " + snum + ", " + psize;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
				// rs가 비었을 경우 빈 상태로 리턴하기 위해 if를 사용하지 않고 바로 while사용
					pdtInfo = new PdtInfo();
					// 하나의 레코드(상품)를 저장할 인스턴스 생성

					pdtInfo.setPl_id(rs.getString("pl_id"));
					pdtInfo.setCs_idx(rs.getInt("cs_idx"));
					pdtInfo.setBl_idx(rs.getInt("bl_idx"));
					pdtInfo.setPl_orig(rs.getString("pl_orig"));
					pdtInfo.setPl_name(rs.getString("pl_name"));
					pdtInfo.setPl_price(rs.getInt("pl_price"));
					pdtInfo.setPl_cost(rs.getInt("pl_cost"));
					pdtInfo.setPl_discount(rs.getInt("pl_discount"));
					pdtInfo.setPl_opt(rs.getString("pl_opt"));
					pdtInfo.setPl_img1(rs.getString("pl_img1"));
					pdtInfo.setPl_img2(rs.getString("pl_img2"));
					pdtInfo.setPl_img3(rs.getString("pl_img3"));
					pdtInfo.setPl_desc(rs.getString("pl_desc"));
					pdtInfo.setPl_deliv(rs.getString("pl_deliv"));
					pdtInfo.setPl_stock(rs.getInt("pl_stock"));
					pdtInfo.setPl_salecnt(rs.getInt("pl_salecnt"));
					pdtInfo.setPl_review(rs.getInt("pl_review"));
					pdtInfo.setPl_view(rs.getString("pl_view"));
					pdtInfo.setPl_date(rs.getString("pl_date"));
					pdtInfo.setAl_idx(rs.getInt("al_idx"));		
					pdtInfo.setCb_name(rs.getString("cb_name"));
					pdtInfo.setCs_name(rs.getString("cs_name"));					
					// 받아온 레코드들로 PdtInfo 인스턴스에 멤버변수 값으로 넣음

					pdtList.add(pdtInfo);
					// 생성된 PdtInfo형 인스턴스를 pdtList 하나씩 저장
				}
			} catch(Exception e) {
				System.out.println("getPdtList() 오류");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}

			return pdtList;
		}
	public PdtInfo getPdtInfo(String id) {
	// 지정된 id에 해당하는 하나의 상품정보를 PdtInfo형 인스턴스로 리턴하는 메소드
		PdtInfo pdtInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			sql = "select * from t_product_list where pl_id = '" + id + "' ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
			// rs에 데이터가 있으면(해당 게시물이 있으면)
				pdtInfo = new PdtInfo();
				// 리턴할 인스턴스(데이터를 저장할 인스턴스) 생성
				pdtInfo.setPl_id(rs.getString("pl_id"));
				pdtInfo.setCs_idx(rs.getInt("cs_idx"));
				pdtInfo.setBl_idx(rs.getInt("bl_idx"));
				pdtInfo.setPl_orig(rs.getString("pl_orig"));
				pdtInfo.setPl_name(rs.getString("pl_name"));
				pdtInfo.setPl_price(rs.getInt("pl_price"));
				pdtInfo.setPl_cost(rs.getInt("pl_cost"));
				pdtInfo.setPl_discount(rs.getInt("pl_discount"));
				pdtInfo.setPl_opt(rs.getString("pl_opt"));
				pdtInfo.setPl_img1(rs.getString("pl_img1"));
				pdtInfo.setPl_img2(rs.getString("pl_img2"));
				pdtInfo.setPl_img3(rs.getString("pl_img3"));
				pdtInfo.setPl_desc(rs.getString("pl_desc"));
				pdtInfo.setPl_deliv(rs.getString("pl_deliv"));
				pdtInfo.setPl_stock(rs.getInt("pl_stock"));
				pdtInfo.setPl_salecnt(rs.getInt("pl_salecnt"));
				pdtInfo.setPl_review(rs.getInt("pl_review"));
				pdtInfo.setPl_view(rs.getString("pl_view"));
				pdtInfo.setPl_date(rs.getString("pl_date"));
				pdtInfo.setAl_idx(rs.getInt("al_idx"));
				// 받아온 레코드들로 pdtInfo 인스턴스의 멤버 변수에 값을 넣음
			}
		} catch(Exception e) {
			System.out.println("getPdtInfo() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return pdtInfo;
	}
}
