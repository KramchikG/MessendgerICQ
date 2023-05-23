package Server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class PotokClient implements Runnable {

    private Server server ;

    private PrintWriter out;
    private Scanner in;

    private Socket clientSoket = null;

    private  static int client_ = 0;
    public PotokClient(Socket socket, Server server){
        try {

            client_ ++;
            this.server = server;
            this.clientSoket = socket;
            this.out = new PrintWriter(socket.getOutputStream());
            this.in = new Scanner(socket.getInputStream());

        }catch(Exception e){

        }

    }

    public void sendM(String mess){
        out.println(mess);
        out.flush();
    }
    @Override
    public void run(){
        try{
            while (true){
                 server.MessageAll("Hi Client");
                 server.MessageAll(client_+"");
                break;
            }

            while (true){
                 String clientM = in.nextLine();
                 if(clientM.equals("Goodbye")){
                     break;
                 }
                 System.out.println(clientM);//Для разработчиков
                 server.MessageAll(clientM);//Для клиентов
            }

            Thread.sleep(100);
        }catch(Exception e){

        }
    }
}
