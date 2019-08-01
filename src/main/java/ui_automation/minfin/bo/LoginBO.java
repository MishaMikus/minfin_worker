package ui_automation.minfin.bo;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginBO extends BaseMinfinBO {
    public boolean login(String login, String pass) {
        goToPath("/signout");
        goToPath("/login");
        try {
            System.out.println("LOGIN : " + login + " " + pass);
            $(By.xpath("/html/body/main/div/div/div[1]/div/div/div/div/form/div[1]/input")).setValue(login);
            $(By.xpath("/html/body/main/div/div/div[1]/div/div/div/div/form/div[2]/input")).setValue(pass);
            $(By.xpath("/html/body/main/div/div/div[1]/div/div/div/div/form/div[4]/button")).click();
            System.out.println("LOGIN DONE");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("CAN'T LOGIN");
            return false;
        }
        return true;
    }
}
