package Client;

import java.io.IOException;
import java.net.Socket;
public class Client {

    private volatile boolean isConnect = false;

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public static  void main(String[] args){
        Client client = new Client();
        //model
        //gui
    }
}
