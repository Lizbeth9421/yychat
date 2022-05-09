package com.ict.view;

import com.ict.db.common.MessageType;
import com.ict.db.domain.Message;
import com.ict.db.domain.User;
import com.ict.server.control.ClientConnection;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/03/19/15:30
 */
@SuppressWarnings("all")
public class ClientLogin extends JFrame implements ActionListener {
    //定义标签组件
    JLabel head;
    JButton loginButton, registerButton, cancelButton;
    JPanel jPanel;

    //定义登陆界面中间部分的组件
    JLabel username, password, lostPassword, passwordProtect;
    JTextField textField;
    JPasswordField passwordField;
    JButton clearNumberButton;
    JCheckBox stealthLogin, rememberPassword;
    JPanel YYNumberPanel, phoneNumberPanel, emailPanel;
    JTabbedPane tabledPane;

    public static HashMap<String, FriendList> hmFriendList = new HashMap<>();

    public ClientLogin() {

        //设置一个图片标签
        head = new JLabel(new ImageIcon("resources/head.gif"));
        //添加到窗体北部
        this.add(head, "North");
        //创建登录按钮
        loginButton = new JButton(new ImageIcon("resources/login.gif"));
        loginButton.addActionListener(this);//注册按钮事件监听
        //创建注册按钮
        registerButton = new JButton(new ImageIcon("resources/register.gif"));
        //创建取消按钮
        cancelButton = new JButton(new ImageIcon("resources/cancel.gif"));
        //创建一个面板对象
        jPanel = new JPanel();
        //在此面板对象中加入按钮
        jPanel.add(loginButton);
        jPanel.add(registerButton);
        jPanel.add(cancelButton);
        //添加到窗体南部
        this.add(jPanel, "South");
        //-----创建登录界面中间部分的组件---
        username = new JLabel("YY号码", JLabel.CENTER);
        password = new JLabel("YY密码", JLabel.CENTER);
        lostPassword = new JLabel("忘记密码", JLabel.CENTER);
        //设置忘记密码按钮字体为蓝色
        lostPassword.setForeground(Color.CYAN);
        passwordProtect = new JLabel("申请密码保护", JLabel.CENTER);
        clearNumberButton = new JButton(new ImageIcon("resources/clear.gif"));
        textField = new JTextField();
        passwordField = new JPasswordField();
        stealthLogin = new JCheckBox("隐身登录");
        rememberPassword = new JCheckBox("记住密码");
        //初始化YY号码选项卡,设置网格布局模式，默认是FlowLayout布局
        YYNumberPanel = new JPanel(new GridLayout(3, 3));
        YYNumberPanel.add(username);
        YYNumberPanel.add(textField);
        YYNumberPanel.add(clearNumberButton);
        YYNumberPanel.add(password);
        YYNumberPanel.add(passwordField);
        YYNumberPanel.add(lostPassword);
        YYNumberPanel.add(stealthLogin);
        YYNumberPanel.add(rememberPassword);
        YYNumberPanel.add(passwordProtect);
        tabledPane = new JTabbedPane();
        tabledPane.add(YYNumberPanel, "YY号码");
        //初始化手机号码选项卡
        phoneNumberPanel = new JPanel();
        //初始化电子邮箱选项卡
        emailPanel = new JPanel();
        tabledPane.add(phoneNumberPanel, "手机号码");
        tabledPane.add(emailPanel, "电子邮箱");
        this.add(tabledPane, "Center");
        //-----------------------------
        //设置相对位置为null
        this.setLocationRelativeTo(null);
        //设置窗体大小
        this.setSize(350, 250);
        //设置默认关闭按钮
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //设置标题
        this.setTitle("YY聊天室");
        //设置窗体可视
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == loginButton) {
            String name = textField.getText();
            String password = new String(this.passwordField.getPassword());
            //封装User对象
            User user = new User(name, password);
            //创建ClientConnection对象，和服务器建立联系
            ClientConnection connection = new ClientConnection();
            if (connection.loginValidate(user)) {
                hmFriendList.put(name, new FriendList(name));
                System.out.println("客户端登陆成功！");
                Message mess = new Message();
                mess.setSender(name);
                mess.setReceiver("Server");
                mess.setMessageType(MessageType.REQUEST_ONLINE_FRIEND);//设置消息类型
                //使用 sendMessage()方法发送消息
                sendMessage(ClientConnection.s, mess);
                mess.setMessageType(MessageType.NEW_ONLINE_TO_ALL_FRIEND);
                sendMessage(ClientConnection.s, mess);
                this.dispose();//关闭登陆界面
            } else {
                JOptionPane.showMessageDialog(this, "密码错误，请重新登录！");
            }
        }
    }

    public void sendMessage(final Socket s, final Message mess) {
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(mess);//发送消息
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //创建ClientLogin的对象
        new ClientLogin();
    }

}
