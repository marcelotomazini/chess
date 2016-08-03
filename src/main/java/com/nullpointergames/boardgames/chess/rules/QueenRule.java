package com.nullpointergames.boardgames.chess.rules;

import static com.nullpointergames.boardgames.chess.PieceType.QUEEN;

import java.util.List;

import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;
import com.nullpointergames.boardgames.chess.PieceType;

public class QueenRule extends Rule {

	public QueenRule(Board board, Position from) {
		super(board, from);
	}

	@Override
	public PieceType pieceType() {
		return QUEEN;
	}
	
	@Override
	public List<Position> possibleMovesWithoutCheckVerification() {
		BishopRule bishopRule = new BishopRule(board, from);
		RookRule rookRule = new RookRule(board, from);
		
		List<Position> possibleMoves = bishopRule.possibleMovesWithoutCheckVerification();
		possibleMoves.addAll(rookRule.possibleMovesWithoutCheckVerification());
		
		return possibleMoves;
	}
}
