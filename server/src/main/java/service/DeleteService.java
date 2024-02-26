package service;

import dataAccess.TestDAO;

//FOR TESTING ONLY
public class DeleteService {

    private final TestDAO testDAO = new TestDAO();

    public boolean clear(){
        return this.testDAO.clearDB();
    }
}
