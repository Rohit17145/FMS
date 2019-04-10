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
public class ListOfUsers {
	ArrayList<User> listOfUsers = new ArrayList<User>();

	void SaveAllUsers() {
		try {
			FileOutputStream fos = new FileOutputStream("SaveFileUserList.sav");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(listOfUsers);
			oos.close();
			fos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	Boolean LoadAllUsers() {
		try {
			FileInputStream fis = new FileInputStream("SaveFileUserList.sav");
			ObjectInputStream ois = new ObjectInputStream(fis);
			listOfUsers = (ArrayList<User>) ois.readObject();
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

	void PrintAllUsers() {
		for (int i = 0; i < listOfUsers.size(); i++) {
			listOfUsers.get(i).ShowUser();
		}

	}
	
	String GetUserName(int id) {
		for (int i = 0; i < listOfUsers.size(); i++) {
			if (listOfUsers.get(i).Id == id)
				return listOfUsers.get(i).Name;
		}
		return null;
	}
	
	void AddUser(User usr)
	{
		listOfUsers.add(usr);
	}
	
	User Authenticate(String u, String p)
	{
		for (int i = 0; i < listOfUsers.size(); i++) {
			if (listOfUsers.get(i).UserName.equals(u) && listOfUsers.get(i).Password.equals(p))
			{
				//Found, authenticated
				return listOfUsers.get(i);
			}
		}
		
		return null;
	}
	
	
	Boolean IsUserOK(String u)
	{
		//Check if space
		if (u.contains(" "))
			return false;
		
		//Check for uniqueness
		for (int i = 0; i < listOfUsers.size(); i++) {
			if (listOfUsers.get(i).UserName.equals(u))
			{
				//Found not unique
				return false;
			}
		}
		
		return true;
	}
	
	
	//d == -1, return all employees
	ArrayList<Integer> ListOfUsersInDepartment(int d)
	{
		ArrayList<Integer> al = new ArrayList<Integer>();
		for (int i = 0; i < listOfUsers.size(); i++) {
			if (listOfUsers.get(i) instanceof Employee)
			{
				Employee ee = (Employee) listOfUsers.get(i);
				if (d == -1)
					al.add(ee.Id);
				else if (ee.Department == d)
					al.add(ee.Id);
			}
		}
		
		return al;
	}
}
