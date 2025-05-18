package com.jilaba.orderratecalculator.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.print.PrintServiceLookup;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.springframework.stereotype.Component;

import com.jilaba.control.JTextFieldEnum;
import com.jilaba.control.JilabaColumn;
import com.jilaba.control.JilabaTable;
import com.jilaba.control.JilabaTextField;
import com.jilaba.orderratecalculator.common.AppMain;
import com.jilaba.orderratecalculator.common.ImageResources;
import com.jilaba.orderratecalculator.config.AppConfig;
import com.jilaba.orderratecalculator.logic.OrderRateLogic;

@Component
public class FrmOrderRateCalc extends JFrame
		implements ActionListener, KeyListener, WindowListener, FocusListener, MouseListener {
	private static final long serialVersionUID = 1L;

	private JPanel pnlTitleBar, pnlMenu;

	private JPanel pnlInternal;

	private JPanel pnlButtons;

	private JPanel pnlTaskBar;

	private JButton btnMinimize;

	private JLabel lblTime;

	private JLabel lblCalc;

	private JLabel lblHints;

	private JButton btnAdd;

	private JButton btnPrint;

	private JButton btnCancel;

	private JButton btnExit;

	private JilabaTextField txtOrderRate;

	private JilabaTextField txtOrderNo;

	private JilabaTextField txtGrossWt;

	private JilabaTextField txtLessWt;

	private JilabaTextField txtNetWt;

	private JilabaTextField txtHmcAmt;

	private JilabaTextField txtStoneAmt;

	private JilabaTextField txtDiaAmt;

	private JilabaTextField txtNetAmt;

	private JilabaTextField txtGstPer;

	private JilabaTextField txtTagNo;

	private JilabaTextField txtTotAmt;

	private JScrollPane scrOrderRate;

	private JilabaTable tblOrderRate;

	private double totGrs = 0.0D;

	private double totNet = 0.0D;

	private double totLess = 0.0D;

	private double totHmc = 0.0D;

	private double totStnAmt = 0.0D;

	private double totDiaAmt = 0.0D;

	private double totAmt = 0.0D;

	private double totDiaWt = 0.0D;
	private double totWast = 0.0D;

	private String wastWt = "", wastPer = "";

	private Map<String, Object> mapTagno;

	public FrmOrderRateCalc() {
		try {
			getContentPane().setPreferredSize(new Dimension(AppMain.getScreenWidth(), AppMain.getScreenHeight()));
			setTitle("Order Rate Calculator");
			setDefaultCloseOperation(3);
			setUndecorated(true);
			pack();
			setLocationRelativeTo(null);
			addWindowListener(this);

			BufferedImage img = AppMain.getBufferImage(ImageResources.appIcon.getValue());
			if (null != img) {
				setIconImage(img);
			}
		} catch (Exception e) {
			AppMain.errorMessage(e.getMessage(), getContentPane());
		}
	}

	private void componentCreation() throws Exception {
		JPanel pnlMain = new JPanel(null);
		pnlMain.setBounds(0, 0, getContentPane().getWidth(), getContentPane().getHeight());
		pnlMain.setBackground(Color.decode("#e4ccff"));
		getContentPane().add(pnlMain);

		pnlTitleBar = new JPanel(null);
		pnlTitleBar.setBounds(0, 0, pnlMain.getWidth(), pnlMain.getHeight() * 4 / 100);
		pnlTitleBar.setBackground(AppMain.getFgColor());

		pnlMenu = new JPanel(null);
		pnlMenu.setBounds(0, pnlMain.getHeight() * 4 / 100, pnlMain.getWidth(), pnlMain.getHeight() * 8 / 100);
		pnlMenu.setBackground(AppMain.getBgColor());
		pnlMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, AppMain.getFgColor()));

		pnlInternal = new JPanel(null);
		pnlInternal.setBounds(0, pnlMain.getHeight() * 12 / 100, pnlMain.getWidth(), pnlMain.getHeight() * 78 / 100);
		pnlInternal.setBackground(AppMain.getBgColor());

		int y = pnlTitleBar.getHeight() + pnlMenu.getHeight() + pnlInternal.getHeight();

		pnlButtons = new JPanel(null);
		pnlButtons.setBounds(0, y, pnlMain.getWidth(), pnlMain.getHeight() * 5 / 100);
		pnlButtons.setBackground(AppMain.getFgColor());
		pnlButtons.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.white));

		y = pnlMain.getHeight() - pnlMain.getHeight() * 5 / 100;

		pnlTaskBar = new JPanel(null);
		pnlTaskBar.setBounds(0, y, pnlMain.getWidth(), pnlMain.getHeight() * 5 / 100);
		pnlTaskBar.setBackground(AppMain.getBgColor());
		pnlTaskBar.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, AppMain.getFgColor()));

		int x = pnlMain.getWidth() - pnlMain.getWidth() * 1 / 100;

		btnMinimize = new JButton();
		btnMinimize.setBounds(x, pnlTaskBar.getHeight() * 9 / 100, pnlMain.getWidth() * 1 / 100,
				pnlTaskBar.getHeight());
		btnMinimize.setBackground(Color.black);

		setContentPane(pnlMain);
		pnlMain.add(pnlTitleBar);
		pnlMain.add(pnlMenu);
		pnlMain.add(pnlInternal);
		pnlMain.add(pnlButtons);
		pnlMain.add(pnlTaskBar);
		pnlTaskBar.add(btnMinimize);

		createPanelTitleBar();
		createPanelMenuBar();
		createPanelButtons();
		createPanelTaskBar();
		createPanelInternal();
	}

	private void createPanelTitleBar() throws Exception {
		int x = pnlTitleBar.getWidth() - pnlTitleBar.getWidth() * 15 / 100;

		BufferedImage img = AppMain.getBufferImage(ImageResources.calendar.getValue());

		lblTime = new JLabel();
		lblTime.setBounds(x, 0, pnlTitleBar.getWidth() * 15 / 100, pnlTitleBar.getHeight());
		lblTime.setFont(new Font("Arial", 1, 20));
		lblTime.setHorizontalTextPosition(4);
		lblTime.setForeground(Color.white);
		lblTime.setBackground(Color.white);
		lblTime.setIcon(new ImageIcon(img.getScaledInstance(30, 30, 4)));

		img = AppMain.getBufferImage(ImageResources.operator.getValue());
		x = pnlTitleBar.getX();

		JLabel lblOperator = new JLabel("Admin");
		lblOperator.setBounds(x, 0, pnlTitleBar.getWidth() * 12 / 100, pnlTitleBar.getHeight());
		lblOperator.setFont(new Font("Arial", 1, 20));
		lblOperator.setHorizontalTextPosition(4);
		lblOperator.setForeground(Color.white);
		lblOperator.setBackground(Color.white);
		lblOperator.setIcon(new ImageIcon(img.getScaledInstance(35, 35, 4)));

		x = pnlTitleBar.getWidth() - pnlTitleBar.getWidth() * 56 / 100;

		JLabel lblAppName = new JLabel("Order Rate Calculator");
		lblAppName.setBounds(x, 0, pnlTitleBar.getWidth() * 12 / 100, pnlTitleBar.getHeight());
		lblAppName.setFont(new Font("Arial", 1, 20));
		lblAppName.setHorizontalTextPosition(4);
		lblAppName.setForeground(Color.white);
		lblAppName.setBackground(Color.white);

		img = AppMain.getBufferImage(ImageResources.version.getValue());
		x = pnlTitleBar.getWidth() - pnlTitleBar.getWidth() * 35 / 100;

		JLabel lblAppVersion = new JLabel("1.25.02.01.01");
		lblAppVersion.setBounds(x, 0, pnlTitleBar.getWidth() * 10 / 100, pnlTitleBar.getHeight());
		lblAppVersion.setFont(new Font("Arial", 1, 20));
		lblAppVersion.setHorizontalTextPosition(4);
		lblAppVersion.setForeground(Color.white);
		lblAppVersion.setBackground(Color.white);
		lblAppVersion.setIcon(new ImageIcon(img.getScaledInstance(35, 35, 4)));

		img = AppMain.getBufferImage(ImageResources.ip.getValue());
		x = pnlTitleBar.getWidth() * 22 / 100;

		JLabel lblIpId = new JLabel(Inet4Address.getLocalHost().getHostAddress());
		lblIpId.setBounds(x, 0, pnlTitleBar.getWidth() * 10 / 100, pnlTitleBar.getHeight());
		lblIpId.setFont(new Font("Arial", 1, 20));
		lblIpId.setHorizontalTextPosition(4);
		lblIpId.setForeground(Color.white);
		lblIpId.setBackground(Color.white);
		lblIpId.setIcon(new ImageIcon(img.getScaledInstance(35, 35, 4)));

		pnlTitleBar.add(lblTime);
		pnlTitleBar.add(lblOperator);
		pnlTitleBar.add(lblAppName);
		pnlTitleBar.add(lblAppVersion);
		pnlTitleBar.add(lblIpId);
	}

	private void createPanelMenuBar() throws Exception {
		JLabel lblMenuName = new JLabel("Order Rate Calculator");
		lblMenuName.setBounds(10, 0, pnlMenu.getWidth() * 15 / 100, pnlMenu.getHeight());
		lblMenuName.setFont(new Font("Arial", 1, 20));
		lblMenuName.setHorizontalTextPosition(4);
		lblMenuName.setForeground(Color.black);
		pnlMenu.add(lblMenuName);
	}

	private void createPanelButtons() {
		int x = pnlButtons.getWidth() * 12 / 100, y = pnlButtons.getHeight() * 20 / 100;
		int wid = pnlButtons.getWidth() * 7 / 100, hgt = pnlButtons.getHeight() * 50 / 100;

		btnAdd = new JButton("ADD");
		btnAdd.setBounds(x, y, wid, hgt);
		btnAdd.setMnemonic(65);
		btnAdd.setFont(AppMain.getLblFont());

		x = AppMain.getHorizontallGap(pnlButtons, btnAdd, 15.0D);

		btnPrint = new JButton("Print");
		btnPrint.setBounds(x, y, wid, hgt);
		btnPrint.setMnemonic(83);
		btnPrint.setFont(AppMain.getLblFont());

		x = AppMain.getHorizontallGap(pnlButtons, btnPrint, 15.0D);

		btnCancel = new JButton("CANCEL");
		btnCancel.setBounds(x, y, wid, hgt);
		btnCancel.setMnemonic(67);
		btnCancel.setFont(AppMain.getLblFont());

		x = AppMain.getHorizontallGap(pnlButtons, btnCancel, 15.0D);

		btnExit = new JButton("EXIT");
		btnExit.setBounds(x, y, wid, hgt);
		btnExit.setMnemonic(88);
		btnExit.setFont(AppMain.getLblFont());

		pnlButtons.add(btnAdd);
		pnlButtons.add(btnPrint);
		pnlButtons.add(btnCancel);
		pnlButtons.add(btnExit);
	}

	private void createPanelTaskBar() throws Exception {
		int x = pnlTaskBar.getWidth() * 15 / 100, y = pnlTaskBar.getHeight() * 20 / 100;
		int wid = pnlTaskBar.getWidth() * 25 / 100, hgt = pnlTaskBar.getHeight() * 50 / 100;

		lblHints = new JLabel();
		lblHints.setBounds(0, y, wid, hgt);

		x = pnlTaskBar.getWidth() * 75 / 100;
		BufferedImage img = AppMain.getBufferImage(ImageResources.calc.getValue());

		lblCalc = new JLabel();
		lblCalc.setBounds(x, y, 40, 40);
		if (null != img) {
			lblCalc.setIcon(new ImageIcon(img.getScaledInstance(40, 40, 4)));
		}

		pnlTaskBar.add(lblHints);
		pnlTaskBar.add(lblCalc);
	}

	private void createPanelInternal() {
		int x = pnlInternal.getWidth() * 1 / 10, y = pnlInternal.getHeight() * 1 / 10;
		int wid = pnlInternal.getWidth() * 8 / 10, hgt = pnlInternal.getHeight() * 8 / 10;

		JPanel pnlMain = new JPanel(null);
		pnlMain.setBounds(x, y, wid, hgt);
		pnlMain.setBackground(AppMain.getBgColor());
		pnlMain.setBorder(new LineBorder(AppMain.getFgColor(), 3));

		x = pnlMain.getWidth() * 5 / 100;
		y = pnlMain.getHeight() * 4 / 100;
		wid = pnlMain.getWidth() * 15 / 100;
		hgt = pnlMain.getHeight() * 5 / 100;

		JLabel lblOrderRate = new JLabel("Order Rate");
		lblOrderRate.setBounds(x, y, wid, hgt);
		lblOrderRate.setFont(AppMain.getLblFont());

		y = AppMain.getVerticalGap(pnlMain, lblOrderRate, 3.0D);

		JLabel lblOrderNo = new JLabel("Order No");
		lblOrderNo.setBounds(x, y, wid, hgt);
		lblOrderNo.setFont(AppMain.getLblFont());

		y = AppMain.getVerticalGap(pnlMain, lblOrderNo, 3.0D);

		JLabel lblTagNo = new JLabel("Tag No");
		lblTagNo.setBounds(x, y, wid, hgt);
		lblTagNo.setFont(AppMain.getLblFont());

		y = AppMain.getVerticalGap(pnlMain, lblTagNo, 3.0D);

		JLabel lblGrossWt = new JLabel("Gross Wt");
		lblGrossWt.setBounds(x, y, wid, hgt);
		lblGrossWt.setFont(AppMain.getLblFont());

		y = AppMain.getVerticalGap(pnlMain, lblGrossWt, 3.0D);

		JLabel lblLessWt = new JLabel("Less Wt");
		lblLessWt.setBounds(x, y, wid, hgt);
		lblLessWt.setFont(AppMain.getLblFont());

		y = AppMain.getVerticalGap(pnlMain, lblLessWt, 3.0D);

		JLabel lblNetWt = new JLabel("Net Wt");
		lblNetWt.setBounds(x, y, wid, hgt);
		lblNetWt.setFont(AppMain.getLblFont());

		y = AppMain.getVerticalGap(pnlMain, lblNetWt, 3.0D);

		JLabel lblHmcAmt = new JLabel("Hmc Amt");
		lblHmcAmt.setBounds(x, y, wid, hgt);
		lblHmcAmt.setFont(AppMain.getLblFont());

		y = AppMain.getVerticalGap(pnlMain, lblHmcAmt, 3.0D);

		JLabel lblStoneAmt = new JLabel("Stone Amt");
		lblStoneAmt.setBounds(x, y, wid, hgt);
		lblStoneAmt.setFont(AppMain.getLblFont());

		y = AppMain.getVerticalGap(pnlMain, lblStoneAmt, 3.0D);

		JLabel lblDiaAmt = new JLabel("Diamond Amt");
		lblDiaAmt.setBounds(x, y, wid, hgt);
		lblDiaAmt.setFont(AppMain.getLblFont());

		y = AppMain.getVerticalGap(pnlMain, lblDiaAmt, 3.0D);

		JLabel lblNetAmt = new JLabel("Net Amt");
		lblNetAmt.setBounds(x, y, wid, hgt);
		lblNetAmt.setFont(AppMain.getLblFont());

		y = AppMain.getVerticalGap(pnlMain, lblNetAmt, 3.0D);

		JLabel lblGstPer = new JLabel("Gst Per");
		lblGstPer.setBounds(x, y, wid, hgt);
		lblGstPer.setFont(AppMain.getLblFont());

		y = AppMain.getVerticalGap(pnlMain, lblGstPer, 3.0D);

		JLabel lblTotAmt = new JLabel("Tot Amt");
		lblTotAmt.setBounds(x, y, wid, hgt);
		lblTotAmt.setFont(AppMain.getLblFont());

		x = AppMain.getHorizontallGap(pnlMain, lblOrderRate, 2.0D);
		y = lblOrderRate.getY();

		txtOrderRate = new JilabaTextField("");
		txtOrderRate.setBounds(x, y, wid, hgt);
		txtOrderRate.setTextType(JTextFieldEnum.TextInputType.NUMERIC);
		txtOrderRate.setTextSpaceReq(JTextFieldEnum.TextSpaceReq.NOTREQUIRED);
		txtOrderRate.setNumericDigits(JTextFieldEnum.NumericDigits.TWO);

		y = AppMain.getVerticalGap(pnlMain, lblOrderRate, 3.0D);

		txtOrderNo = new JilabaTextField("");
		txtOrderNo.setBounds(x, y, wid, hgt);
		txtOrderNo.setTextType(JTextFieldEnum.TextInputType.ALPHANUMBER);
		txtOrderNo.setTextSpaceReq(JTextFieldEnum.TextSpaceReq.NOTREQUIRED);
		txtOrderNo.setTextCase(JTextFieldEnum.TextInputCase.UPPER);

		y = AppMain.getVerticalGap(pnlMain, lblOrderNo, 3.0D);

		txtTagNo = new JilabaTextField("");
		txtTagNo.setBounds(x, y, wid, hgt);
		txtTagNo.setTextType(JTextFieldEnum.TextInputType.ALPHANUMBER);
		txtTagNo.setTextSpaceReq(JTextFieldEnum.TextSpaceReq.NOTREQUIRED);

		y = AppMain.getVerticalGap(pnlMain, lblTagNo, 3.0D);

		txtGrossWt = new JilabaTextField("");
		txtGrossWt.setBounds(x, y, wid, hgt);
		txtGrossWt.setTextType(JTextFieldEnum.TextInputType.NUMERIC);
		txtGrossWt.setTextSpaceReq(JTextFieldEnum.TextSpaceReq.NOTREQUIRED);
		txtGrossWt.setNumericDigits(JTextFieldEnum.NumericDigits.THREE);

		y = AppMain.getVerticalGap(pnlMain, lblGrossWt, 3.0D);

		txtLessWt = new JilabaTextField("");
		txtLessWt.setBounds(x, y, wid, hgt);
		txtLessWt.setTextType(JTextFieldEnum.TextInputType.NUMERIC);
		txtLessWt.setTextSpaceReq(JTextFieldEnum.TextSpaceReq.NOTREQUIRED);
		txtLessWt.setNumericDigits(JTextFieldEnum.NumericDigits.THREE);

		y = AppMain.getVerticalGap(pnlMain, lblLessWt, 3.0D);

		txtNetWt = new JilabaTextField("");

		txtNetWt.setBounds(x, y, wid, hgt);
		txtNetWt.setTextType(JTextFieldEnum.TextInputType.NUMERIC);
		txtNetWt.setTextSpaceReq(JTextFieldEnum.TextSpaceReq.NOTREQUIRED);
		txtNetWt.setNumericDigits(JTextFieldEnum.NumericDigits.THREE);
		txtNetWt.setEditable(false);

		y = AppMain.getVerticalGap(pnlMain, lblNetWt, 3.0D);

		txtHmcAmt = new JilabaTextField("");
		txtHmcAmt.setBounds(x, y, wid, hgt);
		txtHmcAmt.setTextType(JTextFieldEnum.TextInputType.NUMERIC);
		txtHmcAmt.setTextSpaceReq(JTextFieldEnum.TextSpaceReq.NOTREQUIRED);
		txtHmcAmt.setNumericDigits(JTextFieldEnum.NumericDigits.TWO);

		y = AppMain.getVerticalGap(pnlMain, lblHmcAmt, 3.0D);

		txtStoneAmt = new JilabaTextField("");
		txtStoneAmt.setBounds(x, y, wid, hgt);
		txtStoneAmt.setTextType(JTextFieldEnum.TextInputType.NUMERIC);
		txtStoneAmt.setTextSpaceReq(JTextFieldEnum.TextSpaceReq.NOTREQUIRED);
		txtStoneAmt.setNumericDigits(JTextFieldEnum.NumericDigits.TWO);

		y = AppMain.getVerticalGap(pnlMain, lblStoneAmt, 3.0D);

		txtDiaAmt = new JilabaTextField("");
		txtDiaAmt.setBounds(x, y, wid, hgt);
		txtDiaAmt.setTextType(JTextFieldEnum.TextInputType.NUMERIC);
		txtDiaAmt.setTextSpaceReq(JTextFieldEnum.TextSpaceReq.NOTREQUIRED);
		txtDiaAmt.setNumericDigits(JTextFieldEnum.NumericDigits.TWO);

		y = AppMain.getVerticalGap(pnlMain, lblDiaAmt, 3.0D);

		txtNetAmt = new JilabaTextField("");
		txtNetAmt.setBounds(x, y, wid, hgt);
		txtNetAmt.setTextType(JTextFieldEnum.TextInputType.NUMERIC);
		txtNetAmt.setTextSpaceReq(JTextFieldEnum.TextSpaceReq.NOTREQUIRED);
		txtNetAmt.setNumericDigits(JTextFieldEnum.NumericDigits.TWO);
		txtNetAmt.setEditable(false);

		y = AppMain.getVerticalGap(pnlMain, lblNetAmt, 3.0D);

		txtGstPer = new JilabaTextField("");
		txtGstPer.setBounds(x, y, wid, hgt);
		txtGstPer.setTextType(JTextFieldEnum.TextInputType.NUMERIC);
		txtGstPer.setTextSpaceReq(JTextFieldEnum.TextSpaceReq.NOTREQUIRED);
		txtGstPer.setNumericDigits(JTextFieldEnum.NumericDigits.TWO);

		y = AppMain.getVerticalGap(pnlMain, lblGstPer, 3.0D);

		txtTotAmt = new JilabaTextField("");
		txtTotAmt.setBounds(x, y, wid, hgt);
		txtTotAmt.setTextType(JTextFieldEnum.TextInputType.NUMERIC);
		txtTotAmt.setTextSpaceReq(JTextFieldEnum.TextSpaceReq.NOTREQUIRED);
		txtTotAmt.setNumericDigits(JTextFieldEnum.NumericDigits.TWO);
		txtTotAmt.setEditable(false);

		x = AppMain.getHorizontallGap(pnlMain, txtOrderRate, 5.0D);

		y = pnlMain.getHeight() * 20 / 100;
		wid = pnlMain.getWidth() * 55 / 100;
		hgt = pnlMain.getHeight() * 60 / 100;

		tblOrderRate = new JilabaTable(getTblColumns(wid));
		tblOrderRate.setColumnSelectionAllowed(true);
		tblOrderRate.setAutoResizeMode(4);
		scrOrderRate = new JScrollPane(tblOrderRate);
		scrOrderRate.setBounds(x, y, wid, hgt);

		pnlInternal.add(pnlMain);
		pnlMain.add(lblOrderRate);
		pnlMain.add(lblOrderNo);
		pnlMain.add(lblGrossWt);
		pnlMain.add(lblLessWt);
		pnlMain.add(lblNetWt);
		pnlMain.add(lblHmcAmt);
		pnlMain.add(lblStoneAmt);
		pnlMain.add(lblDiaAmt);
		pnlMain.add(lblNetAmt);
		pnlMain.add(lblGstPer);
		pnlMain.add(lblTagNo);
		pnlMain.add(lblTotAmt);
		pnlMain.add(txtOrderRate);
		pnlMain.add(txtOrderNo);
		pnlMain.add(txtGrossWt);
		pnlMain.add(txtLessWt);
		pnlMain.add(txtNetWt);
		pnlMain.add(txtHmcAmt);
		pnlMain.add(txtStoneAmt);
		pnlMain.add(txtDiaAmt);
		pnlMain.add(txtNetAmt);
		pnlMain.add(txtGstPer);
		pnlMain.add(txtTagNo);
		pnlMain.add(txtTotAmt);
		pnlMain.add(scrOrderRate);
	}

	private List<JilabaColumn> getTblColumns(int wid) {
		List<JilabaColumn> lstColumn = new ArrayList<>();
		lstColumn.add(new JilabaColumn("TagNo", String.class, wid * 1 / 10, 0));
		lstColumn.add(new JilabaColumn("GrsWt", Double.class, wid * 1 / 10, 4));
		lstColumn.add(new JilabaColumn("LessWt", Double.class, wid * 1 / 10, 4));
		lstColumn.add(new JilabaColumn("NetWt", Double.class, wid * 1 / 10, 4));
		lstColumn.add(new JilabaColumn("GstAmt", Double.class, wid * 1 / 10, 4));
		lstColumn.add(new JilabaColumn("TotAmt", Double.class, wid * 1 / 10, 4));
		return lstColumn;
	}

	private void componentListeners() throws Exception {
		btnMinimize.addActionListener(this);
		lblCalc.addMouseListener(this);
		btnAdd.addActionListener(this);
		btnPrint.addActionListener(this);
		btnCancel.addActionListener(this);
		btnExit.addActionListener(this);
		btnAdd.addFocusListener(this);
		btnPrint.addFocusListener(this);
		btnCancel.addFocusListener(this);
		btnExit.addFocusListener(this);
		txtOrderRate.addKeyListener(this);
		txtOrderNo.addKeyListener(this);
		txtGrossWt.addKeyListener(this);
		txtLessWt.addKeyListener(this);
		txtNetWt.addKeyListener(this);
		txtHmcAmt.addKeyListener(this);
		txtStoneAmt.addKeyListener(this);
		txtDiaAmt.addKeyListener(this);
		txtNetAmt.addKeyListener(this);
		txtGstPer.addKeyListener(this);
		txtTagNo.addKeyListener(this);
		txtTotAmt.addKeyListener(this);
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		try {
			if (e.getKeyCode() == 10) {
				if (e.getComponent() == txtOrderRate) {
					txtOrderNo.requestFocus();
				} else if (e.getComponent() == txtOrderNo) {
					txtTagNo.requestFocus();
				} else if (e.getComponent() == txtTagNo) {
					getTagNoDetails();
				} else if (e.getComponent() == txtGrossWt) {
					txtLessWt.requestFocus();
				} else if (e.getComponent() == txtLessWt) {
					txtHmcAmt.requestFocus();
				} else if (e.getComponent() == txtHmcAmt) {
					txtStoneAmt.requestFocus();
				} else if (e.getComponent() == txtStoneAmt) {
					txtDiaAmt.requestFocus();
				} else if (e.getComponent() == txtDiaAmt) {
					txtGstPer.requestFocus();
				} else if (e.getComponent() == txtGstPer) {
					txtTotAmt.requestFocus();
				} else if (e.getComponent() == txtTotAmt) {
					addTblRecord();
					txtTagNo.requestFocus();
				}
			} else if (e.getKeyCode() == 27) {
				btnPrint.requestFocus();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			AppMain.errorMessage(e2.getMessage(), getContentPane());
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == btnMinimize) {
				if (getExtendedState() != 1)
					setExtendedState(1);
			} else if (e.getSource() == btnExit) {
				System.exit(0);
			} else if (e.getSource() == btnPrint) {
				print();
				btnCancel.doClick();
			} else if (e.getSource() == btnCancel) {
				clear();
				btnAdd.requestFocus();
			} else if (e.getSource() == btnAdd) {
				txtOrderRate.requestFocus();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(this, e2.getMessage(), getTitle(), 0);
		}
	}

	public void windowOpened(WindowEvent e) {
		try {
			componentCreation();
			componentListeners();
			ScheduledExecutorService timer = new ScheduledThreadPoolExecutor(1);
			timer.execute(new Runnable() {
				@Override
				public void run() {
					while (true) {
						lblTime.setText((new SimpleDateFormat("dd-MMM-yyyy hh:mm.ss a")).format(new Date()));
					}
				}
			});
			btnAdd.requestFocus();
		} catch (Exception e2) {
			AppMain.errorMessage(e2.getMessage(), getContentPane());
		}
	}

	public void windowClosing(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getComponent() == lblCalc)
			try {
				if (System.getProperty("os.name").contains("Windows")) {
					Runtime.getRuntime().exec("calc.exe");
				} else {
					Runtime.getRuntime().exec("gnome-calculator");
				}
			} catch (IOException e1) {
				AppMain.errorMessage(e1.getMessage(), getContentPane());
			}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void focusGained(FocusEvent e) {
		if (e.getComponent() == btnAdd) {
			btnAdd.setBackground(AppMain.getBtnColor());
			btnAdd.setForeground(Color.WHITE);
			btnAdd.setBorder(new LineBorder(Color.white, 2));
		} else if (e.getComponent() == btnPrint) {
			btnPrint.setBackground(AppMain.getBtnColor());
			btnPrint.setForeground(Color.WHITE);
			btnPrint.setBorder(new LineBorder(Color.white, 2));
		} else if (e.getComponent() == btnCancel) {
			btnCancel.setBackground(AppMain.getBtnColor());
			btnCancel.setForeground(Color.WHITE);
			btnCancel.setBorder(new LineBorder(Color.white, 2));
		} else if (e.getComponent() == btnExit) {
			btnExit.setBackground(AppMain.getBtnColor());
			btnExit.setForeground(Color.WHITE);
			btnExit.setBorder(new LineBorder(Color.white, 2));
		}
	}

	public void focusLost(FocusEvent e) {
		if (e.getComponent() == btnAdd) {
			btnAdd.setBackground(Color.WHITE);
			btnAdd.setForeground(Color.BLACK);
			btnAdd.setBorder(new LineBorder(Color.black, 2));
		} else if (e.getComponent() == btnPrint) {
			btnPrint.setBackground(Color.WHITE);
			btnPrint.setForeground(Color.BLACK);
			btnPrint.setBorder(new LineBorder(Color.black, 2));
		} else if (e.getComponent() == btnCancel) {
			btnCancel.setBackground(Color.WHITE);
			btnCancel.setForeground(Color.BLACK);
			btnCancel.setBorder(new LineBorder(Color.black, 2));
		} else if (e.getComponent() == btnExit) {
			btnExit.setBackground(Color.WHITE);
			btnExit.setForeground(Color.BLACK);
			btnExit.setBorder(new LineBorder(Color.black, 2));
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	private void clear() {
		txtOrderRate.setText("");
		txtOrderNo.setText("");
		txtTagNo.setText("");
		txtGrossWt.setText("");
		txtLessWt.setText("");
		txtNetWt.setText("");
		txtHmcAmt.setText("");
		txtStoneAmt.setText("");
		txtDiaAmt.setText("");
		txtNetAmt.setText("");
		txtGstPer.setText("");
		txtTotAmt.setText("");
		tblOrderRate.clear();

		totGrs = 0.0D;
		totNet = 0.0D;
		totLess = 0.0D;
		totHmc = 0.0D;
		totStnAmt = 0.0D;
		totDiaAmt = 0.0D;
		totAmt = 0.0D;
		totDiaWt = 0.0D;
		wastPer = wastPer.isEmpty() ? "" : wastPer;
		wastWt = "";
	}

	private void getTagNoDetails() throws Exception {
		double tGst = 0.0D, tNetAmt = 0.0D;
		OrderRateLogic logic = (OrderRateLogic) AppConfig.context.getBean(OrderRateLogic.class);
		mapTagno = logic.getTagNoDetails(txtTagNo.getText());
		if (!mapTagno.isEmpty()) {
			if (wastPer.isEmpty()) {
				wastPer = String.valueOf(mapTagno.get("maxwaspergrm"));
			} else {
				if (Double.parseDouble(wastPer) != AppMain
						.getDoubleValue(String.valueOf(mapTagno.get("maxwaspergrm")))) {
					throw new Exception("TagNo Should Contain Same Wastage % as of Previous TagNo !!");
				} else {
					wastPer = String.valueOf(mapTagno.get("maxwaspergrm"));
				}
			}

			txtGrossWt.setText(String.valueOf(mapTagno.get("grosswt")));
			txtLessWt.setText(String.valueOf(mapTagno.get("lesswt")));
			txtNetWt.setText(String.valueOf(mapTagno.get("netwt")));
			txtGstPer.setText(String.valueOf(mapTagno.get("gstper")));
			txtHmcAmt.setText(String.valueOf(mapTagno.get("othercharge")));
			txtStoneAmt.setText(String.valueOf(mapTagno.get("studstnamt")));
			txtDiaAmt.setText(String.valueOf(mapTagno.get("studdiaamt")));

			tNetAmt = ((Double.parseDouble(txtNetWt.getText())
					+ AppMain.getDoubleValue(String.valueOf(mapTagno.get("maxwastage"))))
					* Double.parseDouble(txtOrderRate.getText())) + AppMain.getDoubleValue(txtHmcAmt.getText())
					+ AppMain.getDoubleValue(txtStoneAmt.getText()) + AppMain.getDoubleValue(txtDiaAmt.getText());

			tGst = (((Double.parseDouble(txtNetWt.getText())
					+ AppMain.getDoubleValue(String.valueOf(mapTagno.get("maxwastage"))))
					* Double.parseDouble(txtOrderRate.getText())) + AppMain.getDoubleValue(txtHmcAmt.getText())
					+ AppMain.getDoubleValue(txtStoneAmt.getText()) + AppMain.getDoubleValue(txtDiaAmt.getText()))
					* Double.parseDouble(txtGstPer.getText()) / 100.0D;

			wastWt = String.valueOf(mapTagno.get("maxwastage"));
			txtNetAmt.setText(AppMain.getAmt().format(tNetAmt));
			txtTotAmt.setText(AppMain.getAmt().format(tNetAmt + tGst));
			txtTotAmt.requestFocus();
		} else {
			throw new Exception("Invalid TagNo");
		}
	}

	private void addTblRecord() {
		if (!txtTagNo.getText().isEmpty()) {

			totGrs += Double.parseDouble(txtGrossWt.getText());
			totNet += Double.parseDouble(txtNetWt.getText());
			totLess += Double.parseDouble(txtLessWt.getText());
			totHmc += Double.parseDouble(txtHmcAmt.getText());
			totStnAmt += Double.parseDouble(txtStoneAmt.getText());
			totDiaAmt += Double.parseDouble(txtDiaAmt.getText());
			totAmt += Double.parseDouble(txtTotAmt.getText());
			totDiaWt += AppMain.getDoubleValue(String.valueOf(mapTagno.get("diawt")));
			totWast += AppMain.getDoubleValue(wastWt);

			List<Object> lstObject = new ArrayList<Object>();
			lstObject.add(txtTagNo.getText());
			lstObject.add(txtGrossWt.getText());
			lstObject.add(txtLessWt.getText());
			lstObject.add(txtNetWt.getText());
			lstObject.add(wastWt);
			lstObject.add(txtTotAmt.getText());
			tblOrderRate.addRow(lstObject);

			txtTagNo.setText("");
			txtGrossWt.setText("");
			txtLessWt.setText("");
			txtNetWt.setText("");
			txtHmcAmt.setText("");
			txtStoneAmt.setText("");
			txtDiaAmt.setText("");
			txtNetAmt.setText("");
			txtGstPer.setText("");
			txtTotAmt.setText("");
		}
	}

	private void print() throws Exception {
		if (tblOrderRate.getRowCount() > 0) {
			CodeSource codeSource = com.jilaba.orderratecalculator.form.FrmOrderRateCalc.class.getProtectionDomain()
					.getCodeSource();

			String fi = new File(codeSource.getLocation().getPath()).getParent();

			while (fi.contains("BOOT") || fi.contains("Classes")) {
				fi = new File(fi).getParentFile().getParent();
			}

			fi = fi.replace("nested:\\", "");

			File file = new File(fi);

			FileWriter fw = new FileWriter(file.getParent() + File.separator + "OrderRate.jas");

			// Header
			fw.write("G0\r\n");
			fw.write("n\n");
			fw.write("M0591\r\n");
			fw.write("MT\r\n");
			fw.write("O0214\r\n");
			fw.write("V0\r\n");
			fw.write("t1\r\n");
			fw.write("Kf0070\r\n");
			fw.write("SG\r\n");
			fw.write("c0000\r\n");
			fw.write("e\r\n");
			fw.write("L\r\n");
			fw.write("D11\r\n");
			fw.write("H16\r\n");
			fw.write("PG\r\n");
			fw.write("pG\r\n");
			fw.write("SG\r\n");
			fw.write("ySPM\r\n");
			fw.write("A2\r\n");

			// Body
			fw.write("1911C1002060010" + "OrderNo " + txtOrderNo.getText() + " "
					+ (new SimpleDateFormat("dd-MMM-yyyy")).format(new Date()).toUpperCase() + "\r\n");
			fw.write("1911C1001780037" + "OrderRate\r\n");
			fw.write("1911C1001780150" + ": " + txtOrderRate.getText() + "\r\n");
			fw.write("1911C1001600037" + "GrossWt\r\n");
			fw.write("1911C1001600150" + ": " + AppMain.getWt().format(totGrs) + "\r\n");
			fw.write("1911C1001410037" + "LessWt\r\n");
			fw.write("1911C1001410150" + ": " + AppMain.getWt().format(totLess) + "\r\n");
			fw.write("1911C1001230037" + "NetWt\r\n");
			fw.write("1911C1001230150" + ": " + AppMain.getWt().format(totNet) + "\r\n");
			fw.write("1911C1001050037" + "Wastage\r\n");
			fw.write("1911C1001050150" + ": " + AppMain.getWt().format(totWast) + "\r\n");
			fw.write("1911C1000860037" + "HMC + Stone\r\n");
			fw.write("1911C1000860150" + ": " + AppMain.getAmt().format(totHmc + totStnAmt) + "\r\n");
			fw.write("1911C1000680037" + "Diamond Amt\r\n");
			fw.write("1911C1000680150" + ": " + AppMain.getAmt().format(totDiaAmt)
					+ ((totDiaWt > 0.0D) ? (" (" + AppMain.getWt().format(totDiaWt) + " cts)") : "") + "\r\n");
			fw.write("1911C1000490037" + "GST \r\n");
			fw.write("1911C1000490150" + ": 3 % \r\n");
			fw.write("1911C1000310037" + "Total Amt\r\n");
			fw.write("1911C1000310150" + ": " + AppMain.getNt().format(totAmt) + "\r\n");

			// Footer
			fw.write("Q0001\r\n");
			fw.write("E\r\n");
			fw.close();

			String printername = PrintServiceLookup.lookupDefaultPrintService().getName();

			String filename = file.getParent() + File.separator + "OrderRatePrint.bat";
			String jasfile = file.getParent() + File.separator + "OrderRate.jas";
			if (null != printername) {
				String findPrinterLocalOrNetWork = printername.substring(0, 2);
				if (!findPrinterLocalOrNetWork.equalsIgnoreCase("\\")) {
					printername = "\\\\" + InetAddress.getLocalHost().getHostAddress().concat("\\").concat(printername);
				}

				StringBuilder printCommand = new StringBuilder("TYPE " + jasfile + ">" + printername + "\n Exit");
				Files.write(Paths.get(filename, new String[0]), printCommand.toString().getBytes(),
						new java.nio.file.OpenOption[0]);
			}
			String[] command = { "cmd.exe", "/C", "Start", filename };
			Runtime.getRuntime().exec(command);
		} else {
			btnAdd.requestFocus();
			throw new Exception("Nothing to Print");
		}
	}
}
