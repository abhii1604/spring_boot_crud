package com.example.Book.Service;

import com.example.Book.Entity.Book;
import com.example.Book.Exception.FooController;
import com.example.Book.JPA.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public Optional<Book> getBookById(Long id) throws Exception {
            Optional<Book> book = bookRepository.findById(id);
            return book;
    }
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }


}
