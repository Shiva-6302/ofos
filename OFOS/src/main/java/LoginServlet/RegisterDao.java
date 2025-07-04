package LoginServlet;

import java.io.*;
import java.sql.*;
import javax.servlet.http.HttpServletResponse;
import LoginServlet.DbConnection;
public class RegisterDao {

    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String username = "jdbc"; 
    private String password = "apple";  
    private String driver = "oracle.jdbc.driver.OracleDriver";

    public void loadDriver(String driver) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, username, password);
            con.setAutoCommit(true); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public String insert(Member member) throws IOException {
        loadDriver(driver);
        Connection con = getConnection();
        String result = "Data Entered Successfully";

        
        System.out.println("Registering user: " + member.getEmail());
        System.out.println("Password: " + member.getPassword());
        System.out.println("Name: " + member.getName());
        System.out.println("Phone: " + member.getPhone());
        System.out.println("Address: " + member.getAddress());

        try {
            
            String userInsertSql = "INSERT INTO users (email, password, type) VALUES (?, ?, 'customer')";
            PreparedStatement pstmt = con.prepareStatement(userInsertSql);
            pstmt.setString(1, member.getEmail());
            pstmt.setString(2, member.getPassword());
            pstmt.executeUpdate();

            
            String selectUIDSql = "SELECT user_id FROM users WHERE email = ?";
            PreparedStatement pstmt1 = con.prepareStatement(selectUIDSql);
            pstmt1.setString(1, member.getEmail());
            ResultSet resultSet = pstmt1.executeQuery();

            int user_id = -1;
            if (resultSet.next()) {
                user_id = resultSet.getInt("user_id");
                System.out.println("Retrieved user_id: " + user_id);
            }

            
            if (user_id != -1) {
                String customerInsertSql = "INSERT INTO customer (name, email, phone_number,address, user_id) VALUES (?, ?, ?, ?,?)";
                PreparedStatement customerStmt = con.prepareStatement(customerInsertSql);
                customerStmt.setString(1, member.getName());
                customerStmt.setString(2, member.getEmail());
                customerStmt.setString(3, member.getPhone());
               
                customerStmt.setString(4, member.getAddress());
                customerStmt.setInt(5, user_id);
                customerStmt.executeUpdate();

               
            } else {
                result = "Error retrieving user_id after insert.";
                System.out.println(result);
            }

        } catch (Exception e) {
            result = "Data not entered successfully";
            e.printStackTrace();
            System.out.println("Register Error: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
