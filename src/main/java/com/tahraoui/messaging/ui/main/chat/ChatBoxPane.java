package com.tahraoui.messaging.ui.main.chat;

import com.tahraoui.messaging.model.Message;
import com.tahraoui.messaging.ui.main.chat.message.AbstractMessageItem;

import javax.swing.Box;
import javax.swing.JScrollPane;

public class ChatBoxPane extends JScrollPane {

	private final Box messagesBox;

	public ChatBoxPane() {
		super(VERTICAL_SCROLLBAR_NEVER, HORIZONTAL_SCROLLBAR_NEVER);

		this.messagesBox = Box.createVerticalBox();

		setViewportView(this.messagesBox);
		getVerticalScrollBar().setUnitIncrement(16);
	}

	public void addMessage(AbstractMessageItem messageItem) { messagesBox.add(messageItem); }
}
