package ExplodingKittens.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
/**
 * Server zur Implementierung der Chat-Funktion
 */
public class ChatServer {
    protected static final List<Socket> sockets = new Vector<>();

    public static void main(String[] args) throws IOException {
    ServerSocket server = new ServerSocket(8889);
    boolean flag = true;

        while (flag){
        try {

            Socket accept = server.accept();
            synchronized (sockets){
                sockets.add(accept);
            }

            Thread thread = new Thread(new ServerThead(accept));
            thread.start();

        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }
        }server.close();
    }

}

