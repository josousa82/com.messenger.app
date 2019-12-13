import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class ChatServer  extends  Thread {

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

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer();

    }



      class AcceptClient extends Thread{

        Socket clientSocket;
        DataInputStream din;
        DataOutputStream dout;

        public AcceptClient(Socket client) throws IOException {

            clientSocket =  client;
            din = new DataInputStream(clientSocket.getInputStream());
            dout = new DataOutputStream(clientSocket.getOutputStream());

            String loginName = din.readUTF();

            loginNames.add(loginName);
            clientSockets.add(clientSocket);
            start();

        }



        @Override
        public void run() {

            while(true){
                // add a login message
                try {
                    String msgFromClient = din.readUTF();
                    StringTokenizer st = new StringTokenizer(msgFromClient);
                    String loginName = st.nextToken();
                    String msgType = st.nextToken();
                    String msg = " ";
                    int logoutFlag = -1;

                    while(st.hasMoreTokens()){
                        msg = msg + " " + st.nextToken();
                    }

                    if(msgType.equals("LOGIN")) {

                        for (int i = 0; i < loginNames.size(); i++) {

                            Socket pSocket = (Socket) clientSockets.elementAt(i);
                            DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                            pOut.writeUTF(loginName + " has logged in.");
                        }
                    }else if (msgType.equals("LOGOUT")){

                        for (int i = 0; i < loginNames.size(); i++) {

                            if(loginName == loginNames.elementAt(i)) logoutFlag = i;

                            Socket pSocket = (Socket) clientSockets.elementAt(i);
                            DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                            pOut.writeUTF(loginName + " has logged out");

                            if(logoutFlag >= 0){
                                loginNames.removeElementAt(logoutFlag);
                                clientSockets.removeElementAt(logoutFlag);
                            }
                        }
                    } else {

                        for (int i = 0; i < loginNames.size(); i++) {

                            Socket pSocket = (Socket) clientSockets.elementAt(i);
                            DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                            pOut.writeUTF(loginName + ":" + msg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
