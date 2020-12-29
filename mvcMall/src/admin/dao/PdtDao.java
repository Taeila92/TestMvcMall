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
	// DB���� ��з� ����� �޾� �����ϴ� �޼ҵ�
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
				// cataBigList�� ���� CataBigInfo�� �ν��Ͻ� ����
				bigInfo.setCb_idx(rs.getInt("cb_idx"));
				bigInfo.setCb_name(rs.getString("cb_name"));
				bigInfo.setCb_date(rs.getString("cb_date"));
				
				cataBigList.add(bigInfo);
				// ������ ArrayList�� ������ CataBigInfo�� �ν��Ͻ� ����
			}
		} catch(Exception e) {
			System.out.println("getCataBigList() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return cataBigList;		
	}
	
	public ArrayList<CataSmallInfo> getCataSmallList(){
		// DB���� ��з� ����� �޾� �����ϴ� �޼ҵ�
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
					// cataBigList�� ���� CataBigInfo�� �ν��Ͻ� ����
					smallInfo.setCs_idx(rs.getInt("cs_idx"));
					smallInfo.setCb_idx(rs.getInt("cb_idx"));
					smallInfo.setCs_name(rs.getString("cs_name"));
					smallInfo.setCs_date(rs.getString("cs_date"));
					
					cataSmallList.add(smallInfo);
					// ������ ArrayList�� ������ CataBigInfo�� �ν��Ͻ� ����
				}
			} catch(Exception e) {
				System.out.println("getCataSmallList() ����");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}
			return cataSmallList;		
		}

	public ArrayList<BrandInfo> getBrandList(){
	// DB���� ��з� ����� �޾� �����ϴ� �޼ҵ�
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
				// ������ ArrayList�� ������ CataBigInfo�� �ν��Ͻ� ����
			}
		} catch(Exception e) {
			System.out.println("getBrandList() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return brandList;
	}
	public int pdtInsert(PdtInfo pdt) {
	// ��ǰ ��� ó���� ���� �޼ҵ�
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		// ����� �Խñ��� ��ȣ�� ��� ���� ResultSet
		String sql = null, plid = pdt.getCs_idx() + "pdt001";

		try {
			
			sql = "select max(right(pl_id, 3)) from t_product_list where cs_idx = " + pdt.getCs_idx();
			// �ش� �Һз����� ���� ū ���� ���� ��ǰ���̵� ������ ���ڸ��� �߶��
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
			System.out.println("pdtInsert() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(pstmt);
		}

		return result;
	}
	public int getPdtCount(String where) {
		// ������ �޾ƿ� ���ǿ� �´� ��ǰ���� �� ������ �����ϴ� �޼ҵ�
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
			// �˻��� ��ǰ�� ������ result�� ����
		} catch(Exception e) {
			System.out.println("getPdtCount() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return rcnt;
	}
	public ArrayList<PdtInfo> getPdtList(
			String where, String orderby, int cpage, int psize) {
		// �˻����ǰ� ���������� �޾ƿ� ���ǿ� �´� ��ǰ���� �����Ͽ� �׸����  ArrayList<PdtInfo>���·� �����ϴ� �޼ҵ�
			Statement stmt = null;
			ResultSet rs = null;
			String sql = null;
			ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
			// �˻� ����� ������ ArrayList��ü
			PdtInfo pdtInfo = null;// �ϳ��� ��ǰ������ ������ �� pdtList�� ����� �ν��Ͻ� 
			int snum = (cpage - 1) * psize;		// ������ limit ��ɿ��� �����͸� ������ ���� �ε��� ��ȣ

			try {
				sql ="select a.*, b.cb_name, c.cs_name from t_product_list a, t_cata_big b, t_cata_small c " + 
						" where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx " + 
						where + orderby +" limit " + snum + ", " + psize;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
				// rs�� ����� ��� �� ���·� �����ϱ� ���� if�� ������� �ʰ� �ٷ� while���
					pdtInfo = new PdtInfo();
					// �ϳ��� ���ڵ�(��ǰ)�� ������ �ν��Ͻ� ����

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
					// �޾ƿ� ���ڵ��� PdtInfo �ν��Ͻ��� ������� ������ ����

					pdtList.add(pdtInfo);
					// ������ PdtInfo�� �ν��Ͻ��� pdtList �ϳ��� ����
				}
			} catch(Exception e) {
				System.out.println("getPdtList() ����");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}

			return pdtList;
		}
	public PdtInfo getPdtInfo(String id) {
	// ������ id�� �ش��ϴ� �ϳ��� ��ǰ������ PdtInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		PdtInfo pdtInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			sql = "select * from t_product_list where pl_id = '" + id + "' ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
			// rs�� �����Ͱ� ������(�ش� �Խù��� ������)
				pdtInfo = new PdtInfo();
				// ������ �ν��Ͻ�(�����͸� ������ �ν��Ͻ�) ����
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
				// �޾ƿ� ���ڵ��� pdtInfo �ν��Ͻ��� ��� ������ ���� ����
			}
		} catch(Exception e) {
			System.out.println("getPdtInfo() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return pdtInfo;
	}
}
