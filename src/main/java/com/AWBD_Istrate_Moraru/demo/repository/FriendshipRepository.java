package com.AWBD_Istrate_Moraru.demo.repository;

import com.AWBD_Istrate_Moraru.demo.entity.Friendship;
import com.AWBD_Istrate_Moraru.demo.entity.User;
import com.AWBD_Istrate_Moraru.demo.utils.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    boolean existsBySenderAndReceiverAndStatus(User sender, User receiver, FriendshipStatus status);
    boolean existsBySenderAndReceiver(User sender, User receiver);
    List<Friendship> findAllBySenderOrReceiver(User user1, User user2);
    @Query("SELECT f FROM Friendship f WHERE (f.sender = :user or f.receiver = :user) AND f.status = :status")
    List<Friendship> findAllAcceptedFriendships(@Param("user") User user, @Param("status") FriendshipStatus status);
    @Modifying
    @Query("DELETE FROM Friendship f WHERE (f.sender = :user1 AND f.receiver = :user2) OR (f.sender = :user2 AND f.receiver = :user1)")
    void deleteByUsers(@Param("user1") User user1, @Param("user2") User user2);

    List<Friendship> findAllByReceiverAndStatus(User user, FriendshipStatus friendshipStatus);
}
