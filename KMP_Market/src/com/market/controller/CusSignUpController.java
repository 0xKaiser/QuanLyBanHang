package com.market.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import com.market.entity.Customer;
import com.market.jdbc.Connect;
import com.market.services.CheckInput;
import com.market.view.CusSUView;
import com.market.view.SignInView;
import com.market.view.UserSUView;

public class CusSignUpController {
	private CusSUView customerview;
	private String ID;
	java.util.Date date;
	java.sql.Date sqlDate;
	private String Name, phone, day, month, year, address;
	private String dateInString;

	public CusSignUpController(CusSUView cusview) {
		this.customerview = cusview;
		cusview.addreturnListener(new returnListener());
		cusview.addCustomerListner(new CustomerListner());
	}

	public void showCustomer() {
		customerview.setVisible(true);
	}

	class returnListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			UserSUView signupView = new UserSUView();
			UserSignUpController signupcontroller = new UserSignUpController(signupView);
			signupcontroller.showSignupView();
			customerview.setVisible(false);
		}
	}

	class CustomerListner implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			boolean kt;
			Customer cus = customerview.getCustomer();

			UserSUView signupView = new UserSUView();
			UserSignUpController signupcontroller = new UserSignUpController(signupView);

			CheckInput check = new CheckInput();
			day = cus.getDay();
			month = cus.getMonth();
			year = cus.getYear();
			dateInString = day + "-" + month + "-" + year;
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			try {
				date = formatter.parse(dateInString);
				sqlDate = new java.sql.Date(date.getTime());
			} catch (ParseException e2) {

				e2.printStackTrace();
			}
			sqlDate = new java.sql.Date(date.getTime());

			if (check.checkName(cus)) {
				JOptionPane.showMessageDialog(null, "Tên không được bỏ trống", "Thông báo",
						JOptionPane.WARNING_MESSAGE);
			} else if (!check.validateName(cus)) {
				JOptionPane.showMessageDialog(null, "Tên không được có số và kí tự đặc biệt", "Thông báo",
						JOptionPane.ERROR_MESSAGE);
			} else if (check.checkPhone(cus)) {
				JOptionPane.showMessageDialog(null, "Số điện thoại không được để trống", "Thông báo",
						JOptionPane.WARNING_MESSAGE);
			} else if (check.checkExistPhone(cus)) {
				JOptionPane.showMessageDialog(null, "Số điện thoại đã được sử dụng", "Thông báo",
						JOptionPane.ERROR_MESSAGE);
			} else if (!check.validatephone(cus)) {
				kt = false;
				JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ ", "Thông báo",
						JOptionPane.ERROR_MESSAGE);

			} else if (kt = true) {
				Connection con = Connect.getConnect();
				try {
					String insertinfor = "INSERT INTO person.Customer(idCustomer,nameCustomer,phone,birthday,sex,address)"
							+ "VALUES(?,?,?,?,?,?)";

					PreparedStatement sta1 = con.prepareStatement(insertinfor);
					sta1.setString(1, ID);
					sta1.setString(2, check.chuanHoa(cus.getName()));// chuanHoa(cus.getName())
					sta1.setString(3, cus.getPhone());
					sta1.setDate(4, sqlDate);
					sta1.setInt(5, cus.getSex());
					sta1.setString(6, check.chuanHoa(cus.getAddress()));
					int rowInsert = sta1.executeUpdate();
					// update acc
					update();
					JOptionPane.showMessageDialog(null, "đăng kí thành công", "Notification",
							JOptionPane.PLAIN_MESSAGE);
					customerview.dispose();
					SignInView sv = new SignInView();
					SignInController sc = new SignInController(sv);
					sc.showSignInView();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}

		}

	}

	public void update() {
		Connection con = Connect.getConnect();
		try {
			String update = "update person.Users set permission  = ? where idUser = ? ";
			boolean permis = true;
			String id = ID;
			PreparedStatement pstmt = con.prepareStatement(update);
			pstmt.setBoolean(1, permis);
			pstmt.setString(2, id);
			int rowAffected = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

}
