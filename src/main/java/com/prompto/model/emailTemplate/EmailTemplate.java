package com.prompto.model.emailTemplate;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class EmailTemplate {
    @Id
    // Using IDENTITY strategy for auto-incrementing primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String subject;

    // Using @Lob to store large text data
    @Lob
    private String bodyHtml;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
