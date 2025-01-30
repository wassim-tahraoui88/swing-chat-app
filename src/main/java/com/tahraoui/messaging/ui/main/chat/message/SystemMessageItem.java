package com.tahraoui.messaging.ui.main.chat.message;

import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class SystemMessageItem extends AbstractMessageItem {

	public SystemMessageItem(String message) {
		var label = new JLabel(message);
		label.setFont(label.getFont().deriveFont(Font.ITALIC));

		label.setForeground(new Color(0x808080));

		add(Box.createHorizontalGlue());
		add(label);
		add(Box.createHorizontalGlue());
	}

	@Override
	public boolean isContinuous(AbstractMessageItem other) { return false; }

	@Override
	public void setContinuous(boolean isContinuous) {}
}

