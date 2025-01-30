package com.tahraoui.messaging.ui.main.chat.message;

import com.tahraoui.jstx.panel.JSTXPanel;
import com.tahraoui.jstx.text.JSTXLabel;
import com.tahraoui.messaging.backend.data.response.MessageResponse;

import javax.swing.Box;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class UserMessageItem extends AbstractMessageItem {

	private final boolean received;
	private JSTXLabel senderLabel;
	private final JSTXPanel box;
	private final MessageResponse messageData;

	public UserMessageItem(boolean received, MessageResponse message) {
		this.received = received;
		this.messageData = message;

		this.box = new JSTXPanel(new GridBagLayout());
		var gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = received ? GridBagConstraints.NORTHWEST : GridBagConstraints.NORTHEAST;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;

		var messageLabel = new UserMessageLabel(received, message.content());

		if (received && message.senderName() != null && !message.senderName().isBlank()) {
			this.senderLabel = new JSTXLabel(message.senderName());
			this.senderLabel.setForeground(new Color(0x808080));
			this.senderLabel.setFont(this.senderLabel.getFont().deriveFont(12f));
			box.add(this.senderLabel, gbc);
			gbc.gridy++;
		}
		this.box.add(messageLabel, gbc);

		if (received) {
			add(Box.createHorizontalStrut(4));
			add(this.box);
			add(Box.createHorizontalGlue());
		}
		else {
			add(Box.createHorizontalGlue());
			add(this.box);
			add(Box.createHorizontalStrut(4));
		}
	}

	@Override
	public void setContinuous(boolean isContinuous) {
		if (!isContinuous || senderLabel == null) return;
		this.box.remove(senderLabel);
		this.senderLabel = null;
		this.box.revalidate();
		this.box.repaint();
	}

	@Override
	public boolean isContinuous(AbstractMessageItem other) {
		if (other instanceof UserMessageItem userMessageItem) return userMessageItem.messageData.senderId() == this.messageData.senderId();
		return false;
	}
}
