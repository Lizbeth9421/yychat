package com.ict.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: Lizbeth9421
 * @Date: 2022/04/11/11:19
 */
@SuppressWarnings("all")
public class FriendChat extends JFrame implements ActionListener {
    JTextArea textArea;
    JScrollPane scrollPane;
    JTextField textField;
    JButton button;
    public FriendChat(String oneToAnother){
        textArea=new JTextArea();//多行文本框
        textArea.setForeground(Color.red);
        scrollPane=new JScrollPane(textArea);
        this.add(scrollPane,"Center");

        textField=new JTextField(15);//单行文本框
        button=new JButton("发送");
        button.addActionListener(this);
        button.setForeground(Color.blue);
        JPanel jPanel=new JPanel();
        jPanel.add(textField);
        jPanel.add(button);
        this.add(jPanel,"South");


        this.setSize(350,250);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle(oneToAnother+"聊天界面");
        this.setIconImage(new ImageIcon("resources/duck2.gif").getImage());
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource()==button){
            textArea.append(textField.getText()+"\r\n");
        }
    }

    public static void main(String[] args) {
        //new FriendChat();
    }
}
