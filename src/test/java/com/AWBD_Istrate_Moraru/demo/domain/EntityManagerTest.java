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
@ActiveProfiles("postgres")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EntityManagerTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void findProduct() {
        System.out.println(entityManager.getEntityManagerFactory());
        Game game = entityManager.find(Game.class, 143L);
        assertEquals(game.getTitle(), "Terroria");
    }
}