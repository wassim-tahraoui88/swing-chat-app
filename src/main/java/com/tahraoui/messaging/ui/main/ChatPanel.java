package com.tahraoui.messaging.ui.main;

import com.tahraoui.jstx.panel.JSTXPanel;
import com.tahraoui.jstx.text.JSTXLabel;
import com.tahraoui.messaging.backend.ConnectionService;
import com.tahraoui.messaging.backend.data.response.MessageResponse;
import com.tahraoui.messaging.backend.data.response.SystemMessageResponse;
import com.tahraoui.messaging.model.Message;
import com.tahraoui.messaging.ui.listener.ChatBoxListener;
import com.tahraoui.messaging.ui.main.chat.ChatBoxPane;
import com.tahraoui.messaging.ui.main.chat.message.AbstractMessageItem;
import com.tahraoui.messaging.ui.main.chat.message.SystemMessageItem;
import com.tahraoui.messaging.ui.main.chat.message.UserMessageItem;

import java.awt.BorderLayout;

public class ChatPanel extends JSTXPanel implements ChatBoxListener {

	private final ChatBoxPane chatPane = new ChatBoxPane();

	public ChatPanel() {
		super(new BorderLayout());
		setupLayout();
	}

	private void setupLayout() {
		add(new JSTXLabel(ConnectionService.getInstance().getUsername()), BorderLayout.NORTH);
		add(chatPane, BorderLayout.CENTER);
	}

	private void addMessage(AbstractMessageItem messageItem) {
		chatPane.addMessage(messageItem);
		chatPane.revalidate();
		chatPane.repaint();
	}

	@Override
	public void receiveMessage(MessageResponse message) {
		var messageItem = new UserMessageItem(false, new Message(message.content()));
		addMessage(messageItem);
	}
	@Override
	public void receiveSystemMessage(SystemMessageResponse message) {
		var messageItem = new SystemMessageItem(new Message(message.content()));
		addMessage(messageItem);
	}
}

