package com.huihe.book.controller;

import com.huihe.book.model.Book;
import com.huihe.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/manager")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("{id}")
    public Book getById(@PathVariable Integer id){
        return bookService.selectById(id);
    }
    @GetMapping
    public List<Book> getAll(){
        return bookService.selectAll();
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable Integer id){
        return bookService.deleteById(id)>0;
    }

    @PostMapping
    public void updateById(Book book, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        bookService.updateById(book);
        request.getRequestDispatcher("/").forward(request, response);
    }

    @PutMapping("/add")
    public void addBook(Book book){
        System.out.println(book);
        bookService.addBook(book);
    }
}
