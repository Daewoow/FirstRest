package org.shaurmeow.firstrest.services;

import jakarta.transaction.Transactional;
import org.shaurmeow.firstrest.repository.User;
import org.shaurmeow.firstrest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        Optional<User> opUser = userRepository.findByEmail(user.getEmail());
        if (opUser.isPresent()) {
            throw new IllegalStateException("User already exists");
        }
        user.setAge(Period.between(user.getBirthday(), LocalDate.now()).getYears());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.findById(id).ifPresentOrElse(user -> userRepository.deleteById(id), () -> {
            throw new IllegalStateException("User does not exist");
        });
    }

    @Transactional // для транзакций
    public void update(Long id, String email, String name) {
        Optional<User> opUser = userRepository.findById(id);
        if (opUser.isEmpty()) {
            throw new IllegalStateException("User for update is empty");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("Email for update is already used");
        }
        User neededUser = opUser.get();
        neededUser.setEmail(email);
        neededUser.setName(name);
        // userRepository.save(neededUser); так как есть @Transactional, сохранения сохранятся сами
    }
}
