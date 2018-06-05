package com.example.scrabble.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.scrabble.model.BoardSquare;

public interface BoardRepository extends JpaRepository<BoardSquare, Long> {

	@Query("SELECT b FROM BoardSquare b where b.gameId = :gameId order by b.row,b.column") 
    public List<BoardSquare> findBoardByGameId(@Param("gameId") Long gameId);
	
	@Query("SELECT max(b.moveId) FROM BoardSquare b where b.gameId = :gameId")
	public int findMaxMoveId(@Param("gameId") Long gameId);
     
}
