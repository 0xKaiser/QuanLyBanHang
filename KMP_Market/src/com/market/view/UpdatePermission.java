package com.market.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UpdatePermission {
	JFrame frame = new JFrame();
	private JButton btConfirm;
	private JTextField txtIdUser, txtPermission;
	DefaultTableModel dtmFullCus;

	Connection con = null;
	PreparedStatement prStatement = null;
	ResultSet res = null;

	public UpdatePermission() {
		addControl();
		addEvent();
		connectDatabasee();
//		showFullCus();
	}

	private void addEvent() {
		btConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Confirm();
			}
		});
	}

	private void connectDatabasee() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBanHang;integratedSecurity=true;";
			con = DriverManager.getConnection(connectionUrl);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean checkExIdUser(String id) {
		try {
			String sql = "select * from person.Users where idUser=?";
			prStatement = con.prepareStatement(sql);
			prStatement.setString(1, id);
			res = prStatement.executeQuery();
			return res.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void Confirm() {
		if (!checkExIdUser(txtIdUser.getText())) {
			JOptionPane.showMessageDialog(null, "Mã [" + txtIdUser.getText() + "] chưa tồn tại. Hãy nhập lại!");
		} else {
			try {
				String sql = "update person.Users set permission=? where idUser=? ";
				prStatement = con.prepareStatement(sql);
				if (txtPermission.getText().equals("Không")) {
					prStatement.setInt(1, 0);
				} else {
					prStatement.setInt(1, 1);
				}
				prStatement.setString(2, txtIdUser.getText());
				int x = prStatement.executeUpdate();
				if (x > 0) {
					JOptionPane.showMessageDialog(null, "Sửa thành công!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//	private void showFullCus() {
//		try {
//			String sql = "select * from virtual.showFullCus";
//			prStatement = con.prepareStatement(sql);
//			res = prStatement.executeQuery();
//			dtmFullCus.setRowCount(1);
//			while (res.next()) {
//				Vector<Object> vec = new Vector<Object>();
//				vec.add(res.getString("idCustomer"));
//				vec.add(res.getString("nameCustomer"));
//				vec.add(res.getString("phone"));
//				vec.add(res.getString("birthday"));
//				if (res.getInt("sex") == 0) {
//					vec.add("Nam");
//				} else {
//					vec.add("Nữ");
//				}
//				vec.add(res.getString("address"));
//				vec.add(res.getString("dayRegister"));
//				if (res.getInt("permission") == 0) {
//					vec.add("Không");
//				} else {
//					vec.add("Có");
//				}
//				dtmFullCus.addRow(vec);
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}

	private void addControl() {
		// Hiển thị thông tin nhập
		JLabel TitleLb = new JLabel("Sửa phân quyền khách hàng");
		TitleLb.setBounds(180, 5, 300, 30);
		TitleLb.setForeground(Color.red.brighter());
		TitleLb.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(TitleLb);

		// Nhập mã khách
		JLabel lblIdUser = new JLabel("Mã người dùng");
		lblIdUser.setBounds(25, 50, 200, 25);
		lblIdUser.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(lblIdUser);

		// mã khách
		txtIdUser = new JTextField();
		txtIdUser.setBounds(200, 50, 200, 25);
		txtIdUser.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(txtIdUser);

		// Phân quyền
		JLabel lblPermission = new JLabel("Quyền sử dụng");
		lblPermission.setBounds(25, 100, 200, 25);
		lblPermission.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(lblPermission);

		// phân quyền
		txtPermission = new JTextField();
		txtPermission.setBounds(200, 100, 200, 25);
		txtPermission.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(txtPermission);

		// Nút xác nhận
		btConfirm = new JButton("Xác nhận");
		btConfirm.setBounds(240, 150, 130, 25);
		btConfirm.setFont(new Font("arial", Font.ROMAN_BASELINE, 17));

		frame.add(btConfirm);
	}

	public void showWindow() {
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // đặt đầu tránh lỗi
		frame.setSize(600, 300);
		frame.setLayout(null);
		frame.setResizable(false); // đặt size cố định
		frame.setVisible(true);
	}
}
