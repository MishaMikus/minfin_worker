package ui_automation.bolt;

public class BoltLogonBo extends BaseBoltBO{

    public void login(String login, String pass) {
        goToPath("/login");
        inputById("username",login);
        inputById("password",pass);
        clickByType("submit");
        System.out.println();
    }
}
