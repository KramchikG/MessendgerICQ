package Server;

import com.sun.security.ntlm.Client;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
     private static final int PORT = 9090;
     private ArrayList<PotokClient> clientss = new ArrayList<>();

     public Server(){
         try {
             ServerSocket serverSocket = new ServerSocket(PORT);
             System.out.println("Запуск");
             Socket client = null;

             while(true){
                 client = serverSocket.accept();
                 PotokClient client1 = new PotokClient(client, this);
                 clientss.add(client1);
             }


         }catch (Exception e) {

         }


     }
     public void MessageAll(String mess){
        for(PotokClient entry : clientss){
            entry.sendM(mess);
        }
     }
     public void removeClient(PotokClient potokClient){
         clientss.remove(potokClient);
     }
    public static void main(String[] args) {
      Server server = new Server();
    }
}
