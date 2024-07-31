package az.edu.turing.usermanageappjpa.service;

import az.edu.turing.usermanageappjpa.domain.entity.User;
import az.edu.turing.usermanageappjpa.domain.repository.UserRepository;
import az.edu.turing.usermanageappjpa.model.dto.UserDto;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new ValidationException("Username already exists");
        }
        if (userDto.getAge() < 18) {
            throw new ValidationException("Age must be at least 18");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setAge(userDto.getAge());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setPhotoURL(userDto.getPhotoURL());
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, UserDto userDto) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDto.getUsername());
            user.setAge(userDto.getAge());
            user.setPhotoURL(userDto.getPhotoURL());
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        });
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public long getUserCount() {
        return userRepository.count();
    }

    public Optional<User> updateProfilePhoto(Long id, UserDto userDto) {
        return userRepository.findById(id).map(user -> {
            user.setPhotoURL(userDto.getPhotoURL());
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        });
    }

}
