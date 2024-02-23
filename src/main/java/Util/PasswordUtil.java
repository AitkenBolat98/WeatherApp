package Util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String hashPassword(String password){
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }
    public static boolean checkPassword(String enteredPassword,String hashPassword){
        return BCrypt.checkpw(enteredPassword,hashPassword);
    }
}
