package com.tahraoui.messaging.ui;

import com.tahraoui.jstx.JSTXConstants;
import com.tahraoui.jstx.panel.JSTXPanel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Random;

public class MainContainer extends JSTXPanel implements JSTXConstants {

	public MainContainer() {
		super(new BorderLayout(BASE_PADDING, BASE_PADDING));
		setBorder(new EmptyBorder(BASE_PADDING, BASE_PADDING, BASE_PADDING, BASE_PADDING));

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

		add(new JScrollPane(box, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
	}
}
