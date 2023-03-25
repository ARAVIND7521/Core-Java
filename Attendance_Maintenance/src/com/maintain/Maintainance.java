package com.maintain;

import java.sql.*;
import com.Login.LoginPage;

public class Maintainance {
	public static void main(String agrs[]) throws ClassNotFoundException, SQLException {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/Attendance_Maintenance";
		String username = "root";
		String password = "JeanMartin002";
		try {
//			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException ex) {
			throw new Error("ERROR ", ex);
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		System.out.println("\t\tATTENDANCE MAINTENANCE\n");
		LoginPage loginPage = new LoginPage();
		loginPage.choose();
	}
}
