package com.huihe.book.mapper;


import org.apache.ibatis.annotations.*;

import com.huihe.book.model.Book;

import java.util.List;

public interface BookMapper {

    List<Book> selectAll(); //查询全部

    Book selectById(@Param("id")int id); //用编号查询

    int deleteById(@Param("id")int id); //通过id删除

    int addBook(Book book); //添加图书

    int updateById(Book book); //通过id更新
}
