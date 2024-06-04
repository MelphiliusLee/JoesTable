package minhao_CSCI201_Assignment4;

import java.sql.*;

public class JDBCConnector {

    private Connection connect() throws SQLException {
        // Replace with your database credentials
        String url = "jdbc:mysql://localhost/CSCI201_Lab9?user=CSCI201MySQL&password=mike'smysql";
        String user = "root";
        String password = "mike'smysql";
        
        return DriverManager.getConnection("jdbc:mysql://localhost/JoesTableDB?user=root&password=mike'smysql");
    }

    public boolean insertNewUser(String username, String password, String email) {
        try (Connection conn = connect()) {
            String sql = "INSERT INTO Users (username, password, email) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, email);
                statement.setBoolean(4, true);
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
 // “How could I use jdbc to insert?” prompt (4 lines). ChatGPT, 25 Nov. version, OpenAI, 25 Nov. 2023, chat.openai.com/chat
    public boolean insertRestaurant(String name, String address, String phone, String cuisineTitle, String price, double rating, String imageUrl, String yelpUrl) {
        try (Connection conn = connect()) {
            String sql = "INSERT INTO Restaurants (name, address, phone, cuisine_title, price, rating, image_url, yelp_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, address);
                statement.setString(3, phone);
                statement.setString(4, cuisineTitle);
                statement.setString(5, price);
                statement.setDouble(6, rating);
                statement.setString(7, imageUrl);
                statement.setString(8, yelpUrl);
                
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean addFavorite(int userId, int restaurantId) {
        try (Connection conn = connect()) {
            String sql = "INSERT INTO Favorites (user_id, restaurant_id) VALUES (?, ?)";
            
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, userId);
                statement.setInt(2, restaurantId);
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFavorite(int userId, int restaurantId) {
        try (Connection conn = connect()) {
            String sql = "DELETE FROM Favorites WHERE user_id = ? AND restaurant_id = ?";
            
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, userId);
                statement.setInt(2, restaurantId);
                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertReservation(int userId, int restaurantId, Date date, Time time) {
        try (Connection conn = connect()) {
            String sql = "INSERT INTO Reservations (user_id, restaurant_id, date, time) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, userId);
                statement.setInt(2, restaurantId);
                statement.setDate(3, date);
                statement.setTime(4, time);
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean authenticateUser(String username, String password) {
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                
                if (resultSet.next()) {
                    return updateLoginStatus(username, true, conn); 
                }
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean updateLoginStatus(String username, boolean loginStatus, Connection conn) {
        String updateSql = "UPDATE Users SET login = ? WHERE username = ?";
        try (PreparedStatement updateStatement = conn.prepareStatement(updateSql)) {
            updateStatement.setBoolean(1, loginStatus);
            updateStatement.setString(2, username);
            int rowsUpdated = updateStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

