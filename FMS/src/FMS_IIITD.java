import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FMS_IIITD {

	final static String COMMAND_EXIT = "exit";
	final static String COMMAND_HELP = "help";
	final static String COMMAND_LOGOUT = "logout";
	final static String COMMAND_INFO = "info";
	final static String COMMAND_LIST_USERS = "list users";
	final static String COMMAND_LIST_SR = "list services";
	final static String COMMAND_CLOSE_SR = "close service";
	final static String COMMAND_REGISTER_USER = "reg user";
	final static String COMMAND_SERVICE_REQUEST = "service";
	final static String COMMAND_SERVICE_BAD = "bad";

	final static Departments departmentList = new Departments();

	static ListOfUsers listUsers = new ListOfUsers();
	static ListOfServiceRequests listServiceRequests = new ListOfServiceRequests();
	static int userid = 0;
	static int serviceid = 0;

	public static void PrintHelp() {
		System.out.println("Command Help");
		System.out.println("------------");
		System.out.println(COMMAND_EXIT + " - Save & Exit Application");
		System.out.println(COMMAND_HELP + " - Show help text");
		System.out.println(COMMAND_INFO + " - Show user information");
		System.out.println(COMMAND_LIST_USERS + " - Show list of users (Employee/Administrators)");
		System.out.println(COMMAND_LIST_SR + " - Show list of service requests");
		System.out.println(COMMAND_CLOSE_SR + " - CLose service request");
		System.out.println(COMMAND_LOGOUT + " - Logout user ");
		System.out.println(COMMAND_REGISTER_USER + " - New User Registration (Administrator) ");
		System.out.println(COMMAND_SERVICE_REQUEST + " - New Service Request ");
		System.out.println(COMMAND_SERVICE_BAD + " - Fine Bad Employee (Administrator) ");
	}

	public static void main(String[] args) {

		User authenticatedUser = null;

		// User List
		if (!listUsers.LoadAllUsers()) {
			// Not Loaded, create Admin User
			Administrator a = new Administrator(1, "Super Admin", 98000005, "admin", "admin");
			listUsers.AddUser(a);
			listUsers.SaveAllUsers();
		}

		listUsers.PrintAllUsers();
		// For unique userid
		userid = listUsers.listOfUsers.size() + 1;

		// Service Requests
		// Load from File
		listServiceRequests.LoadAllUsers();
		listServiceRequests.PrintAllServiceRequests();

		// For unique Service Requests
		serviceid = listServiceRequests.listOfServiceRequests.size() + 1;

		try {
			String input = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while (true) {

				System.out.println("\n\nWelcome to FMS IIITD\n\n");
				System.out.println("Login to continue...(type 'exit' to End)\n");

				while (true) {

					System.out.print("Enter your User Name> ");
					String user = br.readLine();
					if (user.toLowerCase().equals(COMMAND_EXIT)) {
						System.out.print("Bye bye.");
						return;
					}
					System.out.print("Enter your Password> ");
					String pass = br.readLine();

					authenticatedUser = listUsers.Authenticate(user, pass);
					if (authenticatedUser != null) {
						System.out.println("Welcome, " + authenticatedUser.Name);
						break;
					} else
						System.out.println("Incorrect user name or password. Please try again");
				}

				System.out.println("Enter command, or '" + COMMAND_HELP + "' for help");

				while (true) {

					System.out.print("> ");
					input = br.readLine();
					System.out.println("[" + input + "]");

					if (input.length() == COMMAND_EXIT.length() && input.toLowerCase().equals(COMMAND_EXIT)) {
						// Exit
						System.out.println("Bye bye.");
						// Save all before quit
						listUsers.SaveAllUsers();
						listServiceRequests.SaveAllSRs();
						return;

					} else if (input.length() == COMMAND_HELP.length() && input.toLowerCase().equals(COMMAND_HELP)) {
						// Help
						PrintHelp();
						continue;

					} else if (input.length() == COMMAND_LOGOUT.length()
							&& input.toLowerCase().equals(COMMAND_LOGOUT)) {
						// Logout
						System.out.println("Logging out " + authenticatedUser.Name + " ...");
						authenticatedUser = null;
						break;

					} else if (input.length() == COMMAND_INFO.length() && input.toLowerCase().equals(COMMAND_INFO)) {
						// Info
						authenticatedUser.ShowUser();
						continue;

					} else if (input.length() == COMMAND_LIST_USERS.length()
							&& input.toLowerCase().equals(COMMAND_LIST_USERS)) {
						// List of Users
						authenticatedUser.ListUsers(br, listUsers);
						continue;

					} else if (input.length() == COMMAND_LIST_SR.length()
							&& input.toLowerCase().equals(COMMAND_LIST_SR)) {
						// List of SRs
						authenticatedUser.ListSRs(br, listServiceRequests);
						continue;

					} else if (input.length() == COMMAND_CLOSE_SR.length()
							&& input.toLowerCase().equals(COMMAND_CLOSE_SR)) {
						// Close SR
						authenticatedUser.CloseSR(br, listServiceRequests);
						listServiceRequests.SaveAllSRs();
						continue;
						
					} else if (input.length() == COMMAND_SERVICE_BAD.length()
							&& input.toLowerCase().equals(COMMAND_SERVICE_BAD)) {
						// Find Bad Employee
						if (authenticatedUser instanceof Administrator)
							FindBadEmployee();
						else
							System.out.println("You do not have right to run this command.");
						continue;
						
					} else if (input.length() == COMMAND_REGISTER_USER.length()
							&& input.toLowerCase().equals(COMMAND_REGISTER_USER)) {
						// New Registration
						User usr = authenticatedUser.RegisterNewUser(br, listUsers, userid);
						if (usr != null) {
							// User Created, add and save
							System.out.println("New user '" + usr.Name + "' with id " + usr.Id + " created!");
							userid++;
							listUsers.AddUser(usr);
							listUsers.SaveAllUsers();
						} else {
							System.out.println("Error creating user.");
						}
						continue;

					} else if (input.length() == COMMAND_SERVICE_REQUEST.length()
							&& input.toLowerCase().equals(COMMAND_SERVICE_REQUEST)) {
						// New Service Request
						ServiceRequest sr = listServiceRequests.NewServiceRequest(br, ++serviceid,
								authenticatedUser.Id);
						if (sr != null) {
							// SR Created, assign to employee, or error if department not available
							AllocateSR(sr);
							System.out.println("New SR created!");
							listServiceRequests.SaveAllSRs();
						} else {
							System.out.println("Error creating service request.");
						}
						continue;

					} else {
						// Unknown Command
						System.out.println("Command not understood. Enter 'help' for list of commands.");
					}
				}
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private static void AllocateSR(ServiceRequest sr) {
		// 1. Search for list of employees for the given department
		// 2. Check no of allocated SRs
		// 3. Assign to one with minimum SR

		// 1. Lsit of employees
		ArrayList<Integer> empInDept = listUsers.ListOfUsersInDepartment(sr.ServiceType);

		// 2. Find SRs for each employee

		if (empInDept.size() == 0) {
			// No employee in department, notify to Admin
			System.out.println("Error: No Employee found in the given department. Notifying to Admin.");
			// Send Message to Admin???
		} else {

			int minCount = -1;
			int minEmp = -1;
			ArrayList<Integer> empSRCount = new ArrayList<Integer>();
			for (int i = 0; i < empInDept.size(); i++) {
				int count = listServiceRequests.NumberOfServiceRequestsForEmployee(empInDept.get(i));
				empSRCount.add(i, count);

				if (minCount == -1) {
					minCount = count;
					minEmp = empInDept.get(i);
				} else if (count < minCount) {
					minCount = count;
					minEmp = empInDept.get(i);
				}
			}

			if (minEmp > -1) {
				// Employee Found, allocate to it
				sr.ServiceAssignedTo = minEmp;

				System.out.println("Service Assigned to: " + FMS_IIITD.listUsers.GetUserName(minEmp));
			} else {
				// Error Case
				System.out.println("Error: No Employee found in the given department. Notifying to Admin.");
				// Send Message to Admin???
			}

		}

	}

	// 1. Task >24hr
	// 2. Task Negative Feedback
	// 3. Task 10 less than other
	private static void FindBadEmployee() {
		//Task >24hr
		ArrayList<Integer> emp = listServiceRequests.EmployeeLateByHours(24);
		if (emp.size() == 0) {
			System.out.println("\n\nAll employees completed Service Requests within 24 hours");

		} else {
			System.out.println("\n\nList of employees who did not completed Service Requests within 24 hours");
			for (int i = 0; i < emp.size(); i++) {
				String name = listUsers.GetUserName(emp.get(i));
				int j = i + 1;
				System.out.println(" " + j + ". " + name);
			}

		}
		
		
		// 2. Task Negative Feedback
		emp = listServiceRequests.EmployeeNegetiveFeedback();
		if (emp.size() == 0) {
			System.out.println("\n\nAll employees having positive feedback");

		} else {
			System.out.println("\n\nList of employees having negetive feedback");
			for (int i = 0; i < emp.size(); i++) {
				String name = listUsers.GetUserName(emp.get(i));
				int j = i + 1;
				System.out.println(" " + j + ". " + name);
			}

		}
		
		
		// Task 10 less than other
		ArrayList<Integer> empSRCount = new ArrayList<Integer>();
		emp = listUsers.ListOfUsersInDepartment(-1);
		//Task Count for each employee
		int maxCount = 0;
		for (int i = 0; i < emp.size(); i++) {
			int count = listServiceRequests.GetTasksCompleted(emp.get(i));
			if (count > maxCount) maxCount = count;
			empSRCount.add(i, count);
		}
		
		//Check if anyone 10 below maxCount
		int j = 1;
		for (int i = 0; i < empSRCount.size(); i++) {
			if ((maxCount-empSRCount.get(i)) > 10)
			{
				//FOund bad employee
				String name = listUsers.GetUserName(emp.get(i));
				System.out.println(" " + j + ". " + name);
				j++;
			}
		}
		
	}

}
