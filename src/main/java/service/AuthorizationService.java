package service;

import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;

@Slf4j
public class AuthorizationService {
    private UserService userService = new UserService();

    public boolean isPasswordSame(String login, String password) {
        String pwdDB = userService.getUserPasswordByLogin(login);
        if (pwdDB != null) {
            if(BCrypt.checkpw(password,pwdDB)){
                return true;
            }else {
                return false;
            }

        }return false;
    }

}