package com.nullpointergames.boardgames.chess;

import static com.nullpointergames.boardgames.PieceColor.BLACK;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.chess.PieceType.KING;
import static com.nullpointergames.boardgames.chess.PieceType.ROOK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;

public class ChessGameTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private final ChessGame game = newGame();
	
	@Before
	public void setup() {
		Locale.setDefault(Locale.US);
	}

	private ChessGame newGame() {
		return new ChessGame(BLACK);
	}
	
	@Test
	public void newChessGame() {
		assertThat(game.getTurn(), equalTo(WHITE));
	}

	@Test
	public void validWhiteMove() throws PromotionException {
		move('a', 2, 'a', 3);

		assertThat(game.getTurn(), equalTo(BLACK));
	}

	private void move(char colFrom, int rowFrom, char colTo, int rowTo) throws PromotionException {
		game.move(new Position(colFrom, rowFrom), new Position(colTo, rowTo));
	}

	@Test
	public void validBlackMove() throws PromotionException {
		move('a', 2, 'a', 3);
		move('a', 7, 'a', 6);
		
		assertThat(game.getTurn(), equalTo(WHITE));
	}

	@Test
	public void invalidMove() throws PromotionException {
		exception.expect(RuntimeException.class);
	    exception.expectMessage("Illegal move");
	    
		move('a', 2, 'b', 3);
	}

	@Test
	public void itsNotYourTurn() throws PromotionException {
		exception.expect(RuntimeException.class);
		exception.expectMessage("It's not your turn");
		
		move('a', 7, 'a', 6);
	}
	
	@Test
	public void canNotMoveTwiceSameColor() throws PromotionException {
		exception.expect(RuntimeException.class);
		exception.expectMessage("It's not your turn");
		
		move('a', 2, 'a', 3);
		move('a', 3, 'a', 4);
	}
	
	@Test
	public void kingSideCastling() throws PromotionException {
		move('e', 2, 'e', 3);
		move('a', 7, 'a', 6);
		move('f', 1, 'e', 2);
		move('a', 6, 'a', 5);
		move('g', 1, 'f', 3);
		move('a', 5, 'a', 4);
		move('e', 1, 'g', 1);
		
		Piece king = getPiece('g', 1);
		assertThat(king.type(), equalTo(KING));
		
		Piece rook = getPiece('f', 1);
		assertThat(rook.type(), equalTo(ROOK));
	}

	@Test
	public void queenSideCastling() throws PromotionException {
		move('d', 2, 'd', 3);
		move('a', 7, 'a', 6);
		move('c', 1, 'e', 3);
		move('a', 6, 'a', 5);
		move('d', 1, 'd', 2);
		move('a', 5, 'a', 4);
		move('b', 1, 'c', 3);
		move('a', 4, 'a', 3);
		move('e', 1, 'c', 1);
		
		Piece king = getPiece('c', 1);
		assertThat(king.type(), equalTo(KING));
		
		Piece rook = getPiece('d', 1);
		assertThat(rook.type(), equalTo(ROOK));
	}
	
	@Test
	public void check() throws PromotionException {
		exception.expect(RuntimeException.class);
		exception.expectMessage("Check");
		
		move('e', 2, 'e', 3);
		move('a', 7, 'a', 6);
		move('d', 1, 'f', 3);
		move('a', 6, 'a', 5);
		move('f', 3, 'f', 7);
	}

	@Test
	public void checkMateWhiteWins() throws PromotionException {
		try {
			move('e', 2, 'e', 3);
			move('a', 7, 'a', 6);
			move('d', 1, 'f', 3);
			move('a', 6, 'a', 5);
			move('f', 1, 'c', 4);
			move('a', 5, 'a', 4);
			move('f', 3, 'f', 7);
			fail();
		} catch (Exception e) {
			assertThat(e.getMessage(), equalTo("Checkmate. You lost"));
		}
		
		try {
			move('a', 4, 'a', 3);
			fail();
		} catch (Exception e) {
			assertThat(e.getMessage(), equalTo("Game over. You lost"));
		}
		
		assertThat(game.isOver(), is(true));
	}

	@Test
	public void checkMateBlackWins() throws PromotionException {
		try {
			move('a', 2, 'a', 3);
			move('e', 7, 'e', 6);
			move('a', 3, 'a', 4);
			move('d', 8, 'h', 4);
			move('a', 4, 'a', 5);
			move('f', 8, 'c', 5);
			move('a', 5, 'a', 6);
			move('h', 4, 'f', 2);
			fail();
		} catch (Exception e) {
			assertThat(e.getMessage(), equalTo("Checkmate. You won"));
		}
		
		try {
			move('a', 6, 'b', 7);
			fail();
		} catch (Exception e) {
			assertThat(e.getMessage(), equalTo("Game over. You won"));
		}
		
		assertThat(game.isOver(), is(true));
	}
	
	private Piece getPiece(char col, int row) {
		return game.find(new Position(col, row)).piece();
	}
}
