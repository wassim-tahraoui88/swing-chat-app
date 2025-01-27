package com.tahraoui.messaging.backend;

import com.tahraoui.messaging.ui.listener.NavigationListener;

import java.awt.EventQueue;
import java.util.LinkedList;
import java.util.List;

public class Navigator {

	private final List<NavigationListener> navigationListeners;

	public Navigator() {
		this.navigationListeners = new LinkedList<>();

	}

	public void add(NavigationListener listener) {
		navigationListeners.add(listener);

	}

	public void switchToHome() {
		EventQueue.invokeLater(() -> {
			for (var listener : navigationListeners) listener.switchToHome();
		});
	}
	public void switchToChatbox() {
		EventQueue.invokeLater(() -> {
			for (var listener : navigationListeners) listener.switchToChatbox();
		});
	}
}
