package android.edu.cn.login.model;

import org.litepal.crud.DataSupport;

/**
 * Created by StoryBegins on 2017/11/28.
 */

public class Users extends DataSupport{

    private String UsersId;
    private String UsersPassword;
    private String PhoneNumber;

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUsersId() {
        return UsersId;
    }

    public void setUsersId(String usersId) {
        UsersId = usersId;
    }


    public String getUsersPassword() {
        return UsersPassword;
    }

    public void setUsersPassword(String usersPassword) {
        UsersPassword = usersPassword;
    }


}
