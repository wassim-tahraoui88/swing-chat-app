package com.tahraoui.messaging.ui.main;

import com.tahraoui.jstx.JSTXConstants;
import com.tahraoui.jstx.Themeable;
import com.tahraoui.jstx.button.JSTXButton;
import com.tahraoui.jstx.input.JSTXNumberField;
import com.tahraoui.jstx.input.JSTXPasswordField;
import com.tahraoui.jstx.input.JSTXTextField;
import com.tahraoui.jstx.container.JSTXPanel;
import com.tahraoui.jstx.text.JSTXLabel;
import com.tahraoui.jstx.util.GraphicsUtils;
import com.tahraoui.jstx.util.SvgUtils;
import com.tahraoui.jstx.util.ThemeConfig;
import com.tahraoui.messaging.backend.ConnectionService;
import com.tahraoui.messaging.model.UserCredentials;
import com.tahraoui.messaging.util.NetworkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.event.ActionListener;
import java.util.List;

public class HomePanel extends JSTXPanel implements JSTXConstants, Themeable {

	private static final Logger LOGGER = LogManager.getLogger(HomePanel.class);

	private final Insets insets = new Insets(0, MAX_PADDING,0, MAX_PADDING);

	private final JSTXTextField hostUsernameField, hostPortField, joinUsernameField, joinPortField;
	private final JSTXPasswordField hostPasswordField, joinPasswordField;
	private final JToolTip portStatus;

	private final Image infoImage = SvgUtils.getSvgIcon("/icons/popup/info.svg").getImage();

	public HomePanel() {

		var infoIcon = new ImageIcon(GraphicsUtils.ColorUtils.colorizeImage(infoImage, ThemeConfig.getInstance().getSecondaryColor()));

		this.hostUsernameField = new JSTXTextField(15,"Enter username...");
		this.hostPortField = new JSTXNumberField(15,"8080...");
		this.hostPasswordField = new JSTXPasswordField(15,"Enter session password...");

		this.joinUsernameField = new JSTXTextField(15,"Enter username...");
		this.joinPortField = new JSTXNumberField(15,"8080...");
		this.joinPasswordField = new JSTXPasswordField(15,"Enter session password...");

		this.portStatus = new JToolTip();

		this.hostPortField.setActionable(infoIcon, _ -> handlePortVerification());

		setupLayout();
	}

	@Override
	public void configureTheme() {
		var themeConfig = ThemeConfig.getInstance();
		themeConfig.addThemeListener(() -> {
			var infoIcon = new ImageIcon(GraphicsUtils.ColorUtils.colorizeImage(infoImage, themeConfig.getSecondaryColor()));
			this.hostPortField.updateIcon(infoIcon);
		});
	}

	private boolean verifyPort() {
		var port = hostPortField.getText();
		try {
			var portNumber = Integer.parseInt(port);
			return portNumber > 0 && portNumber < 65536 && NetworkUtils.isPortAvailable(portNumber);
		}
		catch (NumberFormatException _) {
			return false;
		}
	}
	private void handlePortVerification() {
		portStatus.setVisible(false);

		portStatus.setTipText(verifyPort() ? "Port number is valid." :"Invalid port number...");
		var mousePosition = MouseInfo.getPointerInfo().getLocation();

		portStatus.setLocation(mousePosition.x, mousePosition.y);
		portStatus.setVisible(true);
	}

	private void handleHost() {
		var username = hostUsernameField.getText();
		var port = hostPortField.getText();
		var password = String.valueOf(hostPasswordField.getPassword());

		if (port.isBlank() || username.isBlank() || password.isBlank()) {
//			Modal.showError("Connection Error","Please fill in all fields...");
			LOGGER.warn("Please fill in all fields...");
			return;
		}

		if (verifyPort()) ConnectionService.getInstance().host(Integer.parseInt(port), new UserCredentials(username, password));
//		else ModalFactory.showWarning("Connection Warning", "Port number is invalid...");
		else LOGGER.warn("Port number is invalid...");
	}
	private void handleJoin() {
		var username = joinUsernameField.getText();
		var port = joinPortField.getText();
		var password = String.valueOf(joinPasswordField.getPassword());

		if (port.isBlank() || username.isBlank() || password.isBlank()) {
//			Modal.showError("Connection Error","Please fill in all fields...");
			LOGGER.warn("Please fill in all fields...");
			return;
		}

		try {
			ConnectionService.getInstance().join(Integer.parseInt(port), new UserCredentials(username, password));
		}
		catch (NumberFormatException _) {
			LOGGER.warn("Invalid port number...");
//			portStatus.setText("Invalid port number...");
//			var screenPosition = button_join.localToScreen(button_join.getBoundsInLocal());
//			portStatus.show(button_join, screenPosition.getMaxX(), screenPosition.getMinY());
		}
	}

	private void setupLayout() {
		setLayout(new GridBagLayout());

		var gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, MAX_PADDING,0, MAX_PADDING);

		gbc.weightx = 1; gbc.weighty = 1;
		gbc.gridy = 0;

		var leftForm = new JSTXPanel();
		var rightForm = new JSTXPanel();

		createForm(leftForm,"Host a new chat session",
				List.of(hostUsernameField, hostPortField, hostPasswordField),
				List.of("Username:", "Port Number (Between 1 and 65535):", "Password:"),
				"Host", _ -> handleHost());

		createForm(rightForm,"Join an existing chat session",
				List.of(joinUsernameField, joinPortField, joinPasswordField),
				List.of("Username:", "Port Number (Between 1 and 65535):", "Password:"),
				"Join", _ -> handleJoin());

		gbc.gridx = 0; add(leftForm, gbc);
		gbc.gridx = 1; add(rightForm, gbc);
	}
	private void createForm(JSTXPanel panel, String formTitle, List<JTextField> fields, List<String> labels, String submitText, ActionListener listener) {
		panel.setLayout(new GridBagLayout());

		var gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0,0, MAX_PADDING,0);
		gbc.weightx = 1; gbc.weighty = 1;
		gbc.gridx = 0; gbc.gridy = 0;

		var titleLabel = new JSTXLabel(formTitle, JSTXLabel.CENTER);
		titleLabel.setFont(titleLabel.getFont().deriveFont(20f));

		gbc.anchor = GridBagConstraints.CENTER;
		panel.add(titleLabel, gbc);

		var smallInsets = new Insets(0, MID_PADDING,0,0);
		var bigInsets = new Insets(0,0, BASE_PADDING,0);

		gbc.anchor = GridBagConstraints.WEST;

		for (int i = 0, fieldsSize = fields.size(); i < fieldsSize; i++) {
			var field = fields.get(i);

			gbc.gridy++; gbc.insets = smallInsets;
			panel.add(new JSTXLabel(labels.get(i), JSTXLabel.LEFT), gbc);

			gbc.gridy++; gbc.insets = bigInsets;
			panel.add(field, gbc);
		}

		var button = new JSTXButton(submitText);
		button.addActionListener(listener);

		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridy++; gbc.insets = bigInsets;
		panel.add(button, gbc);
	}

	@Override
	public Insets getInsets() { return insets; }
}
