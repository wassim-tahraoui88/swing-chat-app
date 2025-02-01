package com.tahraoui.messaging.ui.main.chat;

import com.tahraoui.jstx.JSTXConstants;
import com.tahraoui.jstx.container.JSTXPanel;
import com.tahraoui.jstx.scroll.JSTXScrollPane;
import com.tahraoui.messaging.ui.main.chat.message.AbstractMessageItem;

import javax.swing.JComponent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class ChatBoxPane extends JSTXScrollPane implements JSTXConstants {

	private final Insets insets = new Insets(BASE_PADDING, MIN_PADDING, BASE_PADDING, MIN_PADDING);
	private final JSTXPanel messagesBox;
	private final GridBagConstraints gbc, fillerGbc;
	private final JComponent filler;
	private AbstractMessageItem lastMessageItem;

	public ChatBoxPane() {
		super(VERTICAL_SCROLLBAR_NEVER, HORIZONTAL_SCROLLBAR_NEVER);

		this.messagesBox = new JSTXPanel(new GridBagLayout());

		this.filler = new JSTXPanel(true);

		this.gbc = new GridBagConstraints();
		this.fillerGbc = new GridBagConstraints();
		setupConstraints();

		setViewportView(this.messagesBox);

		this.messagesBox.add(filler, gbc);
	}

	public void addMessage(AbstractMessageItem messageItem) {
		messagesBox.remove(filler);

		var isContinuous = lastMessageItem != null && messageItem.isContinuous(lastMessageItem);
		gbc.insets = isContinuous ? new Insets(0, 0, 0, 0) : new Insets(MIN_PADDING, 0, 0, 0);

		gbc.gridy++;
		messageItem.setContinuous(isContinuous);
		messagesBox.add(messageItem, gbc);

		addFiller();

		messagesBox.revalidate();
		messagesBox.repaint();

		lastMessageItem = messageItem;
	}

	private void addFiller() {
		fillerGbc.gridy = gbc.gridy + 1;
		messagesBox.add(filler, fillerGbc);
	}
	private void setupConstraints() {
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;

		fillerGbc.fill = GridBagConstraints.VERTICAL;
		fillerGbc.insets = new Insets(0,0,0,0);
		fillerGbc.gridx = fillerGbc.gridy = 0;
		fillerGbc.weighty = 1;
	}

	@Override
	public Insets getInsets() { return insets; }
}
