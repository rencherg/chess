package dataAccess;

import java.sql.SQLException;

public class TestDAO {
    public TestDAO(){
    }

    public static boolean clearDB() throws SQLException {

        DatabaseManager.clearTable("auth_data");
        DatabaseManager.clearTable("game_data");
        DatabaseManager.clearTable("user_data");
        return true;
    }
}
