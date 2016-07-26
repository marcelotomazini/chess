package com.nullpointergames.boardgames.utils;

import java.util.ResourceBundle;

public class MessageUtils {

	public static String getMessage(String key) {
		ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle");
		return messages.getString(key);
	}
	
	public static final String ILLEGAL_MOVE = "illegalMove";
	public static final String IT_IS_NOT_YOUR_TURN = "itIsNotYourTurn";
	public static final String OOPS = "oops";
	public static final String CHECK = "check";
	public static final String CHECKMATE = "checkmate";
	public static final String GAME_OVER = "gameOver";
	public static final String YOU_WON = "youWon";
	public static final String YOU_LOST = "youLost";
}
