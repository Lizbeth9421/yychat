package com.ict.server.control;

import com.ict.db.common.MessageType;
import com.ict.db.domain.Message;
import com.ict.db.domain.UserRelation;
import com.ict.db.util.DbUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/04/18/11:16
 */
@SuppressWarnings("all")
public class ServerReceiverThread extends Thread {
    Socket s;

    ServerReceiverThread(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //创建对象输入流
                ObjectInputStream inputStream = new ObjectInputStream(s.getInputStream());
                //接受message对象
                Message message = (Message) inputStream.readObject();
                if (MessageType.ADD_NEW_FRIEND.equals(message.getMessageType())){
                    String sender = message.getSender();
                    String newFriend=message.getContent();
                    //首先查询新好友在 user 表中是否存在
                    if (!(DbUtils.seekUser(newFriend))){//新好友在 user 表中存在
                        //在 userrelation 表中查询新好友是不是已经是好友了
                        if (DbUtils.seekFriendIsExit(sender, newFriend, 1)){
                            //已经是好友了
                            message.setMessageType(MessageType.ADD_NEW_FRIEND_FAILURE_ALREADY_FRIEND);
                        }else {
                            //添加好友记录
                            DbUtils.insertUserRelation(new UserRelation(sender,newFriend,1));
                            String allFriend = DbUtils.seekAllFriends(sender, 1);
                            message.setContent(allFriend);//全部好友保存在 content 字段
                            message.setMessageType(MessageType.ADD_NEW_FRIEND_SUCCESS);//设置添加新好友成功消息类型
                        }
                    }else {//新好友在 user 表中不存在
                        message.setMessageType(MessageType.ADD_NEW_FRIEND_FAILURE_NO_USER);//设置添加新好友失败消息类型
                    }
                    Socket socket = ChatServer.hmSocket.get(sender);
                    sendMessage(socket, message);//发送消息到客户端
                }
                if (MessageType.COMMON_CHAT_MESSAGE.equals(message.getMessageType())) {
                    System.out.println(message.getSender() + " 对 " + message.getReceiver()
                            + " 说：" + message.getContent());
                    //从 hmSocket 中拿到接收方的 Socket 对象，然后转发聊天信息
                    String receiver = message.getReceiver();
                    Socket receiverSocket = ChatServer.hmSocket.get(receiver);
                    System.out.println("接收方 " + receiver + " 的 Socket 对象：" + receiverSocket);
                    if (receiverSocket != null) {//接收方在线，才能转发信息
                        ObjectOutputStream outputStream = new ObjectOutputStream(receiverSocket.getOutputStream());
                        outputStream.writeObject(message);//发送聊天信息
                    } else {
                        System.out.println(receiver + "不在线上");
                    }
                }
                if (message.getMessageType().equals(MessageType.REQUEST_ONLINE_FRIEND)) {
                    Set<String> onlineFriendSet = ChatServer.hmSocket.keySet();
                    Iterator<String> iterator = onlineFriendSet.iterator();
                    String onlineFriend = "";
                    while (iterator.hasNext()) {
                        onlineFriend = " " + iterator.next() + onlineFriend;//在线好友名字的字符串
                    }
                    message.setReceiver(message.getSender());//接收方为发送方
                    message.setSender("Server");//发送方为服务器
                    message.setMessageType(MessageType.RESPONSE_ONLINE_FRIEND);//设置消息类型
                    message.setContent(onlineFriend);//设置消息内容是在线好友的名字
                    sendMessage(s, message);//发送在线好友的响应消息
                }
                if (message.getMessageType().equals(MessageType.NEW_ONLINE_TO_ALL_FRIEND)){
                    message.setMessageType(MessageType.NEW_ONLINE_FRIEND);//设置消息类型
                    Set<String> onlineFriendSet=ChatServer.hmSocket.keySet();//拿到在线好友名字的集合
                    Iterator<String> it=onlineFriendSet.iterator();
                    while(it.hasNext()){//循环中依次给全部在线好友发送消息
                        String receiver = it.next();
                        message.setReceiver(receiver);//接收方为每个在线好友
                        //拿到接收消息用户的 Socket 对象
                        Socket socket = ChatServer.hmSocket.get(receiver);
                        sendMessage(socket, message);///告诉 receiver 用户，sender 上线了，激活 sender 图标
                    }
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 发送消息方法
     *
     * @param s
     * @param message
     */
    public void sendMessage(final Socket s, final Message message) {
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(message);//发送聊天信息
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
