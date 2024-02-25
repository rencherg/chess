package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.HashSet;
import java.util.Set;

public class TempDB {
    public static Set<UserData> userSet = new HashSet();
    public static Set<AuthData> authSet = new HashSet();
    public static Set<GameData> gameSet = new HashSet();
}
