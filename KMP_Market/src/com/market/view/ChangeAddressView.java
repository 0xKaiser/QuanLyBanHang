package com.market.view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.market.entity.Customer;

public class ChangeAddressView extends JFrame implements ActionListener {
	private JTextField txtten;
	private JTextField txtsdt;
	private JTextField txtdiachi;

	private JButton btnUpdate;
//	private String  ten ;
//	private String sdt; 
//	private String diachi ;

	public ChangeAddressView() {
		addControls();
//		addEvents();
	}

//	private void addEvents() {
//		btnUpdate.addActionListener(new updateEvent());
//
//	}

	public void addControls() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container con = getContentPane();
		JPanel pnMain = new JPanel();
		pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
		con.add(pnMain);

//		JPanel pnName = new JPanel();
//		pnName.setLayout(new FlowLayout());
//		JLabel lblName = new JLabel("Họ tên ");
//		txtten = new JTextField(20);
//		pnName.add(lblName);
//		pnName.add(txtten);
//		pnMain.add(pnName);
//
//		JPanel pnPhone = new JPanel();
//		pnPhone.setLayout(new FlowLayout());
//		JLabel lblPhone = new JLabel("Số điện thoại ");
//		txtsdt = new JTextField(20);
//		pnPhone.add(lblPhone);
//		pnPhone.add(txtsdt);
//		pnMain.add(pnPhone);

		JPanel pnAddress = new JPanel();
		pnAddress.setLayout(new FlowLayout());
		JLabel lblAddress = new JLabel("Địa chỉ ");
		txtdiachi = new JTextField(20);
		pnAddress.add(lblAddress);
		pnAddress.add(txtdiachi);
		pnMain.add(pnAddress);

		JPanel pnButton = new JPanel();
		pnButton.setLayout(new FlowLayout());
		btnUpdate = new JButton("Cập nhật ");
		pnButton.add(btnUpdate);
		pnMain.add(pnButton);

//		lblName.setPreferredSize(lblPhone.getPreferredSize());
//		lblAddress.setPreferredSize(lblPhone.getPreferredSize());

		this.setTitle("Thông tin ");
		this.setSize(350, 100);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
//		this.setVisible(true);

		btnUpdate.addActionListener(this);

	}

	public Customer getCustomer() {
		return new Customer(txtdiachi.getText().trim());
	}

	public void actionPerformed(ActionEvent e) {

	}

	public void updateListener(ActionListener updata) {
		btnUpdate.addActionListener(updata);
	}
}
