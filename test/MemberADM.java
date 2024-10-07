package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MemberADM {
	// ojdb6 으로 개발하는 순서
	// 1. 드라이버로드 1번만 하면 된다.
	// 2. CRUD작업이 있을 떄 마다 다음 과정을 생각한다.
	// -------------------------------------
	// 	2-1. 커넥션 가져오기
			// 오라클에서 작업하기 전에 커넥션 자원을 회득해야 한다.
			// 커넥션 자원은 한정적으로 쓰고 나면 반납하는 것이 좋다. 2-7
			// 오라클은 커넥션 자원을 유한으로 만들어 놓고 공유해서 사용하도록 한다.
	//  2-2. 쿼리문 만들기
	//  2-3. 쿼리문 완성하기(Mapping)
	//  2-4. 쿼리문 전송하여 오라클에서 실행
	//  2-5. 오라클에서 리턴값 전송
	//  2-6. 자바에서 2-5에서 받은 리턴값 처리
	//  2-7. 커넥션 자원 반납(중요)
	MemberADM(){
		// 드라이버 로드는 1번만 하면 되므로 생성자에서 작업
		init();
		insert();
	}
	private void init() {
		try {
			// 한번만 하면 댐.
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("오라클 드라이버 로드 성공");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void insert() {
		MemberDTO  m = new MemberDTO();
		// insert test
		// MemberDTO m = new MemberDTO();
		// JVM에 저장되어있다.
		m.setId("a");
		m.setName("Kim");
		m.setAge(32);
		// DTO 객체를 만들었음 오라클에 저장해보자
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
					"system", "11111111");
			System.out.println("커넥션 자원 획득 성공");
			String sql = "insert into memberOne values(?,?,?,default)";
			// 쿼리문은 커넥션을 통해서 연결해라
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 매핑
			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getName());
			pstmt.setInt(3, m.getAge());
			// 실행 후 리턴 값 가져오기
			int result = pstmt.executeUpdate();
			if(result == 0 ) {
				conn.rollback();
			}else {
				conn.commit();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (conn != null){
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
}