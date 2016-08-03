package com.nullpointergames.boardgames.chess.rules;

import static com.nullpointergames.boardgames.chess.PieceType.KNIGHT;

import java.util.List;

import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;
import com.nullpointergames.boardgames.chess.PieceType;

public class KnightRule extends Rule {
	
	public KnightRule(Board board, Position from) {
		super(board, from);
	}

	@Override
	public List<Position> possibleMovesWithoutCheckVerification() {
		int colTo = from.col() + 1;
		int rowTo = from.row() + 2;
		addPosition(colTo, rowTo);

		colTo = from.col() + 1;
		rowTo = from.row() - 2;
		addPosition(colTo, rowTo);

		colTo = from.col() - 1;
		rowTo = from.row() + 2;
		addPosition(colTo, rowTo);

		colTo = from.col() - 1;
		rowTo = from.row() - 2;
		addPosition(colTo, rowTo);

		colTo = from.col() + 2;
		rowTo = from.row() + 1;
		addPosition(colTo, rowTo);

		colTo = from.col() + 2;
		rowTo = from.row() - 1;
		addPosition(colTo, rowTo);

		colTo = from.col() - 2;
		rowTo = from.row() + 1;
		addPosition(colTo, rowTo);

		colTo = from.col() - 2;
		rowTo = from.row() - 1;
		addPosition(colTo, rowTo);

		return possibleMoves;
	}

	@Override
	protected void addPosition(int colTo, int rowTo) {
		try {
			super.addPosition(colTo, rowTo);
		} catch (RuntimeException e) {}
	}

	@Override
	public PieceType pieceType() {
		return KNIGHT;
	}

}
