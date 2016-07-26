package com.nullpointergames.boardgames;

import static com.nullpointergames.boardgames.PieceColor.NULL;
import static com.nullpointergames.boardgames.chess.PieceType.KING;
import static com.nullpointergames.boardgames.utils.MessageUtils.ILLEGAL_MOVE;
import static com.nullpointergames.boardgames.utils.MessageUtils.getMessage;

import java.util.ArrayList;
import java.util.List;

import com.nullpointergames.boardgames.chess.PieceType;
import com.nullpointergames.boardgames.chess.RuleFactory;
import com.nullpointergames.boardgames.chess.exceptions.NoMoreMovesForThisDirection;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;

public abstract class Rule {

	protected static final char FIRST_COLUMN_IN_THE_BOARD = 'a';
	protected static final char LAST_COLUMN_IN_THE_BOARD = 'h';
	protected static final int LAST_LINE_IN_THE_BOARD = 8;
	protected static final int FIRST_LINE_IN_THE_BOARD = 1;
	
	protected final List<Move> possibleMoves = new ArrayList<>();
	
	protected final Board board; 
	protected final PieceColor color; 
	protected final Position from;
	
	public Rule(final Board board, final Position from) {
		this.board = board;
		this.from = from;
		this.color = from == null ? null : board.getPieceColor(from);
	}
	
	public abstract List<Move> possibleMovesWithoutCheckVerification();

	public abstract PieceType pieceType();

	public List<Move> possibleMoves() {
		List<Move> possibleMoves = possibleMovesWithoutCheckVerification();
		
		for(Move m : new ArrayList<>(possibleMoves))
			if(!canMoveTo(board, m))
				possibleMoves.remove(m);
		
		return possibleMoves;
	}
	
	private boolean canMoveTo(Board board, Move m) {
		try {
			Position from = m.from();
			Position to = m.to();
			
			Board clone = board.clone();
			Piece piece = clone.getPiece(from);
			
			clone.put(piece, to);
			clone.clear(from);
			piece.setFirstMove(false);
			aditionalMoves(clone, from, to);

			for(Block b : clone) {
				Piece p = b.piece();
				if(p.color() == piece.color())
					continue;
				
				for(Move move : RuleFactory.getRule(clone, b.position()).possibleMovesWithoutCheckVerification()) {
					Piece maybeKing = clone.getPiece(move.to());
					if(maybeKing.type() == KING)
						return false;
				}
			}
		} catch (PromotionException e) {} catch (RuntimeException e) {
 			return false;
		}
		return true;
	}
	
	protected Piece getPiece(Board board, Position position) {
		return board.find(position).piece();
	}

	protected Piece getPiece(Board board, char col, int row) {
		return getPiece(board, new Position(col, row));
	}

	protected Move newMove(final char colFrom, final int rowFrom, final char colTo, final int rowTo) {
		return newMove(new Position(colFrom, rowFrom), colTo, rowTo);
	}

	protected Move newMove(final Position from, final char colTo, final int rowTo) {
		return new Move(from, new Position(colTo, rowTo));
	}

	public Move move(Position to) throws PromotionException {
		Move move = new Move(from, to);
		
		if(!possibleMoves().contains(move))
			throw new RuntimeException(getMessage(ILLEGAL_MOVE));
		
		Piece piece = board.getPiece(from);
		board.put(piece, to);
		board.clear(from);
		piece.setFirstMove(false);
		aditionalMoves(board, from, to);
		
		return move;
	}

	protected void aditionalMoves(Board board, Position from, Position to) throws PromotionException {}
	
	protected void addMove(int col, int row) {
		Piece anotherPiece = getPiece(board, (char)col, row);
		
		if (canMove(anotherPiece, col))
			possibleMoves.add(newMove(from.col(), from.row(), (char)col, row));
		
		if (anotherPiece.color() != NULL)
			throw new NoMoreMovesForThisDirection();
	}

	protected boolean canMove(Piece anotherPiece, int col) {
		return anotherPiece.color() != color;
	}
}
