package com.app.ecom;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private List<User> userList = new ArrayList<>();

    public List<User> fetchAllUsers() {
        return userList;
    }

    public List<User> addUsers( User user) {
        userList.add(user);
        return userList;
    }

    public Optional<User> fetchUser(Long id) {
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public User updateUser(Long id, User user) {
        Optional<User> existingUser = userList.stream()
                .filter(user1 -> user1.getId().equals(id))
                .findFirst();
        if(existingUser.isPresent()) {
            User user1  = existingUser.get();
            user1.setFirstName(user.getFirstName());
            user1.setLastName(user.getLastName());
            return user1;
        }
        return null;
    }
}
