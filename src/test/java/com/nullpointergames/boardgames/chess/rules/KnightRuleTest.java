package com.nullpointergames.boardgames.chess.rules;

import static com.nullpointergames.boardgames.PieceColor.BLACK;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.chess.PieceType.KNIGHT;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;

public class KnightRuleTest extends RuleTest {

	@Override
	protected Rule rule(Position from) {
		return new KnightRule(board, from);
	}

	@Before
	public void setup() {
		board.put(whiteKnight(), new Position('d', 4));
	}
	
	@Test
	public void knightMoves() {
		moveFrom('d', 4);
		
		assertThat(possibleMoves, hasSize(8));
		assertThatCanMoveTo('b', 3);
		assertThatCanMoveTo('b', 5);
		assertThatCanMoveTo('c', 2);
		assertThatCanMoveTo('c', 6);
		assertThatCanMoveTo('e', 2);
		assertThatCanMoveTo('e', 6);
		assertThatCanMoveTo('f', 3);
		assertThatCanMoveTo('f', 5);
	}

	@Test
	public void cornerKnightMoves() {
		putWhiteKnight('h', 1);
		
		moveFrom('h', 1);
		
		assertThat(possibleMoves, hasSize(2));
		assertThatCanMoveTo('f', 2);
		assertThatCanMoveTo('g', 3);
	}
	
	@Test
	public void captureMove() {
		putBlackKnight('b', 3);
		putBlackKnight('b', 5);
		
		moveFrom('d', 4);
		
		assertThat(possibleMoves, hasSize(8));
		assertThatCanMoveTo('b', 3);
		assertThatCanMoveTo('b', 5);
		assertThatCanMoveTo('c', 2);
		assertThatCanMoveTo('c', 6);
		assertThatCanMoveTo('e', 2);
		assertThatCanMoveTo('e', 6);
		assertThatCanMoveTo('f', 3);
		assertThatCanMoveTo('f', 5);
	}

	@Test
	public void canNotMove() {
		putWhiteKnight('b', 3);
		putWhiteKnight('b', 5);
		putWhiteKnight('c', 2);
		putWhiteKnight('c', 6);
		putWhiteKnight('e', 2);
		putWhiteKnight('e', 6);
		putWhiteKnight('f', 3);
		putWhiteKnight('f', 5);
		
		moveFrom('d', 4);
		
		assertThat(possibleMoves, hasSize(0));
	}
	
	private Piece whiteKnight() {
		return new Piece(KNIGHT, WHITE);
	}

	private Piece blackKnight() {
		return new Piece(KNIGHT, BLACK);
	}

	private void putWhiteKnight(char col, int row) {
		put(whiteKnight(), col, row);
	}

	private void putBlackKnight(char col, int row) {
		put(blackKnight(), col, row);
	}
}
