package ExplodingKittens.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * ServerThread, hauptsächlich zur Verarbeitung mehrerer Client-Anforderungen
 */
public class ServerThead extends ChatServer implements Runnable{

    Socket socket;
    String socketName;

    public ServerThead(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            socketName = socket.getRemoteSocketAddress().toString();


            boolean flag = true;
            while (flag)
            {

                String line = reader.readLine();

                if (line == null){
                    flag = false;
                    continue;
                }
                print(line);
            }

            closeConnect();
        } catch (IOException e) {
            try {
                closeConnect();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    /**
     * Leiten Sie Nachrichten an alle Online-Client-Sockets weiter
     * @param msg               Nachrichten von Spieler
     * @throws IOException      Fehler bei IO
     */
    private void print(String msg) throws IOException {
        PrintWriter out ;
        synchronized (sockets){
            for (Socket sc : sockets){
                out = new PrintWriter(sc.getOutputStream());
                out.println(msg);
                out.flush();
            }
        }
    }
    /**
     *
     * Schließen Sie die Socket-Verbindung
     * @throws IOException      Fehler bei IO
     */
    public void closeConnect() throws IOException {
        synchronized (sockets){
            sockets.remove(socket);
        }
        socket.close();
    }
}


