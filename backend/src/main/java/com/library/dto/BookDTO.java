package com.library.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
public class BookDTO {
    private Long id;
    
    @NotBlank(message = "ISBN is required")
    private String isbn;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Author is required")
    private String author;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    private String publisher;
    private Integer publicationYear;
    private String language;
    
    @NotNull(message = "Total copies is required")
    @Min(value = 0, message = "Copies cannot be negative")
    private Integer totalCopies;
    
    private Integer availableCopies;
    private String shelfNumber;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
