package com.nullpointergames.boardgames.chess.rules;

import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.chess.PieceType.QUEEN;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;

public class QueenRuleTest extends RuleTest {

	@Override
	protected Rule rule(Position from) {
		return new QueenRule(board, from);
	}

	@Before
	public void setup() {
		board.put(whiteQueen(), new Position('d', 4));
	}
	
	@Test
	public void rookMoves() {
		moveFrom('d', 4);
		
		assertThat(possibleMoves, hasSize(27));
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
	
	private Piece whiteQueen() {
		return new Piece(QUEEN, WHITE);
	}
}
