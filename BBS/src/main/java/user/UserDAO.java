package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// ������ db�� �����ؼ� �����͸� �������ų� �ִ� ������ �ϴ� ������ ���� ��ü
public class UserDAO {
	
	private Connection conn;
	private PreparedStatement pstmt; //��ŷ������ ���� ���
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
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; //�Ű������� ���� userID�� ?�� �� �� �ְ� ��
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery(); //�ϳ��� ��ü�� ������ �־���
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1; // �α��� ����
				}
				else
					return 0; //��й�ȣ ����ġ
			}
			return -1; //���̵� x
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -2; //�����ͺ��̽� ����
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
		}return -1; //�����ͺ��̽� ����
	}
}