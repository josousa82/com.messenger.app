import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer   {

   public static Vector clientSocket;
   public static Vector loginName;

    public ChatServer() throws IOException{

        ServerSocket server = new ServerSocket(5217);

        clientSocket = new Vector();
        loginName = new Vector();

        while (true){
            Socket client = server.accept();
            AcceptClient acceptClient  = new AcceptClient();

        }
    }
}
