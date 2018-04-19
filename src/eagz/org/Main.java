package eagz.org;


import javax.servlet.ServletException;
import javax.xml.ws.Endpoint;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import eagz.org.utilities.Service;

public class Main {
	public static Service s = new Service();
	
	public static void main(String[] args) throws LifecycleException {
		try {
			Integer port = 8080;
			Tomcat tomcat = new Tomcat();
			Context ctxt = null;
			
			String web_app = "WebContent";
			String webService = "http://localhost:8085/test";
			tomcat.setPort(port);
			ctxt = tomcat.addWebapp("/", System.getProperty("user.dir") + "\\" +web_app);
			
			Tomcat.addServlet(ctxt, "Servlet", new Servlet());
			ctxt.addServletMappingDecoded("/WebService", "Servlet");
			
			Endpoint.publish(webService, s);
			
			tomcat.start();
			tomcat.getServer().await();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}