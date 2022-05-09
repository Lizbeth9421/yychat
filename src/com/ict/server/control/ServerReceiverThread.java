package com.ict.server.control;

import com.ict.db.common.MessageType;
import com.ict.db.domain.Message;

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
