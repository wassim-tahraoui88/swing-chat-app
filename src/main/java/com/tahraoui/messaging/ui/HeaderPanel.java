package com.tahraoui.messaging.ui;

import com.tahraoui.jstx.JSTXConstants;
import com.tahraoui.jstx.button.JSTXIconButton;
import com.tahraoui.jstx.extension.DragExtension;
import com.tahraoui.jstx.extension.MaximizeExtension;
import com.tahraoui.jstx.extension.MinimizeExtension;
import com.tahraoui.jstx.init.JSTXBootstrapper;
import com.tahraoui.jstx.panel.JSTXBoxH;
import com.tahraoui.jstx.panel.JSTXPanel;
import com.tahraoui.jstx.text.JSTXLabel;
import com.tahraoui.jstx.util.Config;
import com.tahraoui.jstx.util.SvgUtils;

import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.OverlayLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;

public class HeaderPanel extends JSTXPanel implements JSTXConstants {

	private static final int ICON_SIZE = 12;

	private final Insets insets = new Insets(BASE_PADDING, BASE_PADDING,BASE_PADDING, BASE_PADDING);

	private final JSTXLabel titleLabel;
	private final JSTXIconButton minimizeButton, maximizeButton, restoreButton, closeButton;

	public HeaderPanel() {
		super(new BorderLayout(BASE_PADDING,0));

		titleLabel = new JSTXLabel(Config.getInstance().getAppName(), JSTXLabel.CENTER);
		titleLabel.setFont(titleLabel.getFont().deriveFont(24f));

		minimizeButton = new JSTXIconButton(SvgUtils.getSvgIcon("/icons/header/minimize.svg", ICON_SIZE));
		maximizeButton = new JSTXIconButton(SvgUtils.getSvgIcon("/icons/header/maximize.svg", ICON_SIZE));
		restoreButton = new JSTXIconButton(SvgUtils.getSvgIcon("/icons/header/restore.svg", ICON_SIZE));
		closeButton = new JSTXIconButton(SvgUtils.getSvgIcon("/icons/header/close.svg", ICON_SIZE));

		closeButton.setBackground(Color.RED);

		setupLayout();
		attachExtensions();

		closeButton.addActionListener(_ -> JSTXBootstrapper.getInstance().exit());
	}

	@Override
	public Insets getInsets() { return insets; }

	private void setupLayout() {
		var maximizePanel = new JSTXPanel();
		maximizePanel.setLayout(new OverlayLayout(maximizePanel));
		maximizePanel.add(maximizeButton);
		maximizePanel.add(restoreButton);

		var buttonsPanel = new JSTXBoxH();
		buttonsPanel.addItem(minimizeButton);
		buttonsPanel.addItem(maximizePanel);
		buttonsPanel.addItem(closeButton);

		var separator = new JSeparator(JSeparator.HORIZONTAL);
		var separatorBox = Box.createVerticalBox();
		separatorBox.add(Box.createVerticalGlue());
		separatorBox.add(separator);
		separatorBox.add(Box.createVerticalGlue());

		add(titleLabel, BorderLayout.WEST);
		add(separatorBox, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.EAST);

		maximizeButton.setVisible(true);
		restoreButton.setVisible(false);
	}
	private void attachExtensions() {
		new DragExtension(this).attach();
		new MinimizeExtension(minimizeButton).attach();
		new MaximizeExtension(maximizeButton, maximizeButton, restoreButton).attach();
		new MaximizeExtension(restoreButton, maximizeButton, restoreButton).attach();
	}
}
