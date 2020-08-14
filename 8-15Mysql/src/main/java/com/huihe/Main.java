package com.huihe;

import com.huihe.mapper.BookMapper;
import com.huihe.model.Book;
import com.huihe.util.JDBCUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class Main {
    private BookMapper bookMapper;
    private InputStream inputStream;
    private SqlSession sqlSession;
    @Before
    public void before(){
        //读取配置文件
        inputStream = Main.class.getResourceAsStream("/mybatis-config.xml");
        //创建SqlSessionFactory工厂类
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(inputStream);
        //使用工厂得到sqlSession对象
        sqlSession = factory.openSession(true);
        //使用sqlSession对象创建接口代理对象
        bookMapper = sqlSession.getMapper(BookMapper.class);
    }

    @Test
    public void test(){
        //使用代理对象操作数据库
        List<Book> books = bookMapper.selectAll();
        System.out.println(books);
    }

    @After
    public void after() throws IOException {
        //释放资源
        inputStream.close();
        sqlSession.close();
    }

    public static void main(String[] args) throws Exception {
        Connection connection = JDBCUtils.getConnection();
        connection.setAutoCommit(false);
        String sql1 = "update user set money=money-? where name=?";
        String sql2 = "update user set money=money+? where name=?";
        PreparedStatement ps1 = connection.prepareStatement(sql1),
                ps2 = connection.prepareStatement(sql2);
        try {
            JDBCUtils.executeUpdate(ps1, new Object[]{100, "zs"});
            int a = 1/0;
            JDBCUtils.executeUpdate(ps2, new Object[]{100, "ls"});
            connection.commit();
        } catch (Exception e){
            System.out.println("出现异常，回滚事务");
            connection.rollback();
        } finally {
            JDBCUtils.closeResource(null, ps1, connection);
            JDBCUtils.closeResource(null, ps2, null);
        }
    }

}
