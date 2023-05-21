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
    @Override
    public void run(){
        try{
            while (true){

                break;
            }
            Thread.sleep(100);
        }catch(Exception e){

        }
    }
}
