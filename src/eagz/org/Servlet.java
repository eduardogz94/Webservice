package eagz.org;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.json.JSONObject;

import eagz.org.utilities.ServiceInterface;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Servlet() {
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	URL url = new URL("http://localhost:8085/test?wsdl");
		QName qname = new QName("http://utilities.org.eagz/", "ServiceService");
		Service service = Service.create(url, qname);
		ServiceInterface server = service.getPort(ServiceInterface.class);
		
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
	
		String email = request.getParameter("email"); 
		String pass = request.getParameter("password");
		if (server.check()) {
		switch(server.user(email,pass)) {
			case 0:
					json.put("data", "Wrong data");
					json.put("Service",server.show());
				break;
			case 1:
					json.put("type", "admin");
					json.put("Service",server.show());
				break;
			case 2:
					json.put("type", "user");
					json.put("Service",server.show());
				break;
			}
		}
		out.print(json.toString());
		System.out.println(json);
	}
    
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		URL url = new URL("http://localhost:8085/test?wsdl");
		QName qname = new QName("http://utilities.org.eagz/", "ServiceService");
		Service service = Service.create(url, qname);
		ServiceInterface server = service.getPort(ServiceInterface.class);
		
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		JSONObject reqBody = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
	
		String email = reqBody.getString("email"); 
		String pass = reqBody.getString("password");
		
		switch(server.add(email,pass)) {
			case 0:
				if (server.check()) {
					json.put("status", "401").put("data", "Wrong data");
					json.put("Service",server.show());
			    }
				break;
			case 1:
				if (server.check()) {
					json.put("status", "200").put("type", "admin");
					json.put("Service",server.show());
			    }
				break;
			case 2:
				if (server.check()) {
					json.put("status", "200").put("type", "user");
					json.put("Service",server.show());
			    }
				break;
			}
		out.print(json.toString());
	}
}