package com.market.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddType {
	JFrame frame = new JFrame();
	private JButton btConfirm, btAddTypee;
	private JTextField txtNewId, txtNewName, txtNewDes;

	Connection conn = null;
	PreparedStatement preStatement = null;
	Statement statement = null;
	ResultSet result = null;

	public AddType() {
		connectDatabase();
		addControl();
		addEvent();
	}

	private void addEvent() {
		btConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddNewType();
			}
		});
	}

	private void connectDatabase() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBanHang;integratedSecurity=true;";
			conn = DriverManager.getConnection(connectionUrl);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean checkExType(String id) {
		try {
			String sql1 = "select * from production.TypeProduct where idType=?";
			PreparedStatement pStatement = conn.prepareStatement(sql1);
			pStatement.setString(1, id);
			ResultSet rs = pStatement.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void AddNewType() {
		if (checkExType(txtNewId.getText())) {
			JOptionPane.showMessageDialog(null, "Mã [" + txtNewId.getText() + "] đã tồn tại. Hãy nhập lại!");
		} else {
			try {
				String sql = "insert into production.TypeProduct values (?,?,?)";
				preStatement = conn.prepareStatement(sql);
				preStatement.setString(1, txtNewId.getText());
				preStatement.setString(2, txtNewName.getText());
				preStatement.setString(3, txtNewDes.getText());

				int x = preStatement.executeUpdate();
				if (x > 0) {
					JOptionPane.showMessageDialog(null, "Thêm mới thành công!");
				} else {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void addControl() {
		// Hiển thị thông tin nhập
		JLabel TitleLb = new JLabel("Nhập thông tin chi tiết");
		TitleLb.setBounds(80, 5, 200, 30);
		TitleLb.setForeground(Color.red.brighter());
		TitleLb.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(TitleLb);

		// Nhập mã loại
		JLabel lblIdNew = new JLabel("Mã loại");
		lblIdNew.setBounds(25, 50, 100, 25);
		lblIdNew.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(lblIdNew);

		// TextField mã loại
		txtNewId = new JTextField();
		txtNewId.setText("Nhập mã mới");
//		txtInfoType.setEditable(false);
		txtNewId.setBounds(155, 50, 150, 25);
		txtNewId.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(txtNewId);

		// Tên mã mới
		JLabel lblNameNew = new JLabel("Tên loại");
		lblNameNew.setBounds(25, 100, 120, 25);
		lblNameNew.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(lblNameNew);

		// mã mới
		txtNewName = new JTextField();
		txtNewName.setText("Nhập tên mới");
//		PhoneTf.setEditable(false);
		txtNewName.setBounds(155, 100, 150, 25);
		txtNewName.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(txtNewName);

		// Mô tả
		JLabel lblDes = new JLabel("Mô tả");
		lblDes.setBounds(25, 155, 100, 25);
		lblDes.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(lblDes);

		// Mô tả
		txtNewDes = new JTextField();
//		txtNewDes.setText("Address Test");
//		AddressTf.setEditable(false);
		txtNewDes.setBounds(155, 150, 150, 25);
		txtNewDes.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(txtNewDes);

		// Nút xác nhận
		btConfirm = new JButton("Xác nhận");
		btConfirm.setBounds(130, 200, 130, 25);
		btConfirm.setFont(new Font("arial", Font.ROMAN_BASELINE, 17));

		frame.add(btConfirm);
	}

	public void showWindow() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null); // đặt đầu tránh lỗi, sau setsize
		frame.setLayout(null);
		frame.setResizable(false); // đặt size cố định
		frame.setVisible(true);
	}
}
