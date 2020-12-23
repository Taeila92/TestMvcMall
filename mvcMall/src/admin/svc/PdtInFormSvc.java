package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class PdtInFormSvc {
	public ArrayList<CataBigInfo> getCataBigList(){
		//대분류목록을 ArrayList형 인스턴스로 리턴하는 메소드
		ArrayList<CataBigInfo> cataBigList = null;
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);

		cataBigList = pdtDao.getCataBigList();

		close(conn);
		return cataBigList;
	}

	public ArrayList<CataSmallInfo> getCataSmallList(){
		//소분류목록을 ArrayList형 인스턴스로 리턴하는 메소드
		ArrayList<CataSmallInfo> cataSmallList = null;
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);

		cataSmallList = pdtDao.getCataSmallList();

		close(conn);
		return cataSmallList;
	}
	public ArrayList<BrandInfo> getBrandList(){
		//브랜드목록을 ArrayList형 인스턴스로 리턴하는 메소드
		ArrayList<BrandInfo> brandList = null;
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);

		brandList = pdtDao.getBrandList();
		// 수정할 글에 대한 권한이 있을 경우 해당 데이터를 가져옴

		close(conn);
		return brandList;
	}
}
