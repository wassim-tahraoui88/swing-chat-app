package com.tahraoui.messaging.ui.main;

import com.tahraoui.jstx.panel.JSTXPanel;
import com.tahraoui.jstx.text.JSTXLabel;
import com.tahraoui.messaging.backend.ConnectionService;
import com.tahraoui.messaging.backend.data.response.MessageResponse;
import com.tahraoui.messaging.backend.data.response.SystemMessageResponse;
import com.tahraoui.messaging.ui.listener.ChatBoxListener;
import com.tahraoui.messaging.ui.main.chat.ChatBoxPane;
import com.tahraoui.messaging.ui.main.chat.ChatInputPane;
import com.tahraoui.messaging.ui.main.chat.message.AbstractMessageItem;
import com.tahraoui.messaging.ui.main.chat.message.SystemMessageItem;
import com.tahraoui.messaging.ui.main.chat.message.UserMessageItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.BorderLayout;

public class ChatPanel extends JSTXPanel implements ChatBoxListener {

	private static final Logger LOGGER = LogManager.getLogger(ChatPanel.class);

	private final ChatBoxPane chatBoxPane;
	private final ChatInputPane chatInputPane;

	public ChatPanel() {
		super(new BorderLayout());

		ConnectionService.getInstance().setChatBoxListener(this);

		this.chatBoxPane = new ChatBoxPane();
		this.chatInputPane = new ChatInputPane();

		setupLayout();
	}

	private void setupLayout() {
		add(new JSTXLabel(ConnectionService.getInstance().getUsername()), BorderLayout.NORTH);
		add(chatBoxPane, BorderLayout.CENTER);
		add(chatInputPane, BorderLayout.SOUTH);
	}

	private void addMessage(AbstractMessageItem messageItem) {
		chatBoxPane.addMessage(messageItem);
		chatBoxPane.revalidate();
		chatBoxPane.repaint();
	}

	@Override
	public void receiveMessage(MessageResponse message) {
		var connectionInstance = ConnectionService.getInstance();
		var messageItem = new UserMessageItem(message.senderId() != connectionInstance.getId(), message);
		addMessage(messageItem);
	}
	@Override
	public void receiveSystemMessage(SystemMessageResponse message) {
		var messageItem = new SystemMessageItem(message.content());
		addMessage(messageItem);
	}
}