package com.nullpointergames.boardgames.chess.rules;

import static com.nullpointergames.boardgames.PieceColor.BLACK;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.chess.PieceType.ROOK;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;

public class RookRuleTest extends RuleTest {

	@Override
	protected Rule rule(Position from) {
		return new RookRule(board, from);
	}
	
	@Before
	public void setup() {
		board.put(whiteRook(), new Position('d', 4));
	}
	
	@Test
	public void rookMoves() {
		moveFrom('d', 4);
		
		assertThat(possibleMoves, hasSize(14));
		assertThatCanMoveTo('d', 1);
		assertThatCanMoveTo('d', 2);
		assertThatCanMoveTo('d', 3);
		assertThatCanMoveTo('d', 5);
		assertThatCanMoveTo('d', 6);
		assertThatCanMoveTo('d', 7);
		assertThatCanMoveTo('d', 8);
		
		assertThatCanMoveTo('a', 4);
		assertThatCanMoveTo('b', 4);
		assertThatCanMoveTo('c', 4);
		assertThatCanMoveTo('e', 4);
		assertThatCanMoveTo('f', 4);
		assertThatCanMoveTo('g', 4);
		assertThatCanMoveTo('h', 4);
	}
	
	@Test
	public void captureMove() {
		putBlackRook('d', 5);
		putBlackRook('d', 3);
		putBlackRook('c', 4);
		putBlackRook('e', 4);
		
		moveFrom('d', 4);
		
		assertThat(possibleMoves, hasSize(4));
		assertThatCanMoveTo('d', 5);
		assertThatCanMoveTo('d', 3);
		assertThatCanMoveTo('c', 4);
		assertThatCanMoveTo('e', 4);
	}

	private void putBlackRook(char col, int row) {
		board.put(blackRook(), new Position(col, row));		
	}

	private Piece whiteRook() {
		return new Piece(ROOK, WHITE);
	}
	
	private Piece blackRook() {
		return new Piece(ROOK, BLACK);
	}
}
