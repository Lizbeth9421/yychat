package com.ict.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

/**
 * 好友列表
 *
 * @Author: Lizbeth9421
 * @Date: 2022/04/02/11:08
 */
@SuppressWarnings("all")
public class FriendList extends JFrame implements ActionListener, MouseListener {
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

    public FriendList(String name) {
        this.name = name;
        //创建好友面板中的组件
        friendPanel = new JPanel(new BorderLayout());
        myFriendButton1 = new JButton("我的好友");
        friendPanel.add(myFriendButton1, "North");
        //创建中间的好友列表滚动条面板
        friendListPanel = new JPanel(new GridLayout(MYFRIEND_COUNT, 1));//好友列表50行1列
        for (int i = 0; i < friendLabel.length; i++) {
            String imageUrl = "resources/" + (int) (Math.random() * 6) + ".jpg";//随机生成图片路径
            ImageIcon imageIcon = new ImageIcon(imageUrl);
            friendLabel[i] = new JLabel(i + "", imageIcon, JLabel.LEFT);
            friendListPanel.add(friendLabel[i]);//好友图标添加到好友列表
            //为每一个好友标签组件上添加鼠标监听器
            friendLabel[i].addMouseListener(this);
        }
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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(800, 600, 350, 250);
        this.setVisible(true);
    }


    public static void main(String[] args) {
        //new FriendList("");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == myFriendButton2) {
            cardLayout.show(this.getContentPane(), "card1");
        }
        if (e.getSource() == myStrangerButton1) {
            cardLayout.show(this.getContentPane(), "card2");
        }
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        if (e.getClickCount() == 1) {//双击鼠标左键时
            final JLabel source = (JLabel) e.getSource();//获得被双击好友的标签组件
            String toName = source.getText();//拿到好友的名字
            FriendChat chat = new FriendChat(name, toName);//创建好友聊天界面
            //将好友聊天界面添加到好友聊天界面的HashMap中
            friendChatHashMap.put(name+"to"+toName, chat);
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
        source.setForeground(Color.red);
    }

    @Override
    public void mouseExited(final MouseEvent e) {//鼠标离开好友标签组件时
        final JLabel source = (JLabel) e.getSource();
        source.setForeground(Color.blue);
    }
}
