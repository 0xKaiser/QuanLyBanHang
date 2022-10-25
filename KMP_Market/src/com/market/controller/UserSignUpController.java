package com.market.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.market.entity.User;
import com.market.jdbc.Connect;
import com.market.services.Auto_Increase_Id;
import com.market.services.CheckInput;
import com.market.services.Md5;
import com.market.view.CusSUView;
import com.market.view.SignInView;
import com.market.view.UserSUView;

public class UserSignUpController {
	private UserSUView usuv;
	private Auto_Increase_Id autoId = new Auto_Increase_Id();
	String ids = autoId.getId("person.Users");
	private Md5 mahoa = new Md5();

	public UserSignUpController(UserSUView View) {
		this.usuv = View;

		View.addSignupListener(new SignupListener());
		View.addExitListener(new ExitListener());
		View.addReturnListener(new ReturnListener());
	}

	public void showSignupView() {
//		signupview.setTitle("Signup");
//		signupview.setSize(350, 250);
//		signupview.setLocationRelativeTo(null);
//		signupview.setResizable(false);
		usuv.setVisible(true);
	}

	class SignupListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			User user = usuv.getUser();
			CheckInput checkip = new CheckInput();

			if (checkip.checkExistUser(user)) {

				JOptionPane.showMessageDialog(null, "Tài khoản đã tồn tại ", "thông báo", JOptionPane.WARNING_MESSAGE);

			} else if (checkip.validateUser(user)) {

				JOptionPane.showMessageDialog(null, "Tài khoản không được bỏ trống", "Thông báo",
						JOptionPane.WARNING_MESSAGE);

			} else if (!checkip.checkUser(user)) {

				JOptionPane.showMessageDialog(null,
						"Tài khoản có ít nhất 5 kí tự bao gồm chữ thường,số và không có kí tự đặc biệt", "Thông báo",
						JOptionPane.ERROR_MESSAGE);

			} else if (checkip.validatePass(user)) {

				JOptionPane.showMessageDialog(null, "Mật khẩu không được để trống", "thông báo",
						JOptionPane.WARNING_MESSAGE);

			} else if (!checkip.checkPass(user)) {

				JOptionPane.showMessageDialog(null,
						"Nhập mật khẩu phải có ít nhất 8 kí tự bao gồm số,chữ thường,chữ hoa và 1 số kí tự đặc biệt",
						"Thông báo", JOptionPane.ERROR_MESSAGE); // (@ $ ! % * ? &)

			} else if (!checkip.checkConfirmPass(user)) {
				JOptionPane.showMessageDialog(null, "Mật khẩu xác nhận không trùng khớp ", "Thông báo",
						JOptionPane.ERROR_MESSAGE);
			} else {
				Connection con = Connect.getConnect();
				try {
					String insertacc1 = "INSERT INTO person.Users(idUser,username,password,permission)"
							+ "VALUES(?,?,?,?)";
					PreparedStatement statement = con.prepareStatement(insertacc1);
//					Statement statement = con.createStatement();
					statement.setString(1, autoId.getId("person.Users"));
					statement.setString(2, user.getUserName());
					statement.setString(3, mahoa.convertHashToString(user.getPassWord())); // user.getPassWord()
					statement.setString(4, "False");

					int rowInsert = statement.executeUpdate();
					JOptionPane.showMessageDialog(null, "Tạo tài khoản thành công ", "Thông báo",
							JOptionPane.PLAIN_MESSAGE);
					CusSUView customerView = new CusSUView();
					CusSignUpController customerController = new CusSignUpController(customerView);
					customerController.showCustomer();
					customerController.setID(ids);
					usuv.dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "thêm thất bại", "Notification", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}

	}

	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	class ReturnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				SignInView sv = new SignInView();
				SignInController sc;
				sc = new SignInController(sv);
				sc.showSignInView();
				usuv.setVisible(false);
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}

		}
	}

}
