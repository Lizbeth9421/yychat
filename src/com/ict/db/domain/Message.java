package com.ict.db.domain;

import java.io.Serializable;
import java.util.Date;

import com.ict.db.common.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/05/23/10:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable, MessageType {
    private Integer id;

    /**
     * 发送者
     */
    private String sender;

    /**
     * 接收者
     */
    private String receiver;

    /**
     * 内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date sendTime;

    private String messageType;

    private static final long serialVersionUID = 1L;
}