package com.project.estimateupdate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.springframework.stereotype.Component;

import com.project.estimateupdate.common.ApplicationMain;
import com.project.estimateupdate.common.ImageResources;
import com.project.estimateupdate.config.ApplicationConfig;
import com.project.estimateupdate.dao.LoginDao;

@Component
public class FrmLogin extends JFrame implements ActionListener, KeyListener {

	/**
	 * Author Aswin K
	 */
	private static final long serialVersionUID = 1L;

	private JComboBox<String> cmbUser;
	private JPasswordField txtPass;
	private JButton btnLogin, btnExit;

	public FrmLogin() {
		try {
			setTitle("Login");
			setPreferredSize(
					new Dimension(ApplicationMain.getAppWidth() * 25 / 100, ApplicationMain.getAppHeight() * 50 / 100));
			setSize(ApplicationMain.getAppWidth() * 25 / 100, ApplicationMain.getAppHeight() * 50 / 100);
			setUndecorated(true);
			setLocationRelativeTo(null);
			setResizable(false);
			pack();
			toFront();
			setDefaultCloseOperation(EXIT_ON_CLOSE);

			BufferedImage img = ApplicationMain.getBufferImage(ImageResources.appIcon.getValue());
			if (null != img) {
				setIconImage(img);
			}

			componentCreation();
			compoenentListeners();

		} catch (Exception e) {
			AppStart.getLogger().log(Level.SEVERE, e.getMessage(), e);
			ApplicationMain.errorMessage(e.getMessage(), getContentPane());
		}
	}

	private void componentCreation() throws Exception {

		JLabel lblEntry;
		JPanel pnlMain, pnlEntry;

		pnlMain = new JPanel(null);
		pnlMain.setBounds(0, 0, ApplicationMain.getAppWidth() * 25 / 100, ApplicationMain.getAppHeight() * 50 / 100);
		pnlMain.setBackground(Color.BLACK);

		pnlEntry = new JPanel(null);
		pnlEntry.setBounds(5, 5, pnlMain.getWidth() - 10, pnlMain.getHeight() - 10);

		Image image = ApplicationMain.getBufferImage(ImageResources.login.getValue())
				.getScaledInstance(pnlEntry.getWidth(), pnlEntry.getHeight(), Image.SCALE_SMOOTH);

		lblEntry = new JLabel();
		lblEntry.setBounds(0, 0, pnlEntry.getWidth(), pnlEntry.getHeight());
		if (null != image) {
			lblEntry.setIcon(new ImageIcon(image));
		}

		int x = pnlEntry.getWidth() * 275 / 1000, y = pnlEntry.getHeight() * 427 / 1000,
				wid = pnlEntry.getWidth() * 545 / 1000, hgt = pnlEntry.getHeight() * 945 / 10000;

		cmbUser = new JComboBox<String>();
		cmbUser.setBounds(x, y, wid, hgt);

		y = ApplicationMain.verticalGap(pnlEntry, cmbUser, 2.9);

		txtPass = new JPasswordField();
		txtPass.setBounds(x, y, wid, hgt);

		y = ApplicationMain.verticalGap(pnlEntry, txtPass, 10);
		x = pnlEntry.getWidth() * 22 / 100;
		wid = pnlEntry.getWidth() * 25 / 100;
		hgt = pnlEntry.getWidth() * 10 / 100;

		btnLogin = new JButton("LOGIN");
		btnLogin.setBounds(x, y, wid, hgt);
		btnLogin.setMnemonic(KeyEvent.VK_L);
		btnLogin.setBackground(ApplicationMain.getFgColor());
		btnLogin.setForeground(Color.white);
		btnLogin.setFont(ApplicationMain.getLblFont());

		x = ApplicationMain.horizontalGap(pnlEntry, btnLogin, 6);

		btnExit = new JButton("EXIT");
		btnExit.setBounds(x, y, wid, hgt);
		btnExit.setMnemonic(KeyEvent.VK_X);
		btnExit.setVerifyInputWhenFocusTarget(false);
		btnExit.setBackground(ApplicationMain.getFgColor());
		btnExit.setForeground(Color.white);
		btnExit.setFont(ApplicationMain.getLblFont());

		setContentPane(pnlMain);
		pnlMain.add(pnlEntry);
		pnlEntry.add(lblEntry);

		lblEntry.add(cmbUser);
		lblEntry.add(txtPass);
		lblEntry.add(btnLogin);
		lblEntry.add(btnExit);
	}

	private void compoenentListeners() {

		cmbUser.addKeyListener(this);
		txtPass.addKeyListener(this);

		btnLogin.addActionListener(this);
		btnExit.addActionListener(this);

		cmbUser.setInputVerifier(new InputVerifier() {
			@Override
			public boolean verify(JComponent input) {
				if (!ApplicationMain.checkComboBox(cmbUser)) {
					ApplicationMain.errorMessage("Select Valid User!!", getContentPane());
					return false;
				}
				return true;
			}
		});

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (e.getComponent() == cmbUser) {
					txtPass.requestFocus();
				} else if (e.getComponent() == txtPass) {
					if (String.valueOf(txtPass.getPassword()).isEmpty()) {
						txtPass.requestFocus();
						throw new Exception("Enter Valid Password!!");
					} else {
						btnLogin.requestFocus();
					}
				}
			}

		} catch (Exception e1) {
			AppStart.getLogger().log(Level.SEVERE, e1.getMessage(), e1);
			ApplicationMain.errorMessage(e1.getMessage(), getContentPane());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnLogin) {
				confirmLogin();
			} else if (e.getSource() == btnExit) {
				System.exit(0);
			}
		} catch (Exception e1) {
			AppStart.getLogger().log(Level.SEVERE, e1.getMessage(), e1);
			ApplicationMain.errorMessage(e1.getMessage(), getContentPane());
		}
	}

	public void loadInitialize() throws Exception {
		txtPass.setText("");
		LoginDao loginDao = ApplicationConfig.context.getBean(LoginDao.class);
		loginDao.getOperator(cmbUser);
	}

	private void confirmLogin() throws Exception {
		if (checkLogin()) {
			ApplicationMain.setLoginOperator(String.valueOf(cmbUser.getSelectedItem()));
			FrmMdi mdi = ApplicationConfig.context.getBean(FrmMdi.class);
			mdi.setVisible(true);
			dispose();
		} else {
			txtPass.requestFocus();
			throw new Exception("Invalid User/Password!!!");
		}
	}

	private boolean checkLogin() throws Exception {
		LoginDao loginDao = ApplicationConfig.context.getBean(LoginDao.class);
		return loginDao.checkLogin(String.valueOf(cmbUser.getSelectedItem()), String.valueOf(txtPass.getPassword()));
	}
}
