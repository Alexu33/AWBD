package com.AWBD_Istrate_Moraru.demo.repository;

import com.AWBD_Istrate_Moraru.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
