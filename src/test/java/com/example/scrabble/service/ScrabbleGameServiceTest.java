package com.example.scrabble.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.example.scrabble.dto.GameMovesDTO;
import com.example.scrabble.exceptions.ScrabbleGameException;
import com.example.scrabble.model.BoardSquare;
import com.example.scrabble.model.Game;
import com.example.scrabble.repository.BoardRepository;
import com.example.scrabble.repository.GameRespository;
import com.example.scrabble.repository.MoveRepository;
import com.example.scrabble.request.GameDetails;
import com.example.scrabble.request.PlayWord;

public class ScrabbleGameServiceTest {
	
	@Mock
	GameRespository gameRespository;
	
	@Mock
	BoardRepository boardRespository;
	
	@Mock
	MoveRepository moveRepository;
	
	@InjectMocks
	ScrabbleGameService scrabbleGameService;
	
  @BeforeMethod
  public void beforeMethod() {
	  MockitoAnnotations.initMocks(this);
  }


  @Test
  public void testCreate() throws ScrabbleGameException {	  
	  when(gameRespository.saveAndFlush(any())).thenReturn(getGame());
	  Game game = scrabbleGameService.create(getGameDetails());
	  assertEquals(game.getLength(),10);
	  assertEquals(game.getWidth(),10);
	  assertEquals(game.getPlayerCount(),2);
	  assertEquals(game.getStatus(),"active");
  }

  @Test
  public void testGetGameBoard() throws ScrabbleGameException {
	  when(gameRespository.getOne(any())).thenReturn(getGame());
	  when(boardRespository.findBoardByGameId(any())).thenReturn(getBoardSquares());
	  Map<Integer, String> rowData = scrabbleGameService.getGameBoard(1);
	  assertEquals(rowData.get(1),"0 0 0 0 0 0 0 0 0 0");
  }

  @Test
  public void getGamesList() throws ScrabbleGameException {
	  when(gameRespository.findGamesByStatus(any())).thenReturn(getGameList());
	  List<Game> gamesList = scrabbleGameService.getGamesList("active");
	  assertEquals(gamesList.get(0).getLength(),10);
	  assertEquals(gamesList.get(0).getWidth(),10);
	  assertEquals(gamesList.get(0).getStatus(),"active");
  }

  @Test
  public void getMatchHistory() {
    //throw new RuntimeException("Test not implemented");
  }

  @Test
  public void testPlayAWordDown() throws ScrabbleGameException {
	  long moveId = 1;
	  PlayWord playWord = new PlayWord();
	  playWord.setDirection("down");
	  playWord.setGameId(1);
	  playWord.setPlayerId("player1");
	  playWord.setWord("scrabble");
	  playWord.setxCoordinate(2);
	  playWord.setyCoordinate(3);
	  when(gameRespository.getOne(any())).thenReturn(getGame());
	  when(boardRespository.findBoardByGameId(any())).thenReturn(getBoardSquares());
	  when(boardRespository.findMaxMoveId(any())).thenReturn(1);
	  GameMovesDTO move = scrabbleGameService.playAWord(playWord);
	  assertEquals(move.getDirection(),"down");
	  assertEquals(move.getStartingCoOrdinates(),"2,3");
	  assertEquals(move.getPlayerId(),"player1");
  }
  
  @Test(expectedExceptions = ScrabbleGameException.class)
  public void testPlayAWordinWrongDirection() throws ScrabbleGameException {
	  long moveId = 1;
	  PlayWord playWord = new PlayWord();
	  playWord.setDirection("left");
	  playWord.setGameId(1);
	  playWord.setPlayerId("player1");
	  playWord.setWord("scrabble");
	  playWord.setxCoordinate(2);
	  playWord.setyCoordinate(3);
	  when(gameRespository.getOne(any())).thenReturn(getGame());
	  when(boardRespository.findBoardByGameId(any())).thenReturn(getBoardSquares());
	  when(boardRespository.findMaxMoveId(any())).thenReturn(1);
	  GameMovesDTO move = scrabbleGameService.playAWord(playWord);
	  
  }
  
  private GameDetails getGameDetails() {
		GameDetails gameDetails = new GameDetails();
		gameDetails.setBoardLength(10);
		gameDetails.setBoardWidth(10);
		gameDetails.setPlayerCount(2);
		return gameDetails;
	}
  
  private List<Game> getGameList(){
	  List<Game> gamesList = new ArrayList();
	  gamesList.add(getGame());
	  return gamesList;
  }
  
  private Game getGame() {
		Game game = new Game();
		game.setGameId((long) 1);
		game.setLength(10);
		game.setPlayerCount(2);
		game.setWidth(10);
		game.setStatus("active");
		return game;
	}
  
  private List<BoardSquare> getBoardSquares() {
	  List<BoardSquare> boardDetailsList = new ArrayList();
	  BoardSquare boardDetails;
	  for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				boardDetails = new BoardSquare();
				boardDetails.setRow(i);
				boardDetails.setColumn(j);
				boardDetails.setValue(0);
				boardDetailsList.add(boardDetails);
			}
		}
		
		return boardDetailsList;
		
	}
}
