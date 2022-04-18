package com.ict.server.control;

import cn.hutool.core.util.ObjectUtil;
import com.ict.db.common.MessageType;
import com.ict.db.domain.Message;
import com.ict.view.FriendChat;
import com.ict.view.FriendList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/04/18/11:35
 */
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
                if (MessageType.COMMON_CHAT_MESSAGE.equals(message.getMessageType())) {
                    String receiver=message.getReceiver();
                    String sender=message.getSender();
                    //从 HashMap 中拿到 FriendChat 对象，并显示聊天信息 TOdo
                    FriendChat fc=FriendList.friendChatHashMap.get(receiver+"to"+sender);
                    if (ObjectUtil.isNotNull(fc)){
                        fc.append(message);
                    }else {
                        System.out.println("请打开"+receiver+"to"+sender+"的聊天界面");
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}