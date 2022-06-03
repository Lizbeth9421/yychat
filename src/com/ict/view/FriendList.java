package com.ict.view;

import com.ict.db.common.MessageType;
import com.ict.db.domain.Message;
import com.ict.server.control.ClientConnection;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * 好友列表
 *
 * @Author: Lizbeth9421
 * @Date: 2022/04/02/11:08
 */
@SuppressWarnings("all")
public class FriendList extends JFrame implements ActionListener, MouseListener, WindowListener {
    //定义 HashMap 对象，用来保存 name+toname 和好友聊天界面
    public static HashMap<String, FriendChat> friendChatHashMap = new HashMap<>();

    //定义卡片1（好友面板）中的组件
    JPanel friendPanel;//卡片1的组件容器
    JButton myFriendButton1, myStrangerButton1, blackListButtom1;
    JScrollPane friendListScrollPane;//好友列表的滚动条面板
    JPanel friendListPanel;//好友列表面板
    final Integer MYFRIEND_COUNT = 50;//50个好友
    JLabel friendLabel[] = new JLabel[MYFRIEND_COUNT];//定义好友图标数组

    //定义卡片2（陌生人面板）中的组件
    JPanel strangerPanel;//卡片2的组件容器
    JButton myFriendButton2, myStrangerButton2, blackListButtom2;
    JScrollPane strangerListJScrollPane;//陌生人列表的滚动条
    JPanel strangerListJPanel;//陌生人列表面板
    final Integer STRANGER_COUNT = 20;//20个陌生人
    JLabel strangerLabel[] = new JLabel[20];


    //为了在actionPerformed()中访问cl,声明为成员变量
    CardLayout cardLayout;

    String name;

    //定义添加好友面板和按钮
    JPanel addFriendJPanel;
    JButton addFriendButton, deleteFriendButton;


    JPanel functionJPanel;

    public FriendList(String name, String allFriends) {
        this.name = name;
        functionJPanel = new JPanel(new GridLayout(2, 1));

        //创建好友面板中的组件
        friendPanel = new JPanel(new BorderLayout());//卡片 1 设置边界布局模式

        addFriendJPanel = new JPanel(new GridLayout(2, 1));//添加好友面板设置为网格布局
        addFriendButton = new JButton("添加好友");
        addFriendButton.addActionListener(this);//在添加好友按钮上注册监听器对象
        deleteFriendButton = new JButton("删除好友");
        deleteFriendButton.addActionListener(this);
        myFriendButton1 = new JButton("我的好友");
        //friendPanel.add(addFriendButton, "North");
        functionJPanel.add(addFriendButton);
        functionJPanel.add(deleteFriendButton);
        friendPanel.add(functionJPanel, "North");
        //friendPanel.add(deleteFriendButton,"North");
        friendListPanel = new JPanel();
        showAllFriends(allFriends);
        friendListScrollPane = new JScrollPane(friendListPanel);//创建好友滚动条面板
        friendPanel.add(friendListScrollPane, "Center");

        myStrangerButton1 = new JButton("陌生人");
        myStrangerButton1.addActionListener(this);//注册按钮事件监听
        blackListButtom1 = new JButton("黑名单");
        //为容纳myStrangerButton1和blackListButtom1创建一个新的面板
        JPanel stranger_BlackPanel = new JPanel(new GridLayout(2, 1));
        stranger_BlackPanel.add(myStrangerButton1);
        stranger_BlackPanel.add(blackListButtom1);
        //添加到卡片1南部
        friendPanel.add(stranger_BlackPanel, "South");
        //创建陌生人面板中的组件
        strangerPanel = new JPanel(new BorderLayout());
        myFriendButton2 = new JButton("我的好友");
        myFriendButton2.addActionListener(this);//注册按钮事件监听
        myStrangerButton2 = new JButton("陌生人");
        //为容纳myFriendListButton2和myStrangerButton2定义一个新的面板
        JPanel friend_strangerPanel = new JPanel(new GridLayout(2, 1));
        friend_strangerPanel.add(myFriendButton2);
        friend_strangerPanel.add(myStrangerButton2);
        //添加到卡片2北部
        strangerPanel.add(friend_strangerPanel, "North");
        //创建中间的陌生人列表滚动条面板
        strangerListJPanel = new JPanel(new GridLayout(STRANGER_COUNT, 1));
        for (int i = 0; i < strangerLabel.length; i++) {
            strangerLabel[i] = new JLabel(i + "号陌生人", new ImageIcon("resources/tortoise.gif"), JLabel.LEFT);
            strangerListJPanel.add(strangerLabel[i]);
        }
        strangerListJScrollPane = new JScrollPane(strangerListJPanel);
        strangerPanel.add(strangerListJScrollPane, "Center");//陌生人滚动条添加到卡片2中部

        blackListButtom2 = new JButton("黑名单");
        strangerPanel.add(blackListButtom2, "South");

        //创建卡片布局
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        //窗体中添加卡片
        this.add(friendPanel, "card1");
        this.add(strangerPanel, "card2");
        cardLayout.show(this.getContentPane(), "card1");

        this.setIconImage(new ImageIcon("resources/duck2.gif").getImage());
        this.setTitle(name + "好友列表");
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(this);//在窗体上注册监听器对象（事件处理器对象是自己）
        this.setBounds(800, 600, 350, 250);
        this.setVisible(true);
    }

