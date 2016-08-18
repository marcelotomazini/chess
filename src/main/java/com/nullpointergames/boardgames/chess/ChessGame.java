package com.nullpointergames.boardgames.chess;

import static com.nullpointergames.boardgames.PieceColor.BLACK;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.chess.PieceType.BISHOP;
import static com.nullpointergames.boardgames.chess.PieceType.KING;
import static com.nullpointergames.boardgames.chess.PieceType.KNIGHT;
import static com.nullpointergames.boardgames.chess.PieceType.PAWN;
import static com.nullpointergames.boardgames.chess.PieceType.QUEEN;
import static com.nullpointergames.boardgames.chess.PieceType.ROOK;
import static com.nullpointergames.boardgames.utils.MessageUtils.CHECK;
import static com.nullpointergames.boardgames.utils.MessageUtils.CHECKMATE;
import static com.nullpointergames.boardgames.utils.MessageUtils.GAME_OVER;
import static com.nullpointergames.boardgames.utils.MessageUtils.IT_IS_NOT_YOUR_TURN;
import static com.nullpointergames.boardgames.utils.MessageUtils.YOU_LOST;
import static com.nullpointergames.boardgames.utils.MessageUtils.YOU_WON;
import static com.nullpointergames.boardgames.utils.MessageUtils.getMessage;
import static java.lang.String.format;

import java.util.List;

import com.nullpointergames.boardgames.Block;
import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;
import com.nullpointergames.boardgames.chess.rules.PawnRule;


public class ChessGame {

	private final Board board = new Board();
	private final PieceColor myColor;
	private PieceColor turn = WHITE;
	private boolean isOver;
	private PieceColor winner;

	public ChessGame(PieceColor myColor) {
		this.myColor = myColor;
		putPieces();
	}

	public void move(final Position from, final Position to) throws PromotionException {
		if(isOver) {
			String result = winner == myColor ? getMessage(YOU_WON) : getMessage(YOU_LOST);
			throw new RuntimeException(format("%s. %s", getMessage(GAME_OVER), result));
		}
		
		if(board.getPieceColor(from) != turn)
			throw new RuntimeException(getMessage(IT_IS_NOT_YOUR_TURN));
		
		move(board, from, to);
		nextTurn();
	}
	
	public Block find(Position position) {
		return board.find(position);
	}
	
	public List<Position> getPossibleMoves(Position from) {
		return RuleFactory.getRule(board, from).possibleMoves();
	}
	
	public void promoteTo(PieceType pieceType) {
		PawnRule pawnRule = new PawnRule(board, null);
		pawnRule.promoteTo(board, pieceType);
		nextTurn();
	}

	public PieceColor getTurn() {
		return turn;
	}
	
	public void verifyGame() {
		if(isCheckmate()) {
			isOver = true;
			winner = turn == WHITE ? BLACK : WHITE;
			String result = winner == myColor ? getMessage(YOU_WON) : getMessage(YOU_LOST);
			throw new RuntimeException(format("%s. %s", getMessage(CHECKMATE), result));
		}
		
		if(isCheck())
			throw new RuntimeException(getMessage(CHECK));
	}

	private void move(Board board, Position from, Position to) throws PromotionException {
		RuleFactory.getRule(board, from).move(to);
	}
	
	private void putPiece(PieceType type, PieceColor color, char col, int row) {
		board.put(new Piece(type, color), new Position(col, row));		
	}

	private final void nextTurn() {
		turn = turn.equals(WHITE) ? BLACK : WHITE;
		verifyGame();
	}

	private boolean isCheckmate() {
		for(Block b : board) {
			Piece piece = board.getPiece(b.position());
			if(piece.color() != turn)
				continue;
			
			List<Position> possibleMoves = RuleFactory.getRule(board, b.position()).possibleMoves();
			if(!possibleMoves.isEmpty())
				return false;
		}
		
		return true;
	}

	private boolean isCheck() {
		if(turn == myColor)
			for(Block b : board) {
				Piece p = board.getPiece(b.position());
				if(p.color() == myColor)
					continue;
				
				for(Position position : RuleFactory.getRule(board, b.position()).possibleMoves()) {
					Piece piece = board.getPiece(position);
					if(piece.type() == KING)
						return true;
				}
			}
			
		return false;
	}
	
	private void putPieces() {
		putPiece(ROOK, WHITE, 'a', 1);
		putPiece(KNIGHT, WHITE, 'b', 1);
		putPiece(BISHOP, WHITE, 'c', 1);
		putPiece(QUEEN, WHITE, 'd', 1);
		putPiece(KING, WHITE, 'e', 1);
		putPiece(BISHOP, WHITE, 'f', 1);
		putPiece(KNIGHT, WHITE, 'g', 1);
		putPiece(ROOK, WHITE, 'h', 1);
		putPiece(PAWN, WHITE, 'a', 2);
		putPiece(PAWN, WHITE, 'b', 2);
		putPiece(PAWN, WHITE, 'c', 2);
		putPiece(PAWN, WHITE, 'd', 2);
		putPiece(PAWN, WHITE, 'e', 2);
		putPiece(PAWN, WHITE, 'f', 2);
		putPiece(PAWN, WHITE, 'g', 2);
		putPiece(PAWN, WHITE, 'h', 2);

		putPiece(ROOK, BLACK, 'a', 8);
		putPiece(KNIGHT, BLACK, 'b', 8);
		putPiece(BISHOP, BLACK, 'c', 8);
		putPiece(QUEEN, BLACK, 'd', 8);
		putPiece(KING, BLACK, 'e', 8);
		putPiece(BISHOP, BLACK, 'f', 8);
		putPiece(KNIGHT, BLACK, 'g', 8);
		putPiece(ROOK, BLACK, 'h', 8);
		putPiece(PAWN, BLACK, 'a', 7);
		putPiece(PAWN, BLACK, 'b', 7);
		putPiece(PAWN, BLACK, 'c', 7);
		putPiece(PAWN, BLACK, 'd', 7);
		putPiece(PAWN, BLACK, 'e', 7);
		putPiece(PAWN, BLACK, 'f', 7);
		putPiece(PAWN, BLACK, 'g', 7);
		putPiece(PAWN, BLACK, 'h', 7);
	}
}
