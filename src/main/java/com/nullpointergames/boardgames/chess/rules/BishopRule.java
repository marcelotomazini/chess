package com.nullpointergames.boardgames.chess.rules;

import static com.nullpointergames.boardgames.chess.PieceType.BISHOP;
import static java.lang.Math.abs;

import java.util.List;

import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.Move;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;
import com.nullpointergames.boardgames.chess.PieceType;
import com.nullpointergames.boardgames.chess.exceptions.NoMoreMovesForThisDirection;

public class BishopRule extends Rule {

	public BishopRule(Board board, Position from) {
		super(board, from);
	}

	@Override
	public PieceType pieceType() {
		return BISHOP;
	}
	
	@Override
	public List<Move> possibleMovesWithoutCheckVerification() {
		int row = from.row() + 1;
		int col = abs(from.col()) + 1;
		while(row <= LAST_LINE_IN_THE_BOARD && col <= abs(LAST_COLUMN_IN_THE_BOARD)) {
			try {
				addMove(col, row);
			} catch (NoMoreMovesForThisDirection e) {
				break;
			}
			col++;
			row++;
		}

		row = from.row() + 1;
		col = abs(from.col()) - 1;
		while(row <= LAST_LINE_IN_THE_BOARD && col >= abs(FIRST_COLUMN_IN_THE_BOARD)) {
			try {
				addMove(col, row);
			} catch (NoMoreMovesForThisDirection e) {
				break;
			}
			col--;
			row++;
		}

		row = from.row() - 1;
		col = abs(from.col()) + 1;
		while(row >= FIRST_LINE_IN_THE_BOARD && col <= abs(LAST_COLUMN_IN_THE_BOARD)) {
			try {
				addMove(col, row);
			} catch (NoMoreMovesForThisDirection e) {
				break;
			}
			col++;
			row--;
		}
		
		row = from.row() - 1;
		col = abs(from.col()) - 1;
		while(row >= FIRST_LINE_IN_THE_BOARD && col >= abs(FIRST_COLUMN_IN_THE_BOARD)) {
			try {
				addMove(col, row);
			} catch (NoMoreMovesForThisDirection e) {
				break;
			}
			col--;
			row--;
		}
		
		return possibleMoves;
	}
}
