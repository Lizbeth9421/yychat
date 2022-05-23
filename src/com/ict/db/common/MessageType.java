package com.ict.db.common;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/04/17/17:08
 */
@SuppressWarnings("all")
public interface MessageType {
    String LOGIN_SUCCESS = "1";

    String LOGIN_FAILURE = "2";

    String COMMON_CHAT_MESSAGE = "3";

    String REQUEST_ONLINE_FRIEND = "4";//客户端请求获得在线好友的名字

    String RESPONSE_ONLINE_FRIEND = "5";//服务器返回在线好友的名字

    String NEW_ONLINE_TO_ALL_FRIEND = "6";//客户端发送给服务器的消息类型

    String NEW_ONLINE_FRIEND = "7";//服务器发送给客户端的消息类型

    String USER_REGISTER_SUCCESS = "8";

    String USER_REGISTER_FAILURE = "9";

    String ADD_NEW_FRIEND = "10";//客户端发送到服务器的请求添加新好友消息

    String ADD_NEW_FRIEND_FAILURE_NO_USER = "11";//新增好友失败（用户不存在）

    String ADD_NEW_FRIEND_FAILURE_ALREADY_FRIEND = "12";//新增好友失败（用户已经是好友了）

    String ADD_NEW_FRIEND_SUCCESS = "13";//新增好友成功

    String USER_EXIT_SERVER_THREAD_CLOSE = "14";

    String USER_EXIT_CLIENT_THREAD_CLOSE = "15";
}
