package com.tahraoui.messaging.ui;

import com.tahraoui.jstx.JSTXConstants;
import com.tahraoui.jstx.panel.JSTXPanel;
import com.tahraoui.messaging.ui.home.HomePanel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.Random;

public class MainContainer extends JSTXPanel implements JSTXConstants {

	private static final String HOME = "HOME", CHATBOX = "CHATBOX";

	private final CardLayout layout;
	private final HomePanel homePanel;

	public MainContainer() {
		super();

		this.layout = new CardLayout();
		this.homePanel = new HomePanel();

		setupLayout();
	}

	private void setupLayout() {
		setLayout(layout);
		var otherPanel = new JSTXPanel();

		add(homePanel, HOME);
		add(otherPanel, CHATBOX);

		layout.show(this, HOME);
	}

	private void setupLayoutX() {
		var box = Box.createVerticalBox();
		box.setOpaque(true);
		box.setBackground(Color.RED);
		var random = new Random();


		for (int i = 0; i < 50; i++) {
			var label = new JLabel("Panel %d".formatted(i));
			var sent = random.nextBoolean();
			label.setBackground(sent ? Color.GREEN : Color.BLUE);
			label.setOpaque(true);
			label.setHorizontalAlignment(sent ? JLabel.RIGHT : JLabel.LEFT);

			var panel = new JSTXPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

			if (sent) {
				panel.add(Box.createHorizontalGlue());
				panel.add(label);
			}
			else {
				panel.add(label);
				panel.add(Box.createHorizontalGlue());
			}
			box.add(panel);
		}

		var scrollPane = new JScrollPane(box, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		var verticalScrollBar = scrollPane.getVerticalScrollBar();
		verticalScrollBar.setUnitIncrement(16);

		add(scrollPane, BorderLayout.CENTER);
	}
}
