package com.nullpointergames.boardgames.chess.rules;

import static com.nullpointergames.boardgames.PieceColor.BLACK;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.chess.PieceType.KING;
import static com.nullpointergames.boardgames.chess.PieceType.PAWN;
import static com.nullpointergames.boardgames.chess.PieceType.ROOK;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;

public class KingRuleTest extends RuleTest {

	@Override
	protected Rule rule(Position from) {
		return new KingRule(board, from);
	}

	@Before
	public void setup() {
		board.put(whiteKing(), new Position('a', 1));
	}
	
	@Test
	public void kingMoves() {
		board.put(whiteKing(), new Position('d', 4));
		
		moveFrom('d', 4);
		
		assertThat(possibleMoves, hasSize(8));
		assertThatCanMoveTo('c', 3);
		assertThatCanMoveTo('c', 4);
		assertThatCanMoveTo('c', 5);
		assertThatCanMoveTo('d', 3);
		assertThatCanMoveTo('d', 5);
		assertThatCanMoveTo('e', 3);
		assertThatCanMoveTo('e', 4);
		assertThatCanMoveTo('e', 5);
	}

	@Test
	public void cornerKingMoves() {
		moveFrom('a', 1);
		
		assertThat(possibleMoves, hasSize(3));
		assertThatCanMoveTo('a', 2);
		assertThatCanMoveTo('b', 1);
		assertThatCanMoveTo('b', 2);
	}

	@Test
	public void canNotMove() {
		putWhitePawn('a', 2);
		putWhitePawn('b', 1);
		putWhitePawn('b', 2);
		
		moveFrom('a', 1);
		
		assertThat(possibleMoves, hasSize(0));
	}
	
	@Test
	public void canNotMoveToThreatenedBox() {
		put(blackRook(), 'b', 8);
		put(blackRook(), 'h', 2);
		
		moveFrom('a', 1);
		
		assertThat(possibleMoves, hasSize(0));
	}
	
	@Test
	public void whiteKingSideCastling() {
		put(whiteKing(), 'e', 1);
		put(whiteRook(), 'h', 1);
		
		moveFrom('e', 1);
		
		assertThat(possibleMoves, hasSize(6));
		assertThatCanMoveTo('d', 1);
		assertThatCanMoveTo('f', 1);
		assertThatCanMoveTo('g', 1);
		assertThatCanMoveTo('d', 2);
		assertThatCanMoveTo('e', 2);
		assertThatCanMoveTo('f', 2);
	}

	@Test
	public void whiteQueenSideCastling() {
		put(whiteKing(), 'e', 1);
		put(whiteRook(), 'a', 1);
		
		moveFrom('e', 1);
		
		assertThat(possibleMoves, hasSize(6));
		assertThatCanMoveTo('c', 1);
		assertThatCanMoveTo('d', 1);
		assertThatCanMoveTo('f', 1);
		assertThatCanMoveTo('d', 2);
		assertThatCanMoveTo('e', 2);
		assertThatCanMoveTo('f', 2);
	}

	@Test
	public void blackKingSideCastling() {
		put(blackKing(), 'e', 8);
		put(blackRook(), 'h', 8);
		
		moveFrom('e', 8);
		
		assertThat(possibleMoves, hasSize(6));
		assertThatCanMoveTo('d', 8);
		assertThatCanMoveTo('f', 8);
		assertThatCanMoveTo('g', 8);
		assertThatCanMoveTo('d', 7);
		assertThatCanMoveTo('e', 7);
		assertThatCanMoveTo('f', 7);
	}

	@Test
	public void blackQueenSideCastling() {
		put(blackKing(), 'e', 8);
		put(blackRook(), 'a', 8);
		
		moveFrom('e', 8);
		
		assertThat(possibleMoves, hasSize(6));
		assertThatCanMoveTo('c', 8);
		assertThatCanMoveTo('d', 8);
		assertThatCanMoveTo('f', 8);
		assertThatCanMoveTo('d', 7);
		assertThatCanMoveTo('e', 7);
		assertThatCanMoveTo('f', 7);
	}

	@Test
	public void canNotCastlingPassingByBoxesUnderEnemyAtack() {
		put(blackKing(), 'e', 8);
		put(blackRook(), 'a', 8);
		put(whiteRook(), 'd', 1);
		
		moveFrom('e', 8);
		
		assertThat(possibleMoves, hasSize(3));
		assertThatCanMoveTo('f', 8);
		assertThatCanMoveTo('e', 7);
		assertThatCanMoveTo('f', 7);
	}

	@Test
	public void canNotCastlingWithNoEmptyBoxesBetweenKingAndRook() {
		put(whiteKing(), 'e', 1);
		put(whiteRook(), 'a', 1);
		put(whitePawn(), 'b', 1);
		
		moveFrom('e', 1);
		
		assertThat(possibleMoves, hasSize(5));
		assertThatCanMoveTo('d', 1);
		assertThatCanMoveTo('f', 1);
		assertThatCanMoveTo('d', 2);
		assertThatCanMoveTo('e', 2);
		assertThatCanMoveTo('f', 2);
	}
	
	private Piece whiteRook() {
		return new Piece(ROOK, WHITE);
	}

	private Piece blackRook() {
		return new Piece(ROOK, BLACK);
	}

	private Piece whiteKing() {
		return new Piece(KING, WHITE);
	}

	private Piece blackKing() {
		return new Piece(KING, BLACK);
	}

	private Piece whitePawn() {
		return new Piece(PAWN, WHITE);
	}
	
	private void putWhitePawn(char col, int row) {
		put(whitePawn(), col, row);		
	}
}
