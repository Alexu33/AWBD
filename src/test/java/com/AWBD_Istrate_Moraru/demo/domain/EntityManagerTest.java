package com.AWBD_Istrate_Moraru.demo.domain;

import com.AWBD_Istrate_Moraru.demo.entity.Game;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("h2test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class EntityManagerTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void findGame() {
        System.out.println(entityManager.getEntityManagerFactory());
        Game game = entityManager.find(Game.class, 143L);
        assertEquals(game.getTitle(), "Terroria");
    }
}