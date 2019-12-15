import javax.swing.*;
import javax.swing.border.Border;
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
    JFrame jframe;
    JPanel panel1;
    JPanel panel2;

    Thread thread;

    DataInputStream din;
    DataOutputStream dout;

    String loginName;

    public ChatClient(String loginName) throws IOException {
        super(loginName);
        this.loginName = loginName;

        textArea = new JTextArea(18, 30);
        textField = new JTextField(20);

        send = new JButton("Send");
        send.updateUI();
        logout = new JButton("Logout");
        logout.updateUI();

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

               jframe = new JFrame();
               jframe.getContentPane().setLayout(new BorderLayout());
               jframe.setSize(600  , 400);

               panel1 = new JPanel();
               panel1.setPreferredSize(new Dimension(400, 300));
               panel1.setBackground(Color.LIGHT_GRAY);
               panel1.add(new JScrollPane(textArea));

               panel2 = new JPanel();
               panel2.setBackground(Color.LIGHT_GRAY);



               panel2.add(textField);
               panel2.add(send);
               panel2.add(logout);

               jframe.getContentPane().add(panel1, BorderLayout.PAGE_START);
               jframe.getContentPane().add(panel2, BorderLayout.PAGE_END);

               // add(panel);
               jframe.pack();
               jframe.setVisible(true);
               jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
