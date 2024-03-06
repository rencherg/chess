package passoffTests.DAOTests;

import chess.ChessGame;
import dataAccess.*;
import org.junit.jupiter.api.*;
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
    SQLUserDAO sqlUserDAO = new SQLUserDAO();



    @BeforeAll
    public static void init() throws SQLException, DataAccessException {
    }

    //Old memory DAO test
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

    //Old memory DAO test
    @Test
    @Order(2)
    public void authDAOTest() throws SQLException {

        AuthData myAuthData = this.memoryAuthDAO.createAuth("rencherg");

        Assertions.assertEquals(this.memoryAuthDAO.getAuth(myAuthData.getAuthToken()), myAuthData);
        Assertions.assertNull(this.memoryAuthDAO.getAuth("sample"));

        boolean deleteBoolean = this.memoryAuthDAO.deleteAuth(myAuthData.getAuthToken());

        Assertions.assertTrue(deleteBoolean);

        Assertions.assertNull(this.memoryAuthDAO.getAuth(myAuthData.getAuthToken()));

    }

    //Old memory DAO test
    @Test
    public void getUserSQL() throws SQLException, DataAccessException {
        SQLUserDAO sqlUserDAO = new SQLUserDAO();

        Assertions.assertNotNull(sqlUserDAO.getUser("username"));
        Assertions.assertNull(sqlUserDAO.checkUserData("badusername", "badpassword"));

        Assertions.assertNotNull(sqlUserDAO.getUser("username"));
        Assertions.assertNotNull(sqlUserDAO.checkUserData("username", "password"));

    }

    @Test
    public void getCount() throws SQLException, DataAccessException {
        SQLGameDAO sqlGameDAO = new SQLGameDAO();

        //Need to change
        Assertions.assertTrue(DatabaseManager.rowCount("game_data") == 0);

    }

    @Test
    public void getUserSQLPositive() throws SQLException {
        sqlUserDAO.createUser(new UserData("don", "trustno1", "don@fbi.gov"));
        sqlUserDAO.createUser(new UserData("carlos", "trustno1", "carlos@fbi.gov"));
        sqlUserDAO.createUser(new UserData("smith", "trustno1", "smith@fbi.gov"));

        Assertions.assertEquals("carlos@fbi.gov", sqlUserDAO.getUser("carlos").getEmail());

    }

    @Test
    public void getUserSQLNegative() throws SQLException {
        sqlUserDAO.createUser(new UserData("don", "trustno1", "don@fbi.gov"));
        sqlUserDAO.createUser(new UserData("carlos", "trustno1", "carlos@fbi.gov"));
        sqlUserDAO.createUser(new UserData("smith", "trustno1", "smith@fbi.gov"));

        TestDAO.clearDB();

        Assertions.assertNull(sqlUserDAO.getUser("carlos"));

    }

    @Test
    public void checkUserDataSQLPositive() throws SQLException {
        sqlUserDAO.createUser(new UserData("don", "trustno1", "don@fbi.gov"));
        sqlUserDAO.createUser(new UserData("carlos", "trustno1", "carlos@fbi.gov"));
        sqlUserDAO.createUser(new UserData("smith", "trustno1", "smith@fbi.gov"));

        Assertions.assertEquals("carlos@fbi.gov", sqlUserDAO.checkUserData("carlos", "trustno1").getEmail());

    }

    @Test
    public void checkUserDataSQLNegative() throws SQLException {
        sqlUserDAO.createUser(new UserData("don", "trustno1", "don@fbi.gov"));
        sqlUserDAO.createUser(new UserData("carlos", "trustno1", "carlos@fbi.gov"));
        sqlUserDAO.createUser(new UserData("smith", "trustno1", "smith@fbi.gov"));

        TestDAO.clearDB();

        Assertions.assertNull(sqlUserDAO.checkUserData("carlos", "trustno1"));

    }

    //Verifies that a new user was successfully created.
    @Test
    public void createUserSQLPositive() throws SQLException {
        sqlUserDAO.createUser(new UserData("john paul jones", "trustno1", "fmulder@fbi.gov"));

        Assertions.assertEquals("fmulder@fbi.gov", sqlUserDAO.getUser("john paul jones").getEmail());

    }

    @Test
    //Fix this
    public void createUserSQLNegative() throws SQLException {
        sqlUserDAO.createUser(new UserData(null, "", ""));

//        Assertions.assertEquals("b", sqlUserDAO.getUser("").getEmail());
    }

    @Test
    public void clearDB() throws SQLException, DataAccessException {

        Assertions.assertTrue(TestDAO.clearDB());
    }

    @Test
//    @AfterEach
    public void clearDBAfterEach() throws SQLException, DataAccessException {

        Assertions.assertTrue(TestDAO.clearDB());
    }
}

//Todo:
//1. Fix up resultset
//2. Clear functions
//3. Write unit tests
//4. 1 Unit test for chess board moves
//5. Password Encryption
//5. Connect the codebase to the db code
//6. Provided Unit tests
//7. Clean up code
