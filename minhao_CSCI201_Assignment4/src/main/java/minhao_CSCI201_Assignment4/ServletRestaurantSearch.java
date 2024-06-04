package minhao_CSCI201_Assignment4;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet("/ServletRestaurantSearch")
public class ServletRestaurantSearch extends HttpServlet {

    private static final long serialVersionUID = 1L; // serializeUID
    private final String API_KEY = "65IWarb-3_ElumE4yXQULv7AMbBSqkjbf2DTHHRkdL6KmsLRX4jac8vLLWSVdO7NSmK6Sy7aderj7mJ4QJq31yddgbeWpmRB5t8enisBn92p7Sc-RrubKbtIyw9hZXYx"; 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String term = request.getParameter("term");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        String sort_by = request.getParameter("sort_by");
        
        String urlParameters = "latitude=" + latitude + "&longitude=" + longitude + "&sort_by=" + sort_by;
        if (term != null && !term.isEmpty()) {
            urlParameters += "&term=" + term;
        }

        URL yelpURL = new URL("https://api.yelp.com/v3/businesses/search?" + urlParameters);
        System.out.println(yelpURL);
        HttpURLConnection con = (HttpURLConnection) yelpURL.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + API_KEY);
        con.setDoOutput(true);
        con.connect();
        // “How could I execute yelp API request?” prompt (4 lines). ChatGPT, 25 Nov. version, OpenAI, 25 Nov. 2023, chat.openai.com/chat
        try {
            int responseCode = con.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Unexpected response code " + responseCode);
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content.toString());
            PrintWriter out = response.getWriter();
            out.print(content.toString());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the Yelp API request.");
        } finally {
            con.disconnect();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("login") != null;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"isLoggedIn\": " + isLoggedIn + "}");
        out.flush();
    }
}
