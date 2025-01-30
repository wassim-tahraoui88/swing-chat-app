package com.tahraoui.messaging.ui.main.chat.message;

import com.tahraoui.messaging.model.Message;
import com.tahraoui.messaging.ui.main.chat.UserMessageLabel;

import javax.swing.Box;

public class UserMessageItem extends AbstractMessageItem {

	public UserMessageItem(boolean sent, Message message) {
		var messageLabel = new UserMessageLabel(sent, message.getMessage());

		if (sent) {
			add(Box.createHorizontalGlue());
			add(messageLabel);
		}
		else {
			add(messageLabel);
			add(Box.createHorizontalGlue());
		}
	}
}
