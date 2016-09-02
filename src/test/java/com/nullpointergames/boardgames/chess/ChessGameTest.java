package com.nullpointergames.boardgames.chess;

import static com.nullpointergames.boardgames.PieceColor.BLACK;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.chess.PieceType.KING;
import static com.nullpointergames.boardgames.chess.PieceType.NULL;
import static com.nullpointergames.boardgames.chess.PieceType.PAWN;
import static com.nullpointergames.boardgames.chess.PieceType.ROOK;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;

public class ChessGameTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private ChessGame game;
	
	@Before
	public void setup() {
		Locale.setDefault(Locale.US);
		newGame();
	}

	private void newGame() {
		newGame(WHITE);
	}

	private void newGame(PieceColor color) {
		game = new ChessGame(color);
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

	private void moveWithoutVerification(char colFrom, int rowFrom, char colTo, int rowTo) {
		game.moveWithoutVerification(new Position(colFrom, rowFrom), new Position(colTo, rowTo));
	}

	@Test
	public void validBlackMove() throws PromotionException {
		newGame(BLACK);
		
		moveWithoutVerification('a', 2, 'a', 3);
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
		
		newGame(BLACK);
		
		move('a', 7, 'a', 6);
	}

	@Test
	public void chooseWhitePiece() throws PromotionException {
		exception.expect(RuntimeException.class);
		exception.expectMessage("Choose a white piece to move");
		
		move('a', 7, 'a', 6);
	}

	@Test
	public void chooseBlackPiece() throws PromotionException {
		exception.expect(RuntimeException.class);
		exception.expectMessage("Choose a black piece to move");
		
		newGame(BLACK);
		
		move('a', 2, 'a', 3);
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
		moveWithoutVerification('a', 7, 'a', 6);
		move('f', 1, 'e', 2);
		moveWithoutVerification('a', 6, 'a', 5);
		move('g', 1, 'f', 3);
		moveWithoutVerification('a', 5, 'a', 4);
		move('e', 1, 'g', 1);
		
		Piece king = getPiece('g', 1);
		assertThat(king.type(), equalTo(KING));
		
		Piece rook = getPiece('f', 1);
		assertThat(rook.type(), equalTo(ROOK));
	}

	@Test
	public void queenSideCastling() throws PromotionException {
		move('d', 2, 'd', 3);
		moveWithoutVerification('a', 7, 'a', 6);
		move('c', 1, 'e', 3);
		moveWithoutVerification('a', 6, 'a', 5);
		move('d', 1, 'd', 2);
		moveWithoutVerification('a', 5, 'a', 4);
		move('b', 1, 'c', 3);
		moveWithoutVerification('a', 4, 'a', 3);
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
		moveWithoutVerification('a', 7, 'a', 6);
		move('d', 1, 'f', 3);
		moveWithoutVerification('a', 6, 'a', 5);
		move('f', 3, 'f', 7);
		verifyCheckAndCheckmate();
	}

	@Test
	public void checkMateWhiteWins() throws PromotionException {
		try {
			move('e', 2, 'e', 3);
			moveWithoutVerification('a', 7, 'a', 6);
			move('d', 1, 'f', 3);
			moveWithoutVerification('a', 6, 'a', 5);
			move('f', 1, 'c', 4);
			moveWithoutVerification('a', 5, 'a', 4);
			move('f', 3, 'f', 7);
			verifyCheckAndCheckmate();
			fail();
		} catch (Exception e) {
			assertThat(e.getMessage(), equalTo("Checkmate. You won"));
		}
		
		try {
			move('a', 4, 'a', 3);
			fail();
		} catch (Exception e) {
			assertThat(e.getMessage(), equalTo("Game over. You won"));
		}
	}

	@Test
	public void checkMateBlackWins() throws PromotionException {
		newGame(BLACK);
		
		try {
			moveWithoutVerification('a', 2, 'a', 3);
			move('e', 7, 'e', 6);
			moveWithoutVerification('a', 3, 'a', 4);
			move('d', 8, 'h', 4);
			moveWithoutVerification('a', 4, 'a', 5);
			move('f', 8, 'c', 5);
			moveWithoutVerification('a', 5, 'a', 6);
			move('h', 4, 'f', 2);
			verifyCheckAndCheckmate();
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
	}
	
	@Test
	public void longGame() throws PromotionException {
		move('a', 2, 'a', 3);
		moveWithoutVerification('a', 7, 'a', 6);
		move('a', 3, 'a', 4);
		moveWithoutVerification('a', 6, 'a', 5);

		move('b', 2, 'b', 3);
		moveWithoutVerification('b', 7, 'b', 6);
		move('b', 3, 'b', 4);
		moveWithoutVerification('b', 6, 'b', 5);

		move('c', 2, 'c', 3);
		moveWithoutVerification('c', 7, 'c', 6);
		move('c', 3, 'c', 4);
		moveWithoutVerification('c', 6, 'c', 5);

		move('d', 2, 'd', 3);
		moveWithoutVerification('d', 7, 'd', 6);
		move('d', 3, 'd', 4);
		moveWithoutVerification('d', 6, 'd', 5);

		move('e', 2, 'e', 3);
		moveWithoutVerification('e', 7, 'e', 6);
		move('e', 3, 'e', 4);
		moveWithoutVerification('e', 6, 'e', 5);

		move('f', 2, 'f', 3);
		moveWithoutVerification('f', 7, 'f', 6);
		move('f', 3, 'f', 4);
		moveWithoutVerification('f', 6, 'f', 5);

		move('g', 2, 'g', 3);
		moveWithoutVerification('g', 7, 'g', 6);
		move('g', 3, 'g', 4);
		moveWithoutVerification('g', 6, 'g', 5);

		move('h', 2, 'h', 3);
		moveWithoutVerification('h', 7, 'h', 6);
		move('h', 3, 'h', 4);
		moveWithoutVerification('h', 6, 'h', 5);
		
		move('a', 1, 'a', 2);
		moveWithoutVerification('a', 8, 'a', 7);
		move('b', 1, 'a', 3);
		moveWithoutVerification('b', 8, 'a', 6);
		move('c', 1, 'b', 2);
		moveWithoutVerification('c', 8, 'b', 7);
		move('d', 1, 'a', 1);
		moveWithoutVerification('d', 8, 'a', 8);
		move('e', 1, 'd', 1);
		moveWithoutVerification('e', 8, 'd', 8);
		move('f', 1, 'g', 2);
		moveWithoutVerification('f', 8, 'g', 7);
		move('g', 1, 'h', 3);
		moveWithoutVerification('g', 8, 'h', 6);
		move('h', 1, 'h', 2);
		moveWithoutVerification('h', 8, 'h', 7);
	}
	
	@Test
	public void cloneGame() throws Exception {
		move('a', 2, 'a', 3);
		
		ChessGame clone = game.clone();
		assertThat(clone.getBoard().getPieceType(new Position('a', 2)), equalTo(NULL));
		assertThat(clone.getBoard().getPieceType(new Position('a', 3)), equalTo(PAWN));
	}
	
	private Piece getPiece(char col, int row) {
		return game.find(new Position(col, row)).piece();
	}
	
	private void verifyCheckAndCheckmate() {
		game.verifyCheckAndCheckmate();
	}
}
