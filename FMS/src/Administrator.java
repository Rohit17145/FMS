import java.io.BufferedReader;
import java.io.IOException;

/**
 * 
 */

/**
 * @author
 *
 */
public class Administrator extends User {

	protected Administrator(int id, String name, int phone, String un, String pass) {
		super(id, name, phone, un, pass);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -74044864504423868L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see User#ShowUser()
	 */
	@Override
	public void ShowUser() {
		System.out.println("\nADMINISTRATOR");
		super.ShowUser();
	}

	@Override
	public User RegisterNewUser(BufferedReader br, ListOfUsers list, int id) {
		// Take input and create new user based on input
		try {
			System.out.println("\n\nFMS IIITD User Registration\n\n");
			int userType = 0;
			int userPhone = 0;

			// Type of User, allow only 1,2,3
			while (true) {
				System.out.print("Type of User 1 (Student), 2 (Employee), 3 (Administrator) ");
				System.out.print("Enter User Type> ");
				try {
					userType = Integer.parseInt(br.readLine());
				} catch (NumberFormatException e) {

				}
				// Validate
				if (userType > 0 && userType < 4)
					break;
			}

			String userName = null;
			while (true) {

				System.out.print("Enter your Login Name> ");
				userName = br.readLine();
				if (list.IsUserOK(userName))
					break;
				else
					System.out.print("Invalid user name, or already exists. Please select a different name.");
			}

			System.out.print("Enter your Password> ");
			String pass = br.readLine();
			System.out.print("Enter your Name> ");
			String name = br.readLine();

			System.out.print("Enter Phone> ");
			try {
				userPhone = Integer.parseInt(br.readLine());
			} catch (NumberFormatException e) {

			}

			switch (userType) {
			case 1: // Student
				int rollNo = 0;
				System.out.print("Enter Roll No> ");
				try {
					rollNo = Integer.parseInt(br.readLine());
				} catch (NumberFormatException e) {

				}

				System.out.print("Enter your Program> ");
				String program = br.readLine();

				int year = 0;
				System.out.print("Enter Year> ");
				try {
					year = Integer.parseInt(br.readLine());
				} catch (NumberFormatException e) {

				}

				return new Student(id, name, userPhone, userName, pass, rollNo, program, year);

			case 2: // Employee

				int department = -1;
				while (true) {
					FMS_IIITD.departmentList.PrintAllDepartments();
					System.out.print("Enter your Department> ");

					try {
						department = Integer.parseInt(br.readLine());
					} catch (NumberFormatException e) {

					}

					String dname = FMS_IIITD.departmentList.GetDepartmentName(department);
					if (dname == null) {
						System.out.println("Department not found, try again.");
					}else {
						System.out.println("Selected Department: "+dname);
						break;
					}
					

				}
				// System.out.print("Enter your Profession> ");
				// String profession = br.readLine();

				// return new Employee(id, name, userPhone, userName, pass, department,
				// profession);
				return new Employee(id, name, userPhone, userName, pass, department);

			case 3: // Administrators
				return new Administrator(id, name, userPhone, userName, pass);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	public void ListUsers(BufferedReader br, ListOfUsers list) {
		System.out.println("List of all users.");
		for (int i = 0; i < list.listOfUsers.size(); i++) {
			list.listOfUsers.get(i).ShowUser();
		}
	}
	
	
	@Override
	public Boolean ListSRs(BufferedReader br, ListOfServiceRequests list) {
		Boolean available = false;
		System.out.println("All Open Service Requests");
		for (int i = 0; i < list.listOfServiceRequests.size(); i++) {
			if (list.listOfServiceRequests.get(i).ServiceStatusOpen)
			{
				//Service open
				available = true;
				list.listOfServiceRequests.get(i).ShowServiceDetails();
			}
		}
		
		return available;
	}
	
	@Override
	public void CloseSR(BufferedReader br, ListOfServiceRequests listServiceRequests) {
			System.out.println("Administrators cannot close Service Requests.");
	}

}
