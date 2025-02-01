package com.tahraoui.messaging.ui.main.chat;

import com.tahraoui.jstx.button.JSTXAbstractButton;
import com.tahraoui.jstx.button.JSTXIconButton;
import com.tahraoui.jstx.container.JSTXGlassPanel;
import com.tahraoui.jstx.util.SvgUtils;
import com.tahraoui.jstx.util.Utils;
import com.tahraoui.messaging.backend.ConnectionService;
import com.tahraoui.messaging.backend.data.request.MessageRequest;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class ChatInputPane extends JSTXGlassPanel {

	private final JTextField inputField;
	private final JSTXAbstractButton sendButton, attachmentButton;
	private final JFileChooser fileChooser = new JFileChooser(Utils.DESKTOP_DIRECTORY);

	public ChatInputPane() {
		super(0.2f, Color.LIGHT_GRAY);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		this.inputField = new JTextField();
		this.inputField.setBorder(null);
		this.inputField.setOpaque(false);
		this.inputField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				var isModifierDown = (e.getModifiersEx() & (KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK | KeyEvent.ALT_DOWN_MASK | KeyEvent.META_DOWN_MASK)) != 0;
				if (e.getKeyCode() == KeyEvent.VK_ENTER && !isModifierDown) sendButton.doClick(10);
			}
			@Override
			public void keyReleased(KeyEvent e) { super.keyReleased(e); }
		});

		this.sendButton = new JSTXIconButton(SvgUtils.getSvgIcon("/icons/app/send.svg",16),false,4);
		this.sendButton.addActionListener(this::sendMessage);

		this.attachmentButton = new JSTXIconButton(SvgUtils.getSvgIcon("/icons/app/attachment.svg",16),false,4);
		this.attachmentButton.addActionListener(this::attachFile);

		setupLayout();
	}

	private void setupLayout() {
		setOpaque(true);
		add(attachmentButton);
		add(Box.createHorizontalStrut(4));
		add(inputField);
		add(Box.createHorizontalStrut(4));
		add(sendButton);
	}

	public void attachFile(ActionEvent e) {
		System.out.println("Attaching file...");
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			var file = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + file.getName());
		}
	}

	public void sendMessage(ActionEvent e) {
		var message = inputField.getText();
		if (message.isEmpty()) return;
		var connectionInstance = ConnectionService.getInstance();
		var encryptedMessage = connectionInstance.encryptMessage(message);
		System.out.printf("Sending message: %s%n", Arrays.toString(encryptedMessage));
		connectionInstance.writeRequest(new MessageRequest(connectionInstance.getId(), connectionInstance.getUsername(), encryptedMessage));
		inputField.setText(null);
	}
}
