package com.example.scrabble.request;

public class GameDetails {
	
	private int boardLength;	
	private int boardWidth;	
	private int playerCount;
	
	public int getBoardLength() {
		return boardLength;
	}
	public void setBoardLength(int boardLength) {
		this.boardLength = boardLength;
	}
	public int getBoardWidth() {
		return boardWidth;
	}
	public void setBoardWidth(int boardWidth) {
		this.boardWidth = boardWidth;
	}
	public int getPlayerCount() {
		return playerCount;
	}
	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}
	

}
