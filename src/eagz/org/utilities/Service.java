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
			System.out.println("array: " + arr+ " " + email + " " + pass);
			int value = user(email,pass);	
		return value;
		}
		
		public String show() {
			return "data: " + arr.get(0);
		}
		
		public int user(String email, String pass) {
			int value;
			if(db.checkUser(email,pass) == true) {
				if(db.checkAdmin(email)== true) {
					value = 1;
				}else {
					value = 2;
				}	
			}else {
				value = 0;
			}
			return value;
		}
		
		public boolean check() {
			if (arr.size() == 0) {
				return false;
			} else {
				return true;
			}	
		}
}
