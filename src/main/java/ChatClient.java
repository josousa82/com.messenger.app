import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends JFrame implements Runnable {

    Socket socket;
    JTextArea textArea;
    JTextField textField;
    JButton send;
    JButton logout;

    Thread thread;

    DataInputStream din;
    DataOutputStream dout;

    String loginName;

    public ChatClient(String loginName) throws IOException {
        super(loginName);
        this.loginName = loginName;

        textArea = new JTextArea(18, 50);
        textField = new JTextField(50);

        send = new JButton("Send");
        logout = new JButton("Logout");

        send.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dout.writeUTF(loginName + " " + "DATA " + textField.getText().toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        logout.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    dout.writeUTF(loginName + " " + "LOGOUT");
                }catch (IOException e1){
                    e1.printStackTrace();
                }
            }
        });

        socket = new Socket("localhost", 5217);

        din = new DataInputStream(socket.getInputStream());
        dout = new DataOutputStream(socket.getOutputStream());

        dout.writeUTF(loginName);
        dout.writeUTF(loginName + " LOGIN");

        thread = new Thread(this::run);
        thread.start();
        setup();
    }

    private void setup() {

        setSize(500,400);

        JPanel panel = new JPanel();
               panel.add(new JScrollPane(textArea));
               panel.add(textField);
               panel.add(send);
               panel.add(logout);

               add(panel);

               setVisible(true);
    }

    @Override
    public void run() {
        while (true){
            try {
                textArea.append("\n" + din.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        ChatClient client = new ChatClient(" User1");
        ChatClient client1 = new ChatClient(" User2");
    }
}