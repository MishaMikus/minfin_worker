package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Objects;

public class SystemUtil {

    public static String getMyIP(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMyExternalIP(){
        URL whatismyip = null;
        try {
            whatismyip = new URL("http://checkip.amazonaws.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(whatismyip).openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String ip = null; //you get the IP as a String
        try {
            ip = Objects.requireNonNull(in).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ApplicationPropertyUtil.getBoolean("remote",false))
        return ip;
        else return "127.0.0.1";
    }
    public static String getMyPort(){
        return ApplicationPropertyUtil.getBoolean("remote",false)?"":":8080";
    }
}
