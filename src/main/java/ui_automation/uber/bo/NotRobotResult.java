package ui_automation.uber.bo;

public enum NotRobotResult {
     AUTHORIZED,
    SUCCESS,
    FAIL,
    PASSWORD_NEED,
    CAPTCHA_SOLVED;

private String solve;

    public String getSolve() {
        return solve;
    }

    public void setSolve(String solve) {
        this.solve = solve;
    }
}
