package com.example.scrabble.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.scrabble.dto.GameMovesDTO;
import com.example.scrabble.exceptions.ScrabbleGameException;
import com.example.scrabble.model.BoardSquare;
import com.example.scrabble.model.Game;
import com.example.scrabble.model.GameMoves;
import com.example.scrabble.repository.BoardRepository;
import com.example.scrabble.repository.GameRespository;
import com.example.scrabble.repository.MoveRepository;
import com.example.scrabble.request.GameDetails;
import com.example.scrabble.request.PlayWord;

@Service
public class ScrabbleGameService {

	@Autowired
	GameRespository gameRespository;

	@Autowired
	BoardRepository boardRespository;

	@Autowired
	MoveRepository moveRepository;

	/**
	 * @param gameDetails
	 * @return
	 * @throws ScrabbleGameException 
	 */
	public Game create(GameDetails gameDetails) throws ScrabbleGameException {

		Game game = new Game();
		BoardSquare boardDetails = null;
		long moveId = 0;

		try {
			game.setPlayerCount(gameDetails.getPlayerCount());
			game.setStatus("active");
			game.setLength(gameDetails.getBoardLength());
			game.setWidth(gameDetails.getBoardWidth());
			game = gameRespository.saveAndFlush(game);

			for (int i = 1; i <= gameDetails.getBoardLength(); i++) {
				for (int j = 1; j <= gameDetails.getBoardWidth(); j++) {
					boardDetails = new BoardSquare();
					boardDetails.setRow(i);
					boardDetails.setColumn(j);
					boardDetails.setValue(0);
					boardDetails.setGame(game);
					boardDetails.setMoveId(moveId);
					boardRespository.saveAndFlush(boardDetails);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ScrabbleGameException("Error while creating match");
		}

		return game;
	}

	/**
	 * @param gameId
	 * @return
	 * @throws ScrabbleGameException 
	 */
	public Map<Integer, String> getGameBoard(long gameId) throws ScrabbleGameException {
		Game game = gameRespository.getOne(gameId);
		Map<Integer, String> rowData;
		if (game != null) {
			return getBoardData(game);
		}
		else
			throw new ScrabbleGameException("No valid game found");		
	}

	/**
	 * This method fetches the board data from board_squares and returns back a Map.
	 * @param game
	 * @return
	 */
	private Map<Integer, String> getBoardData(Game game) {
		Map<Integer, String> rowData;
		List<BoardSquare> boardDetailsList = boardRespository.findBoardByGameId(game);
		rowData = new HashMap<Integer, String>();
		boardDetailsList.forEach(boardSquare -> {
			String data = rowData.get(boardSquare.getRow());
			if (data != null)
				rowData.put(boardSquare.getRow(), data + " " + boardSquare.getValue());
			else
				rowData.put(boardSquare.getRow(), Integer.toString(boardSquare.getValue()));
		});
		return rowData;
	}

	/**
	 * @param status
	 * @return list of games
	 * @throws ScrabbleGameException 
	 */
	public List<Game> getGamesList(String status) throws ScrabbleGameException {
		List<Game> gameList = gameRespository.findGamesByStatus(status);
		if(gameList != null) {
			return gameList;
		}else
			throw new ScrabbleGameException("No games found with status : "+ status);
		
	}

	/**
	 * @param playWord
	 * @return
	 * @throws ScrabbleGameException
	 */
	public GameMovesDTO playAWord(PlayWord playWord) throws ScrabbleGameException {

		boolean hasSquarefromExistingWord = false;
		boolean isFirstPlay = false;
		List<BoardSquare> newEnteredSquares = new ArrayList();
		BoardSquare enteredSquare = null;
		long nextMoveId;
		boolean isValidWord = false;

		// Get the game details from DB
		Game game = gameRespository.getOne(playWord.getGameId());
		List<BoardSquare> currentBoard = boardRespository.findBoardByGameId(game);

		int[][] currentBoardValues = new int[game.getLength()][game.getWidth()];
		long[][] currentBoardIds = new long[game.getLength()][game.getWidth()];
		currentBoard.forEach(boardDetails -> {
			currentBoardValues[boardDetails.getRow() - 1][boardDetails.getColumn() - 1] = boardDetails.getValue();
			currentBoardIds[boardDetails.getRow() - 1][boardDetails.getColumn() - 1] = boardDetails.getId();
		});

		// Get the next move/play Id by incrementing the current max moveId
		nextMoveId = boardRespository.findMaxMoveId(game) + 1;

		// Check if first player, then allow any where in board
		isFirstPlay = isEmptyBoard(currentBoard);

		if (playWord.getWord().length() < 2 && isFirstPlay)
			throw new ScrabbleGameException("Starting play cannot be a single letter");

		int x = playWord.getxCoordinate();
		int y = playWord.getyCoordinate();
		int j = 0;
		String direction = playWord.getDirection();
		String newWord = playWord.getWord();

		// Check if the direction of the play is down OR right.
		if (!StringUtils.isEmpty(direction) && "down".equalsIgnoreCase(direction)) {
			// check if the word length fits within the board
			if (game.getLength() <= (newWord.length() - 1) + x) {
				// new word can't fit on the board. throw error
				throw new ScrabbleGameException("Word can't fit on the board. Word too long");
			}
			for (int i = x; i < newWord.length() + x; i++) {
				// Check if the square is empty in the current board.
				if (currentBoardValues[i - 1][y - 1] == 0) {
					// If the square is empty, then populate the square with the newly entered value
					enteredSquare = new BoardSquare();
					enteredSquare.setId(currentBoardIds[i - 1][y - 1]);
					enteredSquare.setRow(i);
					enteredSquare.setColumn(y);
					enteredSquare.setValue(newWord.charAt(j));
					enteredSquare.setGame(game);
					enteredSquare.setMoveId(nextMoveId);
					newEnteredSquares.add(enteredSquare);
				}
				// Check if the square is already filled with another letter
				else if (currentBoardValues[i - 1][y - 1] != newWord.charAt(j)) {
					// Square already filled with another letter. Throw error.
					throw new ScrabbleGameException(
							"Invalid entry. Can't replace an existing letter with another letter");
				} else if (currentBoardValues[i - 1][y - 1] == newWord.charAt(j)) {
					// has atleast one square from existing word.
					hasSquarefromExistingWord = true;
				}
				j++;
			}
		}else if (!StringUtils.isEmpty(direction) && "right".equalsIgnoreCase(direction)) {
			// check if the word length fits within the board
			if (game.getWidth() <= (newWord.length() - 1) + y) {
				// new word can't fit on the board. throw error
				throw new ScrabbleGameException("Word can't fit on the board. Word too long");
			}
			for (int i = y; i < newWord.length() + y; i++) {
				// Check if the square is empty in the current board.
				if (currentBoardValues[x - 1][i - 1] == 0) {
					enteredSquare = new BoardSquare();
					enteredSquare.setId(currentBoardIds[x - 1][i - 1]);
					enteredSquare.setRow(x);
					enteredSquare.setColumn(i);
					enteredSquare.setValue(newWord.charAt(j));
					enteredSquare.setGame(game);
					enteredSquare.setMoveId(nextMoveId);
					newEnteredSquares.add(enteredSquare);
				}
				// Check if the square is already filled with another letter
				else if (currentBoardValues[x - 1][i - 1] != newWord.charAt(j)) {
					// Square already filled with another letter. Throw error.
					throw new ScrabbleGameException(
							"Invalid entry. Can't replace an existing letter with another letter");
				} else if (currentBoardValues[x - 1][i - 1] == newWord.charAt(j)) {
					// has atleast one square from existing word.
					hasSquarefromExistingWord = true;
				}
				j++;
			}
		}else
			throw new ScrabbleGameException("Invalid direction");

		if (!isFirstPlay && !hasSquarefromExistingWord) {
			// throw error. Invalid move. Only first move can be placed anywhere. All other
			// moves need to have atleast one square from existing word.
			throw new ScrabbleGameException(
					"All words must share at least one space with an existing word except for the first play on the board");
		}

		// Check if the word is valid
		isValidWord = true; // TBD - Make a call to dictionary API to validate the word

		// TBD - Check if any other new words are formed as a part of this play. By
		// checking the adjacent squares. Validate them by calling dictionary

		// Update the squares in the DB. Update the play history into game_moves table
		if (isValidWord) {
			newEnteredSquares.forEach(s -> {
				boardRespository.saveAndFlush(s);
			});

			saveMove(playWord, nextMoveId, game);
		} else
			throw new ScrabbleGameException("Invalid word. Word not found in dictionary");

		return new GameMovesDTO(nextMoveId,playWord.getPlayerId(),newWord, x+","+y,direction,10);
	}

	/**
	 * This method inserts the record into game_moves table.
	 * This table stores all the successful moves made by the players in a game 
	 * @param playWord
	 * @param nextMoveId
	 * @param game
	 */
	private void saveMove(PlayWord playWord, long nextMoveId, Game game) {
		GameMoves move = new GameMoves();
		move.setGame(game);
		move.setMoveId(nextMoveId);
		move.setPlayerId(playWord.getPlayerId());
		move.setPointsScored(10); // TBD - Setting the default points for a successful word as 10.
		move.setWord(playWord.getWord());
		move.setStartingCoOrdinates(playWord.getxCoordinate() + "," + playWord.getyCoordinate());
		move.setDirection(playWord.getDirection());
		moveRepository.saveAndFlush(move);
	}

	/**
	 * Checks if the current match is empty
	 * 
	 * @param boardDetails
	 */
	private boolean isEmptyBoard(List<BoardSquare> boardDetails) {
		for (BoardSquare square : boardDetails) {
			if (square.getValue() != 0)
				return false;
		}
		return true;
	}

	/**
	 * Gets the match history for the given gameId
	 * 
	 * @param id
	 * @return
	 * @throws ScrabbleGameException
	 */
	public List<GameMovesDTO> getMatchHistory(Long gameId) throws ScrabbleGameException {
		Game game = gameRespository.getOne(gameId);
		if (game != null) {
			List<GameMoves> gameMoves = moveRepository.findGameMoves(game);
			List<GameMovesDTO> gameMovesDTO = new ArrayList<GameMovesDTO>();
			gameMoves.forEach(gameMove -> {
				gameMovesDTO.add(new GameMovesDTO(gameMove.getMoveId(), gameMove.getPlayerId(), gameMove.getWord(),
						gameMove.getStartingCoOrdinates(), gameMove.getDirection(), gameMove.getPointsScored()));
			});
			return gameMovesDTO;
		} else
			throw new ScrabbleGameException("Invalid game id");
	}

}
