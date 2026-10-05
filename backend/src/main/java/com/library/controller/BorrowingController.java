package com.library.controller;

import com.library.dto.BorrowingDTO;
import com.library.dto.IssueBookRequest;
import com.library.entity.Borrowing;
import com.library.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public List<BorrowingDTO> getAllBorrowings() {
        return borrowingService.getAllBorrowings().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @PostMapping("/issue")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<BorrowingDTO> issueBook(@Valid @RequestBody IssueBookRequest request) {
        Borrowing borrowing = borrowingService.issueBook(request.getBookId(), request.getMemberId(), request.getDueDate());
        return ResponseEntity.ok(convertToDTO(borrowing));
    }

    @PutMapping("/{id}/return")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<BorrowingDTO> returnBook(@PathVariable Long id) {
        Borrowing borrowing = borrowingService.returnBook(id);
        return ResponseEntity.ok(convertToDTO(borrowing));
    }

    @GetMapping("/member/{memberId}")
    public List<BorrowingDTO> getMemberBorrowings(@PathVariable Long memberId) {
        return borrowingService.getMemberBorrowings(memberId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    private BorrowingDTO convertToDTO(Borrowing b) {
        BorrowingDTO dto = new BorrowingDTO();
        dto.setId(b.getId());
        dto.setBookId(b.getBook().getId());
        dto.setBookTitle(b.getBook().getTitle());
        dto.setBookIsbn(b.getBook().getIsbn());
        dto.setMemberId(b.getMember().getId());
        dto.setMemberName(b.getMember().getName());
        dto.setMemberNumber(b.getMember().getMembershipNumber());
        dto.setIssueDate(b.getIssueDate());
        dto.setDueDate(b.getDueDate());
        dto.setReturnDate(b.getReturnDate());
        dto.setStatus(b.getStatus());
        dto.setFineAmount(b.getFineAmount());
        return dto;
    }
}
