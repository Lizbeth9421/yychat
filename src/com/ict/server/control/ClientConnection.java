package com.ict.server.control;

import com.ict.db.common.MessageType;
import com.ict.db.domain.Message;
import com.ict.db.domain.User;

import java.io.IOException;
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
    public static Socket s;

    public ClientConnection() {
        try {
            //创建Socket对象，和服务器建立连接
            s = new Socket("127.0.0.1", 9421);
            System.out.println("客户端连接成功" + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean loginValidate(User user) {
        //默认登录失败
        boolean loginSuccess = false;
        try {
            //通过Socket获取字节输出流对象
            final OutputStream outputStream = s.getOutputStream();
            //把字节输出流包装成对象输出流对象
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            //向服务端发送user对象
            objectOutputStream.writeObject(user);

            //接受服务端发送的信息，信息为message对象
            ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
            Message message = (Message) objectInputStream.readObject();
            if (message.getMessageType().equals(MessageType.LOGIN_SUCCESS)) {
                loginSuccess = true;
                //创建客户端接收线程，用来接收从服务器端发来信息
                new ClientReceiverThread(s).start();
            } else {
                //登陆密码验证失败，关闭客户端的 Socket 对象
                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginSuccess;
    }

    public Message loginValidate1(User user) {
        Message message = null;
        try {
            OutputStream os = s.getOutputStream();//通过 Socket 获得字节输出流对象
            ObjectOutputStream oos = new ObjectOutputStream(os);//把字节输出流包装成对象输出流对象
            oos.writeObject(user);//向服务器端发送 user 对象
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            message = (Message) ois.readObject();//接收服务器端发送的 Message 对象
            if (message.getMessageType().equals(MessageType.LOGIN_SUCCESS)) {
                new ClientReceiverThread(s).start();
            } else {
                s.close();//登陆密码验证失败，关闭客户端的 Socket 对象
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    public boolean registerUser(User user) {
        boolean registerSuccess = false;
        //发送User对象
        try {
            OutputStream outputStream = s.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(user);//向服务器端发送 user 对象
            ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
            //接收服务器端发送的 Message 对象
            Message message = (Message) objectInputStream.readObject();
            if (message.getMessageType().equals(MessageType.USER_REGISTER_SUCCESS)) {
                //注册成功
                registerSuccess = true;
            }
            //不管注册是否成功，都要关闭客户端的 Socket 对象
            s.close();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }
        return registerSuccess;
    }
}
