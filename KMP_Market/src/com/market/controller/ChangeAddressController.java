package com.market.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.market.entity.Customer;
import com.market.services.CheckInput;
import com.market.view.ChangeAddressView;
import com.market.view.PaymentView;

public class ChangeAddressController extends JFrame {
	private ChangeAddressView changeview;
	public static String ten, sdt, diachi;
	private CheckInput check;

	public ChangeAddressController(ChangeAddressView view) {
		this.changeview = view;
		view.updateListener(new update());
	}

	public void showwWindow() {
		changeview.setVisible(true);
	}

	class update implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Customer cus = changeview.getCustomer();

//			ten = cus.getName();
//			sdt = cus.getPhone();
			diachi = cus.getAddress();
			PaymentView pv = new PaymentView();
			PaymentController pc = new PaymentController(pv);
			pv.showWindow();
//			cus.setName(cus.getName());
//			cus.setPhone(cus.getPhone());
//			cus.setAddress(cus.getAddress());

		}

	}
}
