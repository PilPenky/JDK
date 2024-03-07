package Lesson1_HW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static Lesson1_HW.ServerWindow.readFile;

public class ClientGUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private boolean stateLogin;
    private String messageButtonSend;
    private JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JTextField tfLogin = new JTextField("User");
    private final JPasswordField tfPassword = new JPasswordField("123456");
    private final JButton btnLogin = new JButton("Login");
    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JPanel panelCenter = new JPanel(new BorderLayout());
    private final JTextArea messageCenter = new JTextArea();
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");
    private final String connection = "Вы успешно подключились";
    private final String noConnection = "Подключение не удалось";

    public void displayingMessagesOnTheClient(String userMessage) throws FileNotFoundException {
        readFile();
        messageCenter.setText("Вы успешно подключились");
        ArrayList availableRecords = readFile();
        for (Object avRec : availableRecords) {
            messageCenter.setText(messageCenter.getText() + "\n" + avRec);
        }
        messageCenter.setText(messageCenter.getText() + "\n" + userMessage);

        messageCenter.revalidate();
        panelCenter.revalidate();
    }
    ClientGUI(ServerWindow serverWindow) {

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRootPane rootPane = SwingUtilities.getRootPane(btnSend);
                rootPane.setDefaultButton(btnSend);

                if (stateLogin == true && serverWindow.serverCheck() == true) {
                    try {
                        messageButtonSend = tfLogin.getText() + ": " + tfMessage.getText();
                        displayingMessagesOnTheClient(messageButtonSend);
                        serverWindow.userMessageOnServer(messageButtonSend);
                        tfMessage.setText("");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean stateServer = serverWindow.serverCheck();
                System.out.println("Состояние кнопки Login: " + stateServer);

                if (stateServer == true) {
                    if (messageCenter.getText().equals("")) {
                        messageCenter.setText(connection);
                        try {
                            ArrayList availableRecords = readFile();
                            for (Object avRec : availableRecords) {
                                messageCenter.setText(messageCenter.getText() + "\n" + avRec);
                            }
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }

                    } else {
                        messageCenter.setText(messageCenter.getText() + "\n" + connection);
                        try {
                            ArrayList availableRecords = readFile();
                            for (Object avRec : availableRecords) {
                                messageCenter.setText(messageCenter.getText() + "\n" + avRec);
                            }
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    stateLogin = true;

                } else {
                    if (messageCenter.getText().equals("")) {
                        messageCenter.setText(noConnection);
                    } else messageCenter.setText(messageCenter.getText() + "\n" + noConnection);
                    stateLogin = false;
                }
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat Client");
        setAlwaysOnTop(true);

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        add(panelTop, BorderLayout.NORTH);

        panelCenter.add(messageCenter);
        add(panelCenter);

        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);

        log.setEditable(false);
        setVisible(true);
    }
}