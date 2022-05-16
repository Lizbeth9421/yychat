package com.ict.server.control;

import cn.hutool.core.util.ObjectUtil;
import com.ict.db.common.MessageType;
import com.ict.db.common.UserType;
import com.ict.db.domain.Message;
import com.ict.db.domain.User;
import com.ict.db.mapper.UserMapper;
import com.ict.db.util.DbUtils;
import com.ict.db.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/04/17/16:18
 */
@SuppressWarnings("all")
public class ChatServer {
    public static HashMap<String, Socket> hmSocket = new HashMap<>();
    ServerSocket socket;
    Socket s;

    public ChatServer() {
        try {
            socket = new ServerSocket(9421);
            System.out.println("服务器启动成功，正在监听9421端口");
            //服务器要不断等待客户端建立连接，否则只能连接一个客户端
            while (true) {
                //程序阻塞，等待客户端连接
                s = socket.accept();
                System.out.println("客户端连接成功" + s);
                //接受user对象，并在控制台输出
                final User user = (User) new ObjectInputStream(s.getInputStream()).readObject();
                String username = user.getUsername();
                String password = user.getPassword();
                System.out.println("服务端收到客户端登录信息 username：" + username + " password=" + password);
                //创建对象输出流
                ObjectOutputStream outputStream = new ObjectOutputStream(s.getOutputStream());
                Message message = new Message();
                if (user.getUserType().equals(UserType.USER_REGISTER)) {//用户注册
                    //调用 seek()方法，在 user 表中查询是否有同名用户
                    if (DbUtils.seekUser(username)) {//没有同名用户，可以注册
                        DbUtils.insertUser(user); //在 user 表中注册新用户
                        message.setMessageType(MessageType.USER_REGISTER_SUCCESS);
                    } else {
                        message.setMessageType(MessageType.USER_REGISTER_FAILURE);
                    }
                    outputStream.writeObject(message);//发送 mess 对象到客户端
                    s.close();//注册完后，关闭 Socket 对象
                }
                if (user.getUserType().equals(UserType.USER_LOGIN_VALIDATE)) {//用户登陆验证
                    if (isLoginSuccess(username, password)) {
                        System.out.println("密码验证通过");
                        message.setMessageType(MessageType.LOGIN_SUCCESS);
                        //发送message对象到客户端
                        outputStream.writeObject(message);
                        //保存登陆成功的新用户名和 Socket对象类
                        hmSocket.put(username, s);
                        //由于可能有多个客户同时向服务器发送信息,需要为每一个用户创建接收线程
                        new ServerReceiverThread(s).start();
                        System.out.println("启动线程成功");
                    } else {
                        System.out.println("密码验证失败");
                        message.setMessageType(MessageType.LOGIN_FAILURE);
                        //发送message对象到客户端
                        outputStream.writeObject(message);
                        //密码验证失败，关闭服务端Socket对象
                        s.close();
                    }
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断用户是否登录成功，既验证用户名密码是否正确
     *
     * @param username
     * @param password
     * @return
     */
    public boolean isLoginSuccess(String username, String password) {
        return DbUtils.login(username, password);
    }

}
