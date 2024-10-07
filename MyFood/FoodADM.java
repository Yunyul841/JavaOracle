package MyFood;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class FoodADM {
	FoodADM() {
		init();
		Scanner in = new Scanner(System.in); 
		while(true) {
			System.out.println("1. 음식등록");
			System.out.println("2. 음식삭제");
			System.out.println("3. 음식수정");
			System.out.println("4. 음식전체보기");
			System.out.println("5. 종료");
			System.out.println("6. 선택 >>");
			
			int num = in.nextInt();
			in.nextLine();
			if(num == 1) {
				insert();
			}else if(num == 2) {
				del();
			}else if(num == 3) {
				mod();
			}else if(num == 4) {
				list(); 
			}else if(num == 5) {
				break;
			}
		}
	}
	private void init() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("오라클 드라이버 로드 성공");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private void insert() {
		FoodDTO ab = new FoodDTO();
		Scanner in = new Scanner(System.in);
		System.out.println("등록활 음식 번호 입력");
		String inNum = in.nextLine();
		System.out.println("등록할 음식 이름 입력");
		String inName = in.nextLine();
		ab.setFoodNum(inNum);
		ab.setFoodName(inName);
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
					"system", "11111111");
			System.out.println("컬랙션 자원 획득 성공");
			String sql = "insert into foodlist values(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ab.getFoodNum());
			pstmt.setString(2, ab.getFoodName());
			int result = pstmt.executeUpdate();
			if(result ==0) {
				conn.rollback();
			}else {
				conn.commit();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if (conn != null){
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

	private void del() {
		Scanner in = new Scanner(System.in);
		FoodDTO ab = new FoodDTO();
		System.out.println("삭제할 음식 이름 입력");
		String Name = in.nextLine();
		ab.setFoodName(Name);
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
					"system", "11111111");
			System.out.println("컬랙션 자원 획득 성공");
			String sql = "delete from foodlist where foodname = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ab.getFoodName());
			int result = pstmt.executeUpdate();
			if(result == 0) {
				conn.rollback();
			}else {
				conn.commit();
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}finally {
			if (conn != null){
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		 
		
	}

	private void mod() {
		Scanner in = new Scanner(System.in);
		FoodDTO ab = new FoodDTO();
		System.out.println("수정하고싶은 번호를 입력하세요");
		String num = in.nextLine();
		ab.setFoodNum(num);
		System.out.println("수정할 번호를 입력하세요");
		String Number = in.nextLine();
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
					"system", "11111111");
			System.out.println("컬랙션 자원 획득 성공");
			String sql = "update foodlist set foodnum = ? where foodnum = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Number);
			pstmt.setString(2, ab.getFoodNum());
			int result = pstmt.executeUpdate();
			if(result == 0) {
				conn.rollback();
			}else {
				conn.commit();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if (conn != null){
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
	}

	private void list() {
		Scanner in = new Scanner(System.in);
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
					"system", "11111111");
			System.out.println("컬랙션 자원 획득 성공");
			String sql = "select * from MyFood";
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
