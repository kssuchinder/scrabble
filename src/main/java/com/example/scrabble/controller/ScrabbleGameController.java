package com.example.scrabble.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.scrabble.exceptions.ScrabbleGameException;
import com.example.scrabble.model.BoardSquare;
import com.example.scrabble.model.Game;
import com.example.scrabble.model.GameMoves;
import com.example.scrabble.request.GameDetails;
import com.example.scrabble.request.PlayWord;
import com.example.scrabble.service.ScrabbleGameService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(value="scrabble service", description="Sample Scrabble Service")
public class ScrabbleGameController {

	@Autowired
	private ScrabbleGameService scrabbleGameService;
	
	@ApiOperation(value = "Create a new Scrabble game/match", response = Game.class)
	@RequestMapping(value = "game/create", method = RequestMethod.POST)
	public Game createGame(@RequestBody GameDetails game) {
		return scrabbleGameService.create(game);
	}
	
	@ApiOperation(value = "Read endpoint to print an ASCII representation of the board", response = int[][].class)
	@RequestMapping(value = "game/{id}", method = RequestMethod.GET)
	public List<BoardSquare> getGameBoard(@PathVariable Long id) {		
		return scrabbleGameService.getGameBoard(id);
	}
	
	
	@ApiOperation(value = "A match level endpoint to play a word starting at an x,y board coordinate and proceeding\r\n" + 
			"in a direction: either down or right", response = Game.class)
	@RequestMapping(value = "game/play", method = RequestMethod.POST)
	public String playAWord(@RequestBody PlayWord playWord) throws ScrabbleGameException {
		return scrabbleGameService.playAWord(playWord);
	}
	
	@ApiOperation(value = "List endpoint for all active games", response = int[][].class)
	@RequestMapping(value = "game/list/{status}", method = RequestMethod.GET)
	public List<Game> getActiveGames(@PathVariable String status) {		
		return scrabbleGameService.getGamesList(status);
	}
	
	@ApiOperation(value = "Read endpoint for play history for a match", response = int[][].class)
	@RequestMapping(value = "game/history/{id}", method = RequestMethod.GET)
	public List<GameMoves> getMatchHistory(@PathVariable Long id) {		
		return scrabbleGameService.getMatchHistory(id);
	}	
	
}
