package com.project.estimateupdate.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.springframework.stereotype.Component;

import com.project.estimateupdate.AppStart;
import com.project.estimateupdate.common.ApplicationMain;
import com.project.estimateupdate.common.FrmAction;
import com.project.estimateupdate.common.ImageResources;

@Component
public class FrmOpertor extends JInternalFrame
		implements ActionListener, KeyListener, FrmAction, InternalFrameListener {

	private static final long serialVersionUID = 1L;

	public FrmOpertor() {
		try {
			setTitle("FromOperator");
			setPreferredSize(
					new Dimension(ApplicationMain.getInternalFrameWidth(), ApplicationMain.getInternalFrameHeight()));
			setSize(ApplicationMain.getInternalFrameWidth(), ApplicationMain.getInternalFrameHeight());
			setResizable(false);
			pack();

			BufferedImage img = ApplicationMain.getBufferImage(ImageResources.appIcon.getValue());
			if (null != img) {
				setFrameIcon(new ImageIcon(img));
			}

			addInternalFrameListener(this);
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

	}

	@Override
	public void add() {

	}

	@Override
	public void save() {

	}

	@Override
	public void edit() {

	}

	@Override
	public void cancel() {

	}

	@Override
	public void exit() {

	}

	@Override
	public void view() {

	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		try {
			componentCreation();
			componentListeners();
		} catch (Exception e1) {
			AppStart.getLogger().log(Level.ALL, e1.getMessage(), e1);
			ApplicationMain.errorMessage(e1.getMessage(), getContentPane());
		}
	}

	private void componentCreation() {
		JPanel pnlMain,pnlEntry ;

		pnlMain = new JPanel(null);
		pnlMain.setBounds(-ApplicationMain.getInternalFrameWidth() * 1 / 100,
				-ApplicationMain.getInternalFrameHeight() * 1 / 100, ApplicationMain.getInternalFrameWidth(),
				ApplicationMain.getInternalFrameHeight());
		pnlMain.setBackground(Color.WHITE);
		
		 pnlEntry=new JPanel(null);
		 pnlEntry.setBounds(new Rectangle(ApplicationMain.getInternalFrameWidth()/2, ApplicationMain.getInternalFrameHeight()/2));
		 pnlEntry.setBackground(Color.WHITE);
		 pnlEntry.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		

		setContentPane(pnlMain);
		pnlMain.add(pnlEntry);

	}

	private void componentListeners() {

	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {

	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {

	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {

	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {

	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {

	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {

	}

}
