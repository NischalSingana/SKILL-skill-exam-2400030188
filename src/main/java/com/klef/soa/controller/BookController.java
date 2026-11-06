package com.klef.soa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klef.soa.entity.Book;
import com.klef.soa.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String home() {
        return "Book Entity Project";
    }

    @PostMapping("/insert")
    public Book insertBook(@RequestBody Book book) {
        return bookService.insertBook(book);
    }

    @PutMapping("/update/{id}")
    public Book updateBook(@PathVariable("id") Long bookId,
                           @RequestBody Book book) {
        return bookService.updateBook(bookId, book);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long bookId) {
        return bookService.deleteBook(bookId);
    }

    @GetMapping("/all")
    public List<Book> displayAllBooks() {
        return bookService.displayAllBooks();
    }

    @GetMapping("/{id}")
    public Book displayBookById(@PathVariable("id") Long bookId) {
        return bookService.displayBookById(bookId);
    }

    @GetMapping("/author/{author}")
    public List<Book> findByAuthor(@PathVariable String author) {
        return bookService.findByAuthor(author);
    }

    @GetMapping("/category/{category}")
    public List<Book> findByCategory(@PathVariable String category) {
        return bookService.findByCategory(category);
    }
}
