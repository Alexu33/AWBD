package com.AWBD_Istrate_Moraru.demo.repository;

import java.util.List;

import com.AWBD_Istrate_Moraru.demo.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    List<Developer> findByName(String name);

    List<Developer> findByWebsite(String website);
}
