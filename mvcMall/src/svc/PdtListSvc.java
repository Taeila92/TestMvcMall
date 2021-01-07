package svc;

import static db.JdbcUtil.*;
import java.util.*;

import admin.dao.PdtDao;

import java.sql.*;
import dao.*;
import vo.*;

public class PdtListSvc {
	public ArrayList<CataBigInfo> getCataBigList() {
	// 대분류 목록을 ArrayList 형 인스턴스로 리턴하는 메소드
		ArrayList<CataBigInfo> cataBigList = null;
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		cataBigList = pdtDao.getCataBigList();
		close(conn);
		return cataBigList;
	}

	public ArrayList<CataSmallInfo> getCataSmallList() {
	// 소분류 목록을 ArrayList 형 인스턴스로 리턴하는 메소드
		ArrayList<CataSmallInfo> cataSmallList = null;
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		cataSmallList = pdtDao.getCataSmallList();
		close(conn);
		return cataSmallList;
	}

	public ArrayList<BrandInfo> getBrandList() {
	// 브랜드 목록을 ArrayList 형 인스턴스로 리턴하는 메소드
		ArrayList<BrandInfo> brandList = null;
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		brandList = pdtDao.getBrandList();
		close(conn);
		return brandList;
	}

	public int getPdtCount(String where) {
	// 검색된 상품의 전체 개수를 리턴하는 메소드
		int rcnt = 0;	// 전체 레코드 개수를 저장할 변수
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		rcnt = pdtDao.getPdtCount(where);
		close(conn);

		return rcnt;
	}

	public ArrayList<PdtInfo> getPdtList(String where, String orderby, int cpage, int psize) {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		// 상품 목록을 저장할 ArrayList객체로 PdtInfo형 인스턴스만 저장함
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		pdtList = pdtDao.getPdtList(where, orderby, cpage, psize);
		close(conn);

		return pdtList;
	}
}
