package com.klef.soa.service;

import java.util.List;

import com.klef.soa.entity.Book;

public interface BookService
{
    Book insertBook(Book book);
    Book updateBook(Long bookId, Book book);
    String deleteBook(Long bookId);
    List<Book> displayAllBooks();
    Book displayBookById(Long bookId);
    List<Book> findByAuthor(String author);
    List<Book> findByCategory(String category);
}