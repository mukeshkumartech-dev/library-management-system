package com.library.service;

import com.library.entity.Book;
import com.library.exception.BadRequestException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public Book createBook(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BadRequestException("Book with ISBN " + book.getIsbn() + " already exists");
        }
        if (book.getTotalCopies() < 0) {
            throw new BadRequestException("Total copies cannot be negative");
        }
        book.setAvailableCopies(book.getTotalCopies());
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        
        if (!book.getIsbn().equals(bookDetails.getIsbn()) && bookRepository.existsByIsbn(bookDetails.getIsbn())) {
            throw new BadRequestException("Book with ISBN " + bookDetails.getIsbn() + " already exists");
        }

        int borrowedCopies = book.getTotalCopies() - book.getAvailableCopies();
        if (bookDetails.getTotalCopies() < borrowedCopies) {
            throw new BadRequestException("Total copies cannot be less than currently borrowed copies (" + borrowedCopies + ")");
        }

        book.setIsbn(bookDetails.getIsbn());
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setCategory(bookDetails.getCategory());
        book.setPublisher(bookDetails.getPublisher());
        book.setPublicationYear(bookDetails.getPublicationYear());
        book.setLanguage(bookDetails.getLanguage());
        
        book.setAvailableCopies(bookDetails.getTotalCopies() - borrowedCopies);
        book.setTotalCopies(bookDetails.getTotalCopies());
        
        book.setShelfNumber(bookDetails.getShelfNumber());
        book.setDescription(bookDetails.getDescription());

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Book book = getBookById(id);
        if (book.getAvailableCopies() < book.getTotalCopies()) {
            throw new BadRequestException("Cannot delete book. Some copies are currently issued.");
        }
        bookRepository.delete(book);
    }

    public List<Book> searchBooks(String query) {
        return bookRepository.searchBooks(query);
    }
}
