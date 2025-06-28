package com.project.serverregister.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.project.serverregister.common.AppMain;
import com.project.serverregister.common.ImageResources;
import com.project.utility.Encryption;

@Component
public class FrmMainScreen extends JFrame implements ActionListener, KeyListener, WindowListener {

	private static final long serialVersionUID = 1L;

	private JLabel lblServerPic;

	private JButton btnRegister, btnExit;

	private JTextField txtServerIp, txtUser, txtPort;
	private JPasswordField txtPass;

	public FrmMainScreen() {
		try {
			getContentPane().setPreferredSize(
					new Dimension(AppMain.getScreenWid() * 35 / 100, AppMain.getScreenHgt() * 4 / 10));
			setTitle("Server Register");
			setUndecorated(true);
			pack();
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLocationRelativeTo(null);

			addWindowListener(this);
			BufferedImage img = AppMain.getBufferImage(ImageResources.appIcon.getValue());
			if (null != img) {
				setIconImage(img);
			}
		} catch (Exception e) {
			AppMain.errorMessage(e.getMessage(), this);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		try {
			componentCreation();
			componentListeners();

			BufferedImage img = AppMain.getBufferImage(ImageResources.server.getValue());

			if (null != img) {
				lblServerPic.setIcon(new ImageIcon(img.getScaledInstance(190, 200, Image.SCALE_SMOOTH)));
			}

			txtServerIp.requestFocus();
		} catch (Exception e2) {
			e2.printStackTrace();
			AppMain.errorMessage(e2.getMessage(), this);
		}

	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (e.getComponent() == txtServerIp) {
					if (txtServerIp.getText().isEmpty()) {
						throw new Exception("Server IP Should Not Be Empty");
					}
					txtUser.requestFocus();
				} else if (e.getComponent() == txtUser) {
					if (txtUser.getText().isEmpty()) {
						throw new Exception("User Should Not Be Empty");
					}
					txtPass.requestFocus();
				} else if (e.getComponent() == txtPass) {
					txtPort.requestFocus();
				} else if (e.getComponent() == txtPort) {
					if (txtPort.getText().isEmpty()) {
						throw new Exception("Port No Should Not Be Empty");
					}
					btnRegister.requestFocus();
				}
			}
		} catch (Exception e2) {
			AppMain.errorMessage(e2.getMessage(), getContentPane());
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnRegister) {
				serverRegister();
			} else if (e.getSource() == btnExit) {
				System.exit(0);
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			AppMain.errorMessage(e2.getMessage(), getContentPane());
		}
	}

	private void componentCreation() throws Exception {
		JPanel pnlMain, pnlHead, pnlBody;
		JLabel lblTilte, lblServerIp, lblUser, lblPass, lblPort, lblServerType, lblServerTypeName;
		Font f = new Font("Comic Sans MS", Font.BOLD, 16);

		pnlMain = new JPanel(null);
		pnlMain.setBounds(0, 0, getContentPane().getWidth(), getContentPane().getHeight());
		pnlMain.setBackground(Color.WHITE);
		pnlMain.setBorder(new LineBorder(Color.BLACK, 10));

		pnlHead = new JPanel(null);
		pnlHead.setBounds(0, 0, pnlMain.getWidth(), pnlMain.getHeight() * 15 / 100);
		pnlHead.setBorder(new MatteBorder(0, 0, 4, 0, Color.white));
		pnlHead.setBackground(Color.decode("#ff105d"));

		pnlBody = new JPanel(null);
		pnlBody.setBounds(0, pnlMain.getHeight() * 15 / 100, pnlMain.getWidth(), pnlMain.getHeight() * 85 / 100);
		pnlBody.setBorder(new MatteBorder(4, 0, 0, 0, Color.decode("#ff105d")));
		pnlBody.setBackground(Color.WHITE);

		lblTilte = new JLabel("  Server Registration");
		lblTilte.setBounds(0, pnlHead.getHeight() * 25 / 100, pnlHead.getWidth() * 5 / 10,
				pnlHead.getHeight() * 5 / 10);
		lblTilte.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		lblTilte.setForeground(Color.WHITE);

		int x = pnlBody.getWidth() * 5 / 100, y = pnlBody.getHeight() * 8 / 100, wid = pnlBody.getWidth() * 1 / 4,
				hgt = pnlBody.getHeight() * 12 / 100;

		lblServerIp = new JLabel("Server Ip");
		lblServerIp.setBounds(x, y, wid, hgt);
		lblServerIp.setFont(f);

		y = AppMain.verticalGap(pnlBody, lblServerIp, 2);

		lblUser = new JLabel("User Name");
		lblUser.setBounds(x, y, wid, hgt);
		lblUser.setFont(f);

		y = AppMain.verticalGap(pnlBody, lblUser, 2);

		lblPass = new JLabel("Password");
		lblPass.setBounds(x, y, wid, hgt);
		lblPass.setFont(f);

		y = AppMain.verticalGap(pnlBody, lblPass, 2);

		lblPort = new JLabel("Port No");
		lblPort.setBounds(x, y, wid, hgt);
		lblPort.setFont(f);

		y = AppMain.verticalGap(pnlBody, lblPort, 2);

		lblServerType = new JLabel("Server Type");
		lblServerType.setBounds(x, y, wid, hgt);
		lblServerType.setFont(f);

		x = pnlBody.getWidth() * 2 / 10;
		y = AppMain.verticalGap(pnlBody, lblServerType, 5);
		wid = pnlBody.getWidth() * 17 / 100;
		hgt = pnlBody.getHeight() * 1 / 10;

		btnRegister = new JButton("Register");
		btnRegister.setBounds(x, y, wid, hgt);
		btnRegister.setMnemonic(KeyEvent.VK_R);
		btnRegister.setBackground(Color.decode("#ff105d"));
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setFont(f);

		x = AppMain.horizontalGap(pnlBody, btnRegister, 15);

		btnExit = new JButton("Exit");
		btnExit.setBounds(x, y, wid, hgt);
		btnExit.setMnemonic(KeyEvent.VK_X);
		btnExit.setBackground(Color.decode("#ff105d"));
		btnExit.setForeground(Color.WHITE);
		btnExit.setFont(f);
		btnExit.setVerifyInputWhenFocusTarget(false);

		x = pnlBody.getWidth() * 65 / 100;
		y = pnlBody.getHeight() * 20 / 100;
		wid = pnlBody.getWidth() * 1 / 4;
		hgt = pnlBody.getHeight() * 1 / 2;

		lblServerPic = new JLabel();
		lblServerPic.setBounds(x, y, wid, hgt);

		x = AppMain.horizontalGap(pnlBody, lblServerIp, 1.5);
		y = lblServerIp.getY();
		wid = pnlBody.getWidth() * 3 / 10;
		hgt = pnlBody.getHeight() * 1 / 10;

		txtServerIp = new JTextField();
		txtServerIp.setBounds(x, y, wid, hgt);
		txtServerIp.setFont(f);
		txtServerIp.setHorizontalAlignment(JTextField.CENTER);

		y = AppMain.verticalGap(pnlBody, txtServerIp, 5);

		txtUser = new JTextField();
		txtUser.setBounds(x, y, wid, hgt);
		txtUser.setFont(f);
		txtUser.setHorizontalAlignment(JTextField.CENTER);

		y = AppMain.verticalGap(pnlBody, txtUser, 5);

		txtPass = new JPasswordField();
		txtPass.setBounds(x, y, wid, hgt);
		txtPass.setFont(f);
		txtPass.setHorizontalAlignment(JTextField.CENTER);

		y = AppMain.verticalGap(pnlBody, txtPass, 5);

		txtPort = new JTextField();
		txtPort.setBounds(x, y, wid, hgt);
		txtPort.setFont(f);
		txtPort.setHorizontalAlignment(JTextField.CENTER);

		y = AppMain.verticalGap(pnlBody, txtPort, 5);

		lblServerTypeName = new JLabel("MYSQL");
		lblServerTypeName.setBounds(x, y, wid, hgt);
		lblServerTypeName.setFont(f);
		lblServerTypeName.setHorizontalAlignment(JLabel.CENTER);

		getContentPane().add(pnlMain);
		pnlMain.add(pnlHead);
		pnlMain.add(pnlBody);

		pnlHead.add(lblTilte);
		pnlBody.add(lblServerIp);
		pnlBody.add(lblUser);
		pnlBody.add(lblPass);
		pnlBody.add(lblPort);
		pnlBody.add(lblServerType);

		pnlBody.add(lblServerPic);

		pnlBody.add(btnRegister);
		pnlBody.add(btnExit);

		pnlBody.add(txtServerIp);
		pnlBody.add(txtUser);
		pnlBody.add(txtPass);
		pnlBody.add(txtPort);
		pnlBody.add(lblServerTypeName);

	}

	private void componentListeners() {

		txtServerIp.addKeyListener(this);
		txtUser.addKeyListener(this);
		txtPass.addKeyListener(this);
		txtPort.addKeyListener(this);

		btnRegister.addActionListener(this);
		btnExit.addActionListener(this);

	}

	private void serverRegister() throws Exception {
		if (getConnection()) {

			File file = new File("server.common");

			FileWriter fw = new FileWriter(file.getAbsolutePath());
			fw.write(txtServerIp.getText().concat("\n"));
			fw.write(Encryption.enCrypt("testmasterdb").concat("\n"));
			fw.write(Encryption.enCrypt(txtUser.getText()).concat("\n"));
			fw.write(Encryption.enCrypt(String.valueOf(txtPass.getPassword())).concat("\n"));
			fw.write(Encryption.enCrypt(txtPort.getText()).concat("\n"));
			fw.close();

			JOptionPane.showMessageDialog(getContentPane(), "Server Register Succuss", getTitle(),
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private boolean getConnection() throws SQLException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://".concat(txtServerIp.getText()).concat(":").concat(txtPort.getText())
				.concat("/sys").concat("?applicationName=").concat(AppMain.getAppName()));
		dataSource.setUsername(txtUser.getText());
		dataSource.setPassword(String.valueOf(txtPass.getPassword()));
		return dataSource.getConnection().isValid(100);
	}
}
