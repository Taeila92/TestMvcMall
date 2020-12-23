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
				bigInfo.setCb_name(rs.getString("cb_idx"));
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
					smallInfo.setCs_name(rs.getString("cs_idx"));
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
				brandInfo.setBl_name(rs.getString("bl_idx"));
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
}
