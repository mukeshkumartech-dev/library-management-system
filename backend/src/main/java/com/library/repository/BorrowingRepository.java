package com.library.repository;

import com.library.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    List<Borrowing> findByMemberId(Long memberId);
    List<Borrowing> findByStatus(String status);
    List<Borrowing> findByBookIdAndStatus(Long bookId, String status);
    long countByStatus(String status);
}
