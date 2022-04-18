package com.ict.db.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @author Lizbeth9421
 */
public class MyBatisUtil {
    static SqlSessionFactory factory=null;
    static {
        //1得到SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder factoryBuilder=new SqlSessionFactoryBuilder();
        //2得到myBatis.cfg.xml配置文件
        InputStream in= MyBatisUtil.class.getResourceAsStream("/mybatis.cfg.xml");
        //3得到factoryBuilder去构造SqlSessionFactory
        factory=factoryBuilder.build(in);
    }

    public static SqlSession getSqlSession(){
        return factory.openSession();
    }

    public static void close(SqlSession session){
        if(session!=null){
            session.commit();
            session.close();
        }
    }
}
