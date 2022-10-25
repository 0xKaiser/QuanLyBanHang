package com.market.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.market.services.BuyServices;
import com.market.view.ChangeAddressView;
import com.market.view.PaymentView;

public class PaymentController {
	private PaymentView paymentview;

	public PaymentController(PaymentView View) {

		this.paymentview = View;
		View.addChangeListener(new changeListener());
		View.addBuyListener(new buyListener());

	}

	class changeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			ChangeAddressView change = new ChangeAddressView();
			ChangeAddressController control = new ChangeAddressController(change);
			control.showwWindow();

		}

	}

	class buyListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			BuyServices bs = new BuyServices();
			System.out.println(paymentview.getIdCus());
			if (bs.checkBillCreatSuccess(paymentview.getIdCus()))
				System.out.println("mua hang thanh cong");

		}

	}
}
