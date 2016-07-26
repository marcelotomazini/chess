package com.nullpointergames.boardgames.chess.rules;

import static com.nullpointergames.boardgames.NullPiece.nullPiece;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.chess.PieceType.KING;
import static java.lang.Math.abs;

import java.util.List;

import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.Move;
import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;
import com.nullpointergames.boardgames.chess.PieceType;
import com.nullpointergames.boardgames.chess.RuleFactory;

public class KingRule extends Rule {

	public KingRule(Board board, Position from) {
		super(board, from);
	}

	@Override
	public PieceType pieceType() {
		return KING;
	}
	
	@Override
	public void aditionalMoves(Board board, Position from, Position to) {
		if(abs(to.col() - from.col()) > 1) {
			char rookColFrom = from.col() - to.col() > 0 ? 'a' : 'h';
			char rookColTo = from.col() - to.col() > 0 ? 'd' : 'f';
			
			Piece rook = board.getPiece(new Position(rookColFrom, to.row()));
			rook.setFirstMove(false);
			board.put(rook, new Position(rookColTo, to.row()));
			board.clear(new Position(rookColFrom, to.row()));
		}
	}
	
	@Override
	public List<Move> possibleMovesWithoutCheckVerification() {
		char colTo = (char) (from.col() + 1);
		addMove(colTo, from.row());

		colTo = (char) (from.col() + 1);
		int rowTo = from.row() + 1;
		addMove(colTo, rowTo);

		colTo = (char) (from.col() + 1);
		rowTo = from.row() - 1;
		addMove(colTo, rowTo);

		colTo = (char) (from.col() - 1);
		addMove(colTo, from.row());

		colTo = (char) (from.col() - 1);
		rowTo = from.row() + 1;
		addMove(colTo, rowTo);

		colTo = (char) (from.col() - 1);
		rowTo = from.row() - 1;
		addMove(colTo, rowTo);

		rowTo = from.row() + 1;
		addMove(from.col(), rowTo);

		rowTo = from.row() - 1;
		addMove(from.col(), rowTo);
		
		if(isCastlingPossible(board, from)) {
			Piece piece = board.getPiece(from);
			int row = piece.color() == WHITE ? FIRST_LINE_IN_THE_BOARD : LAST_LINE_IN_THE_BOARD;

			Piece maybeRook = findKingSideRook(row);
			addMove(row, maybeRook, 1);
			
			maybeRook = findQueenSideRook(row);
			addMove(row, maybeRook, -1);
		}
		
		return possibleMoves;
	}

	private void addMove(int row, Piece maybeRook, int side) {
		if(maybeRook.type() != PieceType.ROOK)
			return;

		if(maybeRook.isFirstMove()) {
			Move newMove = newMove(from, (char)(abs(from.col()) + side), row);
			Piece piece = board.getPiece(from);
			if(!isThreatened(board, piece, newMove))
				addMove((char)(abs(from.col()) + side * 2), row);
		}
	}

	private Piece findQueenSideRook(int row) {
		for(int i = abs(from.col()) - 1; i >= abs(FIRST_COLUMN_IN_THE_BOARD); i--) {
			Piece piece = board.getPiece(new Position((char)i, row));
			if(!piece.equals(nullPiece()))
				return piece;
		}
		
		return nullPiece();
	}

	private Piece findKingSideRook(int row) {
		for(int i = abs(from.col()) + 1; i <= abs(LAST_COLUMN_IN_THE_BOARD); i++) {
			Piece piece = board.getPiece(new Position((char)i, row));
			if(!piece.equals(nullPiece()))
				return piece;
		}
		
		return nullPiece();
	}

	private boolean isCastlingPossible(Board board, Position from) {
		return board.getPiece(from).isFirstMove();
	}

	private void addMove(char colTo, int rowTo) {
		try {
			addMove((int)colTo,  rowTo);
		} catch (RuntimeException e) {}
	}

	private boolean isThreatened(Board board, Piece piece, Move newMove) {
		for(int row = FIRST_LINE_IN_THE_BOARD; row <= LAST_LINE_IN_THE_BOARD; row++)
			for(int col = abs('a'); col <= abs(LAST_COLUMN_IN_THE_BOARD); col++) {
				if(piece.color() == board.getPieceColor(new Position((char)col, row)))
					continue;
				
				Piece anotherPiece = board.getPiece(new Position((char)col, row));
				if(anotherPiece.type() == KING)
					continue;
				
				for(Move m : RuleFactory.getRule(board, new Position((char)col, row)).possibleMovesWithoutCheckVerification())
					if(m.to().equals(newMove.to()))
						return true;
			}
		return false;
	}
}
