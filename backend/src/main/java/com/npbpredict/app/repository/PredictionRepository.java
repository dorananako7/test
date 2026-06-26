package com.npbpredict.app.repository;

import com.npbpredict.app.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Prediction(予想)エンティティに対するデータベース操作を提供するRepositoryインターフェース。
 */
@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {

    /**
     * 指定された試合IDに関連するすべての予想を取得します。
     * メソッドの命名規則により、Spring Data JPAが自動でクエリを生成します。
     */
    List<Prediction> findByGameId(Long gameId);

    /**
     * 指定された試合IDに関連する予想と、その予想を行ったユーザー情報を一度に取得します。
     * JOIN FETCH を使用することで、N+1問題を回避し、パフォーマンスを最適化しています。
     */
    @Query("SELECT p FROM Prediction p JOIN FETCH p.user WHERE p.game.id = :gameId")
    List<Prediction> findByGameIdWithUser(@Param("gameId") Long gameId);
}
