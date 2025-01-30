package com.tahraoui.messaging.ui.main.chat;

import com.tahraoui.jstx.button.JSTXAbstractButton;
import com.tahraoui.jstx.button.JSTXIconButton;
import com.tahraoui.jstx.input.JSTXTextField;
import com.tahraoui.jstx.panel.JSTXBoxH;
import com.tahraoui.jstx.util.SvgUtils;
import com.tahraoui.messaging.backend.ConnectionService;
import com.tahraoui.messaging.backend.data.request.MessageRequest;

import java.awt.event.ActionEvent;

public class ChatInputPane extends JSTXBoxH  {

	private final JSTXTextField inputField;
	private final JSTXAbstractButton sendButton;

	public ChatInputPane() {
		super(0);

		this.inputField = new JSTXTextField("Type a message...");
		this.sendButton = new JSTXIconButton(SvgUtils.getSvgIcon("/icons/app/send.svg"));
		this.sendButton.addActionListener(this::sendMessage);

		setupLayout();
	}

	private void setupLayout() {
		add(inputField);
		add(sendButton);
	}

	public void sendMessage(ActionEvent e) {
		var message = inputField.getText();
		if (message.isEmpty()) return;
		var connectionInstance = ConnectionService.getInstance();
		connectionInstance.writeRequest(new MessageRequest(connectionInstance.getId(), connectionInstance.getUsername(), connectionInstance.encryptMessage(message)));
		inputField.setText(null);
	}
}
