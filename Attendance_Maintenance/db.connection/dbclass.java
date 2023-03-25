import java.sql.*;

public class dbclass {
	public static void main(String agrs[]) throws ClassNotFoundException, SQLException {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/Attendance_Maintenance";
		String username = "root";
		String password = "JeanMartin002";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from UserCredential");
			System.out.println("SUCCESSFULLY CONNECTED");

		} catch (ClassNotFoundException | SQLException ex) {
			throw new Error("Error ", ex);
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}