package com.tahraoui.messaging.ui;

import com.tahraoui.jstx.JSTXConstants;
import com.tahraoui.jstx.container.JSTXPanel;
import com.tahraoui.messaging.backend.ConnectionService;
import com.tahraoui.messaging.ui.listener.NavigationListener;
import com.tahraoui.messaging.ui.main.ChatPanel;
import com.tahraoui.messaging.ui.main.HomePanel;

import java.awt.CardLayout;

public class MainContainer extends JSTXPanel implements JSTXConstants, NavigationListener {

	private enum Panel {
		HOME, CHAT
	}

	private final CardLayout layout;

	private final HomePanel homePanel;
	private final ChatPanel chatPanel;

	public MainContainer() {
		ConnectionService.getInstance().addNavigationListener(this);

		this.layout = new CardLayout();

		this.homePanel = new HomePanel();
		this.chatPanel = new ChatPanel();

		setupLayout();
	}

	private void setupLayout() {
		setLayout(layout);

		add(homePanel, Panel.HOME.name());
		add(chatPanel, Panel.CHAT.name());

		layout.show(this, String.valueOf(Panel.HOME));
	}

	@Override
	public void switchToHome() { switchPanel(Panel.HOME); }

	@Override
	public void switchToChat() { switchPanel(Panel.CHAT); }

	private void switchPanel(Panel panel) {
		layout.show(this, panel.name());
		revalidate();
		repaint();
	}
}
