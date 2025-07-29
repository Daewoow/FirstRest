package org.shaurmeow.firstrest.controllers;

import org.shaurmeow.firstrest.repository.User;
import org.shaurmeow.firstrest.repository.UserRepository;
import org.shaurmeow.firstrest.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    // @Autowired
    private final UserService userService;
    private final UserRepository userRepository;

    //    public UserController() {
//        this.userService = new UserService();
//    }
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService; // Spring сам инициализирует через DI
        this.userRepository = userRepository;
    }

    @GetMapping("all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.create(user);
    }

    @DeleteMapping("delete/{id}")
    public void deleteUser(@PathVariable(name = "id") Long id) {
        userService.delete(id);
    }

    @PutMapping("update/{id}")
    public void updateUser(
            @PathVariable(name = "id") Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name) {
        userService.update(id, email, name);
    }
}
