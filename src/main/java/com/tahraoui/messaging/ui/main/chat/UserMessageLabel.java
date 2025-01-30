package com.tahraoui.messaging.ui.main.chat;

import com.tahraoui.jstx.text.JSTXLabel;

import java.awt.Color;

public class UserMessageLabel extends JSTXLabel {

	public UserMessageLabel(boolean sent, String text) {
		super(text);
		setOpaque(true);
		setBackground(sent ? Color.GREEN : Color.BLUE);
		setHorizontalAlignment(sent ? RIGHT : LEFT);
	}
}
