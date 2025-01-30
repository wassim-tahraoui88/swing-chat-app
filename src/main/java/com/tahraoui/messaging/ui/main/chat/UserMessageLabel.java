package com.tahraoui.messaging.ui.main.chat;

import com.tahraoui.jstx.JSTXConstants;
import com.tahraoui.jstx.panel.JSTXPanel;
import com.tahraoui.jstx.text.JSTXLabel;
import com.tahraoui.jstx.util.GraphicsUtils;
import com.tahraoui.jstx.util.ThemeConfig;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class UserMessageLabel extends JSTXPanel implements JSTXConstants {

	private final Insets insets;
	private final JSTXLabel label;
	private int leftRadius, rightRadius;
	private final int borderWidth;
	private final boolean received;

	public UserMessageLabel(boolean received, String text) {
		this.borderWidth = 1;
		this.received = received;

		this.label = new JSTXLabel(text);
		var padding = 2;//borderWidth + BASE_PADDING;
		this.insets = new Insets(padding,padding,padding,padding);

		if (received) setRadii(0,16);
		else setRadii(16,0);

		var themeConfig = ThemeConfig.getInstance();
		setBackground(received ? themeConfig.getPrimaryColor() : themeConfig.getSecondaryColor());
		label.setForeground(received ? themeConfig.getPrimaryTextColor() : themeConfig.getSecondaryTextColor());

		add(label);

		configureTheme();
	}

	private void setRadii(int leftRadius, int rightRadius) {
		this.leftRadius = leftRadius;
		this.rightRadius = rightRadius;
		repaint();
	}

	@Override
	public void configureTheme() {
		var themeConfig = ThemeConfig.getInstance();
		themeConfig.addThemeListener(() -> {
			setBackground(received ? themeConfig.getPrimaryColor() : themeConfig.getSecondaryColor());
			label.setForeground(received ? themeConfig.getPrimaryTextColor() : themeConfig.getSecondaryTextColor());
		});
	}

	@Override
	public Insets getInsets() { return insets; }

	@Override
	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);

		var g2 = (Graphics2D) g.create();
		GraphicsUtils.setRenderingHints(g2);

		var area = createLeftCorners();
		area.intersect(createRightCorners());
		g2.setColor(getBackground());
		g2.fill(area);

		g2.setStroke(new BasicStroke(borderWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.setColor(getBackground().darker());
		g2.draw(area);

		g2.dispose();
	}

	private Area createLeftCorners() {
		var area = createCornerShape(leftRadius,0, getHeight());
		area.intersect(createCornerShape(leftRadius,0,0));
		return area;
	}
	private Area createRightCorners() {
		var width = getWidth();
		var area = createCornerShape(rightRadius, width, getHeight());
		area.intersect(createCornerShape(rightRadius, width,0));
		return area;
	}

	private Area createCornerShape(int radius, int x, int y) {
		var width = getWidth() - borderWidth * 2;
		var height = getHeight() - borderWidth * 2;
		var areaX = borderWidth;
		var areaY = borderWidth;

		var roundX = Math.min(width, radius);
		var roundY = Math.min(height, radius);
		var area = new Area(new RoundRectangle2D.Double(areaX, areaY, width, height, roundX, roundY));
		area.add(new Area(new Rectangle2D.Double(x == 0 ? roundX / 2f : areaX, areaY, width - roundX / 2f, height)));
		area.add(new Area(new Rectangle2D.Double(areaX, y == 0 ? roundY / 2f : areaY, width, height - roundY / 2f)));
		return area;
	}
}
