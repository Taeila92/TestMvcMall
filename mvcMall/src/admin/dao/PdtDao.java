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

	public ArrayList<CataBigInfo> getCataBigList() {
	// DB에서 대분류 목록을 받아 리턴하는 메소드
		ArrayList<CataBigInfo> cataBigList = new ArrayList<CataBigInfo>();
		CataBigInfo bigInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			sql = "select * from t_cata_big";
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

	public ArrayList<CataSmallInfo> getCataSmallList() {
	// DB에서 소분류 목록을 받아 리턴하는 메소드
		ArrayList<CataSmallInfo> cataSmallList = new ArrayList<CataSmallInfo>();
		CataSmallInfo smallInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			sql = "select * from t_cata_small order by cb_idx, cs_idx";
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
				// 리턴할 ArrayList에 생성한 CataSmallInfo형 인스턴스 저장
			}
		} catch(Exception e) {
			System.out.println("getCataSmallList() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return cataSmallList;
	}

	public ArrayList<BrandInfo> getBrandList() {
	// DB에서 대분류 목록을 받아 리턴하는 메소드
		ArrayList<BrandInfo> brandList = new ArrayList<BrandInfo>();
		BrandInfo brandInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			sql = "select * from t_brand_list";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				brandInfo = new BrandInfo();
				// brandInfoList에 담을 BrandInfo형 인스턴스 생성

				brandInfo.setBl_idx(rs.getInt("bl_idx"));
				brandInfo.setBl_name(rs.getString("bl_name"));
				brandInfo.setBl_date(rs.getString("bl_date"));

				brandList.add(brandInfo);
				// 리턴할 ArrayList에 생성한 BrandInfo형 인스턴스 저장
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
		ResultSet rs = null;
		String sql = null, plid = pdt.getCs_idx() + "pdt001";

		try {
			sql = "select max(right(pl_id, 3)) from t_product_list where cs_idx = " + pdt.getCs_idx();
			// 해당 소분류에서 가장 큰 값을 가진 상품아이디 마지막 세자리를 잘라옴
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int n = 1;
				if (rs.getString(1) != null)	n = Integer.parseInt(rs.getString(1)) + 1;
				if (n < 10)			plid = pdt.getCs_idx() + "pdt00" + n;
				else if (n < 100)	plid = pdt.getCs_idx() + "pdt0" + n;
				else				plid = pdt.getCs_idx() + "pdt" + n;
			}

			sql = "insert into t_product_list (pl_id, cs_idx, bl_idx, pl_orig, pl_name, pl_price, " + 
			"pl_cost, pl_discount, pl_opt, pl_img1, pl_img2, pl_img3, pl_desc, pl_deliv, pl_stock, " + 
			"pl_view, al_idx) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";
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
			pstmt.setString(14, "");
			pstmt.setInt(15, pdt.getPl_stock());
			pstmt.setString(16, pdt.getPl_view());
			result = pstmt.executeUpdate();

		} catch(Exception e) {
			System.out.println("pdtInsert() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(pstmt);
		}

		return result;
	}

	public int pdtUpdate(PdtInfo pdt) {
	// 상품 수정 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		String sql = null;

		try {
			sql = "update t_product_list set " + 
				"cs_idx = '"		+ pdt.getCs_idx()		+ "', " + 
				"bl_idx = '"		+ pdt.getBl_idx()		+ "', " + 
				"pl_orig = '"		+ pdt.getPl_orig()		+ "', " + 
				"pl_name = '"		+ pdt.getPl_name()		+ "', " + 
				"pl_price = '"		+ pdt.getPl_price()		+ "', " + 
				"pl_cost = '"		+ pdt.getPl_cost()		+ "', " + 
				"pl_discount = '"	+ pdt.getPl_discount()	+ "', " + 
				"pl_opt = '"		+ pdt.getPl_opt()		+ "', " + 
				"pl_img1 = '"		+ pdt.getPl_img1()		+ "', " + 
				"pl_img2 = '"		+ pdt.getPl_img2()		+ "', " + 
				"pl_img3 = '"		+ pdt.getPl_img3()		+ "', " + 
				"pl_desc = '"		+ pdt.getPl_desc()		+ "', " + 
				"pl_stock = '"		+ pdt.getPl_stock()		+ "', " + 
				"pl_view = '"		+ pdt.getPl_view()		+ "' " + 
				"where pl_id = '"	+ pdt.getPl_id()		+ "' ";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("pdtUpdate() 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}

	public int pdtDelete(String id) {
	// 상품 삭제 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		String sql = null;

		try {
			sql = "delete from t_product_list where pl_id = '" + id + "' ";
			System.out.println(sql);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("pdtDelete() 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}

	public int getPdtCount(String where) {
	// 조건을 받아와 조건에 맞는 상품들의 총 개수를 리턴하는 메소드
		int rcnt = 0;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			sql = "select count(*) from t_product_list a, t_cata_big b, t_cata_small c " + 
				" where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	rcnt = rs.getInt(1);
		} catch(Exception e) {
			System.out.println("getPdtCount() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return rcnt;
	}

	public ArrayList<PdtInfo> getPdtList(String where, String orderby, int cpage, int psize) {
	// 검색조건과 정렬조건을 받아와 조건에 맞는 상품들을 정렬하여 그 목록을 ArrayList<PdtInfo>형으로 리턴하는 메소드
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		// 상품 목록을 저장할 ArrayList객체로 PdtInfo형 인스턴스만 저장함
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		PdtInfo pdtInfo = null;		// 하나의 상품정보를 저장한 후 pdtList에 저장될 인스턴스
		int snum = (cpage - 1) * psize;		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호

		try {
			sql = "select a.*, b.cb_name, c.cs_name from t_product_list a, t_cata_big b, " + 
				" t_cata_small c where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx " + 
				where + orderby + " limit " + snum + ", " + psize;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				pdtInfo = new PdtInfo();
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
				pdtList.add(pdtInfo);
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
			int saleCnt = 0;
			stmt = conn.createStatement();
			sql = "select count(*) from t_order_detail where pl_id = '" + id + "'";
			// 지정된 상품의 판매량을 구하기 위한 쿼리
			rs = stmt.executeQuery(sql);
			if (rs.next())	saleCnt = rs.getInt(1);

			sql = "select a.*, b.cb_name, c.cs_name, d.bl_name " + 
				" from t_product_list a, t_cata_big b, t_cata_small c, t_brand_list d " + 
				" where a.cs_idx = c.cs_idx and a.bl_idx = d.bl_idx and " + 
				" b.cb_idx = c.cb_idx and a.pl_id = '" + id + "' ";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				pdtInfo = new PdtInfo();
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
				pdtInfo.setPl_salecnt(saleCnt);
				pdtInfo.setPl_review(rs.getInt("pl_review"));
				pdtInfo.setPl_view(rs.getString("pl_view"));
				pdtInfo.setPl_date(rs.getString("pl_date"));
				pdtInfo.setAl_idx(rs.getInt("al_idx"));
				pdtInfo.setCb_name(rs.getString("cb_name"));
				pdtInfo.setCs_name(rs.getString("cs_name"));
				pdtInfo.setBl_name(rs.getString("bl_name"));
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
