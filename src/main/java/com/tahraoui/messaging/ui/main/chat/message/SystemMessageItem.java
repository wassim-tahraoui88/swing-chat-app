package com.tahraoui.messaging.ui.main.chat.message;

import com.tahraoui.messaging.model.Message;

import javax.swing.JLabel;
import java.awt.Color;

public class SystemMessageItem extends AbstractMessageItem {

	public SystemMessageItem(Message message) {
		var label = new JLabel(message.getMessage());

		label.setForeground(new Color(0x888888));

		add(label);

	}
}

