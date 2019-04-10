import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author
 *
 *	Electrician : Electrical dpt
	Housekeeping: cleaning dpt
	Horticulture: gardening dpt
	Carpenter : Repairing dpt
	Mason : building dpt
	(Others like mosquito repellent spray , smoke detector , fire alarm working or not ..... Miscellaneous dpt)

	Dpt * department
 *
 */
public final class Departments {

	public class Pair {

		public Integer id;
		public String department;
		public String profession;

		public Pair(int i, String string1, String string2) {
			id = i;
			department = string1;
			profession = string2;
		}

	}

	public ArrayList<Pair> list = new ArrayList<Pair>();



	Departments() {
		list.add(new Pair(1, "Electrical", "Electrician"));
		list.add(new Pair(2, "Cleaning", "Housekeeping"));
		list.add(new Pair(3, "Gardening", "Horticulture"));
		list.add(new Pair(4, "Repairing", "Carpenter"));
		list.add(new Pair(5, "Miscellaneous", "Others"));

	}
	
	String GetDepartmentName(int d)
	{
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).id == d)
			{
				//Found
				return list.get(i).department;
			}
		}
		
		return null;
	}
	
	String PrintAllDepartments()
	{
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).id + ". "+list.get(i).department+" ("+list.get(i).profession+")");
		}
		
		return null;
	}
	
	String GetProfessionName(int d)
	{
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).id == d)
			{
				//Found
				return list.get(i).profession;
			}
		}
		
		return null;
	}

}
