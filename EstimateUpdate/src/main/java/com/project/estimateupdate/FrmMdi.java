package com.project.estimateupdate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.MenuElement;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;

import org.springframework.stereotype.Component;

import com.project.estimateupdate.common.ApplicationMain;
import com.project.estimateupdate.common.FrmAction;
import com.project.estimateupdate.common.ImageResources;
import com.project.estimateupdate.config.ApplicationConfig;
import com.project.estimateupdate.form.FrmOpertor;

@Component
public class FrmMdi extends JFrame
		implements ActionListener, KeyListener, WindowListener, FrmAction, MouseListener, MenuKeyListener {

	private static final long serialVersionUID = 1L;

	private JPanel pnlTitleBar, pnlMenu, pnlComp, pnlInternal, pnlButtons, pnlTaskBar;
	private JButton btnMinimize;
	private JLabel lblTime, lblCalc, lblHints;
	private JButton btnAdd, btnSave, btnEdit, btnCancel, btnView, btnExit;
	private JMenu mnuTransaction, mnuView, mnuExit;
	private JMenuItem mnuItemOpertor, mnuItemMulControl, mnuItemProduct, mnuItemEstimate, mnuItemOpertorView,
			mnuItemMulControlView, mnuItemProductView, mnuItemEstimateView;
	private FrmAction currentForm;

	public FrmMdi() {
		try {
			setTitle("FromMDI");
			setPreferredSize(new Dimension(ApplicationMain.getAppWidth(), ApplicationMain.getAppHeight()));
			setSize(ApplicationMain.getAppWidth(), ApplicationMain.getAppHeight());
			setUndecorated(true);
			setLocationRelativeTo(null);
			setResizable(false);
			pack();
			setDefaultCloseOperation(EXIT_ON_CLOSE);

			BufferedImage img = ApplicationMain.getBufferImage(ImageResources.appIcon.getValue());
			if (null != img) {
				setIconImage(img);
			}

			addWindowListener(this);
		} catch (Exception e) {
			AppStart.getLogger().log(Level.ALL, e.getMessage(), e);
			ApplicationMain.errorMessage(e.getMessage(), getContentPane());
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {

			if (e.getSource() == btnMinimize) {
				if (getExtendedState() != JFrame.ICONIFIED) {
					setExtendedState(JFrame.ICONIFIED);
				}
			} else if (e.getSource() == btnExit) {
				exit();
			} else if (e.getSource() == mnuItemOpertor) {
				FrmOpertor frmOpertor = ApplicationConfig.context.getBean(FrmOpertor.class);
				frmOpertor.setVisible(true);
				currentForm = frmOpertor;
			}

		} catch (Exception e2) {
			AppStart.getLogger().log(Level.ALL, e2.getMessage(), e2);
			ApplicationMain.errorMessage(e2.getMessage(), getContentPane());
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		try {
			componentCreation();
			componentListeners();

			ScheduledExecutorService timer = new ScheduledThreadPoolExecutor(1);
			timer.execute(new Runnable() {
				@Override
				public void run() {
					while (true) {
						lblTime.setText(new SimpleDateFormat("dd-MMM-yyyy hh:mm.ss a").format(new Date()));
					}
				}
			});

		} catch (Exception e1) {
			AppStart.getLogger().log(Level.ALL, e1.getMessage(), e1);
			ApplicationMain.errorMessage(e1.getMessage(), getContentPane());
		}
	}

	private void componentCreation() throws Exception {

		JPanel pnlMain;

		pnlMain = new JPanel(null);
		pnlMain.setBounds(0, 0, ApplicationMain.getAppWidth(), ApplicationMain.getAppHeight());
		pnlMain.setBackground(Color.white);

		pnlTitleBar = new JPanel(null);
		pnlTitleBar.setBounds(0, 0, pnlMain.getWidth(), pnlMain.getHeight() * 4 / 100);
		pnlTitleBar.setBackground(ApplicationMain.getFgColor());

		pnlMenu = new JPanel(null);
		pnlMenu.setBounds(0, pnlMain.getHeight() * 4 / 100, pnlMain.getWidth() * 80 / 100,
				pnlMain.getHeight() * 8 / 100);
		pnlMenu.setBackground(Color.white);
		pnlMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ApplicationMain.getFgColor()));

		pnlComp = new JPanel(null);
		pnlComp.setBounds(pnlMain.getWidth() * 80 / 100, pnlMain.getHeight() * 4 / 100, pnlMain.getWidth() * 20 / 100,
				pnlMain.getHeight() * 8 / 100);
		pnlComp.setBackground(Color.white);
		pnlComp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ApplicationMain.getFgColor()));

		pnlInternal = new JPanel(null);
		pnlInternal.setBounds(0, pnlMain.getHeight() * 12 / 100, pnlMain.getWidth(), pnlMain.getHeight() * 78 / 100);
		pnlInternal.setBackground(ApplicationMain.getBgColor());

		int y = pnlTitleBar.getHeight() + pnlMenu.getHeight() + pnlInternal.getHeight();

		pnlButtons = new JPanel(null);
		pnlButtons.setBounds(0, y, pnlMain.getWidth(), pnlMain.getHeight() * 5 / 100);
		pnlButtons.setBackground(ApplicationMain.getFgColor());
		pnlButtons.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.white));

		y = pnlMain.getHeight() - pnlMain.getHeight() * 5 / 100;

		pnlTaskBar = new JPanel(null);
		pnlTaskBar.setBounds(0, y, pnlMain.getWidth(), pnlMain.getHeight() * 5 / 100);
		pnlTaskBar.setBackground(Color.white);
		pnlTaskBar.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, ApplicationMain.getFgColor()));

		int x = pnlMain.getWidth() - pnlMain.getWidth() * 1 / 100;

		btnMinimize = new JButton();
		btnMinimize.setBounds(x, pnlTaskBar.getHeight() * 9 / 100, pnlMain.getWidth() * 1 / 100,
				pnlTaskBar.getHeight());
		btnMinimize.setBackground(Color.GRAY);

		setContentPane(pnlMain);
		pnlMain.add(pnlTitleBar);
		pnlMain.add(pnlMenu);
		pnlMain.add(pnlComp);
		pnlMain.add(pnlInternal);
		pnlMain.add(pnlButtons);
		pnlMain.add(pnlTaskBar);
		pnlTaskBar.add(btnMinimize);

		createPanelTitleBar();
		createPanelMenuBar();
		createPanelButtons();
		createPanelTaskBar();

		ApplicationMain.setInternalFrameWidth(pnlInternal.getWidth() * 101 / 100);
		ApplicationMain.setInternalFrameHeight(pnlInternal.getHeight() * 101 / 100);
	}

	private void createPanelTitleBar() throws Exception {
		int x = pnlTitleBar.getWidth() - pnlTitleBar.getWidth() * 12 / 100;
		BufferedImage img = ApplicationMain.getBufferImage(ImageResources.calender.getValue());

		JLabel lblServerIp, lblAppVersion, lblIpId, lblOperator;

		lblTime = new JLabel();
		lblTime.setBounds(x, 0, pnlTitleBar.getWidth() * 12 / 100, pnlTitleBar.getHeight());
		lblTime.setFont(new Font("Arial", Font.BOLD, 15));
		lblTime.setHorizontalTextPosition(JLabel.RIGHT);
		lblTime.setForeground(Color.white);
		lblTime.setBackground(Color.white);
		lblTime.setIcon(new ImageIcon(img.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));

		img = ApplicationMain.getBufferImage(ImageResources.server.getValue());
		x = pnlTitleBar.getWidth() - pnlTitleBar.getWidth() * 30 / 100;

		lblServerIp = new JLabel(ApplicationMain.getServerIp());
		lblServerIp.setBounds(x, 0, pnlTitleBar.getWidth() * 10 / 100, pnlTitleBar.getHeight());
		lblServerIp.setFont(new Font("Arial", Font.BOLD, 15));
		lblServerIp.setHorizontalTextPosition(JLabel.RIGHT);
		lblServerIp.setForeground(Color.white);
		lblServerIp.setBackground(Color.white);
		lblServerIp.setIcon(new ImageIcon(img.getScaledInstance(35, 35, Image.SCALE_SMOOTH)));

		img = ApplicationMain.getBufferImage(ImageResources.version.getValue());
		x = pnlTitleBar.getWidth() - pnlTitleBar.getWidth() * 53 / 100;

		lblAppVersion = new JLabel(ApplicationMain.getAppVersion());
		lblAppVersion.setBounds(x, 0, pnlTitleBar.getWidth() * 10 / 100, pnlTitleBar.getHeight());
		lblAppVersion.setFont(new Font("Arial", Font.BOLD, 15));
		lblAppVersion.setHorizontalTextPosition(JLabel.RIGHT);
		lblAppVersion.setForeground(Color.white);
		lblAppVersion.setBackground(Color.white);
		lblAppVersion.setIcon(new ImageIcon(img.getScaledInstance(35, 35, Image.SCALE_SMOOTH)));

		img = ApplicationMain.getBufferImage(ImageResources.ip.getValue());
		x = pnlTitleBar.getWidth() * 22 / 100;

		lblIpId = new JLabel(ApplicationMain.getAppVersion());
		lblIpId.setBounds(x, 0, pnlTitleBar.getWidth() * 10 / 100, pnlTitleBar.getHeight());
		lblIpId.setFont(new Font("Arial", Font.BOLD, 15));
		lblIpId.setHorizontalTextPosition(JLabel.RIGHT);
		lblIpId.setForeground(Color.white);
		lblIpId.setBackground(Color.white);
		lblIpId.setIcon(new ImageIcon(img.getScaledInstance(35, 35, Image.SCALE_SMOOTH)));

		img = ApplicationMain.getBufferImage(ImageResources.operator.getValue());
		x = pnlTitleBar.getWidth() * 1 / 100;

		lblOperator = new JLabel(ApplicationMain.getLoginOperator());
		lblOperator.setBounds(x, 0, pnlTitleBar.getWidth() * 10 / 100, pnlTitleBar.getHeight());
		lblOperator.setFont(new Font("Arial", Font.BOLD, 15));
		lblOperator.setHorizontalTextPosition(JLabel.RIGHT);
		lblOperator.setForeground(Color.white);
		lblOperator.setBackground(Color.white);
		lblOperator.setIcon(new ImageIcon(img.getScaledInstance(35, 35, Image.SCALE_SMOOTH)));

		pnlTitleBar.add(lblTime);
		pnlTitleBar.add(lblServerIp);
		pnlTitleBar.add(lblAppVersion);
		pnlTitleBar.add(lblIpId);
		pnlTitleBar.add(lblOperator);
	}

	private void createPanelMenuBar() throws Exception {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, pnlMenu.getWidth(), pnlMenu.getHeight());

		BufferedImage img = ApplicationMain.getBufferImage(ImageResources.mnuTransaction.getValue());

		mnuTransaction = new JMenu("   Transaction   ");
		mnuTransaction.setMnemonic(KeyEvent.VK_T);
		mnuTransaction.setVerticalTextPosition(JMenu.BOTTOM);
		mnuTransaction.setHorizontalTextPosition(JMenu.CENTER);
		mnuTransaction.setFont(ApplicationMain.getLblFont());
		if (null != img) {
			mnuTransaction.setIcon(new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		}

		img = ApplicationMain.getBufferImage(ImageResources.mnuView.getValue());

		mnuView = new JMenu("     View     ");
		mnuView.setMnemonic(KeyEvent.VK_V);
		mnuView.setVerticalTextPosition(JMenu.BOTTOM);
		mnuView.setHorizontalTextPosition(JMenu.CENTER);
		mnuView.setFont(ApplicationMain.getLblFont());
		if (null != img) {
			mnuView.setIcon(new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		}

		img = ApplicationMain.getBufferImage(ImageResources.mnuExit.getValue());

		mnuExit = new JMenu("     Exit     ");
		mnuExit.setMnemonic(KeyEvent.VK_X);
		mnuExit.setVerticalTextPosition(JMenu.BOTTOM);
		mnuExit.setHorizontalTextPosition(JMenu.CENTER);
		mnuExit.setFont(ApplicationMain.getLblFont());
		if (null != img) {
			mnuExit.setIcon(new ImageIcon(img.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		}

		menuBar.add(mnuTransaction);
		menuBar.add(mnuView);
		menuBar.add(mnuExit);
		pnlMenu.add(menuBar);

		createMenuCreation();
	}

	private void createMenuCreation() throws Exception {
		BufferedImage img = ApplicationMain.getBufferImage(ImageResources.mnuItmOperator.getValue());

		mnuItemOpertor = new JMenuItem("Operator");
		mnuItemOpertor.setIcon(null);
		mnuItemOpertor.setMnemonic(KeyEvent.VK_O);
		if (null != img) {
			mnuItemOpertor.setIcon(new ImageIcon(img.getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
		}

		img = ApplicationMain.getBufferImage(ImageResources.mnuItmOperatorView.getValue());

		mnuItemProduct = new JMenuItem("Operator");
		mnuItemProduct.setIcon(null);
		mnuItemProduct.setMnemonic(KeyEvent.VK_O);
		if (null != img) {
			mnuItemProduct.setIcon(new ImageIcon(img.getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
		}

		img = ApplicationMain.getBufferImage(ImageResources.mnuItmMulControl.getValue());

		mnuItemMulControl = new JMenuItem("MulControl");
		mnuItemMulControl.setIcon(null);
		mnuItemMulControl.setMnemonic(KeyEvent.VK_M);
		if (null != img) {
			mnuItemMulControl.setIcon(new ImageIcon(img.getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
		}

		img = ApplicationMain.getBufferImage(ImageResources.mnuItmEstimate.getValue());

		mnuItemEstimate = new JMenuItem("Estimate");
		mnuItemEstimate.setIcon(null);
		mnuItemEstimate.setMnemonic(KeyEvent.VK_E);
		if (null != img) {
			mnuItemEstimate.setIcon(new ImageIcon(img.getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
		}

		img = ApplicationMain.getBufferImage(ImageResources.mnuItmOperatorView.getValue());

		mnuItemOpertorView = new JMenuItem("Operator View");
		mnuItemOpertorView.setIcon(null);
		mnuItemOpertorView.setMnemonic(KeyEvent.VK_O);
		if (null != img) {
			mnuItemOpertorView.setIcon(new ImageIcon(img.getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
		}

		img = ApplicationMain.getBufferImage(ImageResources.mnuItmOperatorView.getValue());

		mnuItemProductView = new JMenuItem("Operator");
		mnuItemProductView.setIcon(null);
		mnuItemProductView.setMnemonic(KeyEvent.VK_O);
		if (null != img) {
			mnuItemProductView.setIcon(new ImageIcon(img.getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
		}

		img = ApplicationMain.getBufferImage(ImageResources.mnuItmMulControlView.getValue());

		mnuItemMulControlView = new JMenuItem("MulControl View");
		mnuItemMulControlView.setIcon(null);
		mnuItemMulControlView.setMnemonic(KeyEvent.VK_M);
		if (null != img) {
			mnuItemMulControlView.setIcon(new ImageIcon(img.getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
		}

		img = ApplicationMain.getBufferImage(ImageResources.mnuItmEstimateView.getValue());

		mnuItemEstimateView = new JMenuItem("Estimate View");
		mnuItemEstimateView.setIcon(null);
		mnuItemEstimateView.setMnemonic(KeyEvent.VK_E);
		if (null != img) {
			mnuItemEstimateView.setIcon(new ImageIcon(img.getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
		}

		mnuTransaction.add(mnuItemOpertor);
		mnuTransaction.add(mnuItemMulControl);
		mnuTransaction.add(mnuItemEstimate);

		mnuView.add(mnuItemOpertorView);
		mnuView.add(mnuItemMulControlView);
		mnuView.add(mnuItemEstimateView);
	}

	private void createPanelButtons() {

		int x = pnlButtons.getWidth() * 15 / 100, y = pnlButtons.getHeight() * 20 / 100,
				wid = pnlButtons.getWidth() * 5 / 100, hgt = pnlButtons.getHeight() * 50 / 100;

		btnAdd = new JButton("ADD");
		btnAdd.setBounds(x, y, wid, hgt);
		btnAdd.setMnemonic(KeyEvent.VK_A);
		btnAdd.setFont(ApplicationMain.getLblFont());

		x = ApplicationMain.horizontalGap(pnlButtons, btnAdd, 8);

		btnSave = new JButton("SAVE");
		btnSave.setBounds(x, y, wid, hgt);
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.setFont(ApplicationMain.getLblFont());

		x = ApplicationMain.horizontalGap(pnlButtons, btnSave, 8);

		btnEdit = new JButton("EDIT");
		btnEdit.setBounds(x, y, wid, hgt);
		btnEdit.setMnemonic(KeyEvent.VK_E);
		btnEdit.setFont(ApplicationMain.getLblFont());

		x = ApplicationMain.horizontalGap(pnlButtons, btnEdit, 8);

		btnView = new JButton("VIEW");
		btnView.setBounds(x, y, wid, hgt);
		btnView.setMnemonic(KeyEvent.VK_V);
		btnView.setFont(ApplicationMain.getLblFont());

		x = ApplicationMain.horizontalGap(pnlButtons, btnView, 8);

		btnCancel = new JButton("CANCEL");
		btnCancel.setBounds(x, y, wid, hgt);
		btnCancel.setMnemonic(KeyEvent.VK_C);
		btnCancel.setFont(ApplicationMain.getLblFont());

		x = ApplicationMain.horizontalGap(pnlButtons, btnCancel, 8);

		btnExit = new JButton("EXIT");
		btnExit.setBounds(x, y, wid, hgt);
		btnExit.setMnemonic(KeyEvent.VK_X);
		btnExit.setFont(ApplicationMain.getLblFont());

		btnAdd.setVisible(false);
		btnSave.setVisible(false);
		btnEdit.setVisible(false);
		btnView.setVisible(false);
		btnCancel.setVisible(false);
		btnExit.setVisible(false);

		pnlButtons.add(btnAdd);
		pnlButtons.add(btnSave);
		pnlButtons.add(btnEdit);
		pnlButtons.add(btnView);
		pnlButtons.add(btnCancel);
		pnlButtons.add(btnExit);
	}

	private void createPanelTaskBar() throws Exception {
		int x = pnlTaskBar.getWidth() * 15 / 100, y = pnlTaskBar.getHeight() * 20 / 100,
				wid = pnlTaskBar.getWidth() * 25 / 100, hgt = pnlTaskBar.getHeight() * 50 / 100;

		lblHints = new JLabel();
		lblHints.setBounds(0, y, wid, hgt);

		x = pnlTaskBar.getWidth() * 75 / 100;

		BufferedImage img = ApplicationMain.getBufferImage(ImageResources.calc.getValue());

		lblCalc = new JLabel();
		lblCalc.setBounds(x, y, 40, 40);
		if (null != img) {
			lblCalc.setIcon(new ImageIcon(img.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		}

		pnlTaskBar.add(lblHints);
		pnlTaskBar.add(lblCalc);
	}

	private void componentListeners() throws Exception {
		btnMinimize.addActionListener(this);
		lblCalc.addMouseListener(this);

		btnAdd.addActionListener(this);
		btnSave.addActionListener(this);
		btnEdit.addActionListener(this);
		btnView.addActionListener(this);
		btnCancel.addActionListener(this);
		btnExit.addActionListener(this);

		btnAdd.addMouseListener(this);
		btnSave.addMouseListener(this);
		btnEdit.addMouseListener(this);
		btnView.addMouseListener(this);
		btnCancel.addMouseListener(this);
		btnExit.addMouseListener(this);

		mnuItemOpertor.addActionListener(this);
		mnuItemOpertorView.addActionListener(this);
		mnuItemEstimate.addActionListener(this);
		mnuItemEstimateView.addActionListener(this);
		mnuItemMulControl.addActionListener(this);
		mnuItemMulControlView.addActionListener(this);
		mnuItemProduct.addActionListener(this);
		mnuItemProductView.addActionListener(this);

		mnuExit.addMenuKeyListener(this);
		mnuExit.addMouseListener(this);
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
	public void add() {

	}

	@Override
	public void save() {

	}

	@Override
	public void cancel() {

	}

	@Override
	public void exit() {
		try {
			FrmLogin frmLogin = ApplicationConfig.context.getBean(FrmLogin.class);
			frmLogin.loadInitialize();
			frmLogin.setVisible(true);
			dispose();
		} catch (Exception e) {
			AppStart.getLogger().log(Level.ALL, e.getMessage(), e);
			ApplicationMain.errorMessage(e.getMessage(), getContentPane());
		}
	}

	@Override
	public void view() {

	}

	@Override
	public void edit() {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {

			if (e.getComponent() == lblCalc) {
				try {
					if (System.getProperty("os.name").contains("Windows")) {
						Runtime.getRuntime().exec("calc.exe");
					} else {
						Runtime.getRuntime().exec("gnome-calculator");
					}
				} catch (IOException e1) {
					ApplicationMain.errorMessage(e1.getMessage(), getContentPane());
				}
			} else if (e.getComponent() == mnuExit) {
				exit();
			}

		} catch (Exception e2) {
			AppStart.getLogger().log(Level.ALL, e2.getMessage(), e2);
			ApplicationMain.errorMessage(e2.getMessage(), getContentPane());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		try {
			if (e.getComponent() == btnAdd) {
				btnAdd.setBackground(ApplicationMain.getBtnColor());
				btnAdd.setForeground(Color.WHITE);
			} else if (e.getComponent() == btnSave) {
				btnSave.setBackground(ApplicationMain.getBtnColor());
				btnSave.setForeground(Color.WHITE);
			} else if (e.getComponent() == btnView) {
				btnView.setBackground(ApplicationMain.getBtnColor());
				btnView.setForeground(Color.WHITE);
			} else if (e.getComponent() == btnEdit) {
				btnEdit.setBackground(ApplicationMain.getBtnColor());
				btnEdit.setForeground(Color.WHITE);
			} else if (e.getComponent() == btnCancel) {
				btnCancel.setBackground(ApplicationMain.getBtnColor());
				btnCancel.setForeground(Color.WHITE);
			} else if (e.getComponent() == btnExit) {
				btnExit.setBackground(ApplicationMain.getBtnColor());
				btnExit.setForeground(Color.WHITE);
			}
		} catch (Exception e2) {
			AppStart.getLogger().log(Level.ALL, e2.getMessage(), e2);
			ApplicationMain.errorMessage(e2.getMessage(), getContentPane());
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		try {
			if (e.getComponent() == btnAdd) {
				btnAdd.setBackground(Color.WHITE);
				btnAdd.setForeground(Color.BLACK);
			} else if (e.getComponent() == btnSave) {
				btnSave.setBackground(Color.WHITE);
				btnSave.setForeground(Color.BLACK);
			} else if (e.getComponent() == btnEdit) {
				btnEdit.setBackground(Color.WHITE);
				btnEdit.setForeground(Color.BLACK);
			} else if (e.getComponent() == btnView) {
				btnView.setBackground(Color.WHITE);
				btnView.setForeground(Color.BLACK);
			} else if (e.getComponent() == btnCancel) {
				btnCancel.setBackground(Color.WHITE);
				btnCancel.setForeground(Color.BLACK);
			} else if (e.getComponent() == btnExit) {
				btnExit.setBackground(Color.WHITE);
				btnExit.setForeground(Color.BLACK);
			}
		} catch (Exception e2) {
			AppStart.getLogger().log(Level.ALL, e2.getMessage(), e2);
			ApplicationMain.errorMessage(e2.getMessage(), getContentPane());
		}

	}

	@Override
	public void menuKeyPressed(MenuKeyEvent e) {
		try {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				MenuElement[] selectionPath = e.getMenuSelectionManager().getSelectedPath();
				for (int i = 0; i < selectionPath.length; i++) {
					if (selectionPath[i] == mnuExit) {
						exit();
					}
				}
			}

		} catch (Exception e2) {
			AppStart.getLogger().log(Level.ALL, e2.getMessage(), e2);
			ApplicationMain.errorMessage(e2.getMessage(), getContentPane());
		}
	}

	@Override
	public void menuKeyTyped(MenuKeyEvent e) {

	}

	@Override
	public void menuKeyReleased(MenuKeyEvent e) {

	}

}
