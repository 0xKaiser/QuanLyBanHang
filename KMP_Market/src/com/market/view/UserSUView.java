package com.market.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.market.entity.User;

public class UserSUView extends JFrame implements ActionListener {

	private JTextField txtUser;
	private JPasswordField txtPass, txtCPass;
	private JButton btnDangKi, btnThoat, btnTroLai;

	public UserSUView() {
		addControl();
	}

	private void addControl() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container con = getContentPane();
		JPanel pnMain = new JPanel();
		pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
		con.add(pnMain);

		JPanel pnTitle = new JPanel();
		pnTitle.setLayout(new FlowLayout());
		JLabel lblTieude = new JLabel("ĐĂNG KÍ TÀI KHOẢN");
		pnTitle.add(lblTieude);
		pnMain.add(pnTitle);
		lblTieude.setForeground(Color.BLACK);
		Font fontTieuDe = new Font("arial", Font.BOLD, 20);
		lblTieude.setFont(fontTieuDe);

		JPanel pnUser = new JPanel();
		pnUser.setLayout(new FlowLayout());
		JLabel lblUser = new JLabel("Tài khoản ");
		txtUser = new JTextField(20);
		pnUser.add(lblUser);
		pnUser.add(txtUser);
		pnMain.add(pnUser);

		JPanel pnPass = new JPanel();
		pnPass.setLayout(new FlowLayout());
		JLabel lblPass = new JLabel("Mật khẩu ");
		txtPass = new JPasswordField(20);
		pnPass.add(lblPass);
		pnPass.add(txtPass);
		pnMain.add(pnPass);

		JPanel pnCPass = new JPanel();
		pnCPass.setLayout(new FlowLayout());
		JLabel lblCPass = new JLabel("Xác nhận mật khẩu ");
		txtCPass = new JPasswordField(20);
//		txtCPass = new JTextField(20);
		pnCPass.add(lblCPass);
		pnCPass.add(txtCPass);
		pnMain.add(pnCPass);

		// tạo button
		JPanel pnButton = new JPanel();
		pnButton.setLayout(new FlowLayout());
		btnTroLai = new JButton("Trở lại ");
		btnDangKi = new JButton("Đăng ký ");
		btnThoat = new JButton("Bỏ qua ");
		pnButton.add(btnTroLai);
		pnButton.add(btnDangKi);
		pnButton.add(btnThoat);
		pnMain.add(pnButton);

		btnDangKi.addActionListener(this);
		btnThoat.addActionListener(this);
		btnTroLai.addActionListener(this);

		lblUser.setPreferredSize(lblCPass.getPreferredSize());
		lblPass.setPreferredSize(lblCPass.getMaximumSize());

		this.setTitle("Signup");
		this.setSize(350, 280);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

//		this.setVisible(true);

	public User getUser() {
		return new User(txtUser.getText().trim(), String.copyValueOf(txtPass.getPassword()),
				String.copyValueOf(txtCPass.getPassword()));

	}

	public void actionPerformed(ActionEvent e) {
	}

	public void addSignupListener(ActionListener listener) {
		btnDangKi.addActionListener(listener);
	}

	public void addExitListener(ActionListener exit) {
		btnThoat.addActionListener(exit);
	}

	public void addReturnListener(ActionListener trlai) {
		btnTroLai.addActionListener(trlai);
	}
}
