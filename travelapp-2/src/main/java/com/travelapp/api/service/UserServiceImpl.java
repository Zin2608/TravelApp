package com.travelapp.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travelapp.api.entity.User;
import com.travelapp.api.repo.UserRepository;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Override
    public boolean authenticateUser(String email, String password) {
        // Lấy người dùng từ cơ sở dữ liệu bằng email
        User user = userRepository.findByEmail(email);

        // Nếu không tìm thấy người dùng hoặc mật khẩu không khớp, trả về false
        if (user == null || !user.getPassword().equals(password)) {
            return false;
        }

        // Nếu mọi thứ đều khớp, trả về true
        return true;
    }
    
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}