package ui_automation.upg;

public class UpgLoginBo extends BaseUpgBO {
    public void login(String login, String pass) {
        goToPath("/");
        //userForm:realUsername
        //clickById("userForm:realUsername");
        inputById("MFormLogin_login", login);

        //userForm:realUserpass
        // clickById("userForm:realUserpass");
        inputById("MFormLogin_password", pass);
        clickByClassName("ui-btn-hidden");
    }

}
