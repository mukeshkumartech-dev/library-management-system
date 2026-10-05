package com.library.config;

import com.library.entity.Book;
import com.library.entity.Member;
import com.library.entity.User;
import com.library.repository.BookRepository;
import com.library.repository.MemberRepository;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
            
            User librarian = new User();
            librarian.setUsername("librarian");
            librarian.setPassword(passwordEncoder.encode("lib123"));
            librarian.setRole("LIBRARIAN");
            userRepository.save(librarian);
        }
        
        if (bookRepository.count() == 0) {
            Book book1 = new Book();
            book1.setIsbn("978-0134685991");
            book1.setTitle("Effective Java");
            book1.setAuthor("Joshua Bloch");
            book1.setCategory("Programming");
            book1.setPublisher("Addison-Wesley");
            book1.setPublicationYear(2017);
            book1.setLanguage("English");
            book1.setTotalCopies(5);
            book1.setAvailableCopies(5);
            book1.setShelfNumber("A1");
            book1.setDescription("A must-read for Java developers.");
            bookRepository.save(book1);
            
            Book book2 = new Book();
            book2.setIsbn("978-0596009205");
            book2.setTitle("Head First Java");
            book2.setAuthor("Kathy Sierra");
            book2.setCategory("Programming");
            book2.setPublisher("O'Reilly Media");
            book2.setPublicationYear(2005);
            book2.setLanguage("English");
            book2.setTotalCopies(3);
            book2.setAvailableCopies(3);
            book2.setShelfNumber("A2");
            book2.setDescription("A brain-friendly guide.");
            bookRepository.save(book2);
        }

        if (memberRepository.count() == 0) {
            Member m1 = new Member();
            m1.setMembershipNumber("MEM001");
            m1.setName("John Doe");
            m1.setEmail("john.doe@example.com");
            m1.setPhone("1234567890");
            m1.setAddress("123 Main St, City");
            m1.setDateOfBirth(LocalDate.of(1990, 1, 1));
            memberRepository.save(m1);
        }
    }
}
