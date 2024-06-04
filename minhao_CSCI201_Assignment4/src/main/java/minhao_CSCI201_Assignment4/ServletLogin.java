package minhao_CSCI201_Assignment4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        JDBCConnector connector = new JDBCConnector();
        boolean isAuthenticated;
		isAuthenticated = connector.authenticateUser(username, password);
		if (isAuthenticated) {
			 // “How could I start a session?” prompt (4 lines). ChatGPT, 25 Nov. version, OpenAI, 25 Nov. 2023, chat.openai.com/chat
		    // Start a new session
		    HttpSession session = request.getSession();
		    session.setAttribute("username", username);
		    session.setAttribute("login", true);

		    response.setStatus(HttpServletResponse.SC_OK);
		    response.getWriter().write("{\"status\": \"success\", \"message\": \"Authentication successful.\"}");
		} else {
		    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
		    response.getWriter().write("{\"status\": \"error\", "
		    		+ "\"message\": \"Authentication failed. Invalid username or password.\"}");
		}
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        
    }
}

