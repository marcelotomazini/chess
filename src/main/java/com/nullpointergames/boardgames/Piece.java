package com.nullpointergames.boardgames;

import static com.nullpointergames.boardgames.PieceColor.WHITE;

import com.nullpointergames.boardgames.chess.PieceType;

public class Piece {

	private PieceColor color;
	private PieceType type;
	private boolean firstMove = true;

	public Piece(PieceType type, PieceColor color) {
		this.type = type;
		this.color = color;

	}

	public PieceType type() {
		return type;
	}
	
	// public static boolean underAttack(final Color color, final char colTo,
	// final int rowTo, final Game game) {
	// for (int row = 1; row <= 8; row++)
	// for (char col = 'a'; col <= 'h'; col++) {
	// final Piece piece = game.getBoard().get(col, row);
	// if (piece != null && !piece.color().equals(color)) {
	// final Move move = new Move(piece.code(), col, row, colTo, rowTo);
	// final Move.Type moveType = piece.canMove(move, game, new
	// EmptyPiece()).getMoveType();
	// if (moveType.equals(Move.Type.ATTACK) ||
	// moveType.equals(Move.Type.MOVENMENT) ||
	// moveType.equals(Move.Type.PROMOTION) ||
	// moveType.equals(Move.Type.ENPASSANT))
	// return true;
	// }
	// }
	// return false;
	// }

	public boolean isFirstMove() {
		return firstMove;
	}

	public PieceColor color() {
		return color;
	}

	// protected abstract char _code();

	// public char unicode() {
	// switch (code()) {
	// case 'K':
	// return '\u2654';
	// case 'Q':
	// return '\u2655';
	// case 'R':
	// return '\u2656';
	// case 'B':
	// return '\u2657';
	// case 'N':
	// return '\u2658';
	// case 'P':
	// return '\u2659';
	//
	// case 'k':
	// return '\u265A';
	// case 'q':
	// return '\u265B';
	// case 'r':
	// return '\u265C';
	// case 'b':
	// return '\u265D';
	// case 'n':
	// return '\u265E';
	// case 'p':
	// return '\u265F';
	// }
	// return '?';
	// }

	// public abstract MoveResponse canMove(Move move, Game game, Piece
	// pieceTo);

	// public char code() {
	// return color.equals(Color.WHITE) ?
	// Character.toUpperCase(_code()) : _code();
	// }

	public char unicode() {
		return color == WHITE ? type.getWhiteUnicode() : type.getBlackUnicode();
	}

	// public abstract List<Move> moves(char col, int row, Game game);

	// @Override
	// public String toString() {
	// return String.format("%s %s", color(), code());
	// }
	//
	// public String getMessageCode() {
	// return String.valueOf(code());
	// }

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;
		
		Piece anotherPiece = (Piece) obj;
		return anotherPiece.type == type &&
				anotherPiece.color == color;
	}

	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}
}
