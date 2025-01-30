package com.tahraoui.messaging.ui.main.chat;

import com.tahraoui.jstx.panel.JSTXPanel;
import com.tahraoui.jstx.text.JSTXLabel;
import com.tahraoui.messaging.ui.main.chat.message.AbstractMessageItem;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class ChatBoxPane extends JScrollPane {

	private final JSTXPanel messagesBox;
	private final GridBagConstraints gbc;
	private final JComponent filler;

	public ChatBoxPane() {
		super(VERTICAL_SCROLLBAR_NEVER, HORIZONTAL_SCROLLBAR_NEVER);

		this.messagesBox = new JSTXPanel(new GridBagLayout());

		this.filler = new JSTXPanel();

		this.gbc = new GridBagConstraints();
		setupConstraints();

		setViewportView(this.messagesBox);
		getVerticalScrollBar().setUnitIncrement(16);

		this.messagesBox.add(filler, gbc);
	}

	private void addFiller() {
		var fillerGbc = new GridBagConstraints();
		fillerGbc.gridx = 0;
		fillerGbc.gridy = gbc.gridy + 1;
		fillerGbc.weighty = 1;
		fillerGbc.fill = GridBagConstraints.VERTICAL;
		messagesBox.add(filler, fillerGbc);
	}

	private void setupConstraints() {
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0,0,8,0);
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 0;
		gbc.gridy = 0;
	}

	public void addMessage(AbstractMessageItem messageItem) {
		messagesBox.remove(filler);

		gbc.gridy++;
		messagesBox.add(messageItem, gbc);
		addFiller();

		messagesBox.revalidate();
		messagesBox.repaint();
	}
}