    public void showAllFriends(final String allFriends) {
        String[] myFriend = allFriends.split(" ");
        friendListPanel.removeAll();//在好友列表面板中移去全部组件
        friendListPanel.setLayout(new GridLayout(myFriend.length - 1, 1));
        for (int i = 1; i < myFriend.length; i++) {
            String imageUrl = "resources/" + i % 6 + ".jpg";//好友图标使用固定的图片
            ImageIcon imageIcon = new ImageIcon(imageUrl);
            friendLabel[i] = new JLabel(myFriend[i] + "", imageIcon, JLabel.LEFT);
            friendListPanel.add(friendLabel[i]);//好友图标添加到好友列表
            //为每一个好友标签组件上添加鼠标监听器
            friendLabel[i].addMouseListener(this);
        }
        //让好友列表面板中的组件重新生效
        friendListPanel.revalidate();
    }

    public void activeNewOnlineFriendIcon(String newOnlineFriend) {
        //this.friendLabel[Integer.valueOf(newOnlineFriend)].setEnabled(true);
    }

    public void activeOnlineFriendIcon(Message message) {
        String onlineFriend = message.getContent();
        String[] onlineFriendName = onlineFriend.split(" ");
        //激活全部在线好友图标
        for (int i = 1; i < onlineFriendName.length; i++) {
            //this.friendLabel[Integer.valueOf(onlineFriendName[i])].setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == myFriendButton2) {
            cardLayout.show(this.getContentPane(), "card1");
        }
        if (e.getSource() == myStrangerButton1) {
            cardLayout.show(this.getContentPane(), "card2");
        }
        if (e.getSource() == addFriendButton) {
            String newFriend = JOptionPane.showInputDialog("请输入新好友的名字：");//在输入框中输入信号有的名字
            System.out.println("newFriend:" + newFriend);
            if (newFriend != null) {
                Message message = new Message();
                message.setSender(name);
                message.setReceiver("server");
                message.setContent(newFriend);//新好名字保存在content字段中
                message.setMessageType(MessageType.ADD_NEW_FRIEND);//设置消息类型（添加新好友）
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(ClientConnection.s.getOutputStream());
                    objectOutputStream.writeObject(message);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }

        if (e.getSource() == deleteFriendButton) {
            String deleteFriend = JOptionPane.showInputDialog("请输入要删除好友的名字:");
            System.out.println("deleteFriend" + deleteFriend);
            if (deleteFriend != null) {
                Message message = new Message();
                message.setSender(name);
                message.setReceiver("server");
                message.setContent(deleteFriend);
                message.setMessageType(MessageType.DELETE_USER);
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(ClientConnection.s.getOutputStream());
                    objectOutputStream.writeObject(message);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        if (e.getClickCount() == 1) {//双击鼠标左键时
            final JLabel source = (JLabel) e.getSource();//获得被双击好友的标签组件
            String toName = source.getText();//拿到好友的名字
            FriendChat chat = new FriendChat(name, toName);//创建好友聊天界面
            //将好友聊天界面添加到好友聊天界面的HashMap中
            friendChatHashMap.put(name + "to" + toName, chat);
        }
    }

    @Override
    public void mousePressed(final MouseEvent e) {

    }

    @Override
    public void mouseReleased(final MouseEvent e) {

    }

    @Override
    public void mouseEntered(final MouseEvent e) {//鼠标进入好友标签组件时
        final JLabel source = (JLabel) e.getSource();
        source.setForeground(Color.red);//好友名字为红色
    }

    @Override
    public void mouseExited(final MouseEvent e) {//鼠标离开好友标签组件时
        final JLabel source = (JLabel) e.getSource();
        source.setForeground(Color.blue);//好友名字为蓝色
    }

    @Override
    public void windowOpened(final WindowEvent e) {

    }

    @Override
    public void windowClosing(final WindowEvent e) {
        System.out.println(name + " 准备关闭客户端...");
        //向服务器发送关闭客户端消息
        Message message = new Message();
        message.setSender(name);
        message.setReceiver("Server");
        message.setMessageType(MessageType.USER_EXIT_SERVER_THREAD_CLOSE);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(ClientConnection.s.getOutputStream());
            objectOutputStream.writeObject(message);//向服务器发送消息 } catch (IOException e) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }

    @Override
    public void windowClosed(final WindowEvent e) {

    }

    @Override
    public void windowIconified(final WindowEvent e) {

    }

    @Override
    public void windowDeiconified(final WindowEvent e) {

    }

    @Override
    public void windowActivated(final WindowEvent e) {

    }

    @Override
    public void windowDeactivated(final WindowEvent e) {

    }
}
