import java.io.BufferedReader;
import java.io.Serializable;

/**
 * 
 */

/**
 * @author 
 *
 */
public abstract class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3488060211748726227L;
	

	public int Id;
	
	public String Name;
	public int Phone;
	
	public String UserName;
	public String Password;
	
	protected User(int id, String name, int phone, String un, String pass)
	{
		Id = id;
		Name = name;
		Phone = phone;
		UserName = un;
		Password = pass;
	}
	
	public void ShowUser() {
		System.out.println("Id " + Id);
		System.out.println("Name " + Name);
		System.out.println("Phone " + Phone);
		System.out.println("UserName " + UserName);
		System.out.println("Password " + Password);
	}

	public User RegisterNewUser(BufferedReader br, ListOfUsers list, int id) {
		System.out.println("You do not have permission to create new user.");
		return null;
	}
	
	public void ListUsers(BufferedReader br, ListOfUsers list) {
		System.out.println("You do not have permission to see list of user.");
	}
	
	public Boolean ListSRs(BufferedReader br, ListOfServiceRequests list) {
		System.out.println("You do not have permission to see list of Service Requests.");
		return false;
	}

	public abstract void CloseSR(BufferedReader br, ListOfServiceRequests listServiceRequests);


}
