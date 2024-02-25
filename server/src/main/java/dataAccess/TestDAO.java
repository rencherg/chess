package dataAccess;

public class TestDAO {
    public TestDAO(){
    }

    public boolean clearDB(){

        TempDB.gameSet.clear();
        TempDB.userSet.clear();
        TempDB.authSet.clear();
        return true;
    }
}
