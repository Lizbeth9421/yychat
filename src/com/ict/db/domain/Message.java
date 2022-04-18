package com.ict.db.domain;

import com.ict.db.common.MessageType;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/04/17/17:12
 */
@Data
public class Message implements Serializable, MessageType {
    private String messageType;
}
