package com.Login;

import java.util.Scanner;
import com.admin.AdminPortal;
import com.user.UserPortal;

public class LoginPage {
	public void choose() {
		try (Scanner Option = new Scanner(System.in)) {
			System.out.println("FOR YOUR PREFERENCE\n");
			System.out.println("1 USER\n2 ADMIN\n");
			System.out.print("LOGIN :");
			String choice = Option.nextLine();
			switch (choice) {
			case "1":
				System.out.println("\t\tGO TO USER LOGIN PAGE\n");
				UserPortal myPortal = new UserPortal();
				myPortal.userLogin();
				break;
			case "2":
				System.out.println("\t\tGO TO ADMIN LOGIN PAGE\n");
				AdminPortal myAdmin = new AdminPortal();
				myAdmin.adminLogin();
				break;
			default:
				System.out.println("\t\tINVALID DATA!!! TRY AGAIN........\n");
				choose();
				break;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			choose();
		}
	}

}
