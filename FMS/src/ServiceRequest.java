import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 
 */

/**
 * @author 
 *
 */
public class ServiceRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4314529075012784996L;
	
	public int ServiceId;
	public String ServiceLocation;
	public int ServiceType;	//Department/Profession
	public String ServiceDescription;

	public int ServiceRequestFrom;
	public int ServiceAssignedTo;
	
	public Boolean ServiceStatusOpen;
	public Instant ServiceCreationDate;
	
	public Instant ServiceCloseDate;
	public String ServiceEmployeeString;
	
	public Boolean ServiceFeedbackGiven;
	public Boolean ServiceFeedbackPositive;
	public String ServiceFeedbackString;
	
	ServiceRequest(int id, int from, String location, int type, String description)
	{
		ServiceId = id;
		//Set Date
		ServiceCreationDate = Instant.now();
		ServiceRequestFrom = from;
		ServiceLocation = location;
		ServiceType = type;
		ServiceDescription = description;
		
		ServiceAssignedTo = -1;
		ServiceStatusOpen = true;
		ServiceCloseDate = null;
		ServiceFeedbackPositive = false;
		ServiceFeedbackString = null;
		
		ServiceFeedbackGiven = false;
		ServiceEmployeeString = null;
		
	}
	
	void ShowServiceDetails()
	{
		System.out.println("\nService Request");
		System.out.println("---------------");
		System.out.println("Id " + ServiceId);
		System.out.println("ServiceType " + ServiceType + 
				" ("+FMS_IIITD.departmentList.GetProfessionName(ServiceType)+" / "+
				FMS_IIITD.departmentList.GetDepartmentName(ServiceType)+" )");
		System.out.println("Location " + ServiceLocation);
		System.out.println("Description " + ServiceDescription);
		System.out.println("RequestFrom " + ServiceRequestFrom + " (" +FMS_IIITD.listUsers.GetUserName(ServiceRequestFrom)+")");
		System.out.println("AssignedTo " + ServiceAssignedTo + " (" +FMS_IIITD.listUsers.GetUserName(ServiceAssignedTo)+")");
		System.out.println("StatusOpen " + ServiceStatusOpen);
		System.out.println("CreationDate " + ServiceCreationDate);
		System.out.println("CloseDate " + ServiceCloseDate);
		System.out.println("Employee Feedback " + ServiceEmployeeString);
		System.out.println("FeedbackGiven " + ServiceFeedbackGiven);
		System.out.println("FeedbackPositive " + ServiceFeedbackPositive);
		System.out.println("FeedbackString " + ServiceFeedbackString);

	}
	
	long GetCompletionTimeHours()
	{
		if (ServiceStatusOpen)
			return 0;
		else
			return ChronoUnit.HOURS.between(ServiceCreationDate , ServiceCloseDate);
	}

	public boolean GetNegetiveFeedback() {
		return !ServiceFeedbackPositive;
	}
	

}
