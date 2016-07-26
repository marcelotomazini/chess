package com.nullpointergames.boardgames.chess.rules;

import static com.nullpointergames.boardgames.chess.PieceType.ROOK;
import static java.lang.Math.abs;

import java.util.List;

import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.Move;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;
import com.nullpointergames.boardgames.chess.PieceType;
import com.nullpointergames.boardgames.chess.exceptions.NoMoreMovesForThisDirection;

public class RookRule extends Rule {

	public RookRule(Board board, Position from) {
		super(board, from);
	}

	@Override
	public PieceType pieceType() {
		return ROOK;
	}

	@Override
	public List<Move> possibleMovesWithoutCheckVerification() {
		for (int i = from.row() + 1; i <= LAST_LINE_IN_THE_BOARD; i++)
			try {
				addMove(from.col(), i);
			} catch (NoMoreMovesForThisDirection e) {
				break;
			}
		
		for (int i = from.row() - 1; i >= FIRST_LINE_IN_THE_BOARD; i--)
			try {
				addMove(from.col(), i);
			} catch (NoMoreMovesForThisDirection e) {
				break;
			}

		for (int i = abs(from.col()) + 1; i <= abs(LAST_COLUMN_IN_THE_BOARD); i++)
			try {
				addMove((char)i, from.row());
			} catch (NoMoreMovesForThisDirection e) {
				break;
			}
			
		for (int i = abs(from.col()) - 1; i >= abs(FIRST_COLUMN_IN_THE_BOARD); i--)
			try {
				addMove((char)i, from.row());
			} catch (NoMoreMovesForThisDirection e) {
				break;
			}

		return possibleMoves;
	}
}
