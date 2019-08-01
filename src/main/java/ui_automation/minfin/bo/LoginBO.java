package ui_automation.minfin.bo;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginBO extends BaseMinfinBO {
    public boolean login(String login, String pass) {
        goToPath("/signout");
        goToPath("/login");
        try {
            //<input class="mfm-auth--input" type="text" placeholder="Логин" name="Login" autofocus="" required="">
            $(By.xpath("/html/body/main/div/div/div[1]/div/div/div/div/form/div[1]/input")).setValue(login);
            $(By.xpath("/html/body/main/div/div/div[1]/div/div/div/div/form/div[2]/input")).setValue(pass);
            $(By.xpath("/html/body/main/div/div/div[1]/div/div/div/div/form/div[4]/button")).click();

//            executeJavaScriptAction("SET LOGIN", "document.getElementsByName(\"Login\")[1].value='" + login + "';");
//            executeJavaScriptAction("SET LOGIN", "document.getElementsByName(\"Login\")[2].value='" + login + "';");
//            executeJavaScriptAction("SET LOGIN", "document.getElementsByName(\"Login\")[3].value='" + login + "';");
//
//            executeJavaScriptAction("SET PASS", "document.getElementsByName(\"Password\")[1].value='" + pass + "';");
//            executeJavaScriptAction("SET PASS", "document.getElementsByName(\"Password\")[2].value='" + pass + "';");
//            executeJavaScriptAction("SET PASS", "document.getElementsByName(\"Password\")[3].value='" + pass + "';");
//            executeJavaScriptAction("SUBMIT", "document.querySelectorAll(\".mfm-auth--submit-btn[type=submit]\")[1].click();");
//            executeJavaScriptAction("SUBMIT", "document.querySelectorAll(\".mfm-auth--submit-btn[type=submit]\")[2].click();");
//            executeJavaScriptAction("SUBMIT", "document.querySelectorAll(\".mfm-auth--submit-btn[type=submit]\")[3].click();");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("could'nt login");
            return false;
        }
        return true;
    }
}
