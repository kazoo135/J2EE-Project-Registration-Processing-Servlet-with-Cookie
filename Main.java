

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {

	private String username;
	private String password;
	//private String register;
	private String login; 
	PrintWriter out = null; 
	private static final long serialVersionUID = 1L;

	public Main() {
		super();

	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	username = request.getParameter("username");
    	password = request.getParameter("password");
    	//register = request.getParameter("register");
    	login = request.getParameter("login");
    	if(check(username, password, response)){
    		if(login != null)
    			processLogin(request, response);
    		else
    			processRegister(request, response);
    	}
    }
//B
	//three cases that you return false:
	private boolean check(String username, String password,
			HttpServletResponse response) throws IOException{
		// username and password are null.
		if( (username == null) && (password == null)){
			System.out.println("USR=NULL and PWRD=NULL");
			userAndPassNull(response);
			return false;
			// username is not null but it is an empty string.
		}else if ((username != null)&& (username.equals(""))){
			System.out.println("username is not null but it is an empty string.");
			userPassIsEmpty(response);
			return false;
			// password is not null but it is an empty string.
		}else if((password != null)&& (password.equals(""))){
			System.out.println("password is not null but it is an empty string.");
			userPassIsEmpty(response);
			return false;
		}else{
			//You've passed the check!;
			return true; 
		}

	}// end of check()
//C
  private void processRegister(HttpServletRequest request, HttpServletResponse response)
		  throws IOException{
	  boolean found = false; 
	  Cookie[] cookies = request.getCookies();
	if(cookies != null && cookies.length > 0 ){
		for (int i = 0; i < cookies.length; i++) {
			Cookie c = cookies[i];
			if(c.getName().equals(username )){
				found = true; 
			}

		}//end for loop
		
		if(found){
			// Tell user you have already registered.
			System.out.println(" Your've Already registered");
			registered(response);
		}else{
			// user needs to register
			System.out.println("No matching cookies, Please Register.");
			notYourCookie(response);
			makeCookie(response);
			System.out.println("Cookie has been baked");
		}
	}else{
		//there is no cookie. Make one.
		System.out.println("No cookies found.");
		makeCookie(response);
		System.out.println("Cookie has been baked");
		noCookiesFound(response);
	}
	
}//end processRegister
//D
  private void makeCookie(HttpServletResponse response){
	    // Make a cookie with its username and value to be the user username and
	    // password respectively.
	  Cookie aCookie = new Cookie(username, password);
	  aCookie.setMaxAge(60*60*24*14);// set for 14 days
	  response.addCookie(aCookie);
	  }
//E
  private void processLogin(HttpServletRequest request, HttpServletResponse response) 
		  throws IOException{
	  boolean found = false; 
	  Cookie[] cookies = request.getCookies(); 
	  if(cookies != null && cookies.length > 0 ){
		  for (int i = 0; i < cookies.length; i++) {
			  Cookie c = cookies[i]; 
			  if (c.getName().equals(username)) {
				found = true; 
			  }
		  }//end for loop
	  }else{
		  //No cookie at all. Ask the use to register
		  System.out.println("No Cookies at all - Please tegister");
		  noCookies(response);
	  }
	  
	  if(found){
		  System.out.println("Welcome page");
		  welcomePage(response);
	  }else{
		  System.out.println("Found cookies but not yours. "
		  		+ "Please Register");
		  notYourCookieRegister(response);
	  }
	  
  }// end processLogin
  
  // HTML Output methods 
  
  private void noCookiesFound(HttpServletResponse response) 
		  throws IOException{
	  response.setContentType("text/html");
	  out = response.getWriter();  
	  out.println("<html><head>");
	  out.println("<style>h1,h2,h3{ text-align:center;}</style>");
	  out.println("<title>Cookie Test</title></head>");
	  out.println("<body>");
	  out.println("<h2>No Cookies found. We've baked one for you</h2>");
	  out.println("<h3>Try loging in <a href='http://localhost:8080/Project/Cookie.html' >Back to Resister/Login</a></h3>");
	  out.println("</body></html>");
  }
  
  private void registered(HttpServletResponse response)throws IOException{
	  response.setContentType("text/html");
	  out = response.getWriter();
	  out.println("<html><head>");
	  out.println("<style>h1,h2,h3{ text-align:center;}</style>");
	  out.println("<title>Cookie Test</title></head>");
	  out.println("<body>");
	  out.println("<h2>You've already registered. Please login</h2>");
	  out.println("<h3><a href='http://localhost:8080/Project/Cookie.html' >Back to Resister/Login</a></h3>");
	  out.println("</body></html>");
  }
  
  private void noCookies(HttpServletResponse response)throws IOException{
	  response.setContentType("text/html");
	  out = response.getWriter();
	  out.println("<html><head>");
	  out.println("<style>h1,h2,h3{ text-align:center;}</style>");
	  out.println("<title>Cookies Test</title></head>");
	  out.println("<body>");
	  out.println("<h2>No cookies. Please take a moment to register</h2>");
	  out.println("<h3><a href='http://localhost:8080/Project/Cookie.html'>Back to Registration/Login</a></h3>");
	  out.println("</body></html>");
  }
  private void notYourCookie(HttpServletResponse response)throws IOException{
	  response.setContentType("text/html");
	  out = response.getWriter();
	  out.println("<html><head>");
	  out.println("<style>h1,h2,h3{ text-align:center;}</style>");
	  out.println("<title>Cookies Test</title></head>");
	  out.println("<body>");
	  out.println("<h2>Found a cookie but not yours. We've backed so, please take a moment to login</h2>");
	  out.println("<h3><a href='http://localhost:8080/Project/Cookie.html'>Back to Registration/Login</a></h3>");
	  out.println("</body></html>");
  }
  
  private void notYourCookieRegister(HttpServletResponse response)throws IOException{
	  response.setContentType("text/html");
	  out = response.getWriter();
	  out.println("<html><head>");
	  out.println("<style>h1,h2,h3{ text-align:center;}</style>");
	  out.println("<title>Cookies Test</title></head>");
	  out.println("<body>");
	  out.println("<h2>Found a cookie but not yours. Please take a moment to Register</h2>");
	  out.println("<h3><a href='http://localhost:8080/Project/Cookie.html'>Back to Registration/Login</a></h3>");
	  out.println("</body></html>");
  }
  private void welcomePage(HttpServletResponse response)throws IOException{
	  response.setContentType("text/html");
	  out = response.getWriter();
	  out.println("<html><head>");
	  out.println("<style>h1,h2,h3{ text-align:center;}</style>");
	  out.println("<title>Cookies Test</title></head>");
	  out.println("<body>");
	  out.println("<h1>Welcome to the Cookie Monster Site</h1>");
	  out.println("</body></html>");
  }
  
  private void userAndPassNull(HttpServletResponse response)throws IOException{
	  response.setContentType("text/html");
	  out = response.getWriter();
	  out.println("<html><head>");
	  out.println("<style>h1,h2,h3{ text-align:center;}</style>");
	  out.println("<title>Cookies Test</title></head>");
	  out.println("<body>");
	  out.println("<h1>User and Password are required fields</h1>");
	  out.println("<h3><a href='http://localhost:8080/Project/Cookie.html'>" 
	  + "Back to Registration</a></h3>");
	  out.println("</body></html>");
  }
  
  private void userPassIsEmpty(HttpServletResponse response)throws IOException{
	  response.setContentType("text/html");
	  out = response.getWriter();
	  out.println("<html><head>");
	  out.println("<style>h1,h2,h3{ text-align:center;}</style>");
	  out.println("<title>Cookies Test</title></head>");
	  out.println("<body>");
	  out.println("<h1>User or Password is empty and are required fields."
			  + "Try again</h1>");
	  out.println("<h3><a href='http://localhost:8080/Project/Cookie.html'>" 
	  + "Back to Registration/Login</a></h3>");
	  out.println("</body></html>");
  }

}//End of Main
