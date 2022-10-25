package com.market.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JTable;

import com.market.jdbc.Connect;
import com.market.view.ChangePass;
import com.market.view.HomePageView;
import com.market.view.PaymentView;

public class HomePageController {
	JTable tblProduct;
	Connection conn = Connect.getConnect();
	private HomePageView homePageView;
	private SignInController signInController;
	private ChangePass cp = new ChangePass();
	private String idx;

	public HomePageController(HomePageView view) throws SQLException {
		this.homePageView = view;
		view.displayAllProduct();
		view.displayTypeProduct();
//		view.displayProductInCart(idx); //ko an lenh
		view.addTblProductMouseListener();
		view.addTblWannaBuyMouseListener(idx);
		view.addbtExitListener(new btExitListener());
		view.addbtAddToCartListener(new btAddToCartListener());
		view.addcbTypeProductListener(new cbTypeProductListener());
		view.addbtSearchListener(new btSearchListener());
		view.addcbSortListener(new cbSortListener());
		view.addbtPayListener(new btPayListener());
		view.addbtSignInDetailsListener(new btSignInDetailsListener());
		view.addbtCusInforListener(new btCusInforListener());
		view.addbtPurItemsListener(new btPurItemsListener());
		view.addbtChangePassListener(new btChangePassListener());
		view.addbtChangeInforListener(new btChangeInforListener());
		cp.addbtChangePass2Listener(new btChangePass2Listener());
//		cip.addbtChangeInfor2Listener(new btChangeInfor2Listener());
		view.addbtDelFromCartListener(new btDelFromCartListener());
	}

	public void showHomePageView() {
		homePageView.showWindow();
	}

	// Thêm sự kiện button mua
	class btExitListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	// Thêm sự kiện button thêm vào giỏ hàng
	class btAddToCartListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			homePageView.processAddToCart(idx);
			homePageView.displayProductInCart(idx);
		}
	}

	// Thêm sự kiện cho combobox lọc theo loại sản phẩm
	class cbTypeProductListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (homePageView.getCbTypeProduct().getSelectedIndex() == 0) {
				homePageView.displayAllProduct();
			} else {
				homePageView.displayProductByType(homePageView.getCbTypeProduct().getSelectedIndex());
			}
		}
	}

	// Thêm sự kiện tìm kiếm
	class btSearchListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			homePageView.processSearch();
		}
	}

	// Thêm sự kiện cho combobox lọc theo giá
	class cbSortListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (homePageView.getCbSort().getSelectedIndex() == 0) {
				homePageView.diplayPriceAscending();
			} else if (homePageView.getCbSort().getSelectedIndex() == 1) {
				homePageView.displayPriceDescending();
			}
		}
	}

	// Thêm sự kiện cho button thanh toán
	class btPayListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			PaymentView pm = new PaymentView();
			PaymentController pmc = new PaymentController(pm);
			pm.showWindow();
			pm.showInforCus(idx);
			pm.showProductInCart(idx);
			pm.setIdCus(idx);
		}

	}

	// Thêm sự kiện cho button thông tin đăng nhập
	class btSignInDetailsListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			homePageView.diplaySignInDetails(idx);
		}

	}

	// Thêm sự kiện cho button thông tin khách hàng
	class btCusInforListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				homePageView.displayCusInfor(idx);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	// Thêm sự kiện cho button sản phẩm đã mua
	class btPurItemsListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				homePageView.displayPurItems(idx);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	// Thêm sự kiện cho button thay đổi mật khẩu
	class btChangePassListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			cp.showWindow();
		}

	}

	// Thêm sự kiện cho button thay đổi mật khẩu 2
	class btChangePass2Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			cp.processChangePass(idx);
		}

	}

	// Thêm sự kiện cho button thay đổi thông tin
	class btChangeInforListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			homePageView.processChangeInFor(idx);
		}

	}

	// Thêm sự kiện cho button thay đổi thông tin 2
	class btChangeInfor2Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

		}

	}

	// Thêm sự kiện cho button xóa khỏi giỏ hàng
	class btDelFromCartListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			homePageView.DelFromCart(idx);
			homePageView.displayProductInCart(idx);
		}

	}

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

}
