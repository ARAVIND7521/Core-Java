package com.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Scanner;
import com.Login.LoginPage;

public class AdminPortal {
	public void adminLogin() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Attendance_Maintenance", "root",
					"JeanMartin002");
			Statement stmt = con.createStatement();
			Scanner sc = new Scanner(System.in);
			System.out.print("ENTER USERNAME:");
			String UserName = sc.next();
			System.out.print("\n");
			System.out.print("ENTER PASSWORD:");
			String Password = sc.next();
			System.out.print("\n");
			try {
				String var = "select Admindetails.AdminID, AdminDetails.AdminName, AdminCredential.UserName, AdminCredential.Password, AdminDetails.ContactNo, AdminDetails.Address from AdminDetails inner join AdminCredential on AdminDetails.AdminID = AdminCredential.AdminID where UserName='"
						+ UserName + "' and Password= '" + Password + "'";
				ResultSet rs = stmt.executeQuery(var);
				if (rs.next()) {
					System.out.println("\t\tWELCOME!  " + rs.getString(2) + "\n");
					Calendar calendar = Calendar.getInstance();
					System.out.println("TODAY :" + calendar.getTime());
					System.out.println("\nSEE YOUR DETAILS");
					System.out.println("-------------------------------------");
					System.out.println("AdminID\t AdminName\t Address\t");
					System.out.println("-------------------------------------");
					System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(6));
					System.out.println("-------------------------------------");
					adminOption(UserName, rs);
				} else {
					System.out.println("USERNAME OR PASSWORD IS INVALID!!! TRY AGAIN.....");
					adminLogin();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
				adminLogin();
			} finally {
				con.close();
				stmt.close();
				sc.close();
			}
		} catch (SQLException | ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
			adminLogin();
		}
	}

	public void adminOption(String UserName, ResultSet rs) {
		try (Scanner Option = new Scanner(System.in)) {
			System.out.println("\nFOR YOUR PREFERENCE\n");
			System.out.println("1 ADD NEW EMPLOYEE\n2 REMOVE THE EMPLOYEE\n3 SEE THE EMPLOYEE DETAILS\n4 LOGOUT");
			System.out.print("\nENTER YOUR OPTIONAL NUMBER :");
			String AdminDetails = Option.nextLine();
			switch (AdminDetails) {
			case "1":
				System.out.println("\n\t\tWELCOME!! TO ADD NEW HIRE !!!");
				addUser(UserName, rs);
				break;
			case "2":
				System.out.println("\n\t\tWELCOME!! TO DISCARD THE EMPLOYEES !!!");
				removeId(UserName, rs);
				break;
			case "3":
				System.out.println("\n\t\tSEE THE EMPLOYEE DETAILS");
				viewEmployee(UserName, rs);
				break;
			case "4":
				System.out.println("\n\t\tEXIT!!! BACK TO BEGINNING LOGIN");
				LoginPage myLoginPage = new LoginPage();
				myLoginPage.choose();
				break;
			default:
				System.out.println("\n\t\tERROR!!!");
				adminOption(UserName, rs);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			adminOption(UserName, rs);
		}
	}

	public void addUser(String UserName, ResultSet rs) {
		try (Scanner Add = new Scanner(System.in)) {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Attendance_Maintenance", "root",
					"JeanMartin002");
			Statement stmt = con.createStatement();
			System.out.print("\nNEW HIRE FIRST NAME :");
			String First_Name = Add.next();
			System.out.print("\n");
			System.out.print("ADD NEW LAST NAME :");
			String Last_Name = Add.next();
			System.out.print("\n");
			System.out.print("ADD THE EXPERIENCE:");
			String Exp = Add.next();
			System.out.print("\n");
			System.out.print("ADD THE DESIGNATION :");
			String Des = Add.next();
			System.out.print("\n");
			System.out.println("ENTER IN FOLLOWING FORMAT : YYYY-MM-DD");
			System.out.print("ADD THE JOIN DATE :");
			String Doj = Add.next();
			System.out.print("\n");
			System.out.print("ADD THE ADDRESS :");
			String Address = Add.next();
			System.out.print("\n");
			System.out.print("ADD THE ZIPCODE :");
			String ZipCode = Add.next();
			System.out.print("\n");
			String SQLDATA = "INSERT INTO UserDetails (First_Name, Last_Name, Experience, Designation, DOJ, Address, Zipcode, Is_Active) values('"
					+ First_Name + "','" + Last_Name + "','" + Exp + "','" + Des + "','" + Doj + "','" + Address + "','"
					+ ZipCode + "',1)";
			stmt.executeUpdate(SQLDATA);
			System.out.println("SUCCESSFULLY UPDATED!!!\n");
			System.out.println("\t\tGENERATE USERNAME AND PASSWORD\n");
			String userName = First_Name.toLowerCase() + Last_Name.toLowerCase() + "@company.com";
			System.out.println("USERNAME IS : " + userName + "\n".toLowerCase());
			System.out.print("NEW PASSWORD :");
			String Password = Add.next();
			System.out.print("\n");
			System.out.print("MOBILE NO :");
			String MobileNo = Add.next();
			System.out.print("\n");
			String SQLInput = " INSERT INTO usercredential (UserName, Password, MobileNo) values('" + userName + "','"
					+ Password + "','" + MobileNo + "')";
			stmt.executeUpdate(SQLInput);
			System.out.println("SUCCESSFULLY UPDATED!!!\n");
			String var = "select UserDetails.EmpID, UserDetails.First_Name, UserDetails.Last_Name, UserCredential.UserName, UserCredential.Password ,UserDetails.Experience, UserDetails.Designation, UserDetails.DOJ, UserDetails.Address, UserDetails.Zipcode from UserDetails inner join UserCredential on UserDetails.EmpID = UserCredential.EmpID where First_name='"
					+ First_Name + "'";
			ResultSet rs1 = stmt.executeQuery(var);
			if (rs1.next()) {
				System.out.println("SEE THE NEW EMPLOYEE DETAILS");
				System.out.println(
						"---------------------------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println(
						"EmpID\t First_Name\t Last_Name\t Username\t Password\t Experience\t Designation\t DOJ\t Address\t Zipcode\t");
				System.out.println(
						"---------------------------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println(rs1.getInt(1) + "\t" + rs1.getString(2) + "\t" + rs1.getString(3) + "\t"
						+ rs1.getString(4) + "\t" + rs1.getString(5) + "\t" + rs1.getString(6) + "\t" + rs1.getString(7)
						+ "\t" + rs1.getDate(8) + "\t" + rs1.getString(9) + "\t" + rs1.getInt(10));
				System.out.println(
						"---------------------------------------------------------------------------------------------------------------------------------------------------------------");
				adminOption(UserName, rs);
			} else {
				System.out.println("ERROR!!!\n");
				adminOption(UserName, rs);
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			addUser(UserName, rs);
		}
	}

	public void removeId(String UserName, ResultSet rs) {
		try (Scanner View = new Scanner(System.in)) {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Attendance_Maintenance", "root",
					"JeanMartin002");
			Statement stmt = con.createStatement();
			System.out.print("\nDISCONTINUE EMPLOYEE ID :");
			int EmpID = View.nextInt();
			System.out.print("\n");
			LocalDate dateObj = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String CurrentDate = dateObj.format(formatter);
			String sql = "select UserDetails.EmpID, UserDetails.First_Name, UserDetails.Last_Name, UserCredential.UserName, UserCredential.Password ,UserDetails.Experience, UserDetails.Designation, UserDetails.DOJ, UserDetails.Address, UserDetails.Zipcode, UserDetails.Is_Active from UserDetails inner join UserCredential on UserDetails.EmpID = UserCredential.EmpID where Is_Active = 1 and UserDetails.EmpID= '"
					+ EmpID + "'";
			ResultSet resultset = stmt.executeQuery(sql);
			if (resultset.next()) {
				String SQLDetail = "UPDATE UserDetails SET DOD = '" + CurrentDate + "', Is_Active = 0 WHERE EmpID='"
						+ EmpID + "'";
				stmt.executeUpdate(SQLDetail);
				System.out.println("\t\tSUCCESSFULLY DISCARD!!!");
				adminOption(UserName, rs);
			} else {
				System.out.println("EMPLOYEE ID IS INVALID!!! TRY AGAIN.....");
				removeId(UserName, rs);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			removeId(UserName, rs);
		}
	}

	public void viewEmployee(String UserName, ResultSet rs) {
		try (Scanner View = new Scanner(System.in)) {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Attendance_Maintenance",
					"root", "JeanMartin002");
			Statement stmt = connection.createStatement();
			System.out.println("\nVIEW EMPLOYEE DETAILS\n");
			System.out.println("1 CURRENT EMPLOYEES\n2 PAST EMPLOYEES");
			System.out.print("\nENTER THE OPTIONAL NUMBER :");
			int ViewOption = View.nextInt();
			if (ViewOption == 1) {
				System.out.print("\nEMPLOYEE ID :");
				int EmpID = View.nextInt();
				String var = "select EmpID, First_Name, Last_Name, Designation , DOJ, datediff(CURDATE(), DOJ)/365 as \"Experience\", address, zipcode from userdetails where Is_Active = 1 and EmpID='"
						+ EmpID + "'";
				ResultSet rs1 = stmt.executeQuery(var);
				if (rs1.next()) {
					System.out.println(
							"-------------------------------------------------------------------------------------------------------------");
					System.out.println(
							"EmpID\t First_Name\t Last_Name\t Designation\t DOJ\t Experience\t Address\t Zipcode\t");
					System.out.println(
							"-------------------------------------------------------------------------------------------------------------");
					System.out.println(rs1.getInt(1) + "\t" + rs1.getString(2) + "\t" + rs1.getString(3) + "\t"
							+ rs1.getString(4) + "\t" + rs1.getDate(5) + "\t" + rs1.getString(6) + "\t" + rs1.getString(7)
							+ "\t" + rs1.getString(8));
					System.out.println(
							"-------------------------------------------------------------------------------------------------------------");
					adminOption(UserName, rs);
				} else {
					System.out.print("\n\tINVALID EMPLOYEE ID\n");
					viewEmployee(UserName, rs);
				}
			} else if (ViewOption == 2) {
				System.out.print("\nEMPLOYEE ID :");
				int EmpID = View.nextInt();
				String varopt = "select EmpID, First_Name, Last_Name, Designation , DOJ, DOD, datediff(DOD, DOJ)/365 as \"Experience\", address, zipcode from userdetails where Is_Active = 0 and EmpID='"
						+ EmpID + "'";
				ResultSet resultset = stmt.executeQuery(varopt);
				if (resultset.next()) {
					System.out.println(
							"-------------------------------------------------------------------------------------------------------------------");
					System.out.println(
							"EmpID\t First_Name\t Last_Name\t Designation\t DOJ \t DOD\t Experience\t Address\t Zipcode\t");
					System.out.println(
							"-------------------------------------------------------------------------------------------------------------------");
					System.out.println(resultset.getInt(1) + "\t" + resultset.getString(2) + "\t"
							+ resultset.getString(3) + "\t" + resultset.getString(4) + "\t" + resultset.getDate(5)
							+ "\t" + resultset.getDate(6) + "\t" + resultset.getString(7) + "\t"
							+ resultset.getString(8) + "\t" + resultset.getString(9));
					System.out.println(
							"-------------------------------------------------------------------------------------------------------------------");
					adminOption(UserName, rs);
				} else {
					System.out.print("\n\tINVALID EMPLOYEE ID\n");
					viewEmployee(UserName, rs);
				}
			} else {
				System.out.print("\n\tSORRY!!! INVALID\n");
				viewEmployee(UserName, rs);
			}
			View.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			viewEmployee(UserName, rs);
		}
	}
}

