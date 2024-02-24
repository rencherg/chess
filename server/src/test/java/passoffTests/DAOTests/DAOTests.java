package passoffTests.DAOTests;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import model.AuthData;
import model.UserData;

public class DAOTests {

    private MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    private MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    private MemoryGameDAO memoryGameDAO = new MemoryGameDAO();

    @BeforeAll
    public static void init() {

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
        UserData myUser1 = new UserData("rencherg", "password", "rencher.grant@gmail.com");
        UserData myUser2 = new UserData("fmulder", "TrustNo1", "f.mulder@gmail.com");

        AuthData myAuthData = this.memoryAuthDAO.createAuth("rencherg");

        Assertions.assertEquals(this.memoryAuthDAO.getAuth(myAuthData.getAuthToken()), myAuthData);
        Assertions.assertNull(this.memoryAuthDAO.getAuth("sample"));

        boolean deleteBoolean = this.memoryAuthDAO.deleteAuth(myAuthData.getAuthToken());

        Assertions.assertTrue(deleteBoolean);

        Assertions.assertNull(this.memoryAuthDAO.getAuth(myAuthData.getAuthToken()));

    }
}
