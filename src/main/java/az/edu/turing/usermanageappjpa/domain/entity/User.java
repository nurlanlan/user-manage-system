package az.edu.turing.usermanageappjpa.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Column(unique = true, nullable = false)
    private String username;

    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(name = "photo_url", columnDefinition = "TEXT")
    private String photoURL;
}

