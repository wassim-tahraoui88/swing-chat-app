package com.tahraoui.messaging;

import com.tahraoui.jstx.JSTXConstants;
import com.tahraoui.jstx.init.JSTXBootstrapper;
import com.tahraoui.jstx.panel.JSTXPanel;
import com.tahraoui.jstx.panel.JSTXRoundPanel;
import com.tahraoui.jstx.util.Config;
import com.tahraoui.messaging.ui.MainContainer;
import com.tahraoui.messaging.ui.HeaderPanel;
import com.tahraoui.messaging.ui.SidebarContainer;

import javax.swing.OverlayLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Insets;

public class Controller extends JSTXRoundPanel implements JSTXConstants {

	private final Insets insets = new Insets(BASE_PADDING, BASE_PADDING, BASE_PADDING, BASE_PADDING);

	public Controller() {
		super(Config.getInstance().getWindowRadius(), new GradientPaint(0,0,new Color(0xeeeeee),0,0, new Color(0xbbbbbb)),true);
		setupLayout();
	}

	private void setupLayout() {
		setLayout(new BorderLayout(0, BASE_PADDING));

		var headerPanel = new HeaderPanel();
		var mainPanel = new MainContainer();
		var sidebarPanel = new SidebarContainer();

		var contentPanel = new JSTXPanel();
		contentPanel.setLayout(new OverlayLayout(contentPanel));

//		contentPanel.add(sidebarPanel);
		contentPanel.add(mainPanel);

		add(headerPanel, BorderLayout.PAGE_START);
		add(contentPanel, BorderLayout.CENTER);
		add(new JSTXPanel(), BorderLayout.PAGE_END);
	}

	@Override
	public Insets getInsets() { return insets; }

	public static void main() {
		JSTXBootstrapper.getInstance().init(Controller::new);

	}
}
