package com.example.scrabble.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.scrabble.model.Game;
import com.example.scrabble.model.GameMoves;

public interface MoveRepository extends JpaRepository<GameMoves, Long>  {

	@Query("SELECT g FROM GameMoves g where g.game = :gameId order by moveId ") 
    public List<GameMoves> findGameMoves(@Param("gameId") Game gameId);
	
}
