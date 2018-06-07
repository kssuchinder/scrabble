package com.example.scrabble.model;

import javax.persistence.*;

@Entity
public class GameMoves {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String playerId;
	//private Long gameId;
	@ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
	private Long moveId;
	private String word;
	private String startingCoOrdinates;
	private String direction;
	private int pointsScored;
	
	public String getStartingCoOrdinates() {
		return startingCoOrdinates;
	}
	public void setStartingCoOrdinates(String startingCoOrdinates) {
		this.startingCoOrdinates = startingCoOrdinates;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
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
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
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
