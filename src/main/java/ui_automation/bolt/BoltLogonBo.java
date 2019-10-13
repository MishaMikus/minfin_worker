package ui_automation.bolt;
public class BoltLogonBo extends BaseBoltBO {

    public BoltLogonBo login(String login, String pass) {
        goToPath("/login");
        inputById("username", login);
        inputById("password", pass);
        clickByType("submit");
        return this;
    }
}
