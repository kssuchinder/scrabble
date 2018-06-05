package com.example.scrabble.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.scrabble.model.BoardSquare;
import com.example.scrabble.model.Game;

public interface GameRespository extends JpaRepository<Game, Long> {

	@Query("SELECT g FROM Game g where g.status = :status ") 
    public List<Game> findGamesByStatus(@Param("status") String status);
}
