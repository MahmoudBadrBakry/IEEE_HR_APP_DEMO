
package ieee.hr.app;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
public class SQLDB {
    private static SQLDB ourInstance = new SQLDB();
    private Connection connection;
    
    public static SQLDB getInstance() {
        return ourInstance;
    }
    
    private SQLDB() {
        try {
            String url = "jdbc:sqlite:IEEEDB.db";
            connection = DriverManager.getConnection(url);
//            System.out.println("Connection to SQLiteDB has been established.");

        } catch (SQLException e) {
//            System.out.println(e.getMessage());
        }
    }
    
    public static ObservableList<Member> selectAllMembers() {

        ObservableList<Member> members = FXCollections.observableArrayList();

        String sql = "SELECT id, name, acadimicYear , mail , phone "
                + ", committee , info FROM members";

        try {
            Connection conn = getInstance().getConnection();
            Statement stmt  = conn.createStatement();
            ResultSet resultSet    = stmt.executeQuery(sql);

            while (resultSet.next()) {
                members.add(
                        new Member(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("acadimicYear"),
                                resultSet.getString("mail"),
                                resultSet.getString("phone"),
                                resultSet.getString("committee"),
                                resultSet.getString("info")
                        )
                );
            }
        } catch (SQLException e) {
//            System.out.println(e.getMessage());
        }

        return members;

    }
    public static void insertMember(Member member) {

        String sql = "INSERT INTO members(id, name, acadimicYear , mail , phone "
                + ", committee , info) VALUES(?,?,?,?,?,?,?)";

        try {
            Connection conn = getInstance().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, member.getId());
            preparedStatement.setString(2, member.getName());
            preparedStatement.setInt(3, member.getAcadimicYear());
            preparedStatement.setString(4, member.getMail());
            preparedStatement.setString(5, member.getPhone());
            preparedStatement.setString(6, member.getCommittee());
            preparedStatement.setString(7, member.getInfo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
//            System.out.println(e.getMessage());
        }

    }
    
    public static void modifyMember(Member member) {
        String sql = "UPDATE members SET name = ?, acadimicYear = ?,  mail = ?"
                + ",phone = ? , committee = ? , info = ? WHERE id = ?";

        try {
            Connection conn = getInstance().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            // set the corresponding param
            preparedStatement.setString(1, member.getName());
            preparedStatement.setInt(2, member.getAcadimicYear());
            preparedStatement.setString(3, member.getMail());
            preparedStatement.setString(4, member.getPhone());
            preparedStatement.setString(5, member.getCommittee());
            preparedStatement.setString(6, member.getInfo());
            preparedStatement.setInt(7, member.getId());
            // execute the delete statement
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
//            System.out.println(e.getMessage());
        }
    }

    
    public static void deleteMember(int memberId) {
        String sql = "DELETE FROM members WHERE id = ?";

        try {
            Connection conn = getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setInt(1, memberId);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
//            System.out.println(e.getMessage());
        }
    }
    
      public Connection getConnection() {
        return connection;
    }

    
}
