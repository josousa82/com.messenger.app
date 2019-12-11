import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer  extends  Thread{

    // apparently a vector is used due to the synchronization capacity
    // review and refactor the code, consider using ArrayList

     static Vector clientSockets;
     static Vector loginNames;

    public ChatServer() throws IOException{

        ServerSocket server = new ServerSocket(5217);

        clientSockets = new Vector();
        loginNames = new Vector();

        while (true){
            Socket client = server.accept();
            AcceptClient acceptClient  = new AcceptClient(client);

        }
    }
}
