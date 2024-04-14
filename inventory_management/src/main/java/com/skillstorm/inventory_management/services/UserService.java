package com.skillstorm.inventory_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.inventory_management.models.Company;
import com.skillstorm.inventory_management.models.User;
import com.skillstorm.inventory_management.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyService companyService;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(int id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            return user.get();
        }
        return null;
    }

    public List<User> findUsersByName(String username) {
        Optional<List<User>> users = userRepository.findAllByUsername(username);

        if (users.isPresent()) {
            return users.get();
        }
        return null;
    }

    public User saveUser(User user) {

        Company companyWithId = companyService.saveCompany(user.getCompany());
        user.setCompany(companyWithId);
        return userRepository.save(user);
    }

    public int updateUsername(User user, String newName) {
        return userRepository.updateUsername(user.getUserId(), newName);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    
}
