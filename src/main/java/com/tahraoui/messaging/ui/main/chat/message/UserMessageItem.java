package com.tahraoui.messaging.ui.main.chat.message;

import com.tahraoui.jstx.panel.JSTXPanel;
import com.tahraoui.jstx.text.JSTXLabel;
import com.tahraoui.messaging.backend.data.response.MessageResponse;
import com.tahraoui.messaging.ui.main.chat.UserMessageLabel;

import javax.swing.Box;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class UserMessageItem extends AbstractMessageItem {

	public UserMessageItem(boolean received, MessageResponse message) {
		var box = new JSTXPanel(new GridBagLayout());
		var gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = received ? GridBagConstraints.NORTHWEST : GridBagConstraints.NORTHEAST;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;

		var senderLabel = new JSTXLabel(message.senderName());
		senderLabel.setForeground(new Color(0x808080));
		senderLabel.setFont(senderLabel.getFont().deriveFont(12f));

		var messageLabel = new UserMessageLabel(received, message.content());

		if (received && message.senderName() != null && !message.senderName().isBlank()) {
			box.add(senderLabel, gbc);
			gbc.gridy++;
		}
		box.add(messageLabel, gbc);

		if (received) {
			add(Box.createHorizontalStrut(5));
			add(box);
			add(Box.createHorizontalGlue());
		}
		else {
			add(Box.createHorizontalGlue());
			add(box);
			add(Box.createHorizontalStrut(5));
		}
	}
}
