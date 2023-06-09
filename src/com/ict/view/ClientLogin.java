package com.ict.view;

import cn.hutool.core.util.ObjectUtil;
import com.ict.db.common.MessageType;
import com.ict.db.common.UserType;
import com.ict.db.domain.Message;
import com.ict.db.domain.User;
import com.ict.db.util.DbUtils;
import com.ict.db.util.EmailUtils;
import com.ict.server.control.ClientConnection;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/03/19/15:30
 */
@SuppressWarnings("all")
public class ClientLogin extends JFrame implements ActionListener, KeyListener {
    //定义标签组件
    JLabel head;
    JButton loginButton, registerButton, cancelButton, lostPassword;
    JPanel jPanel;

    //定义登陆界面中间部分的组件
    JLabel username, password, passwordProtect;
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
        //注册按钮事件监听
        loginButton.addActionListener(this);
        //创建注册按钮
        registerButton = new JButton(new ImageIcon("resources/register.gif"));
        //注册按钮事件监听
        registerButton.addActionListener(this);
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
        lostPassword = new JButton("忘记密码");
        lostPassword.addActionListener(this);
        //设置忘记密码按钮字体为蓝色
        lostPassword.setForeground(Color.CYAN);
        passwordProtect = new JLabel("申请密码保护", JLabel.CENTER);
        clearNumberButton = new JButton(new ImageIcon("resources/clear.gif"));
        textField = new JTextField();
        passwordField = new JPasswordField();
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    String name = textField.getText();
                    String password = new String(passwordField.getPassword());
                    User user = new User(name, password);
                    user.setUserType(UserType.USER_LOGIN_VALIDATE);//设置 user 对象类型
                    Message message = new ClientConnection().loginValidate1(user);
                    if (message.getMessageType().equals(MessageType.LOGIN_SUCCESS)) {//登录成功
                        String allFriend = message.getContent();//登录端拿到全部好友名字
                        FriendList friendList = new FriendList(name, allFriend);//把好友名字传到好友列表窗体
                        hmFriendList.put(name, friendList);//把好友列表窗体放入map中
                        dispose();//关闭登陆界面
                    } else {
                        JOptionPane.showMessageDialog(null, "密码错误，请重新登陆！");
                    }
                }
            }
        });
        stealthLogin = new JCheckBox("隐身登录");
        rememberPassword = new JCheckBox("记住密码");
        //初始化YY号码选项卡,设置网格布局模式，默认是FlowLayout布局
        YYNumberPanel = new JPanel(new GridLayout(3, 3));
        YYNumberPanel.add(username);
        YYNumberPanel.add(textField);
        YYNumberPanel.add(clearNumberButton);
        clearNumberButton.addActionListener(this);
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

    @SneakyThrows
    @Override
    public void actionPerformed(final ActionEvent e) {
        String name = textField.getText();
        String password = new String(this.passwordField.getPassword());
        //封装User对象
        User user = new User(name, password);
        if (e.getSource() == loginButton) {
            user.setUserType(UserType.USER_LOGIN_VALIDATE);//设置 user 对象类型
            Message message = new ClientConnection().loginValidate1(user);
            if (message.getMessageType().equals(MessageType.LOGIN_SUCCESS)) {//登录成功
                String allFriend = message.getContent();//登录端拿到全部好友名字
                FriendList friendList = new FriendList(name, allFriend);//把好友名字传到好友列表窗体
                hmFriendList.put(name, friendList);//把好友列表窗体放入map中
                this.dispose();//关闭登陆界面
            } else {
                JOptionPane.showMessageDialog(this, "密码错误，请重新登陆！");
            }
        } else if (e.getSource() == registerButton) {
            //设置 user 对象类型
            user.setUserType(UserType.USER_REGISTER);
            if (new ClientConnection().registerUser(user)) {//注册成功
                JOptionPane.showMessageDialog(this, name + " 用户，注册成功了！");
            } else {
                JOptionPane.showMessageDialog(this, name + " 用户名重复，请重新注册！");
            }
        } else if (e.getSource() == clearNumberButton) {
            //清除号码功能
            textField.setText("");
        } else if (e.getSource() == lostPassword) {
            //发邮件
            User user1 = DbUtils.selectUserByName(name);
            if (ObjectUtil.isNotNull(user1)) {
                System.out.println(user1.toString());
                EmailUtils.sendEmail(user1.getEmail(), String.valueOf(user1.getPassword()));
                JOptionPane.showMessageDialog(this, "邮件已发送!");
            } else {
                JOptionPane.showMessageDialog(this, "请输入正确的用户名!");
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
