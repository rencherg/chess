package dataAccess;

import server.model.UserData;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MemoryUserDAO implements UserDAO {

    Set<UserData> userSet = new HashSet();

    public MemoryUserDAO(){

    }

    //Checks if a user exists with the given username
    public UserData getUser(String username){
        Iterator<UserData> dataIterator = userSet.iterator();

        UserData iteratorData;

        while (dataIterator.hasNext()) {

            iteratorData = dataIterator.next();

            if(iteratorData.getUsername() == username){
                return iteratorData;
            }
        }
        return null;
    }

    //Checks if username and password are correct
    public UserData checkUserData(String username, String password){
        Iterator<UserData> dataIterator = userSet.iterator();

        UserData iteratorData;

        while (dataIterator.hasNext()) {

            iteratorData = dataIterator.next();

            if((iteratorData.getUsername() == username) && (iteratorData.getPassword() == password)){
                return iteratorData;
            }
        }
        return null;
    }

    public UserData createUser(UserData user){

        this.userSet.add(user);

        return user;
    }

}
