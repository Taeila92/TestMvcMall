package dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class OrdDao {
	private static OrdDao ordDao;
	private Connection conn;

	private OrdDao() {}
	
	public static OrdDao getInstance() {
		if (ordDao == null) {
			ordDao = new OrdDao();
		}
		return ordDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public int cartInsert(CartInfo cart) {
	// 사용자가 선택한 상품을 장바구니에 담는 메소드
		int result = 0;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			String sql = "select cl_idx from t_cart_list " + 
				" where cl_buyer = '" + cart.getCl_buyer() + "' " + 
				" and cl_ismember = '" + cart.getCl_ismember() + "' " + 
				" and pl_id = '" + cart.getPl_id() + "' " + 
				" and cl_opt = '" + cart.getCl_opt() + "' ";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	// 장바구니의 기존 데이터 중 동일한 상품이 있을 경우
				sql = "update t_cart_list set cl_cnt = cl_cnt + " + cart.getCl_cnt() + 
					" where cl_idx = " + rs.getInt("cl_idx");
			} else {	// 처음 장바구니에 담는 상품일 경우
				sql = "insert into t_cart_list (cl_buyer, cl_ismember, pl_id, cl_opt, cl_cnt) " +
				" values ('" + cart.getCl_buyer() + "', '" + cart.getCl_ismember() + "', '" + 
				cart.getPl_id() + "', '" + cart.getCl_opt() + "', '" + cart.getCl_cnt() + "')";
			}
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("cartInsert() 오류");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return result;
	}

	public ArrayList<CartInfo> getCartList(String where) {
		// 장바구니에서 보여줄 특정 사용자(회원, 비회원)의 장바구니 목록을 리턴하는 메소드
		ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select c.cl_idx, p.pl_id, p.pl_name, p.pl_img1, " +
					" p.pl_opt, c.cl_opt, c.cl_cnt, p.pl_price,p.pl_discount, p.pl_stock " + 
					" from t_cart_list c , t_product_list p " + 
					" where c.pl_id = p.pl_id and p.pl_view = 'y' and p.pl_stock != 0 " + where + 
					" order by p.pl_id, c.cl_opt";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				CartInfo cart = new CartInfo();
				cart.setCl_idx(rs.getInt("cl_idx"));
				cart.setPl_id(rs.getString("pl_id"));
				cart.setPl_name(rs.getString("pl_name"));
				cart.setPl_img1(rs.getString("pl_img1"));
				cart.setPl_opt(rs.getString("pl_opt"));
				cart.setCl_opt(rs.getString("cl_opt"));
				cart.setCl_cnt(rs.getInt("cl_cnt"));
				cart.setPl_stock(rs.getInt("pl_stock"));
				int price = rs.getInt("pl_price");	// 실 구매가
				if (rs.getInt("pl_discount") > 0) {
					float rate = (float)rs.getInt("pl_discount") / 100;
					price = Math.round(price - (price * rate));
				}	// 상품 가격은 할인율이 있을 경우 할인율을 적용한 가격으로 저장함
				cart.setPrice(price);
				cartList.add(cart);
			}
		} catch(Exception e) {
			System.out.println("getCartList() 오류");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return cartList;		
	}
}
