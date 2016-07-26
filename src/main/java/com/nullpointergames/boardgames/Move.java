package com.nullpointergames.boardgames;

import static java.lang.String.format;

public class Move {

	private final Position from;
	private final Position to;
	
	public Move(final Position from, final Position to) {
		this.from = from;
		this.to = to;
	}
	
	public Position from() {
		return from;
	}

	public Position to() {
		return to;
	}

	@Override
	public boolean equals(Object obj) {
		Move anotherMove = (Move)obj;
		return anotherMove.from.equals(from) && anotherMove.to.equals(to);
	}
	
	@Override
	public String toString() {
		return format("%s -> %s", from, to);
	}
}
