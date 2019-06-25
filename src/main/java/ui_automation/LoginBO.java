package ui_automation;
public class LoginBO extends BaseBO{
    public void login(String login, String pass) {
        goToPath("/login");
        executeJavaScriptAction("SET LOGIN", "document.getElementsByName(\"Login\")[3].value='" + login + "';");
        executeJavaScriptAction("SET PASS", "document.getElementsByName(\"Password\")[3].value='" + pass + "';");
        executeJavaScriptAction("SUBMIT", "document.querySelectorAll(\".mfm-auth--submit-btn[type=submit]\")[3].click();");
    }
}
