package com.market.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.market.entity.User;

public class SignInView implements ActionListener {
	JFrame frame = new JFrame();
	// jtable
	JLabel seepass, dontseepass;
	// checkbox
	JCheckBox saveCheckbox;
	// text field
	JTextField accountField;
	JPasswordField passwordField;
	// button
	JButton loginButton, signupButton;
	// option for showdialog
	int input;

	public SignInView() {
		addObject();
	}

	private void addObject() {
		// hiện và đặt vị trí

		// khung tựa đề KMP Market
		JLabel titleLable = new JLabel("KMP Market");
		titleLable.setBounds(205, -10, 250, 80);
		titleLable.setForeground(Color.red.brighter());
		titleLable.setFont(new Font("arial", Font.ROMAN_BASELINE, 40));
		frame.add(titleLable);

		// tên nhóm ở góc dưới bên phải
		JLabel grouplable = new JLabel("Group 4");
		grouplable.setBounds(545, 245, 120, 20);
		grouplable.setFont(new Font("arial", Font.ROMAN_BASELINE, 15));
		frame.add(grouplable);

		// tựa tài khoản trước khung nhập
		JLabel accountLable = new JLabel("Tài khoản");
		accountLable.setBounds(230, 85, 90, 25);
		accountLable.setForeground(Color.black);
		accountLable.setFont(new Font("Times", Font.ROMAN_BASELINE, 20));
		frame.add(accountLable);

		// tựa mật khẩu trước khung nhập
		JLabel passwordLable = new JLabel("Mật khẩu");
		passwordLable.setBounds(230, 130, 90, 25);
		passwordLable.setForeground(Color.black);
		passwordLable.setFont(new Font("Times", Font.ROMAN_BASELINE, 20));
		frame.add(passwordLable);

		// khung nhập user
		accountField = new JTextField();
		accountField.setBounds(330, 85, 240, 30);
		accountField.setFont(new Font("Times", Font.ROMAN_BASELINE, 20));
		frame.add(accountField);

		// khung nhập pass
		passwordField = new JPasswordField();
		passwordField.setBounds(330, 130, 240, 30);
		passwordField.setFont(new Font("Times", Font.ROMAN_BASELINE, 20));
		frame.add(passwordField);

		// xem mật khẩu
		seepass = new JLabel();
		seepass.setBounds(580, 135, 25, 25);
		ImageIcon imageIconSeepass = new ImageIcon(
				new ImageIcon("Image\\eye.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		seepass.setIcon(imageIconSeepass);
		frame.add(seepass);

		// ko xem mật khẩu
		dontseepass = new JLabel();
		dontseepass.setBounds(580, 135, 30, 30);
		ImageIcon imageIconDontSeepass = new ImageIcon(
				new ImageIcon("Image\\closeeyes.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		dontseepass.setIcon(imageIconDontSeepass);
		frame.add(dontseepass);

		// ô nhớ mật khẩu
		saveCheckbox = new JCheckBox("Nhớ mật khẩu");
		saveCheckbox.setBounds(327, 165, 230, 25);
		saveCheckbox.setBackground(Color.white.brighter());
		saveCheckbox.setForeground(Color.black);
		saveCheckbox.setFont(new Font("arial", Font.ROMAN_BASELINE, 15));
		frame.add(saveCheckbox);

		// nút đăng nhập
		loginButton = new JButton("Đăng nhập");
		loginButton.setBounds(330, 195, 115, 27);
		loginButton.setForeground(Color.black);
		loginButton.setFont(new Font("arial", Font.ROMAN_BASELINE, 17));
		loginButton.setFocusable(false);
		loginButton.addActionListener(this);
		frame.add(loginButton);

		// nút đăng kí
		signupButton = new JButton("Đăng kí");
		signupButton.setBounds(455, 195, 115, 27);
		signupButton.setForeground(Color.black);
		signupButton.setFont(new Font("arial", Font.ROMAN_BASELINE, 17));
		signupButton.setFocusable(false);
		signupButton.addActionListener(this);
		frame.add(signupButton);

		// background & icon
		// icon xe đẩy
		JLabel iconlable = new JLabel();
		iconlable.setBounds(5, 40, 230, 230);
		ImageIcon imageIcon1 = new ImageIcon(
				new ImageIcon("Image\\xeday.png").getImage().getScaledInstance(215, 215, Image.SCALE_DEFAULT));
		iconlable.setIcon(imageIcon1);
		frame.add(iconlable);
		// background
		JLabel background = new JLabel();
		background.setBounds(0, -15, 640, 300);
		ImageIcon imageIcon = new ImageIcon(
				new ImageIcon("Image\\background.png").getImage().getScaledInstance(640, 300, Image.SCALE_DEFAULT));
		background.setIcon(imageIcon);
		frame.add(background);

	}

	public void showWindow() {
		frame.setTitle("Đăng nhập");
		frame.setSize(640, 310);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // đặt đầu tránh lỗi
		frame.setLayout(null);
		frame.setResizable(false); // đặt size cố định
		frame.setVisible(true); // đặt cuối cùng
	}

	// hiện thông báo
	public void showMessage(String message, String title, int messageType) {
		JOptionPane.showMessageDialog(null, message, title, messageType);
		/*
		 * -1 : no 0: ERROR_MESSAGE 1 : INFORMATION_MESSAGE 2 : WARNING_MESSAGE 3 :
		 * QUESTION_MESSAGE 4 : PLAIN_MESSAGE
		 */
	}

	public void showConfirm(String message, String title, int messType) {
		input = JOptionPane.showConfirmDialog(null, message, title, messType);
		/*
		 * -1 :DEFAULT_OPTION 0: YES_NO_OPTION 1 : YES_NO_CANCEL_OPTION 2 :
		 * OK_CANCEL_OPTION
		 */
	}

	// khai báo Action
	public void actionPerformed(ActionEvent e) {
	}

	public void addSignInListener(ActionListener signInListener) {
		loginButton.addActionListener(signInListener);
	}

	public void addSignUpListener(ActionListener signUplistener) {
		signupButton.addActionListener(signUplistener);
	}

	public void savepass(ActionListener savePassListener) {
		saveCheckbox.addActionListener(savePassListener);
	}

	public void dispose() {
		frame.dispose();
	}

	public void addseepass(MouseListener Mouseseepass) {
		seepass.addMouseListener(Mouseseepass);
	}

	public void adddontseepass(MouseListener Mousedontseepass) {
		dontseepass.addMouseListener(Mousedontseepass);
	}

	// getters and setters
	public User getUser() {
		return new User(accountField.getText(), String.copyValueOf(passwordField.getPassword()));
	}

	public JCheckBox getSaveCheckbox() {
		return saveCheckbox;
	}

	public void setSaveCheckbox(JCheckBox saveCheckbox) {
		this.saveCheckbox = saveCheckbox;
	}

	public JTextField getAccountField() {
		return accountField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public int getInput() {
		return input;
	}

	public JLabel getSeepass() {
		return seepass;
	}

	public JLabel getDontseepass() {
		return dontseepass;
	}

}
