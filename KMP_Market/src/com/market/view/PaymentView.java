package com.market.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.market.controller.ChangeAddressController;
import com.market.jdbc.Connect;

public class PaymentView implements ActionListener {
	JFrame frame = new JFrame();
	private JButton BuyBt, ChangeBt, BackBt;
	private DefaultTableModel BillTb;
	private JTable Btable;
	JTextField nameTf, phoneTf, addressTf;
	String idCus;

	public PaymentView() {
		addControl();
	}

	public void showInforCus(String idCus) {
		try {
			Connection cnn = Connect.getConnect();
			String sql = "select * from virtual.v_showAllCus where idCustomer ='" + idCus + "'";
			PreparedStatement ps = cnn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			nameTf.setText(rs.getString("nameCustomer"));
			phoneTf.setText(rs.getString("phone"));
			addressTf.setText(rs.getString("address"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void showProductInCart(String idCus) {
		try {
			Connection cnn = Connect.getConnect();
			String sql = "select * from virtual.v_showCart where idCustomer='" + idCus + "'";
			Statement statement = cnn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			BillTb.setRowCount(0);

			while (result.next()) {
				Vector<Object> vec = new Vector<Object>();
				vec.add(result.getString("nameProduct"));
				vec.add(result.getString("quantity"));
				vec.add(result.getString("price"));
				BillTb.addRow(vec);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addControl() {
//			Font fGeneral = new Font("arial", Font.BOLD, 20);
//			Font fTextfield = new Font("arial", Font.ITALIC, 20);
//			Font fButton=new Font("arial", Font.BOLD, 15);

		JLabel TitleLb = new JLabel("Chọn nơi giao hàng");
		TitleLb.setBounds(150, 5, 200, 30);
		TitleLb.setForeground(Color.red.brighter());
		TitleLb.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(TitleLb);

		JLabel BillLb = new JLabel("Mã hóa đơn :");
		BillLb.setBounds(80, 35, 90, 30);
		BillLb.setFont(new Font("arial", Font.ROMAN_BASELINE, 15));
		frame.add(BillLb);

		JLabel DateLb = new JLabel("Ngày : ");
		DateLb.setBounds(240, 35, 60, 30);
		DateLb.setFont(new Font("arial", Font.ROMAN_BASELINE, 15));
		frame.add(DateLb);

		JLabel InforLb = new JLabel("Thông tin");
		InforLb.setBounds(0, 70, 70, 20);
		InforLb.setFont(new Font("arial", Font.ROMAN_BASELINE, 15));
		frame.add(InforLb);

		JLabel NameLb = new JLabel("Họ Và Tên");
		NameLb.setBounds(25, 100, 100, 25);
		NameLb.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(NameLb);

		nameTf = new JTextField();
		nameTf.setEditable(false);
		nameTf.setBounds(155, 100, 150, 25);
		nameTf.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(nameTf);

		JLabel phoneLb = new JLabel("Số điện thoại");
		phoneLb.setBounds(25, 140, 120, 25);
		phoneLb.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(phoneLb);

		phoneTf = new JTextField();
		phoneTf.setEditable(false);
		phoneTf.setBounds(155, 140, 150, 25);
		phoneTf.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(phoneTf);

		JLabel addressLb = new JLabel("Địa chỉ");
		addressLb.setBounds(25, 180, 100, 25);
		addressLb.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(addressLb);

		addressTf = new JTextField();
		addressTf.setText(ChangeAddressController.diachi);
		addressTf.setEditable(false);
		addressTf.setBounds(155, 180, 180, 25);
		addressTf.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(addressTf);

		ChangeBt = new JButton("Thay đổi");
		ChangeBt.setBounds(345, 180, 110, 25);
		ChangeBt.setFont(new Font("arial", Font.ROMAN_BASELINE, 17));
		frame.add(ChangeBt);

		BuyBt = new JButton("Thanh Toán");
		BuyBt.setBounds(85, 270, 130, 25);
		BuyBt.setFont(new Font("arial", Font.ROMAN_BASELINE, 17));
		frame.add(BuyBt);

		BackBt = new JButton("Quay lại");
		BackBt.setBounds(225, 270, 130, 25);
		BackBt.setFont(new Font("arial", Font.ROMAN_BASELINE, 17));
		frame.add(BackBt);

		JLabel IfBill = new JLabel("Thông tin hóa đơn");
		IfBill.setBounds(650, 10, 150, 25);
		IfBill.setFont(new Font("arial", Font.ROMAN_BASELINE, 17));
		frame.add(IfBill);

		// tạo
		BillTb = new DefaultTableModel();
		BillTb.addColumn("Tên sản phẩm");
		BillTb.addColumn("Số Lượng");
		BillTb.addColumn("Giá tiền");
		Btable = new JTable(BillTb);
		Btable.setFont(new Font("arial", Font.ROMAN_BASELINE, 17));
		JScrollPane BillSc = new JScrollPane(Btable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		BillSc.setPreferredSize(new Dimension(420, 400));
		BillSc.setBounds(460, 35, 500, 300);
		frame.add(BillSc);

		JLabel BgInFor1 = new JLabel();
		BgInFor1.setBounds(0, 0, 460, 65);
		ImageIcon imageIcon2 = new ImageIcon(
				new ImageIcon("Image\\background2.jpg").getImage().getScaledInstance(460, 65, Image.SCALE_DEFAULT));
		BgInFor1.setIcon(imageIcon2);
		frame.add(BgInFor1);

		JLabel BgInFor2 = new JLabel();
		BgInFor2.setBounds(460, 0, 460, 400);
		ImageIcon imageIcon3 = new ImageIcon(
				new ImageIcon("Image\\background3.jpg").getImage().getScaledInstance(460, 400, Image.SCALE_DEFAULT));
		BgInFor2.setIcon(imageIcon3);
		frame.add(BgInFor2);

		JLabel BgInFor = new JLabel();
		BgInFor.setBounds(0, 0, 465, 400);
		ImageIcon imageIcon1 = new ImageIcon(
				new ImageIcon("Image\\background1.jpg").getImage().getScaledInstance(460, 400, Image.SCALE_DEFAULT));
		BgInFor.setIcon(imageIcon1);
		frame.add(BgInFor);
		ChangeBt.addActionListener(this);
		BuyBt.addActionListener(this);

	}

	public void showWindow() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(980, 400);
		frame.setLocationRelativeTo(null); // đặt đầu tránh lỗi
		frame.setLayout(null);
		frame.setResizable(false); // đặt size cố định
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

	}

	public void addChangeListener(ActionListener change) {
		ChangeBt.addActionListener(change);
	}

	public void addBuyListener(ActionListener buy) {
		BuyBt.addActionListener(buy);
	}

	public String getIdCus() {
		return idCus;
	}

	public void setIdCus(String idCus) {
		this.idCus = idCus;
	}

}
