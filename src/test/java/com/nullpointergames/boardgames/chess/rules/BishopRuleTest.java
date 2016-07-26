package com.nullpointergames.boardgames.chess.rules;

import static com.nullpointergames.boardgames.PieceColor.BLACK;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.chess.PieceType.BISHOP;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;

public class BishopRuleTest extends RuleTest {

	@Override
	protected Rule rule(Position from) {
		return new BishopRule(board, from);
	}

	@Before
	public void setup() {
		board.put(whiteBishop(), new Position('d', 4));
	}
	
	@Test
	public void bishopMoves() {
		moveFrom('d', 4);
		
		assertThat(possibleMoves, hasSize(13));
		assertThatCanMoveTo('a', 1);
		assertThatCanMoveTo('b', 2);
		assertThatCanMoveTo('c', 3);
		assertThatCanMoveTo('e', 5);
		assertThatCanMoveTo('f', 6);
		assertThatCanMoveTo('g', 7);
		assertThatCanMoveTo('h', 8);
		
		assertThatCanMoveTo('g', 1);
		assertThatCanMoveTo('f', 2);
		assertThatCanMoveTo('e', 3);
		assertThatCanMoveTo('c', 5);
		assertThatCanMoveTo('b', 6);
		assertThatCanMoveTo('a', 7);
	}
	
	@Test
	public void captureMove() {
		putBlackBishop('c', 5);
		putBlackBishop('e', 5);
		putBlackBishop('b', 2);
		putBlackBishop('f', 2);
		
		moveFrom('d', 4);
		
		assertThat(possibleMoves, hasSize(6));
		assertThatCanMoveTo('b', 2);
		assertThatCanMoveTo('c', 3);
		assertThatCanMoveTo('e', 5);
		
		assertThatCanMoveTo('f', 2);
		assertThatCanMoveTo('e', 3);
		assertThatCanMoveTo('c', 5);
	}
	
	private void putBlackBishop(char col, int row) {
		board.put(blackBishop(), new Position(col, row));		
	}

	private Piece whiteBishop() {
		return new Piece(BISHOP, WHITE);
	}
	
	private Piece blackBishop() {
		return new Piece(BISHOP, BLACK);
	}
}
