package com.npbpredict.app.repository;

import com.npbpredict.app.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    List<Prediction> findByGameId(Long gameId);

    @Query("SELECT p FROM Prediction p JOIN FETCH p.user WHERE p.game.id = :gameId")
    List<Prediction> findByGameIdWithUser(@Param("gameId") Long gameId);
}
