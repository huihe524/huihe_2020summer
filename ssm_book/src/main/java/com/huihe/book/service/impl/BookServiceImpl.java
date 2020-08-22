package com.huihe.book.service.impl;



import com.huihe.book.mapper.BookMapper;
import com.huihe.book.model.Book;
import com.huihe.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> selectAll() {
        return bookMapper.selectAll();
    }

    @Override
    public Book selectById(int id) {
        return bookMapper.selectById(id);
    }

    @Override
    public int deleteById(int id) {
        return bookMapper.deleteById(id);
    }

    @Override
    @Transactional
    public int addBook(Book book) {
//               bookMapper.addBook(book);
//               int a = 1/0;
        return bookMapper.addBook(book);
    }

    @Override
    public int updateById(Book book) {
        return bookMapper.updateById(book);
    }

}
