package com.tahraoui.messaging.other;

import com.tahraoui.jstx.button.JSTXIconButton;
//import com.tahraoui.jstx.extension.DragExtension;
import com.tahraoui.jstx.extension.MaximizeExtension;
import com.tahraoui.jstx.extension.MinimizeExtension;
import com.tahraoui.jstx.init.JSTXBootstrapper;
import com.tahraoui.jstx.text.JSTXLabel;
import com.tahraoui.jstx.util.Config;
import com.tahraoui.jstx.util.SvgUtils;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.OverlayLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class HeaderPanel extends JPanel {

	private final JLabel titleLabel;
	private final JButton minimizeButton, maximizeButton, restoreButton, closeButton;

	public HeaderPanel() {
		super(new BorderLayout(),true);

		titleLabel = new JLabel(Config.getInstance().getAppName(), JSTXLabel.CENTER);
		minimizeButton = new JButton(SvgUtils.getSvgIcon("/icons/toolbar/minimize.svg"));
		maximizeButton = new JButton(SvgUtils.getSvgIcon("/icons/toolbar/maximize.svg"));
		restoreButton = new JButton(SvgUtils.getSvgIcon("/icons/toolbar/maximize.svg"));
		closeButton = new JButton(SvgUtils.getSvgIcon("/icons/toolbar/close.svg"));

		setupLayout();
		attachExtensions();

		closeButton.addActionListener(_ -> JSTXBootstrapper.getInstance().exit());
	}
	private void setupLayout() {
		var maximizePanel = new JPanel();
		maximizePanel.setLayout(new OverlayLayout(maximizePanel));
		maximizePanel.add(maximizeButton);
		maximizePanel.add(restoreButton);

		var buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(minimizeButton);
		buttonsPanel.add(maximizePanel);
		buttonsPanel.add(closeButton);


		add(titleLabel, BorderLayout.WEST);
		add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.EAST);

		maximizeButton.setVisible(true);
		restoreButton.setVisible(false);
	}
	private void attachExtensions() {
//		new DragExtension(this).attach();
		new MinimizeExtension(minimizeButton).attach();
		new MaximizeExtension(maximizeButton, maximizeButton, restoreButton).attach();
		new MaximizeExtension(restoreButton, maximizeButton, restoreButton).attach();
	}
}
