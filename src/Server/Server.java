package Server;

import java.net.ServerSocket;

public class Server {
     private static final int PORT = 9090;

     public Server(){
         try {
             ServerSocket serverSocket = new ServerSocket(PORT);
             System.out.println("Запуск");


         }catch (Exception e) {

         }


     }



    public static void main(String[] args) {
      Server server = new Server();
    }
}
