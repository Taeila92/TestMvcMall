package svc;

import static db.JdbcUtil.*;
// db패키지 내의 JdbcUtil클래스가 가진 메소드들을 자유롭게 사용하겠다는 의미
import java.sql.*;
import dao.*;
import vo.*;

public class LoginSvc {
// 로그인에 대한 처리작업을 실행하는 클래스(DB처리 제외)
	public MemberInfo getLoginMember(String uid, String pwd) {
	// 사용자가 입력한 아이디와 비밀번호를 매개변수로 받아 로그인을 처리하는 메소드
	// 처리 결과를 MemberInfo형 인스턴스로 리터해줌
		LoginDao loginDao = LoginDao.getInstance();
		// LoginDao 형 인스턴스 loginDao를 선언 및 생성 
		Connection conn = getConnection();
		// JdbcUtil 클래스의 getConnection() 메소드를 이용하여 
		// Connection 객체 conn 생성(DB연결)
		loginDao.setConnection(conn);
		// loginDao인스턴스에서 사용할 Connection 객체를 전달함
		MemberInfo loginMember = loginDao.getLoginMember(uid, pwd);
		// loginDao인스턴스의 getLoginMember() 메소드를 실행시키고, 
		// 그 결과를 MemberInfo 형 인스턴스 loginMember에 저장
		close(conn);
		// Connection 객체 닫기(DB연결이 끊어짐)
		// DB작업이 모두 종료되면 Connection객체를 닫아줌

		return loginMember;
		// getLoginMember() 메소드의 실행 결과값 리턴
	}
}
