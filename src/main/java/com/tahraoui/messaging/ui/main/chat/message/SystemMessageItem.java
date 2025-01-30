package com.tahraoui.messaging.ui.main.chat.message;

import javax.swing.JLabel;
import java.awt.Color;

public class SystemMessageItem extends AbstractMessageItem {

	public SystemMessageItem(String message) {
		var label = new JLabel(message);

		label.setForeground(new Color(0x888888));

		add(label);

	}
}

