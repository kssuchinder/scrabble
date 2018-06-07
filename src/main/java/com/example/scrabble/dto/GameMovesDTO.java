package com.example.scrabble.dto;

public class GameMovesDTO {

	private Long moveId;
	private String playerId;
	private String word;
	private String startingCoOrdinates;
	private String direction;
	private int pointsScored;
	
	public GameMovesDTO(Long moveId, String playerId, String word, String startingCoOrdinates, String direction,
			int pointsScored) {
		super();
		this.moveId = moveId;
		this.playerId = playerId;
		this.word = word;
		this.startingCoOrdinates = startingCoOrdinates;
		this.direction = direction;
		this.pointsScored = pointsScored;
	}

	public Long getMoveId() {
		return moveId;
	}

	public void setMoveId(Long moveId) {
		this.moveId = moveId;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

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

	public int getPointsScored() {
		return pointsScored;
	}

	public void setPointsScored(int pointsScored) {
		this.pointsScored = pointsScored;
	}
	
	
}
