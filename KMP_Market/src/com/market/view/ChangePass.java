package com.market.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.market.jdbc.Connect;
import com.market.services.Md5;

public class ChangePass {
	JFrame frame = new JFrame();
	JTextField txtUserName, txtOldPass, txtNewPass;
	JButton btChangePass2;
	Font font = new Font("arial", Font.BOLD, 20);
	Connection conn = Connect.getConnect();
	Md5 mahoa = new Md5();

	public ChangePass() {
		addControls();
	}

	public void processChangePass(String idx) {
		try {
			String sql = "update person.Users set password='" + mahoa.convertHashToString(txtNewPass.getText())
					+ "' where idUser='" + idx + "' and username='" + txtUserName.getText() + "'";
			Statement statement = conn.createStatement();
			int results = statement.executeUpdate(sql);
			if (results > 0)
				JOptionPane.showMessageDialog(null, "Thay đổi mật khẩu thành công", "Thông báo", -1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void addControls() {
		JPanel pnAll = new JPanel();
		JPanel pnChangePass = new JPanel();
		pnChangePass.setLayout(new BoxLayout(pnChangePass, BoxLayout.Y_AXIS));
		pnAll.add(pnChangePass, BorderLayout.PAGE_END);
		frame.add(pnAll);

		// Ô để nhập tài khoản, mật khẩu mới và cũ

		JPanel pnUserName = new JPanel();
		pnUserName.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblUserName = new JLabel("Tài khoản: ");
		lblUserName.setFont(font);
		txtUserName = new JTextField(15);
		txtUserName.setFont(font);

		pnUserName.add(lblUserName);
		pnUserName.add(txtUserName);
		pnChangePass.add(pnUserName);

		JPanel pnOldPass = new JPanel();
		pnOldPass.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblOldPass = new JLabel("Mật khẩu cũ: ");
		lblOldPass.setFont(font);
		txtOldPass = new JTextField(15);
		txtOldPass.setFont(font);

		pnOldPass.add(lblOldPass);
		pnOldPass.add(txtOldPass);
		pnChangePass.add(pnOldPass);

		JPanel pnNewPass = new JPanel();
		pnNewPass.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNewPass = new JLabel("Mật khẩu mới: ");
		lblNewPass.setFont(font);
		txtNewPass = new JTextField(15);
		txtNewPass.setFont(font);

		pnNewPass.add(lblNewPass);
		pnNewPass.add(txtNewPass);
		pnChangePass.add(pnNewPass);

		// Button thay đổi mật khẩu
		JPanel pnButton = new JPanel();
		pnButton.setLayout(new BoxLayout(pnButton, BoxLayout.X_AXIS));

		JPanel pnbtChangePass2 = new JPanel();
		pnbtChangePass2.setLayout(new FlowLayout());
		btChangePass2 = new JButton("Thay đổi mật khẩu");
		btChangePass2.setFont(font);
		pnbtChangePass2.add(btChangePass2);
		pnButton.add(pnbtChangePass2);

		lblUserName.setPreferredSize(lblNewPass.preferredSize());
		lblOldPass.setPreferredSize(lblNewPass.preferredSize());

		pnChangePass.add(pnButton);

	}

	public void showWindow() {

		frame.setTitle("Thay đổi mật khẩu");
		frame.setSize(550, 200);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public void addbtChangePass2Listener(ActionListener btChangePass2Listener) {
		btChangePass2.addActionListener(btChangePass2Listener);
	}
}
