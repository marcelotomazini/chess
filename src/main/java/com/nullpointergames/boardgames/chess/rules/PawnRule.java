package com.nullpointergames.boardgames.chess.rules;

import static com.nullpointergames.boardgames.PieceColor.NULL;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.chess.PieceType.PAWN;
import static java.lang.Math.abs;
import static java.util.Arrays.asList;

import java.util.List;

import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;
import com.nullpointergames.boardgames.chess.PieceType;
import com.nullpointergames.boardgames.chess.exceptions.NoMoreMovesForThisDirection;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;

public class PawnRule extends Rule {

	public PawnRule(Board board, Position from) {
		super(board, from);
	}

	@Override
	public PieceType pieceType() {
		return PAWN;
	}
	
	@Override
	protected void aditionalMoves(Board board, Position from, Position to) throws PromotionException {
		int positionToPromotion = board.getPieceColor(to) == WHITE ? 8 : 1;
		
		if(positionToPromotion == to.row())
			throw new PromotionException();
	}
	
	@Override
	protected boolean canMove(Piece anotherPiece, int col) {
		return (from.col() == col && anotherPiece.color() == NULL) ||
				(from.col() != col && anotherPiece.color() != NULL && anotherPiece.color() != color);
	}
	
	@Override
	public List<Position> possibleMovesWithoutCheckVerification() {
		Piece piece = board.getPiece(from);
		int moveDirection = piece.color() == WHITE ? 1 : -1;

		char colTo = from.col();
		int rowTo = from.row() + moveDirection;
		try {
			addPosition((int)colTo, rowTo);
			if(piece.isFirstMove())
				addPosition((int)colTo, rowTo + moveDirection);
		} catch (NoMoreMovesForThisDirection e) {}
		
		colTo = (char) (from.col() - 1);
		rowTo = from.row() + moveDirection;
		try {
			addPosition((int)colTo, rowTo);
		} catch (RuntimeException e) {}
		
		colTo = (char) (from.col() + 1);
		rowTo = from.row() + moveDirection;
		try {
			addPosition((int)colTo, rowTo);
		} catch (RuntimeException e) {}

		return possibleMoves;
	}

	public void promoteTo(Board board, PieceType pieceType) {
		Position position = findPawnPositionToPromote(board);
		Piece promotedPiece = new Piece(pieceType, board.getPieceColor(position));
		board.put(promotedPiece, position);
	}

	@Override
	protected void addPosition(int colTo, int rowTo) {
		try {
			super.addPosition(colTo, rowTo);
		} catch (NoMoreMovesForThisDirection e) {
			throw e;
		} catch (RuntimeException e) {}
	}
	
	private Position findPawnPositionToPromote(Board board) {
		for(int line : asList(FIRST_LINE_IN_THE_BOARD, LAST_LINE_IN_THE_BOARD)) {
			Position position = findPawnPosition(line);
			if(position != null)
				return position;
		}
		
		return null;
	}

	private Position findPawnPosition(int line) {
		for(int i = abs(FIRST_COLUMN_IN_THE_BOARD); i <= abs(LAST_COLUMN_IN_THE_BOARD); i++) {
			Position position = new Position((char)i, line);
			Piece piece = board.getPiece(position);
			if(piece.type() == PAWN)
				return position;
		}
		
		return null;
	}
}
