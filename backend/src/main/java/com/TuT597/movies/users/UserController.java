package com.TuT597.movies.users;

import com.TuT597.movies.exceptions.BadInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto userRegistrationDto, UriComponentsBuilder ucb) {
        var username = getValidValueOrThrow(userRegistrationDto.username(), "username");
        var password = getValidValueOrThrow(userRegistrationDto.password(), "password");
        var possibleUser = userService.findByUsername(username);
        if (possibleUser.isPresent()) throw new BadInputException("username already exists");

        var newUser = userService.save(username, password);
        URI locationOfNewUser = ucb
                .path("users/{username}")
                .buildAndExpand(newUser.getUsername())
                .toUri();
        return ResponseEntity.created((locationOfNewUser)).body(new UserRegistrationResultDto(newUser.getUsername()));
    }

    private String getValidValueOrThrow(String rawString, String fieldname) {
        if (rawString == null) throw new BadInputException(fieldname + " is missing");
        var result = rawString.trim();
        if (result.isEmpty()) throw new BadInputException(fieldname + " should not be blank");
        return result;
    }

    @GetMapping("{username}")
    public ResponseEntity<UserRegistrationResultDto> getUser(@PathVariable String username) {
        var possiblyFoundUser = userService.findByUsername(username);
        return possiblyFoundUser.map(user -> ResponseEntity.ok(new UserRegistrationResultDto(user.getUsername())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