//String var = "select UserDetails.EmpID, UserDetails.First_Name, UserDetails.Last_Name, UserCredential.UserName, UserCredential.Password ,UserDetails.Experience, UserDetails.Designation, UserDetails.DOJ, UserDetails.Address, UserDetails.Zipcode from UserDetails inner join UserCredential on UserDetails.EmpID = UserCredential.EmpID where UserCredential.EmpID= '"
//+ EmpID + "'";
//ResultSet rs = stmt.executeQuery(var);
//if (rs.next()) {
//System.out.print("ENTER PASSWORD:");
//String Password = View.next();
//System.out.print("\n");
//try {
//String var = "select Admindetails.AdminID, AdminDetails.AdminName, AdminCredential.UserName, AdminCredential.Password, AdminDetails.ContactNo, AdminDetails.Address from AdminDetails inner join AdminCredential on AdminDetails.AdminID = AdminCredential.AdminID where Password= '"
//		+ Password + "'";
//ResultSet rs = stmt.executeQuery(var);
//if (rs.next()) {
//String SQL1Credential = "DELETE FROM UserCredential WHERE EmpID='" + EmpID + "'";
//stmt.executeUpdate(SQL1Credential);
//System.out.print("REASON FOR DISCONTINUE :");
//String Reason = View.next();
//System.out.print("\n");