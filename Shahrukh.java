import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Shahrukh extends JFrame {
    static JPanel p2;
    JTextField t1;
    DataOutputStream dout;
    static Box vertical = Box.createVerticalBox();
    JScrollPane scrollPane;
    private static final Color PRIMARY_COLOR = new Color(255, 87, 87);
    private static final Color SENT_MESSAGE_COLOR = new Color(255, 87, 87);
    private static final Color RECEIVED_MESSAGE_COLOR = new Color(230, 230, 230);
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);
    private static final Font MESSAGE_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font TIME_FONT = new Font("Segoe UI", Font.PLAIN, 9);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font STATUS_FONT = new Font("Segoe UI", Font.PLAIN, 11);

    Shahrukh() {
        setSize(380, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(null);
        setLocation(500, 40);
        setShape(new RoundRectangle2D.Double(0, 0, 380, 600, 30, 30));
        getContentPane().setBackground(BACKGROUND_COLOR);

        JPanel p1 = createHeaderPanel();
        add(p1);

        p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
        p2.setBackground(BACKGROUND_COLOR);
        p2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        scrollPane = new JScrollPane(p2);
        scrollPane.setBounds(0, 70, 380, 470);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        add(scrollPane);

        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel);

        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel p1 = new JPanel();
        p1.setBounds(0, 0, 380, 70);
        p1.setBackground(PRIMARY_COLOR);
        p1.setLayout(null);

        ImageIcon back1 = new ImageIcon("icons/back.png");
        Image back2 = back1.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon back3 = new ImageIcon(back2);

        JLabel back = new JLabel(back3);
        back.setBounds(15, 22, 25, 25);
        p1.add(back);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                animateClose();
            }
        });

        ImageIcon profile1 = new ImageIcon("icons/shahrukh.png");
        Image profile2 = profile1.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon profile3 = new ImageIcon(profile2);

        JLabel profile = new JLabel(profile3);
        profile.setBounds(60, 12, 45, 45);
        profile.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        p1.add(profile);

        JLabel name = new JLabel("Shahrukh");
        name.setForeground(Color.WHITE);
        name.setFont(HEADER_FONT);
        name.setBounds(115, 18, 120, 20);
        p1.add(name);

        JLabel active = new JLabel("● Online");
        active.setFont(STATUS_FONT);
        active.setForeground(new Color(200, 255, 200));
        active.setBounds(115, 40, 80, 12);
        p1.add(active);

        JLabel videoCall = createIconLabel("icons/video.png", 30, 30);
        videoCall.setBounds(280, 20, 30, 30);
        p1.add(videoCall);

        JLabel call = createIconLabel("icons/call.png", 30, 30);
        call.setBounds(325, 20, 30, 30);
        p1.add(call);

        return p1;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 540, 380, 60);
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setLayout(null);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        t1 = new JTextField();
        t1.setBounds(15, 10, 290, 40);
        t1.setFont(MESSAGE_FONT);
        t1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        bottomPanel.add(t1);

        ImageIcon send1 = new ImageIcon("icons/send.png");
        Image send2 = send1.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon send3 = new ImageIcon(send2);

        JLabel send = new JLabel(send3);
        send.setBounds(315, 12, 35, 35);
        send.setCursor(new Cursor(Cursor.HAND_CURSOR));
        send.setToolTipText("Send message");
        bottomPanel.add(send);

        ActionListener sendAction = e -> sendMessage();
        send.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                sendMessage();
            }
        });

        t1.addActionListener(sendAction);

        return bottomPanel;
    }

    private JLabel createIconLabel(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(img));
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return label;
    }

    private void sendMessage() {
        String messageToSend = t1.getText().trim();
        if (!messageToSend.isEmpty()) {
            displaySentMessage(messageToSend);
            try {
                dout.writeUTF(messageToSend);
            } catch (Exception ex) {
                showError("Failed to send message");
            }
            t1.setText("");
        }
    }

    private void animateClose() {
        Timer timer = new Timer(10, new ActionListener() {
            double opacity = 1.0;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05;
                if (opacity <= 0) {
                    ((Timer) e.getSource()).stop();
                    dispose();
                }
                setOpacity((float) opacity);
            }
        });
        timer.start();
    }

    public void displaySentMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            JPanel messagePanel = createMessagePanel(message, true);
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            wrapper.setOpaque(false);
            wrapper.add(messagePanel);
            
            vertical.add(wrapper);
            p2.removeAll();
            p2.add(vertical);
            p2.revalidate();
            p2.repaint();
            scrollToBottom();
        });
    }

    public void displayReceivedMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            JPanel messagePanel = createMessagePanel(message, false);
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            wrapper.setOpaque(false);
            wrapper.add(messagePanel);
            
            vertical.add(wrapper);
            p2.removeAll();
            p2.add(vertical);
            p2.revalidate();
            p2.repaint();
            scrollToBottom();
        });
    }

    private JPanel createMessagePanel(String message, boolean isSent) {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setOpaque(false);

        JTextPane messageContent = new JTextPane();
        messageContent.setContentType("text/html");
        messageContent.setText("<html><body style='font-family: Segoe UI; font-size: 12pt; margin:0; padding:0;'>" 
                              + message.replace("\n", "<br>") + "</body></html>");
        messageContent.setEditable(false);
        messageContent.setOpaque(true);
        messageContent.setBackground(isSent ? SENT_MESSAGE_COLOR : RECEIVED_MESSAGE_COLOR);
        messageContent.setForeground(isSent ? Color.WHITE : Color.BLACK);
        messageContent.setBorder(BorderFactory.createEmptyBorder(4, 8, 2, 8));

        JLabel timeLabel = new JLabel(getCurrentTime());
        timeLabel.setFont(TIME_FONT);
        timeLabel.setForeground(Color.GRAY);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 2, 5));
        timeLabel.setAlignmentX(isSent ? Component.RIGHT_ALIGNMENT : Component.LEFT_ALIGNMENT);

        messagePanel.add(messageContent);
        messagePanel.add(timeLabel);

        return messagePanel;
    }

    private void scrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            if (scrollPane != null) {
                JScrollBar vertical = scrollPane.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            }
        });
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(new Date());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Shahrukh shahrukhInstance = new Shahrukh();

        new Thread(() -> {
            try {
                try (Socket s = new Socket("127.0.0.1", 3434)) {
                    DataInputStream din = new DataInputStream(s.getInputStream());
                    shahrukhInstance.dout = new DataOutputStream(s.getOutputStream());

                    while (true) {
                        String received = din.readUTF();
                        shahrukhInstance.displayReceivedMessage(received);
                    }
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }).start();
    }
}