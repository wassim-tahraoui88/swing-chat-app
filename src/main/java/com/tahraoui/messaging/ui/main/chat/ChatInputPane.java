package com.tahraoui.messaging.ui.main.chat;

import com.tahraoui.jstx.button.JSTXAbstractButton;
import com.tahraoui.jstx.button.JSTXIconButton;
import com.tahraoui.jstx.input.JSTXTextField;
import com.tahraoui.jstx.panel.JSTXBoxH;
import com.tahraoui.jstx.util.GraphicsUtils;
import com.tahraoui.jstx.util.SvgUtils;
import com.tahraoui.jstx.util.ThemeConfig;
import com.tahraoui.jstx.util.Utils;
import com.tahraoui.messaging.backend.ConnectionService;
import com.tahraoui.messaging.backend.data.request.MessageRequest;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatInputPane extends JSTXBoxH  {

	private final JSTXTextField inputField;
	private final JSTXAbstractButton sendButton;
	private final JFileChooser fileChooser = new JFileChooser(Utils.DESKTOP_DIRECTORY);
	private final Image image;

	public ChatInputPane() {
		super(0);

		this.image = SvgUtils.getSvgIcon("/icons/app/attachment.svg").getImage();

		var icon = GraphicsUtils.ColorUtils.colorizeImage(image, ThemeConfig.getInstance().getSecondaryColor());

		this.inputField = new JSTXTextField("Type a message...");
		this.inputField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				var isModifierDown = (e.getModifiersEx() & (KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK | KeyEvent.ALT_DOWN_MASK | KeyEvent.META_DOWN_MASK)) != 0;
				if (e.getKeyCode() == KeyEvent.VK_ENTER && !isModifierDown) sendButton.doClick(10);
			}
			@Override
			public void keyReleased(KeyEvent e) { super.keyReleased(e); }
		});
		this.inputField.setActionable(new ImageIcon(icon), this::attachFile);

		this.sendButton = new JSTXIconButton(SvgUtils.getSvgIcon("/icons/app/send.svg",16));
		this.sendButton.addActionListener(this::sendMessage);

		setupLayout();
	}

	@Override
	public void configureTheme() {
		var theme = ThemeConfig.getInstance();
		theme.addThemeListener(() -> {
			var icon = GraphicsUtils.ColorUtils.colorizeImage(image, ThemeConfig.getInstance().getSecondaryColor());
			inputField.updateIcon(new ImageIcon(icon));
		});
	}

	private void setupLayout() {
		add(inputField);
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
		connectionInstance.writeRequest(new MessageRequest(connectionInstance.getId(), connectionInstance.getUsername(), connectionInstance.encryptMessage(message)));
		inputField.setText(null);
	}
}
