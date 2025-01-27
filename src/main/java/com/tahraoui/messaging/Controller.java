package com.tahraoui.messaging;

import com.tahraoui.jstx.init.JSTXBootstrapper;
import com.tahraoui.jstx.panel.JSTXPanel;
import com.tahraoui.jstx.panel.JSTXRoundPanel;
import com.tahraoui.jstx.util.Config;
import com.tahraoui.messaging.ui.MainPanel;
import com.tahraoui.messaging.ui.HeaderPanel;
import com.tahraoui.messaging.ui.SidebarPanel;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class Controller extends JSTXRoundPanel {

	private static final double HEADER_WEIGHT = 0.05, CONTENT_WEIGHT = 0.95, SIDEBAR_WEIGHT = 0.25, MAIN_WEIGHT = 0.75;

	public Controller() {
		super(Config.getInstance().getWindowRadius(), new GradientPaint(0,0,new Color(0xeeeeee),0,0, new Color(0xbbbbbb)),true);
		setupLayout();
	}

	private void setupLayout() {
		setLayout(new GridBagLayout());
		var gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0,0,0,0);


		var headerPanel = new HeaderPanel();
		var mainPanel = new MainPanel();
		var sidebarPanel = new SidebarPanel();

		var contentPanel = new JSTXPanel(new GridBagLayout());

		gbc.gridx = 0; gbc.gridy = 0;
		gbc.weightx = SIDEBAR_WEIGHT; gbc.weighty = 1;
		contentPanel.add(sidebarPanel, gbc);

		gbc.gridx = 1; gbc.gridy = 0;
		gbc.weightx = MAIN_WEIGHT; gbc.weighty = 1;
		contentPanel.add(mainPanel, gbc);

		gbc.gridx = 0; gbc.gridy = 0;
		gbc.weightx = 1; gbc.weighty = HEADER_WEIGHT;
		add(headerPanel, gbc);

		gbc.gridx = 0; gbc.gridy = 1;
		gbc.weightx = 1; gbc.weighty = CONTENT_WEIGHT;
		add(contentPanel, gbc);
	}

	public static void main() {
		JSTXBootstrapper.getInstance().init(new Controller());

	}
}
