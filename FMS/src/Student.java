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
public class Student extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8309429119500633124L;
	public int RollNo;
	public String Program;
	public int Year;
	
	
	Student(int id, String name, int phone, String un, String pass, int roll, String prog, int year) {
		super(id, name, phone, un, pass);
		RollNo = roll;
		Program = prog;
		Year = year;
	}
	
	/* (non-Javadoc)
	 * @see User#ShowUser()
	 */
	@Override
	public void ShowUser() {
		System.out.println("\nSTUDENT");
		super.ShowUser();
		
		System.out.println("Roll No " + RollNo);
		System.out.println("Program " + Program);
		System.out.println("Year " + Year);
	}
	
	@Override
	public Boolean ListSRs(BufferedReader br, ListOfServiceRequests list) {
		Boolean available = false;
		System.out.println("Your Open Service Requests");
		for (int i = 0; i < list.listOfServiceRequests.size(); i++) {
			if ((list.listOfServiceRequests.get(i).ServiceRequestFrom == Id ) 
					&& ((list.listOfServiceRequests.get(i).ServiceStatusOpen) || 
							(!list.listOfServiceRequests.get(i).ServiceFeedbackGiven)))
			{
				//Service Request of User, as well as open
				available = true;
				list.listOfServiceRequests.get(i).ShowServiceDetails();
			}
		}
		
		return available;
	}

	@Override
	public void CloseSR(BufferedReader br, ListOfServiceRequests listServiceRequests) {
		// For Closed SR, update feedback
		if (ListSRs(br, listServiceRequests))
		{
			//Have SR
			System.out.println("You have open request(s).");
			ServiceRequest sr = null;
			String comments = null;
			int srId = -1;
			while (true) {
				System.out.print("Enter SR Id to give feedback > ");

				try {
					srId = Integer.parseInt(br.readLine());
				} catch (NumberFormatException | IOException e) {

				}

				sr = listServiceRequests.GetSRForId(srId);
				if ((sr != null)&&(sr.ServiceRequestFrom == Id)&&(!sr.ServiceStatusOpen)&&(!sr.ServiceFeedbackGiven)) {
					break;
				}else {
					System.out.println("Invalid Id, try again.");
				}
			}
			
			//Now close & comment
			System.out.println("Giving feedback for SR");
			System.out.println("----------------------");
			sr.ShowServiceDetails();
			
			Boolean positive = false;
			try {
				System.out.print("Service Feedback Positive (y/n)?> ");
				comments = br.readLine();
				if (comments.equalsIgnoreCase("y"))
					positive = true;
				
				System.out.print("Enter your Service Comment> ");
				comments = br.readLine();
			} catch (IOException e) {

			}
			
			sr.ServiceFeedbackGiven = true;
			sr.ServiceFeedbackPositive = positive;
			sr.ServiceFeedbackString = comments;
			
			if (positive)
				System.out.println("Positive feedback recorded.");
			else
				System.out.println("Negetive feedback recorded.");
			
			//Send Mail???
		}
		else
		{
			System.out.println("You have no pending SR for feedback.");
		}
		
	}


}
