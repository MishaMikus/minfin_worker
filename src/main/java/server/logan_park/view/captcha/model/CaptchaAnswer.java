package server.logan_park.view.captcha.model;

import javax.validation.constraints.NotNull;

public class CaptchaAnswer {
    @NotNull
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "CaptchaAnswer{" +
                "answer='" + answer + '\'' +
                '}';
    }
}
