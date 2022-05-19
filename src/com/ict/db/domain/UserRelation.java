package com.ict.db.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Lizbeth9421
 * @Date: 2022/05/19/13:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRelation implements Serializable {
    private Integer id;

    private String masterUser;

    private String slaveUser;

    private Integer relation;

    private static final long serialVersionUID = 1L;
}