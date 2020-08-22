package com.huihe.book;

import com.huihe.book.service.BookMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = Main.class.getResourceAsStream("/mybatis-config.xml");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(inputStream);
        SqlSession session = factory.openSession();


//        Book book = new Book(3, "流浪地球", "刘慈欣",
//                "科幻", "电影版挺好");
        BookMapper mapper = session.getMapper(BookMapper.class);
//        System.out.println(mapper.insert(book));

//        System.out.println(mapper.updateById(book));
        System.out.println(mapper.deleteById(1));
        session.commit();


        inputStream.close();
        session.close();
    }
}
