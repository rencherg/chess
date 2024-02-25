package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import java.security.SecureRandom;

public class MemoryAuthDAO implements AuthDAO{

//    Set<AuthData> authSet = new HashSet();

    public MemoryAuthDAO() {

    }

    private String getUniqueToken(){

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        String token = "";

        boolean foundValidToken = false;

        while(foundValidToken == false){
            random.nextBytes(bytes);
            token = bytes.toString();
            if(this.getAuth(token) == null){
                foundValidToken = true;
            }
        }

        return token;
    }

    public AuthData createAuth(String username){

        AuthData authData = new AuthData(this.getUniqueToken(), username);
        TempDB.authSet.add(authData);
        return authData;
    }

    public boolean deleteAuth(String authToken){

        Iterator<AuthData> dataIterator = TempDB.authSet.iterator();

        AuthData iteratorData;

        while (dataIterator.hasNext()) {

            iteratorData = dataIterator.next();

            if(iteratorData.getAuthToken().equals(authToken)){
                TempDB.authSet.remove(iteratorData);
                return true;
            }
        }
        return false;

    }

    public AuthData getAuth(String authToken){

        Iterator<AuthData> dataIterator = TempDB.authSet.iterator();

        AuthData iteratorData;

        while (dataIterator.hasNext()) {

            iteratorData = dataIterator.next();

            if(iteratorData.getAuthToken() == authToken){
                return iteratorData;
            }
        }
        return null;

    }

    public AuthData getAuthUsername(String username){

        Iterator<AuthData> dataIterator = TempDB.authSet.iterator();

        AuthData iteratorData;

        while (dataIterator.hasNext()) {

            iteratorData = dataIterator.next();

            if(iteratorData.getUsername() == username){
                return iteratorData;
            }
        }
        return null;

    }

}
