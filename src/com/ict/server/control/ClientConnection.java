package com.ict.server.control;

import com.ict.db.common.MessageType;
import com.ict.db.domain.Message;
import com.ict.db.domain.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/04/17/16:25
 */
@SuppressWarnings("all")
public class ClientConnection {
    Socket s;

    public ClientConnection() {
        try {
            //创建Socket对象，和服务器建立连接
            s=new Socket("127.0.0.1",9421);
            System.out.println("客户端连接成功"+s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean loginValidate(User user){
        //默认登录失败
        boolean loginSuccess=false;
        try {
            //通过Socket获取字节输出流对象
            final OutputStream outputStream = s.getOutputStream();
            //把字节输出流包装成对象输出流对象
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            //向服务端发送user对象
            objectOutputStream.writeObject(user);

            //接受服务端发送的信息，信息为message对象
            ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
            Message message=(Message)objectInputStream.readObject();
            if (message.getMessageType().equals(MessageType.LOGIN_SUCCESS)){
                loginSuccess=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return loginSuccess;
    }
}
