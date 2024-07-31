package az.edu.turing.usermanageappjpa.controller;

import az.edu.turing.usermanageappjpa.domain.entity.User;
import az.edu.turing.usermanageappjpa.model.dto.UserDto;
import az.edu.turing.usermanageappjpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            logger.info("No users found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.info("Retrieved {} users", users.size());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        logger.info("Creating user with username: {}", userDto.getUsername());
        User createdUser = userService.createUser(userDto);
        logger.info("User created with ID: {}", createdUser.getId());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        logger.info("Updating user with ID: {}", id);
        Optional<User> updatedUser = userService.updateUser(id, userDto);
        return updatedUser
                .map(user -> {
                    logger.info("User with ID {} updated successfully", id);
                    return new ResponseEntity<>(user, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    logger.warn("User with ID {} not found", id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        Optional<User> user = userService.getUserById(id);
        return user.map(u -> {
            logger.info("User with ID {} retrieved successfully", id);
            return new ResponseEntity<>(u, HttpStatus.OK);
        }).orElseGet(() -> {
            logger.warn("User with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted) {
            logger.info("User with ID {} deleted successfully", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.warn("User with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllUsers() {
        logger.info("Deleting all users");
        userService.deleteAllUsers();
        logger.info("All users deleted");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        logger.info("Fetching user count");
        long count = userService.getUserCount();
        logger.info("User count retrieved: {}", count);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PatchMapping("/{id}/photo")
    public ResponseEntity<User> updateProfilePhoto(
            @PathVariable Long id,
            @RequestBody UserDto userDto) {
        logger.info("Updating profile photo for user with ID: {}", id);
        Optional<User> updatedUser = userService.updateProfilePhoto(id, userDto);
        return updatedUser.map(user -> {
            logger.info("Profile photo updated for user with ID {}", id);
            return ResponseEntity.ok(user);
        }).orElseGet(() -> {
            logger.warn("User with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }
}
