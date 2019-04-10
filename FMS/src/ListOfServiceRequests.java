import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author 
 *
 */
public class ListOfServiceRequests {
	ArrayList<ServiceRequest> listOfServiceRequests = new ArrayList<ServiceRequest>();
	
	void SaveAllSRs() {
		try {
			FileOutputStream fos = new FileOutputStream("SaveFileSRList.sav");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(listOfServiceRequests);
			oos.close();
			fos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	Boolean LoadAllUsers() {
		try {
			FileInputStream fis = new FileInputStream("SaveFileSRList.sav");
			ObjectInputStream ois = new ObjectInputStream(fis);
			listOfServiceRequests = (ArrayList<ServiceRequest>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException ioe) {
			//ioe.printStackTrace();
			System.out.println("No Saved state found. Will start fresh.");
			return false;
		} catch (ClassNotFoundException c) {
			System.out.println("Error Class not found");
			c.printStackTrace();
			return false;
		}
		
		return true;
	}

	void PrintAllServiceRequests() {
		for (int i = 0; i < listOfServiceRequests.size(); i++) {
			listOfServiceRequests.get(i).ShowServiceDetails();
		}

	}
	
	
	void AddNewServiceRequest(ServiceRequest sr)
	{
		listOfServiceRequests.add(sr);
	}
	
	
	public ServiceRequest NewServiceRequest(BufferedReader br, int id, int requesterId) {
		// Take input and create new SR
		ServiceRequest sr = null;
		
//		private int ServiceAssignedTo;
//		private Boolean ServiceStatusOpen;
//		private Date ServiceCreationDate;
//		private Date ServiceCloseDate;
//		private Boolean ServiceFeedbackPositive;
//		private String ServiceFeedbackString;
		
		try {
			System.out.println("\n\nFMS IIITD New Service Registration\n\n");


			System.out.print("Enter Service Location> ");
			String srLocation = br.readLine();
			
			///
			//System.out.print("Enter Service Type> ");
			//String srType = br.readLine();
			
			int department = -1;
			while (true) {
				FMS_IIITD.departmentList.PrintAllDepartments();
				System.out.print("Enter Service Type/Department/Profession> ");

				try {
					department = Integer.parseInt(br.readLine());
				} catch (NumberFormatException e) {

				}

				String dname = FMS_IIITD.departmentList.GetDepartmentName(department);
				if (dname == null) {
					System.out.println("Department/profession not found, try again.");
				}else {
					System.out.println("Selected profession: "+dname);
					break;
				}
				

			}
			
			//
			System.out.print("Enter Service Description> ");
			String srDescr = br.readLine();
			sr = new ServiceRequest(id, requesterId, srLocation, department, srDescr);
			
			AddNewServiceRequest(sr);


		} catch (IOException e) {
			e.printStackTrace();
		}

		return sr;
	}
	
	int NumberOfServiceRequestsForEmployee(int ee) {
		int count = 0;
		for (int i = 0; i < listOfServiceRequests.size(); i++) {
			if (listOfServiceRequests.get(i).ServiceAssignedTo == ee)
				count++;
		}
		
		return count;
	}
	
	
	ServiceRequest GetSRForId(int id) {
		for (int i = 0; i < listOfServiceRequests.size(); i++) {
			if (listOfServiceRequests.get(i).ServiceId == id)
				return listOfServiceRequests.get(i);
		}
		
		return null;
	}
	
	ArrayList<Integer>EmployeeLateByHours(long hrs)
	{
		ArrayList<Integer> al = new ArrayList<Integer>();
		for (int i = 0; i < listOfServiceRequests.size(); i++) {
			if (listOfServiceRequests.get(i).GetCompletionTimeHours() > hrs)
			{
				al.add(listOfServiceRequests.get(i).ServiceAssignedTo);
			}
		}
		
		return al;
	}

	public ArrayList<Integer> EmployeeNegetiveFeedback() {
		ArrayList<Integer> al = new ArrayList<Integer>();
		for (int i = 0; i < listOfServiceRequests.size(); i++) {
			if (listOfServiceRequests.get(i).GetNegetiveFeedback())
			{
				al.add(listOfServiceRequests.get(i).ServiceAssignedTo);
			}
		}
		
		return al;
	}

	//Return number of closed SR
	public int GetTasksCompleted(int empId) {
		int count = 0;
		for (int i = 0; i < listOfServiceRequests.size(); i++) {
			if ((listOfServiceRequests.get(i).ServiceAssignedTo==empId) && (!listOfServiceRequests.get(i).ServiceStatusOpen))
			{
				count++;
			}
		}
		
		return count;
	}
	
}
