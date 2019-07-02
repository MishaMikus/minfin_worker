package server.views.dashboard;

public class TradeStatusView {
    private String buttonColor;
    private String buttonLabel;
    private String message;

    public TradeStatusView(String buttonColor, String buttonLabel, String message) {
        this.buttonColor = buttonColor;
        this.buttonLabel = buttonLabel;
        this.message = message;
    }

    @Override
    public String toString() {
        return "TradeStatusView{" +
                "buttonColor='" + buttonColor + '\'' +
                ", buttonLabel='" + buttonLabel + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getButtonLabel() {
        return buttonLabel;
    }

    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public String getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(String buttonColor) {
        this.buttonColor = buttonColor;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
