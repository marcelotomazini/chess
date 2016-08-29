package com.nullpointergames.boardgames;

import java.util.ArrayList;

public class Board extends ArrayList<Block> {

	private static final long serialVersionUID = 1L;

	public Board(boolean rotate) {
		createBlocks(rotate);
	}

	public void put(Piece piece, Position position) {
		find(position).piece(piece);
	}

	public Block find(Position position) {
		for (Block block : this)
			if (block.position().equals(position))
				return block;

		throw new RuntimeException();
	}

	private void createBlocks(boolean rotate) {
		if(rotate)
			for (int row = 1; row <= 8; row++)
				addNewBlock(row);
		else
			for (int row = 8; row >= 1; row--)
				addNewBlock(row);
	}

	private void addNewBlock(int row) {
		for (char col = 'a'; col <= 'h'; col++)
			add(new Block(new Position(col, row)));
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		int i = 0;
		for (Block block : this) {
			final Piece piece = block.piece();
			sb.append(piece.unicode());
			i++;
			if (i == 8) {
				i = 0;
				sb.append('\n');
			}
		}
		return sb.toString();
	}

	public PieceColor getPieceColor(Position position) {
		return getPiece(position).color();
	}

	public Piece getPiece(Position position) {
		return find(position).piece();
	}

	public void clear(Position position) {
		find(position).clear();
	}
	
	public Board clone() {
		Board newBoard = new Board(false);
		for(Block block : this)
			for(Block newBlock : newBoard)
				if(newBlock.position().equals(block.position())) {
					Piece piece = block.piece();
					Piece newPiece = new Piece(piece.type(), piece.color());
					newPiece.setFirstMove(piece.isFirstMove());
					newBlock.piece(newPiece);
				}
		
		return newBoard;
	}
}
