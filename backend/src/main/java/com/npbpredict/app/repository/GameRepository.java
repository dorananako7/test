package com.npbpredict.app.repository;

import com.npbpredict.app.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Gameエンティティに対するデータベース操作（CRUDなど）を提供するRepositoryインターフェース。
 * Spring Data JPAにより、基本的なメソッドは自動的に実装されます。
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
