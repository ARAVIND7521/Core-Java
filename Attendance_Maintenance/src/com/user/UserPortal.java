package com.user;

import java.sql.*;
import java.util.Calendar;
import java.util.Scanner;
import com.Login.LoginPage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserPortal {
	public void userLogin() {
		try {
//			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Attendance_Maintenance", "root",
					"JeanMartin002");
			Statement stmt = con.createStatement();
			Scanner sc = new Scanner(System.in);
			System.out.print("ENTER THE USERNAME:");
			String userName = sc.next();
			System.out.print("\n");
			System.out.print("ENTER THE PASSWORD:");
			String password = sc.next();
			System.out.print("\n");
			try {
				String var = " select UserDetails.EmpID, UserDetails.First_Name, UserDetails.Last_Name, UserCredential.UserName, UserCredential.Password, UserDetails.Designation, UserDetails.DOJ, DATEDIFF(CURDATE(), DOJ)/365 as Experience, UserDetails.Address, UserDetails.Zipcode, UserDetails.Is_Active from UserDetails inner join UserCredential on UserDetails.EmpID = UserCredential.EmpID where Is_Active = 1 and UserName='"
						+ userName + "' and Password= '" + password + "'";
				ResultSet rs = stmt.executeQuery(var);
				if (rs.next()) {
					System.out.println("\t\tWELCOME!  " + rs.getString(2) + "\n");
					Calendar calendar = Calendar.getInstance();
					System.out.println("TODAY :" + calendar.getTime());
					System.out.println("\nSEE YOUR DETAILS");
					System.out.println(
							"----------------------------------------------------------------------------------------------------------");
					System.out.println(
							"EmpID\t First_Name\t Last_Name\t Designation\t DOJ\t Experience\t Address\t Zipcode\t");
					System.out.println(
							"----------------------------------------------------------------------------------------------------------");
					System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
							+ rs.getString(6) + "\t" + rs.getDate(7) + "\t" + rs.getString(8) + "\t" + rs.getString(9)
							+ "\t" + rs.getInt(10));
					System.out.println(
							"----------------------------------------------------------------------------------------------------------");
					UserAccess myUserAccess = new UserAccess();
					myUserAccess.userOption(userName, rs);

				} else {
					System.out.println("USERNAME OR PASSWORD IS INVALID!!! TRY AGAIN.....\n");
					userLogin();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
				userLogin();
			} finally {
				con.close();
				stmt.close();
				sc.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			userLogin();
		}
	}

	public class UserAccess {
		public void userOption(String userName, ResultSet rs) {
			try (Scanner Option = new Scanner(System.in)) { // Create a Scanner object
				System.out.println("\nFOR YOUR PREFERENCE\n");
				System.out.println("1 YOU WANT TO ENTER THE DAILY ATTENDANCE\n2 LOGOUT!!! BACK TO USER PORTAL\n3 EXIT");
				System.out.print("\nENTER YOUR OPTIONAL NUMBER :");
				String userDetails = Option.nextLine();
				switch (userDetails) {
				case "1":
					System.out.println("\n\t\tWELCOME!! TO ATTENDANCE SHEET !!!");
					dailySheet(userName, rs);
					break;
				case "2":
					System.out.println("\n\t\tBACK TO USER PORTAL");
					userLogin();
					break;
				case "3":
					System.out.println("\n\t\tEXIT!!! BACK TO BEGINNING LOGIN");
					LoginPage myLogin = new LoginPage();
					myLogin.choose();
					break;
				default:
					System.out.println("\n\t\tERROR!!!");
					userOption(userName, rs);
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				userOption(userName, rs);
			}
		}

		public void dailySheet(String userName, ResultSet rs) {
			try (Scanner Option = new Scanner(System.in)) {
				System.out.println("YOU WANT TO UPDATE THE ATTENDANCE SHEET!!!\n");
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/Attendance_Maintenance",
						"root", "JeanMartin002");
				Statement stmt1 = con1.createStatement();
				try {
					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
					String currentDate = formatter.format(date);
					System.out.println("UPDATING ATTENDANCE FOR: " + currentDate);
					System.out.println("\nMODE TO WORK:\n");
					System.out.println("1 WORK FROM HOME - WFH\n2 WORK FROM OFFICE - WFO\n3 LEAVE");
					System.out.print("\nENTER THE OPTIONAL NUMBER :");
					String workStatus = Option.nextLine();
					String find = "select * from Attendance_details where EmpID = '" + rs.getInt(1) + "' and "
							+ currentDate + " = '" + workStatus + "'";
					ResultSet set = stmt1.executeQuery(find);
					if (set.next()) {
						System.out.println("\tTODAY ATTENDANCE IS ALREADY UPDATED!!!");
						userOption(userName, rs);
					}
					String find1 = "select * from attendance_details where " + currentDate + " = NULL";
					ResultSet set1 = stmt1.executeQuery(find1);
					if (set1.next()) {
						System.out.println("\tATTENDANCE FOR TODAY");
					}
					String find2 = "select * from attendance_details where " + currentDate + "";
					ResultSet set2 = stmt1.executeQuery(find2);
					if (set2.next()) {
						System.out.println("\tATTENDANCE FOR TODAY");
					} else {
						System.out.println("\tUPDATE THE TODAY ATTNEDANCE!!!");
						String Query = "ALTER TABLE attendance_details ADD " + currentDate + " int";
						stmt1.executeUpdate(Query);
					}
					switch (workStatus) {
					case "1":
						System.out.println("\nWORKING STATUS-WFH");
						String SQL = "INSERT INTO Attendance_details (EmpID, EmpName, Designation, " + currentDate
								+ ") VALUES ('" + rs.getInt(1) + "', '" + rs.getString(2) + "','" + rs.getString(6)
								+ "','" + workStatus + "')";
						stmt1.executeUpdate(SQL);
						System.out.println("SUCCESSFULLY UPDATED!!!\n");
						AddThings myThings = new AddThings();
						myThings.addItem(userName, rs);
						break;
					case "2":
						System.out.println("\nWORKING STATUS-WFO");
						String SQL1 = "INSERT INTO Attendance_details (EmpID, EmpName, Designation, " + currentDate
								+ ") VALUES ('" + rs.getInt(1) + "', '" + rs.getString(2) + "','" + rs.getString(6)
								+ "','" + workStatus + "')";
						stmt1.executeUpdate(SQL1);
						System.out.println("SUCCESSFULLY UPDATED!!!\n");
						AddThings myAddObj = new AddThings();
						myAddObj.addItem(userName, rs);
						break;
					case "3":
						System.out.println("\nWORKING STATUS-LEAVE");
						String SQL2 = "INSERT INTO Attendance_details (EmpID, EmpName, Designation, " + currentDate
								+ ") VALUES ('" + rs.getInt(1) + "', '" + rs.getString(2) + "','" + rs.getString(6)
								+ "','" + workStatus + "')";
						stmt1.executeUpdate(SQL2);
						System.out.println("SUCCESSFULLY UPDATED!!!\n");
						AddThings myThingsObj = new AddThings();
						myThingsObj.addItem(userName, rs);
						break;
					default:
						System.out.println("SORRY!!! INVALID\n");
						UserAccess myUseAccess = new UserAccess();
						myUseAccess.dailySheet(userName, rs);
					}
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					System.out.println("INVALID!!!\n");
					dailySheet(userName, rs);
				} finally {
					stmt1.close();
					con1.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
				dailySheet(userName, rs);
			}
		}
	}

	public class AddThings {
		public void addItem(String userName, ResultSet rs) {
			try (Scanner Option = new Scanner(System.in)) {
				System.out.println("1 ADD ONE-MORE UPDATE\n2 BACK TO OPTION\n3 VIEW ATTENDANCE");
				System.out.print("\nENTER THE OPTIONAL NUMBER :");
				String update = Option.next();
				switch (update) {
				case "1":
					System.out.println("\n\tYOU WANT TO UPDATE MORE DATA\n");
					UserAccess myUseObj = new UserAccess();
					myUseObj.dailySheet(userName, rs);
					break;
				case "2":
					System.out.println("\n\tTHANK FOR YOUR USEFUL UPDATE!!!!\n");
					Status myStatus = new Status();
					myStatus.potalStatus(userName, rs);
					break;
				case "3":
					System.out.println("\n\tVIEW THE ATTENDANCE PORTAL!!!!\n");
					ViewAttendance myView = new ViewAttendance();
					myView.AttendanceStatus(userName, rs);
					break;
				default:
					System.out.println("\n\tSORRY!!! INVALID\n");
					addItem(userName, rs);
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				addItem(userName, rs);
			}
		}
	}

	public class ViewAttendance {
		public void AttendanceStatus(String userName, ResultSet rs) {
			try (Scanner view = new Scanner(System.in)) {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Attendance_Maintenance",
						"root", "JeanMartin002");
				Statement stmt = con.createStatement();
				try {
					String data = "select * from Attendance_details where EmpID = " + rs.getInt(1) + "";
					ResultSet result = stmt.executeQuery(data);
					ResultSetMetaData rsMetaData = result.getMetaData();
					System.out.println("\nSEE YOUR ATTENDANCE DETAILS");
					int count = rsMetaData.getColumnCount();
					System.out.println("---------------------------------------------------------------------");
					for (int i = 1; i <= count; i++) {
						System.out.print(rsMetaData.getColumnName(i) + "   ");
					}
					if (result.next()) {
						System.out.println("\n---------------------------------------------------------------------");

						for (int j = 1; j <= count; j++) {
							System.out.print(result.getString(j) + "   ");
						}
						System.out.println("\n---------------------------------------------------------------------");
						AddThings option = new AddThings();
						option.addItem(userName, rs);
					}
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					AddThings option = new AddThings();
					option.addItem(userName, rs);
				} finally {
					con.close();
					stmt.close();
				}

			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				AddThings option = new AddThings();
				option.addItem(userName, rs);
			}
		}
	}

	public class Status {
		public void potalStatus(String userName, ResultSet rs) {
			try (Scanner log = new Scanner(System.in)) {
				System.out.println("1 BACK TO ON-FEILD\n2 LOGOUT\n3 EXIT ");
				System.out.print("\nENTER THE OPTIONAL NUMBER :");
				int finalOption = log.nextInt();
				if (finalOption == 1) {
					System.out.println("\n\tGO BACK TO USER PORTAL PAGE");
					UserAccess myUserAccess = new UserAccess();
					myUserAccess.userOption(userName, rs);
				} else if (finalOption == 2) {
					System.out.println("\n\tLOGOUT SUCCESSSFULLY!!!\n");
					UserPortal myUserPortal = new UserPortal();
					myUserPortal.userLogin();
				} else if (finalOption == 3) {
					System.out.println("\n\tGO BACK TO BEGINNING LOGIN PAGE\n");
					LoginPage myLoginPage = new LoginPage();
					myLoginPage.choose();
				} else {
					System.out.println("SORRY!!! INVALID\n");
					potalStatus(userName, rs);
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				potalStatus(userName, rs);
			}
		}
	}
}

//System.out.println("ENTER IN FOLLOWING FORMAT : DD-MM-YYYY");
//System.out.print("ENTER THE DATE:");
//System.out.print("UPDATING ATTENDANCE FOR:");
//LocalDate dateObj = LocalDate.now();
//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MON/yyyy");
//String currentDate = dateObj.format(formatter);
//String date = Option.nextLine();
//Date AccuracyDate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
//String SQL = "update AugAttendance set EmpName ='" + EmpName + "',Designation ='" + Designation
//+ "', Aug01='" + WorkStatus + "', Aug02='" + WorkStatus + "', Aug03='" + WorkStatus + "', Aug04='" + WorkStatus
//+ "', Aug05='" + WorkStatus + "', Aug06='" + WorkStatus + "', Aug07='" + WorkStatus + "', Aug08='" + WorkStatus
//+ "', Aug09='" + WorkStatus + "', Aug10='" + WorkStatus + "', Aug11='" + WorkStatus + "', Aug12='" + WorkStatus
//+ "', Aug13='" + WorkStatus + "', Aug14='" + WorkStatus + "', Aug15='" + WorkStatus + "', Aug16='" + WorkStatus
//+ "', Aug17='" + WorkStatus + "', Aug18='" + WorkStatus + "', Aug19='" + WorkStatus + "', Aug20='" + WorkStatus
//+ "', Aug21='" + WorkStatus + "', Aug22='" + WorkStatus + "', Aug22='" + WorkStatus + "', Aug23='" + WorkStatus
//+ "', Aug25='" + WorkStatus + "', Aug26='" + WorkStatus + "', Aug27='" + WorkStatus + "', Aug28='" + WorkStatus
//+ "', Aug29='" + WorkStatus + "', Aug30='" + WorkStatus + "', Aug31='" + WorkStatus + "' where EmpID ='"
//+ EmpID + "'";
//stmt.executeUpdate(SQL);
//System.out.println(result.getInt(1) + "\t" + result.getString(2) + "\t" + result.getString(3)
//+ "\t" + result.getString(4));