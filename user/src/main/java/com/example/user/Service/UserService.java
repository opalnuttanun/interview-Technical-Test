package com.example.user.Service;

import com.example.user.Model.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(4);

    public UserService() {
        users.put(1L, new User(
            1L, "Leanne Graham", "Bret", "Sincere@april.biz",
            "1-770-736-8031 x56442", "hildegard.org"
        ));
        users.put(2L, new User(
            2L, "Ervin Howell", "Antonette", "Shanna@melissa.tv",
            "010-692-6593 x09125", "anastasia.net"
        ));
        users.put(3L, new User(
            3L, "Clementine Bauch", "Samantha", "Nathan@yesenia.net",
            "1-463-123-4447", "ramiro.info"
        ));
    }

   public List<User> findAll() {
    return users.values().stream()
        .sorted(Comparator.comparing(User::getId))
        .toList();
    }

    public User findById(Long id) {
        User user = users.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    public User createUser (UserRequest request) {
        Long id = nextId.getAndIncrement();
        User user = toUser(id, request);
        users.put(id, user);
        return user;
    }

    public User update(Long id, UserRequest request) {
        User updated = toUser(id, request);
        if (users.replace(id, updated) == null) {
            throw new UserNotFoundException(id);
        }
        return updated;
    }

    public void delete(Long id) {
        if (users.remove(id) == null) {
            throw new UserNotFoundException(id);
        }
    }

    private User toUser(Long id, UserRequest request) {
        return new User(
            id,
            request.getName(),
            request.getUsername(),
            request.getEmail(),
            request.getPhone(),
            request.getWebsite()
        );
    }

    public static class UserNotFoundException extends RuntimeException {
        UserNotFoundException(Long id) {
            super("User " + id + " not found");
        }
    }
}