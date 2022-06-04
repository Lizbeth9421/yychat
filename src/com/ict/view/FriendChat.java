package com.ict.view;

import com.ict.db.common.MessageType;
import com.ict.db.domain.Message;
import com.ict.server.control.ClientConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @author: Lizbeth9421
 * @Date: 2022/04/11/11:19
 */
@SuppressWarnings("all")
public class FriendChat extends JFrame implements ActionListener, KeyListener {
    JTextArea textArea;
    JScrollPane scrollPane;
    JTextField textField;
    JButton button;

    String sender;
    String receiver;

    public FriendChat(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
        textArea = new JTextArea();//多行文本框
        textArea.setForeground(Color.red);
        scrollPane = new JScrollPane(textArea);
        this.add(scrollPane, "Center");

        textField = new JTextField(15);//单行文本框
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                if (e.getKeyChar()==KeyEvent.VK_ENTER){
                    if (textField.getText()!=null || textField.getText()!=""){
                        textArea.append(textField.getText() + "\r\n");
                        Message msg = new Message();
                        msg.setSender(sender);
                        msg.setReceiver(receiver);
                        msg.setContent(textField.getText());
                        //设置聊天信息类型
                        msg.setMessageType(MessageType.COMMON_CHAT_MESSAGE);
                        try {
                            //通过 Socket 对象发送聊天信息到服务器端
                            new ObjectOutputStream(ClientConnection.s.getOutputStream()).writeObject(msg);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "不能输入空消息");
                    }
                }
            }
        });
        button = new JButton("发送");
        button.addActionListener(this);
        button.setForeground(Color.blue);
        JPanel jPanel = new JPanel();
        jPanel.add(textField);
        jPanel.add(button);
        this.add(jPanel, "South");


        this.setSize(350, 250);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle(sender + "to" + receiver + "聊天界面");
        this.setIconImage(new ImageIcon("resources/duck2.gif").getImage());
        this.setVisible(true);
    }

    public void append(Message message) {
        textArea.append(message.getSendTime().toLocaleString() + "\r\n" + message.getSender() + "对你说： " + message.getContent() +
                "\r\n");
    }


    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == button) {
            textArea.append(textField.getText() + "\r\n");
            Message msg = new Message();
            msg.setSender(sender);
            msg.setReceiver(receiver);
            msg.setContent(textField.getText());
            //设置聊天信息类型
            msg.setMessageType(MessageType.COMMON_CHAT_MESSAGE);
            try {
                //通过 Socket 对象发送聊天信息到服务器端
                new ObjectOutputStream(ClientConnection.s.getOutputStream()).writeObject(msg);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //new FriendChat();
    }

    @Override
    public void keyTyped(final KeyEvent e) {

    }

    @Override
    public void keyPressed(final KeyEvent e) {

    }

    @Override
    public void keyReleased(final KeyEvent e) {

    }
}
