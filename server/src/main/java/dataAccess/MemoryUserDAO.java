package dataAccess;

import model.UserData;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MemoryUserDAO implements UserDAO {

//    Set<UserData> userSet = new HashSet();

    public MemoryUserDAO(){

    }

    //Checks if a user exists with the given username
    public UserData getUser(String username){
        Iterator<UserData> dataIterator = TempDB.userSet.iterator();

        UserData iteratorData;

        while (dataIterator.hasNext()) {

            iteratorData = dataIterator.next();

            if(iteratorData.getUsername().equals(username)){
                return iteratorData;
            }
        }
        return null;
    }

    //Checks if username and password are correct
    public UserData checkUserData(String username, String password){
        Iterator<UserData> dataIterator = TempDB.userSet.iterator();

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

        TempDB.userSet.add(user);

        Iterator<UserData> iterator = TempDB.userSet.iterator();

        System.out.println("All entries:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().getUsername());
        }
        System.out.println("end");

        return user;
    }

}
