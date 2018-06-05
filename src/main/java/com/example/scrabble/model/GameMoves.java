package com.example.scrabble.model;

import javax.persistence.*;

@Entity
public class GameMoves {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String playerId;
	private Long gameId;
	private Long moveId;
	private String word;
	private int pointsScored;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public Long getGameId() {
		return gameId;
	}
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	public Long getMoveId() {
		return moveId;
	}
	public void setMoveId(Long moveId) {
		this.moveId = moveId;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getPointsScored() {
		return pointsScored;
	}
	public void setPointsScored(int pointsScored) {
		this.pointsScored = pointsScored;
	}
	
	
	}
