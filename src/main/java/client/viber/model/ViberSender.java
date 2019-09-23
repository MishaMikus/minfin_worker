package client.viber.model;

public class ViberSender {
    public static final ViberSender MINFIN_BOT = new ViberSender("Доляровий Барига",
            "https://dl-media.viber.com/1/share/2/long/vibes/icon/image/0x0/40e4/33c1ccfc1991b38eddcfa93841e75a8bc85a2e56e0e79c3cd9c866cb60b840e4.jpg");
    public ViberSender(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }
    private String name;
    private String avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
