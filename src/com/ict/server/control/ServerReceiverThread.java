package com.ict.server.control;

import com.ict.db.common.MessageType;
import com.ict.db.domain.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
                    System.out.println("接收方 "+receiver+" 的 Socket 对象："+receiverSocket);
                    if (receiverSocket != null) {//接收方在线，才能转发信息
                        ObjectOutputStream outputStream = new ObjectOutputStream(receiverSocket.getOutputStream());
                        outputStream.writeObject(message);//发送聊天信息
                    }else {
                        System.out.println(receiver+"不在线上");
                    }
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
