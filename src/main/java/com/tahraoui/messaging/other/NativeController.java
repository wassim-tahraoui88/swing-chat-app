package com.tahraoui.messaging.other;


import com.tahraoui.jstx.panel.JSTXRoundPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class NativeController extends JSTXRoundPanel {

	private static final double HEADER_WEIGHT = 0.05, CONTENT_WEIGHT = 0.95, SIDEBAR_WEIGHT = 0.25, MAIN_WEIGHT = 0.75;

	public NativeController() {
		super(32);
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

		var contentPanel = new JPanel(new GridBagLayout());

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

	public static void main() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		var frame = new JFrame("Native Messaging");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setLocationByPlatform(true);
		frame.setBackground(new Color(0,0,0,0));
		frame.setContentPane(new NativeController());
		frame.setVisible(true);
	}
}
