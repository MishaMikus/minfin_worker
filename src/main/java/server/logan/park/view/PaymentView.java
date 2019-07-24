package server.logan.park.view;

public class PaymentView {
    private String content;
    private String name;
    private String report;

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaymentView(String content) {
        this.content = content;
    }

    public PaymentView() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PaymentView{" +
                "content='" + content + '\'' +
                '}';
    }
}
