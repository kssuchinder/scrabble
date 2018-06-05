package com.example.scrabble.model;

import javax.persistence.*;

@Entity
public class BoardSquare {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private int row;
	private int column;
	private int value;
	private String playerId;
	private Long gameId;
	private Long moveId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getMoveId() {
		return moveId;
	}
	public void setMoveId(Long moveId) {
		this.moveId = moveId;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
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
	
	
}
