import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.TreeMap;

public class AcceptClient extends Thread{

    Socket clientSocket;
    DataInputStream din;
    DataOutputStream dout;

    public AcceptClient(Socket clientSocket) throws IOException {

        this.clientSocket =  clientSocket;
        din = new DataInputStream(clientSocket.getInputStream());
        dout = new DataOutputStream(clientSocket.getOutputStream());

        String loginName = din.readUTF();

    }
}
