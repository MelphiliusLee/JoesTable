package minhao_CSCI201_Assignment4;

import com.google.gson.Gson;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ServletSignUp")
public class ServletSignUp extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");  

        JDBCConnector connector = new JDBCConnector();
        boolean success;
		success = connector.insertNewUser(username, password, email);
		 if (success) {
		    	String json = "{"
		                + "\"status\":\"" + escapeJson("success") + "\","
		                + "\"message\":\"" + escapeJson("User registered successfully.") + "\""
		                + "}";
		    	System.out.println("success");
			    HttpSession session = request.getSession();
		        session.setAttribute("login", true);
		        response.getWriter().write(json);
		    } else {
		    	String json = "{"
		                + "\"status\":\"" + escapeJson("error") + "\","
		                + "\"message\":\"" + escapeJson("User registration failed. Please try again.") + "\""
		                + "}";
		        response.getWriter().write(json);
		    }
        System.out.println("parsed");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

       
    }
    static class ResponseStatus {
        String status;
        String message;

        ResponseStatus(String status, String message) {
            this.status = status;
            this.message = message;
        }
    }
 // “How could I parse json manually?” prompt (4 lines). ChatGPT, 25 Nov. version, OpenAI, 25 Nov. 2023, chat.openai.com/chat
    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}

