package service;

import dataAccess.TestDAO;

//FOR TESTING ONLY
public class DeleteService {

    private TestDAO testDAO;

    public DeleteService() {
        testDAO = new TestDAO();
    }

    public boolean clear(){
        return this.testDAO.clearDB();
    }
}
