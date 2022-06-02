package com.ict.server.view;

import com.ict.server.control.ChatServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/04/17/16:03
 */
public class StartServer extends JFrame implements ActionListener {
    public JButton startServerButton, stopServerButton;

    public StartServer() {
        startServerButton = new JButton("启动服务器");
        //设置字体
        startServerButton.setFont(new Font("宋体", Font.BOLD, 25));
        stopServerButton = new JButton("停止服务器");
        //设置字体
        stopServerButton.setFont(new Font("宋体", Font.BOLD, 25));
        //网格布局
        this.setLayout(new GridLayout(1, 2));
        //为启动按钮添加监听器
        startServerButton.addActionListener(this);
        startServerButton.setEnabled(true);
        //为关闭按钮添加监听器
        stopServerButton.addActionListener(this);
        stopServerButton.setEnabled(true);
        this.add(startServerButton);
        this.add(stopServerButton);
        this.setSize(400, 100);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("resources/duck2.gif").getImage());
        //设置默认关闭按钮
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //设置标题
        this.setTitle("服务器");
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(final ActionEvent e) {
        if(e.getSource() == startServerButton) {
            //创建chatServer服务器对象
            new ChatServer();
        }
        if (e.getSource()==stopServerButton){
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        new StartServer();
    }


}
