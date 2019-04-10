import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * 
 */

/**
 * @author 
 *
 */
public class Employee extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7757295657558858364L;
	public int Department;
	//public String Profession;

	protected Employee(int id, String name, int phone, String un, String pass,  int dept) {
		super(id, name, phone, un, pass);
		// TODO Auto-generated constructor stub
		Department = dept;
		//Profession = pro;
	}
	
	/* (non-Javadoc)
	 * @see User#ShowUser()
	 */
	@Override
	public void ShowUser() {
		System.out.println("\nEMPLOYEE");
		super.ShowUser();
		
		System.out.println("Department " + Department + " ("+FMS_IIITD.departmentList.GetDepartmentName(Department)+")");
		//System.out.println("Profession " + Profession);
	}
	
	@Override
	public void ListUsers(BufferedReader br, ListOfUsers list) {
		System.out.println("You have permission to see list of students.");
		for (int i = 0; i < list.listOfUsers.size(); i++) {
			if (list.listOfUsers.get(i) instanceof Student )
				list.listOfUsers.get(i).ShowUser();
		}
	}
	
	@Override
	public Boolean ListSRs(BufferedReader br, ListOfServiceRequests list) {
		Boolean available = false;
		System.out.println("Your Open Service Requests");
		for (int i = 0; i < list.listOfServiceRequests.size(); i++) {
			if ((list.listOfServiceRequests.get(i).ServiceAssignedTo == Id ) 
					&& (list.listOfServiceRequests.get(i).ServiceStatusOpen))
			{
				//Service assigned to User, as well as open
				available = true;
				list.listOfServiceRequests.get(i).ShowServiceDetails();
			}
		}
		
		return available;
	}

	@Override
	public void CloseSR(BufferedReader br, ListOfServiceRequests listServiceRequests) {
		// Close SR, update feedback
		if (ListSRs(br, listServiceRequests))
		{
			//Have SR
			System.out.println("You have open request(s).");
			ServiceRequest sr = null;
			String comments = null;
			int srId = -1;
			while (true) {
				System.out.print("Enter SR Id to close > ");

				try {
					srId = Integer.parseInt(br.readLine());
				} catch (NumberFormatException | IOException e) {

				}

				sr = listServiceRequests.GetSRForId(srId);
				if ((sr != null)&&(sr.ServiceAssignedTo == Id)&&(sr.ServiceStatusOpen)) {
					break;
				}else {
					System.out.println("Invalid Id, try again.");
				}
			}
			
			//Now close & comment
			System.out.println("Closing SR");
			System.out.println("----------");
			sr.ShowServiceDetails();
			
			System.out.print("Enter your Service Comment> ");
			try {
				comments = br.readLine();
			} catch (IOException e) {

			}
			
			sr.ServiceStatusOpen = false;
			sr.ServiceEmployeeString = comments;
			sr.ServiceCloseDate = Instant.now();

			long hrs = ChronoUnit.HOURS.between(sr.ServiceCreationDate , sr.ServiceCloseDate);
			
			System.out.println("Service Closed in "+hrs+" hours.");
			System.out.println("Service Request Closed. Student Notified.");
			//Send Mail???
		}
		else
		{
			System.out.println("You have no outstanding open SR.");
		}
		
	}


}
