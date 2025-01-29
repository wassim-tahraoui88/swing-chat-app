package com.tahraoui.messaging.ui.home;

import com.tahraoui.jstx.JSTXConstants;
import com.tahraoui.jstx.button.JSTXButton;
import com.tahraoui.jstx.input.JSTXPasswordField;
import com.tahraoui.jstx.input.JSTXTextField;
import com.tahraoui.jstx.panel.JSTXPanel;
import com.tahraoui.jstx.text.JSTXLabel;
import com.tahraoui.messaging.backend.ConnectionService;
import com.tahraoui.messaging.model.UserCredentials;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

public class HomePanel extends JSTXPanel implements JSTXConstants {

	private static final Logger LOGGER = LogManager.getLogger(HomePanel.class);

	private final Insets insets = new Insets(0, MAX_PADDING,0, MAX_PADDING);

	private final JSTXTextField hostUsernameField, hostPortField, joinUsernameField, joinPortField;
	private final JSTXPasswordField hostPasswordField, joinPasswordField;

	public HomePanel() {
		this.hostUsernameField = new JSTXTextField(15,"Enter username...");
		this.hostPortField = new JSTXTextField(15,"8080...");
		this.hostPasswordField = new JSTXPasswordField(15,"Enter session password...");

		this.joinUsernameField = new JSTXTextField(15,"Enter username...");
		this.joinPortField = new JSTXTextField(15,"8080...");
		this.joinPasswordField = new JSTXPasswordField(15,"Enter session password...");

		setupLayout();
	}

	private boolean verifyPort() { return true; }

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
