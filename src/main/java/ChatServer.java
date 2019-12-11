import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer   {

    // apparently a vector is used due to the synchronization capacity
    // review and refactor the code, consider using ArrayList

    private Vector clientSocket;
    private Vector loginNames;

    public ChatServer() throws IOException{

        ServerSocket server = new ServerSocket(5217);

        clientSocket = new Vector();
        loginNames = new Vector();

        while (true){
            Socket client = server.accept();
            AcceptClient acceptClient  = new AcceptClient();

        }
    }

    public void addNewLoginName(String loginName){
        loginNames.add(loginName);
    }

    public  Vector getClientSocket() {
        return new Vector(clientSocket);
    }

    public Vector getLoginNames() {
        return new Vector(loginNames);
    }


}
