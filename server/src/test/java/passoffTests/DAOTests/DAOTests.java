package passoffTests.DAOTests;

import dataAccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import model.AuthData;
import model.UserData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//For testing the DAO classes
public class DAOTests {

    private final MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    private final MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();

    @BeforeAll
    public static void init() throws SQLException, DataAccessException {
    }

    @Test
    @Order(1)
    public void userDAOTest(){
        UserData myUser1 = new UserData("rencherg", "password", "rencher.grant@gmail.com");
        UserData myUser2 = new UserData("fmulder", "TrustNo1", "f.mulder@gmail.com");

        this.memoryUserDAO.createUser(myUser1);
        this.memoryUserDAO.createUser(myUser2);

        Assertions.assertEquals(this.memoryUserDAO.getUser("rencherg"), myUser1);
        Assertions.assertEquals(this.memoryUserDAO.checkUserData("fmulder", "TrustNo1"), myUser2);
        Assertions.assertNotEquals(this.memoryUserDAO.getUser("fmulder"), myUser1);
        Assertions.assertNotEquals(this.memoryUserDAO.checkUserData("rencherg", "password"), myUser2);
    }

    @Test
    @Order(2)
    public void authDAOTest(){

        AuthData myAuthData = this.memoryAuthDAO.createAuth("rencherg");

        Assertions.assertEquals(this.memoryAuthDAO.getAuth(myAuthData.getAuthToken()), myAuthData);
        Assertions.assertNull(this.memoryAuthDAO.getAuth("sample"));

        boolean deleteBoolean = this.memoryAuthDAO.deleteAuth(myAuthData.getAuthToken());

        Assertions.assertTrue(deleteBoolean);

        Assertions.assertNull(this.memoryAuthDAO.getAuth(myAuthData.getAuthToken()));

    }

    @Test
    public void getUserSQL() throws SQLException, DataAccessException {
        SQLUserDAO sqlUserDAO = new SQLUserDAO();

        Assertions.assertNotNull(sqlUserDAO.getUser("username"));
        Assertions.assertNull(sqlUserDAO.checkUserData("badusername", "badpassword"));

        Assertions.assertNotNull(sqlUserDAO.getUser("username"));
        Assertions.assertNotNull(sqlUserDAO.checkUserData("username", "password"));

    }

    @Test
    public void createUserSQL() throws SQLException, DataAccessException {
        SQLUserDAO sqlUserDAO = new SQLUserDAO();

        sqlUserDAO.createUser(new UserData("fmulder", "trustno1", "fmulder@fbi.gov"));

    }

//    @Test
//    @Order(3)
//    public void SQLUserDAO() throws SQLException, DataAccessException {
//
//        SQLUserDAO sqlUserDAO = new SQLUserDAO();
//
//        sqlUserDAO().testFunction();
//
//        Assertions.assertNull(null);

//    }

//    private SQLUserDAO sqlUserDAO() {
//        return new SQLUserDAO();
//    }
}
