package com.skillstorm.inventory_management.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skillstorm.inventory_management.models.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    public Optional<List<User>> findAllByUsername(String name);

    @Query("update User u set u.username = :new_username where userId = :user_id")
    @Modifying
    @Transactional
    public int updateUsername(@Param("user_id") int userId, @Param("new_username") String newName);

}
