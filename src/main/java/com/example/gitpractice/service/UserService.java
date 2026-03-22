package com.example.gitpractice.service;

import com.example.gitpractice.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public UserService() {
        // 初始化一些测试数据
        users.add(new User(idCounter.getAndIncrement(), "张三", "zhangsan@example.com"));
        users.add(new User(idCounter.getAndIncrement(), "李四", "lisi@example.com"));
        users.add(new User(idCounter.getAndIncrement(), "王五", "wangwu@example.com"));
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public User create(User user) {
        user.setId(idCounter.getAndIncrement());
        users.add(user);
        return user;
    }

    public Optional<User> update(Long id, User updatedUser) {
        return findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            return user;
        });
    }

    public boolean delete(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }

    // 按名字搜索用户
    public List<User> searchByName(String name) {
        return users.stream()
                .filter(user -> user.getName().contains(name))
                .collect(java.util.stream.Collectors.toList());
    }
}
