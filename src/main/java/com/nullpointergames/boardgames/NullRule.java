package com.nullpointergames.boardgames;

import static com.nullpointergames.boardgames.chess.PieceType.NULL;
import static java.util.Collections.emptyList;

import java.util.List;

import com.nullpointergames.boardgames.chess.PieceType;

public class NullRule extends Rule {

	public NullRule(Board board, Position from) {
		super(board, from);
	}

	@Override
	public List<Move> possibleMovesWithoutCheckVerification() {
		return emptyList();
	}

	@Override
	public PieceType pieceType() {
		return NULL;
	}

}
