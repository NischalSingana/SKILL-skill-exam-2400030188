package com.klef.soa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.klef.soa.entity.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest 
{
    @Autowired
    private BookRepository repository;

    private Long bookId;

    @BeforeEach
    void setUp() 
    {
        repository.deleteAll();

        Book book = new Book();
        book.setTitle("SOA");
        book.setAuthor("Nischal");
        book.setPublisher("KLU");
        book.setCategory("Technology");
        book.setPrice(500.0);
        Book savedBook = repository.save(book);
        bookId = savedBook.getBookId();
    }

    @Test
    void testFindAllBooks() 
    {
        List<Book> books = repository.findAll();
        assertNotNull(books);
        assertFalse(books.isEmpty());
        System.out.println("Total Books: " + books.size());
    }

    @Test
    void testFindBookById() 
    {
        Optional<Book> book = repository.findById(bookId);
        assertTrue(book.isPresent());
        assertEquals(bookId, book.get().getBookId());
        System.out.println("Book ID: " + book.get().getBookId());
        System.out.println("Title: " + book.get().getTitle());
    }

    @Test
    void testFindBookByAuthor() 
    {
        List<Book> books = repository.findByAuthor("Nischal");
        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertEquals("Nischal", books.get(0).getAuthor());
        System.out.println("Author: " + books.get(0).getAuthor());
        System.out.println("Title: " + books.get(0).getTitle());
    }

    @Test
    void testFindBookByCategory() 
    {
        List<Book> books = repository.findByCategory("Technology");
        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertEquals("Technology", books.get(0).getCategory());
        System.out.println("Category: " + books.get(0).getCategory());
        System.out.println("Title: " + books.get(0).getTitle());
    }

    @Test
    void testDatabaseConnection() 
    {
        long count = repository.count();
        assertTrue(count >= 0);
        System.out.println("Total Books in Database: " + count);
    }

    @Test
    void testBookDetails() 
    {
        Optional<Book> book = repository.findById(bookId);
        assertTrue(book.isPresent());
        Book foundBook = book.get();
        assertNotNull(foundBook.getTitle());
        assertNotNull(foundBook.getAuthor());
        assertNotNull(foundBook.getPublisher());
        assertNotNull(foundBook.getCategory());
        assertTrue(foundBook.getPrice() > 0);
        System.out.println("Title: " + foundBook.getTitle());
        System.out.println("Author: " + foundBook.getAuthor());
        System.out.println("Publisher: " + foundBook.getPublisher());
        System.out.println("Category: " + foundBook.getCategory());
        System.out.println("Price: " + foundBook.getPrice());
    }

    @Test
    void testTotalBookCount() 
    {
        long count = repository.count();
        assertEquals(1L, count);
        System.out.println("Total Books: " + count);
    }
}
