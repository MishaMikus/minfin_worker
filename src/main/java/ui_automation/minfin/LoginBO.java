package ui_automation.minfin;
public class LoginBO extends BaseBO{
    public void login(String login, String pass) {
        goToPath("/login");
        try{
        executeJavaScriptAction("SET LOGIN", "document.getElementsByName(\"Login\")[3].value='" + login + "';");
        executeJavaScriptAction("SET PASS", "document.getElementsByName(\"Password\")[3].value='" + pass + "';");
        executeJavaScriptAction("SUBMIT", "document.querySelectorAll(\".mfm-auth--submit-btn[type=submit]\")[3].click();");}
        catch (Exception e){
            System.out.print("could'nt login");
        }
    }
}
