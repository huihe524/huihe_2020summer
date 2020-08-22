package com.huihe.book.service;

import com.huihe.book.model.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper {

    @Select("select * from book")
    List<Book> selectAll();

    @Select("select * from book where bid=#{id}")
    Book selectById(int id);

    @Delete("delete from book where bid=#{id}")
    int deleteById(@Param("id")int id); //通过id删除

    @Insert("insert into book values (#{bid}, #{bname}, #{author}, #{category}, #{description});")
    int addBook(Book book); //添加图书

    @Update("update book set bname=#{bname}, author=#{author},\n" +
            "         category= #{category}, description = #{description}\n" +
            "         where bid=#{bid}")
    int updateById(Book book); //通过id更新
}
