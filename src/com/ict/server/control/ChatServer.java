package com.ict.server.control;

import com.ict.db.common.MessageType;
import com.ict.db.domain.Message;
import com.ict.db.domain.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/04/17/16:18
 */
public class ChatServer {
    ServerSocket socket;
    Socket s;
    public ChatServer() {
        try {
            socket=new ServerSocket(9421);
            System.out.println("服务器启动成功，正在监听9421端口");
            //服务器要不断等待客户端建立连接，否则只能连接一个客户端
            while (true){
                s=socket.accept();
                System.out.println("客户端连接成功"+s);
                //接受user对象，并在控制台输出
                final User user = (User) new ObjectInputStream(s.getInputStream()).readObject();
                String username = user.getUsername();
                String password = user.getPassword();
                System.out.println("服务端收到客户端登录信息 username："+username+" password="+password);
                Message message = new Message();
                if ("123456".equals(password)){
                    System.out.println("密码验证通过");
                    message.setMessageType(MessageType.LOGIN_SUCCESS);
                }else {
                    System.out.println("密码验证失败");
                    message.setMessageType(MessageType.LOGIN_FAILURE);
                }
                //将message对象发送到客户端
                new ObjectOutputStream(s.getOutputStream()).writeObject(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
