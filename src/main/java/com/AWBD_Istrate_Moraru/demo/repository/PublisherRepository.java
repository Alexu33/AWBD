package com.AWBD_Istrate_Moraru.demo.repository;

import com.AWBD_Istrate_Moraru.demo.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
