package eagz.org.utilities;

import java.util.ArrayList;

import javax.jws.WebService;

import eagz.org.Database;

@WebService(endpointInterface = "eagz.org.utilities.ServiceInterface")
public class Service implements ServiceInterface {
		public Database db = new Database();
		public ArrayList<String> arr = new ArrayList<String>();	
		
		public int add(String email, String pass) {
			arr.add(email);
			int value;
			System.out.println("array: " + arr+ " " + email + " " + pass);
			
			if(db.checkUser(email,pass) == true) {
				int id = db.userId(email);
				
				if(db.checkAdmin(email)== true) {
					System.out.println("Admin-> " + email + " Id: " + id);
					value = 1;
				}else {
					System.out.println("User-> " + email + " Id: " + id);
					System.out.println("Is User");
					value = 2;
				}	
			}else {
				System.out.println("Wrong data --");
				value = 0;
			}
		return value;
		}
		
		public String show() {
			return "data: " + arr.remove(0) + "\n\n";
		}
		
		public boolean check() {
			if (arr.size() == 0) {
				return false;
			} else {
				return true;
			}	
		}
}
