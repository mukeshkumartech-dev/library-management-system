package com.library.service;

import com.library.repository.BookRepository;
import com.library.repository.BorrowingRepository;
import com.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private BorrowingRepository borrowingRepository;

    public Map<String, Object> getDashboardStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalBooks = bookRepository.count();
        long totalMembers = memberRepository.count();
        long issuedBooks = borrowingRepository.countByStatus("ISSUED");
        long overdueBooks = borrowingRepository.countByStatus("OVERDUE");
        
        stats.put("totalBooks", totalBooks);
        stats.put("totalMembers", totalMembers);
        stats.put("issuedBooks", issuedBooks);
        stats.put("overdueBooks", overdueBooks);
        
        return stats;
    }
}
