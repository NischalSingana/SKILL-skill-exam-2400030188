package com.klef.soa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.klef.soa.entity.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    private Long existingBookId;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        Book book1 = new Book();
        book1.setTitle("Spring in Action");
        book1.setAuthor("Craig Walls");
        book1.setPublisher("Manning");
        book1.setCategory("Technology");
        book1.setPrice(49.99);
        Book savedBook1 = repository.save(book1);
        existingBookId = savedBook1.getBookId();

        Book book2 = new Book();
        book2.setTitle("Effective Java");
        book2.setAuthor("Joshua Bloch");
        book2.setPublisher("Addison-Wesley");
        book2.setCategory("Programming");
        book2.setPrice(45.00);
        repository.save(book2);
    }

    // Test 1 – Find All: Call findAll(). Verify that the returned list is not null and validate the expected records where test data is available.
    @Test
    @DisplayName("Test 1 - Find All Books")
    void testFindAllBooks() {
        List<Book> books = repository.findAll();
        assertNotNull(books, "The returned book list should not be null");
        assertFalse(books.isEmpty(), "The book list should not be empty");
        assertEquals(2, books.size(), "Expected 2 books in the repository");
        System.out.println("Total Books: " + books.size());
    }

    // Test 2 – Find by ID: Call findById() with an existing ID and verify that the record is present and its ID matches. Also test a non-existing ID and verify that the result is empty.
    @Test
    @DisplayName("Test 2 - Find Book by ID (Existing and Non-Existing)")
    void testFindBookById() {
        // Existing ID
        Optional<Book> book = repository.findById(existingBookId);
        assertTrue(book.isPresent(), "Book with existing ID should be present");
        assertEquals(existingBookId, book.get().getBookId(), "Book ID should match");
        System.out.println("Found Book ID: " + book.get().getBookId() + ", Title: " + book.get().getTitle());

        // Non-existing ID
        Optional<Book> nonExistingBook = repository.findById(9999L);
        assertFalse(nonExistingBook.isPresent(), "Non-existing book should not be present");
        System.out.println("Non-existing Book isPresent: " + nonExistingBook.isPresent());
    }

    // Test 3 – Find by Derived Query: Test the derived query method(s) assigned to your entity, such as findByName(), findByEmail() or the exact method(s) specified for your entity. Verify the matching result and property value.
    @Test
    @DisplayName("Test 3 - Find by Derived Query (findByAuthor)")
    void testFindByDerivedQuery() {
        List<Book> books = repository.findByAuthor("Craig Walls");
        assertNotNull(books, "Result list should not be null");
        assertFalse(books.isEmpty(), "Result list should not be empty");
        assertEquals("Craig Walls", books.get(0).getAuthor(), "Author should match query");
        assertEquals("Spring in Action", books.get(0).getTitle(), "Title should match");
        System.out.println("Book found by author: " + books.get(0).getTitle() + " by " + books.get(0).getAuthor());
    }

    // Test 4 – Count: Call count() and verify the record count. If you assert an exact count, prepare the test data so that the expected value is deterministic.
    @Test
    @DisplayName("Test 4 - Count Total Books")
    void testCountBooks() {
        long count = repository.count();
        assertEquals(2L, count, "Record count should match expected prepared test data count");
        assertTrue(count > 0, "Record count should be greater than zero");
        System.out.println("Total Books in Database: " + count);
    }

    // Test 5 – Entity Details / Property-Based Result: Retrieve a record and verify that its required properties are not null. If your entity has an enum or category-like property, test the corresponding repository method and verify the matching results.
    @Test
    @DisplayName("Test 5 - Entity Details and Category-based Query Result")
    void testEntityDetailsAndCategoryResult() {
        // Verify required properties are not null
        Optional<Book> bookOpt = repository.findById(existingBookId);
        assertTrue(bookOpt.isPresent(), "Book should be found");
        Book foundBook = bookOpt.get();

        assertNotNull(foundBook.getBookId(), "Book ID should not be null");
        assertNotNull(foundBook.getTitle(), "Title should not be null");
        assertNotNull(foundBook.getAuthor(), "Author should not be null");
        assertNotNull(foundBook.getPublisher(), "Publisher should not be null");
        assertNotNull(foundBook.getCategory(), "Category should not be null");
        assertTrue(foundBook.getPrice() > 0, "Price should be greater than zero");
        System.out.println("Verified Book Details: " + foundBook.getTitle() + " | Category: " + foundBook.getCategory());

        // Verify category-like property query
        List<Book> categoryBooks = repository.findByCategory("Technology");
        assertNotNull(categoryBooks, "Category query result should not be null");
        assertFalse(categoryBooks.isEmpty(), "Category query result should not be empty");
        assertEquals("Technology", categoryBooks.get(0).getCategory(), "Category should match 'Technology'");
        System.out.println("Total Technology Books: " + categoryBooks.size());
    }
}

