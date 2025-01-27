package com.tahraoui.messaging.ui;

import com.tahraoui.jstx.animation.AnimatorAdapter;
import com.tahraoui.jstx.animation.JSTXAnimator;
import com.tahraoui.jstx.animation.interpolation.CubicInterpolations;
import com.tahraoui.jstx.button.JSTXButton;
import com.tahraoui.jstx.panel.JSTXPanel;
import com.tahraoui.messaging.ui.sidebar.SidebarPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class SidebarContainer extends JSTXPanel {

	private static final double SIDEBAR_WEIGHT = 0.25;

	private final JSTXAnimator animator;
	private final SidebarPanel panel;
	private final JSTXPanel spacePanel;
	private boolean isOpening;

	public SidebarContainer() {
		this.isOpening = false;

		this.animator = new JSTXAnimator(2000);
		this.animator.setInterpolation(new CubicInterpolations.EaseOut());

		var button = new JSTXButton("Sidebar Toggle");
		button.addActionListener(_ -> {
			isOpening = !isOpening;
			animator.start();
			System.out.println("Toggled");
		});
		this.panel = new SidebarPanel();
		this.spacePanel = new JSTXPanel();
		this.spacePanel.add(button);

		setupLayout();
	}

	private void setupLayout() {
		var layout = new GridBagLayout();
		setLayout(layout);

		var gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridy = 0; gbc.weighty = 1;

		gbc.gridx = 0; gbc.weightx = 0;

		add(panel, gbc);

		gbc.gridx = 1; gbc.weightx = 1;
		add(spacePanel, gbc);

		this.animator.addAnimation(new AnimatorAdapter() {
			@Override
			public void onStart() { }
			@Override
			public void onTick(float fraction) {
				var weight = (isOpening ? fraction : 1f - fraction) * SIDEBAR_WEIGHT;
				gbc.weightx = weight;
				layout.setConstraints(panel, gbc);
				gbc.weightx = 1f - weight;
				layout.setConstraints(spacePanel, gbc);

				revalidate();
				repaint();
			}
			@Override
			public void onStop() { }
		},true);
	}
}
