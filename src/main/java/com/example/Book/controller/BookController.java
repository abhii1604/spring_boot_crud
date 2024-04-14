package com.example.Book.controller;

import com.example.Book.Entity.Book;
import com.example.Book.Exception.FooController;
import com.example.Book.Service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/allbooks")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) throws Exception {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent())
        {
            return ResponseEntity.ok().body(book.get());
        }
        else{
            String errorMessage = "Employee with ID " + id + " not found";
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Book> createBook(@RequestBody @Valid Book book) {
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.ok().body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) throws Exception {

            Optional<Book> optionalBook = bookService.getBookById(id);
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                book.setTitle(bookDetails.getTitle());
                book.setAuthor(bookDetails.getAuthor());
                book.setBookCategory(bookDetails.getBookCategory());
                Book updatedBook = bookService.saveBook(book);
                return ResponseEntity.ok().body(updatedBook);
            }

        return ResponseEntity.ok().build();
    }
//    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) throws Exception {
//        Optional<Book> book = bookService.getBookById(id);
//        if (!book.isEmpty()) {
//            return ResponseEntity.ok().build();
//        }
//
//
//        book.get().setTitle(bookDetails.getTitle());
//        book.get().setAuthor(bookDetails.getAuthor());
//        Book updatedBook = bookService.saveBook(book.get());
//        return ResponseEntity.ok().body(updatedBook);
//
//
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) throws Exception {
        Optional<Book> book = bookService.getBookById(id);

        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
