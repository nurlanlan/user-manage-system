package az.edu.turing.usermanageappjpa.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private Integer age;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String photoURL;

}
