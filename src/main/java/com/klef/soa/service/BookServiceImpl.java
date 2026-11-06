package com.klef.soa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.soa.entity.Book;
import com.klef.soa.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService
{

    private final BookRepository bookRepository;

    BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book insertBook(Book book)
    {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long bookId, Book book)
    {
        Book existingBook = bookRepository.findById(bookId).orElse(null);

        if (existingBook != null)
        {
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setPublisher(book.getPublisher());
            existingBook.setCategory(book.getCategory());
            existingBook.setPrice(book.getPrice());

            return bookRepository.save(existingBook);
        }

        return null;
    }

    @Override
    public String deleteBook(Long bookId)
    {
        if (bookRepository.existsById(bookId))
        {
            bookRepository.deleteById(bookId);
            return "Book Deleted Successfully";
        }

        return "Book Not Found";
    }

    @Override
    public List<Book> displayAllBooks()
    {
        return bookRepository.findAll();
    }

    @Override
    public Book displayBookById(Long bookId)
    {
        return bookRepository.findById(bookId).orElse(null);
    }

    @Override
    public List<Book> findByAuthor(String author)
    {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> findByCategory(String category)
    {
        return bookRepository.findByCategory(category);
    }
}