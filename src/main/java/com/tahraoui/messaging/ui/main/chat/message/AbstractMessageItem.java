package com.tahraoui.messaging.ui.main.chat.message;

import com.tahraoui.jstx.panel.JSTXPanel;

import javax.swing.BoxLayout;

public abstract class AbstractMessageItem extends JSTXPanel {

	public AbstractMessageItem() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

	}
}
