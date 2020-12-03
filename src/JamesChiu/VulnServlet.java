package JamesChiu;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/VulnLet")
public class VulnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getSession(true);
		Map<String, Cookie> Cookies = new Hashtable<String, Cookie>();
		
		if (request.getCookies() != null) {
			for (int i = 0; i < request.getCookies().length; i++) {
				Cookies.put(request.getCookies()[i].getName(), request.getCookies()[i]);
			}
		}
		
		if (!Cookies.containsKey("User")) { 
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			UserInfo user = new UserInfo(username, password);
			try (
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bos);			
			) {
				oos.writeObject(user);
				oos.flush();
				byte[] objectBytes = bos.toByteArray();
				String objectString = Base64.getEncoder().encodeToString(objectBytes);			
				Cookie objectCookie = new Cookie("User", objectString);
				response.addCookie(objectCookie);
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("<!DOCTYPE html");
				out.println("<html><head>");
				out.println("<meta http-equiv='Content-Type' content='text/html'; charset='UTF-8'>");
				out.println("<title>Status</title></head>");
				out.println("<body><h1>Hello " + username + "</h1>");
				out.println("<div><p>Your account has been successfully created<p></div>");
				out.println("<form method='get' action='http://192.168.86.46:8080/VulnServlet/VulnLet'>");
				out.println("<input type='submit' value='Continue'>");
				out.println("</form></body></html>");
			}
		} else {
			Cookie objectCookie = Cookies.get("User");
			byte[] objectBytes = Base64.getDecoder().decode(objectCookie.getValue());
			System.out.print(objectCookie.getMaxAge());
			try (
				ByteArrayInputStream bis = new ByteArrayInputStream(objectBytes);
				ObjectInputStream ois = new ObjectInputStream(bis);
			) {
				UserInfo user = (UserInfo) ois.readObject();
				String[] info = user.getAccountInfo();
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("<!DOCTYPE html");
				out.println("<html><head>");
				out.println("<meta http-equiv='Content-Type' content='text/html'; charset='UTF-8'>");
				out.println("<title>Account Info</title></head>");
				out.println("<body><h1>Hello " + info[0] + "</h1>");
				out.println("<div><p>Your account was created on " + info[1] + "</p></div>");
				out.println("</body></html>");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}

}
