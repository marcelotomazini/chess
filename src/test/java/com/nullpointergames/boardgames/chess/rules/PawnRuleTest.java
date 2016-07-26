package com.nullpointergames.boardgames.chess.rules;

import static com.nullpointergames.boardgames.PieceColor.BLACK;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.chess.PieceType.PAWN;
import static com.nullpointergames.boardgames.chess.PieceType.QUEEN;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;


public class PawnRuleTest extends RuleTest {

	@org.junit.Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Override
	protected PawnRule rule(Position from) {
		return new PawnRule(board, from);
	}
	
	@Before
	public void setup() {
		board.put(whitePawn(), new Position('b', 2));
	}

	@Test
	public void whitePawnFirstMove() {
		moveFrom('b', 2);
		
		assertThat(possibleMoves, hasSize(2));
		assertThatCanMoveTo('b', 3);
		assertThatCanMoveTo('b', 4);
	}

	@Test
	public void blackPawnFirstMove() {
		board.put(blackPawn(), new Position('a', 7));
		moveFrom('a', 7);
		
		assertThat(possibleMoves, hasSize(2));
		assertThatCanMoveTo('a', 6);
		assertThatCanMoveTo('a', 5);
	}

	@Test
	public void whitePawnSecondMove() throws PromotionException {
		moveTo('b', 3);
		moveFrom('b', 3);
		
		assertThat(possibleMoves, hasSize(1));
		assertThatCanMoveTo('b', 4);
	}
	
	@Test
	public void captureMove() {
		putBlackPawn('a', 3);
		putBlackPawn('b', 3);
		putBlackPawn('c', 3);
		
		moveFrom('b', 2);
		
		assertThat(possibleMoves, hasSize(2));
		assertThatCanMoveTo('a', 3);
		assertThatCanMoveTo('c', 3);
	}

	@Test
	public void canNotMove() {
		putWhitePawn('a', 3);
		putWhitePawn('b', 3);
		putWhitePawn('c', 3);
		
		moveFrom('b', 2);
		
		assertThat(possibleMoves, hasSize(0));
	}

	@Test
	public void promotion() throws PromotionException {
		try {
			board.put(whitePawn(), new Position('b', 7));
			Piece piece = board.getPiece(new Position('b', 7));
			piece.setFirstMove(false);
			
			move('b', 7, 'b', 8);
			fail();
		} catch (PromotionException e) {
			PawnRule rule = rule(null);
			rule.promoteTo(board, QUEEN);
		}
		
		assertThat(board.getPiece(new Position('b', 8)).type(), equalTo(QUEEN));
	}
	
	private void putWhitePawn(char col, int row) {
		put(whitePawn(), col, row);
	}
	
	private void putBlackPawn(char col, int row) {
		put(blackPawn(), col, row);
	}
	
	private Piece whitePawn() {
		return new Piece(PAWN, WHITE);
	}

	private Piece blackPawn() {
		return new Piece(PAWN, BLACK);
	}
	
	private void moveTo(char col, int row) throws PromotionException {
		move('b', 2, col, row);
	}

	private void move(char colFrom, int rowFrom, char colTo, int rowTo) throws PromotionException {
		Rule rule = rule(new Position(colFrom, rowFrom));
		rule.move(new Position(colTo, rowTo));
	}
}
