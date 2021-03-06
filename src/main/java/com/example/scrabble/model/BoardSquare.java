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
	@ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
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
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
	
}
