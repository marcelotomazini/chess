package com.nullpointergames.boardgames.chess;

public enum PieceType {

	KING('\u2654', '\u265A'), 
	QUEEN('\u2655', '\u265B'), 
	BISHOP('\u2657', '\u265D'), 
	KNIGHT('\u2658', '\u265E'), 
	ROOK('\u2656', '\u265C'), 
	PAWN('\u2659', '\u265F'),
	NULL('.', '.');
	
	private final char whiteUnicode;
	private final char blackUnicode;

	PieceType(char white, char black) {
		this.whiteUnicode = white;
		this.blackUnicode = black;
	}

	public char getWhiteUnicode() {
		return whiteUnicode;
	}

	public char getBlackUnicode() {
		return blackUnicode;
	}
}
