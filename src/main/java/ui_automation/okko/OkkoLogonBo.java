package ui_automation.okko;

public class OkkoLogonBo extends BaseOkkoBO {
    public void login(String login, String pass) {
        goToPath("/");
        //userForm:realUsername
        //clickById("userForm:realUsername");
        inputById("userForm:realUsername",login);

        //userForm:realUserpass
       // clickById("userForm:realUserpass");
        inputById("userForm:realUserpass",pass);
        clickByClassName("loginButton");
    }

}
