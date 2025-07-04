package LoginServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CartDao {
    private Connection conn;

    public CartDao(Connection conn) {
        this.conn = conn;
    }

    public int getCartItemCount(int userId) {
        int count = 0;
        try {
            String sql = "SELECT SUM(quantity) FROM cart WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
