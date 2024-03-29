package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// 실제로 db에 접근해서 데이터를 가져오거나 넣는 역할을 하는 데이터 접근 객체
public class UserDAO {
	
	private Connection conn;
	private PreparedStatement pstmt; //해킹방지를 위해 사용
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS?serverTimezone=Asia/Seoul&useSSL=false";
			String dbID = "root";
			String dbPassword = "1234";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; //매개변수로 들어온 userID를 ?에 들어갈 수 있게 함
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery(); //하나의 객체에 실행결과 넣어줌
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1; // 로그인 성공
				}
				else
					return 0; //비밀번호 불일치
			}
			return -1; //아이디 x
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -2; //데이터베이스 오류
	}
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES(?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  user.getUserID());
			pstmt.setString(2,  user.getUserPassword());
			pstmt.setString(3,  user.getUserName());
			pstmt.setString(4,  user.getUserEmail());
			pstmt.setString(5,  user.getUserGender());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}return -1; //데이터베이스 오류
	}
}