package com.ict.server.control;

import cn.hutool.core.util.ObjectUtil;
import com.ict.db.common.MessageType;
import com.ict.db.domain.Message;
import com.ict.view.ClientLogin;
import com.ict.view.FriendChat;
import com.ict.view.FriendList;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/04/18/11:35
 */
@SuppressWarnings("all")
public class ClientReceiverThread extends Thread {
    Socket s;

    public ClientReceiverThread(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ObjectInputStream inputStream = new ObjectInputStream(s.getInputStream());
                //阻塞式编程，没有读到信息（服务器没有发送），就一直等待
                Message message = (Message) inputStream.readObject();
                if (MessageType.USER_EXIT_CLIENT_THREAD_CLOSE.equals(message.getMessageType())) {
                    System.out.println("关闭 " + message.getSender() + " 用户接收线程");
                    s.close();
                    break;//退出线程的循环结构
                }
                if ((MessageType.ADD_NEW_FRIEND_FAILURE_NO_USER.equals(message.getMessageType()))) {
                    JOptionPane.showMessageDialog(null, "新好友名字不存在，添加好友失败！");
                }
                if (MessageType.ADD_NEW_FRIEND_FAILURE_ALREADY_FRIEND.equals(message.getMessageType())) {
                    JOptionPane.showMessageDialog(null, "该用户已经是好友了，不能重复添加！");
                }
                if (MessageType.ADD_NEW_FRIEND_SUCCESS.equals(message.getMessageType())) {
                    JOptionPane.showMessageDialog(null, "添加好友成功！");
                    String sender = message.getSender();
                    FriendList friendList = ClientLogin.hmFriendList.get(sender);
                    friendList.showAllFriends(message.getContent());
                }
                if (MessageType.COMMON_CHAT_MESSAGE.equals(message.getMessageType())) {
                    String receiver = message.getReceiver();
                    String sender = message.getSender();
                    //从 HashMap 中拿到 FriendChat 对象，并显示聊天信息
                    FriendChat fc = FriendList.friendChatHashMap.get(receiver + "to" + sender);
                    if (ObjectUtil.isNotNull(fc)) {
                        fc.append(message);
                    } else {
                        System.out.println("请打开" + receiver + "to" + sender + "的聊天界面");
                    }
                }
                if (MessageType.RESPONSE_ONLINE_FRIEND.equals(message.getMessageType())) {
                    //为了拿到接收方的 FriendList 对象（好友列表界面），需要在创建时保存到 HashMap 对象中
                    FriendList friendList = ClientLogin.hmFriendList.get(message.getReceiver());
                    friendList.activeOnlineFriendIcon(message);
                }
                if (MessageType.NEW_ONLINE_FRIEND.equals(message.getMessageType())) {
                    String receiver = message.getReceiver();//先拿到 receiver 的好友列表界面
                    FriendList fl = ClientLogin.hmFriendList.get(receiver);
                    String sender = message.getSender();
                    fl.activeNewOnlineFriendIcon(sender);//激活新上线好友图标
                }
                if (MessageType.DELETE_USER_SUCCESS.equals(message.getMessageType())) {
                    JOptionPane.showMessageDialog(null, "删除好友成功！");
                    String sender = message.getSender();
                    FriendList friendList = ClientLogin.hmFriendList.get(sender);
                    friendList.showAllFriends(message.getContent());
                }
                if (MessageType.DELETE_USER_FAILED.equals(message.getMessageType())) {
                    JOptionPane.showMessageDialog(null, "删除的好友不存在！");
                }
                if (MessageType.NOT_FRIEND.equals(message.getMessageType())) {
                    JOptionPane.showMessageDialog(null, "所删除好友不是你的好友！");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}