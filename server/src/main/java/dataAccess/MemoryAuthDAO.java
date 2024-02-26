package dataAccess;

import model.AuthData;

import java.util.Iterator;

import java.security.SecureRandom;

public class MemoryAuthDAO implements AuthDAO{

    public MemoryAuthDAO() {

    }

    //Generates a unique token
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

            if(iteratorData.getAuthToken().equals(authToken)){
                return iteratorData;
            }
        }
        return null;

    }
}
