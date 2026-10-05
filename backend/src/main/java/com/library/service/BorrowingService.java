package com.library.service;

import com.library.entity.Book;
import com.library.entity.Borrowing;
import com.library.entity.Member;
import com.library.exception.BadRequestException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.BorrowingRepository;
import com.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRepository borrowingRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private MemberRepository memberRepository;

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    @Transactional
    public Borrowing issueBook(Long bookId, Long memberId, LocalDate dueDate) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
                
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new BadRequestException("Book is not available for issuing");
        }
        
        if (!"ACTIVE".equals(member.getStatus())) {
            throw new BadRequestException("Member is not active");
        }

        // Check if member already has this book issued
        List<Borrowing> existing = borrowingRepository.findByBookIdAndStatus(bookId, "ISSUED");
        for (Borrowing b : existing) {
            if (b.getMember().getId().equals(memberId)) {
                throw new BadRequestException("Member has already borrowed this book");
            }
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setMember(member);
        borrowing.setIssueDate(LocalDate.now());
        borrowing.setDueDate(dueDate);
        borrowing.setStatus("ISSUED");

        return borrowingRepository.save(borrowing);
    }

    @Transactional
    public Borrowing returnBook(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found"));

        if ("RETURNED".equals(borrowing.getStatus())) {
            throw new BadRequestException("Book is already returned");
        }

        borrowing.setReturnDate(LocalDate.now());
        
        long daysOverdue = ChronoUnit.DAYS.between(borrowing.getDueDate(), borrowing.getReturnDate());
        if (daysOverdue > 0) {
            borrowing.setStatus("OVERDUE");
            // $1 per day fine
            borrowing.setFineAmount(new BigDecimal(daysOverdue));
        } else {
            borrowing.setStatus("RETURNED");
            borrowing.setFineAmount(BigDecimal.ZERO);
        }

        Book book = borrowing.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return borrowingRepository.save(borrowing);
    }

    public List<Borrowing> getMemberBorrowings(Long memberId) {
        return borrowingRepository.findByMemberId(memberId);
    }
}
