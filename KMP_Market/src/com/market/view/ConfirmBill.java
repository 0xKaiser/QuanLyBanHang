package com.market.view;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.market.controller.ChangeAddressController;
import com.market.jdbc.Connect;

public class ConfirmBill {
	JFrame frame = new JFrame();
	private JButton ConfirmBt;
	private DefaultTableModel BillTb;
	private JTable Btable;
	JTextField txtidBillNotCf, txtTotalPrice, txtDayBilled;
	String idCus;

	public ConfirmBill() {
		addControl();
		addEvent();
	}

	private void showDetailsBillNotCf(String ma) {
		try {
			Connection cnn = Connect.getConnect();
			String sql1 = "select * from virtual.v_showBillNotConfirm where idBill= '" + ma + "'";
			PreparedStatement preStatement = cnn.prepareStatement(sql1);
			ResultSet rs = preStatement.executeQuery();
			if (rs.next()) {
				txtidBillNotCf.setText(rs.getString("idBill"));
				txtTotalPrice.setText(rs.getString("totalPrice"));
				txtDayBilled.setText(rs.getString("dayBilled"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showBillNotConfirm() {
		try {
			Connection cnn = Connect.getConnect();
			String sql = "select * from virtual.v_showBillNotConfirm";
			PreparedStatement preStatement = cnn.prepareStatement(sql);
			ResultSet result = preStatement.executeQuery();
			BillTb.setRowCount(0);
			while (result.next()) {
				Vector<Object> vec = new Vector<Object>();
				vec.add(result.getString("idBill"));
				vec.add(result.getLong("totalPrice"));
				vec.add(result.getString("dayBilled"));
				BillTb.addRow(vec);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void showInforCus(String idCus) {
		try {
			Connection cnn = Connect.getConnect();
			String sql = "select * from virtual.v_showAllCus where idCustomer ='" + idCus + "'";
			PreparedStatement ps = cnn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			txtidBillNotCf.setText(rs.getString("nameCustomer"));
			txtTotalPrice.setText(rs.getString("phone"));
			txtDayBilled.setText(rs.getString("address"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void showProductNotConfirm() {
		try {
			Connection cnn = Connect.getConnect();
			String sql = "select * from virtual.v_showBillNotConfirm";
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

	private void Confirm() {
		try {
			Connection cnn = Connect.getConnect();
			String sql = "update sales.Bill set stt = '1' where idBill = '" + this.txtidBillNotCf.getText() + "'";
			PreparedStatement ps = cnn.prepareStatement(sql);
			if (ps.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "Xác nhận thành công", "Thông báo ", JOptionPane.CANCEL_OPTION);
				showBillNotConfirm();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Xác nhận thất bại", "Thông báo", 0);
		}

	}

	private void addEvent() {

		ConfirmBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Confirm();
			}

		});

		Btable.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = Btable.getSelectedRow();
				if (row == -1)
					return;
				String ma = (String) Btable.getValueAt(row, 0); // số 0 là lấy chỉ số đầu tiên của hàng
				showDetailsBillNotCf(ma);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}
		});
	}

	private void addControl() {
		Font fGeneral = new Font("arial", Font.BOLD, 20);
		Font fTextfield = new Font("arial", Font.ITALIC, 20);
		Font fButton = new Font("arial", Font.BOLD, 15);

		JLabel NameLb = new JLabel("Mã hóa đơn");
		NameLb.setBounds(25, 100, 150, 25);
		NameLb.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(NameLb);

		txtidBillNotCf = new JTextField();
		txtidBillNotCf.setEditable(false);
		txtidBillNotCf.setBounds(170, 100, 150, 25);
		txtidBillNotCf.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(txtidBillNotCf);

		JLabel phoneLb = new JLabel("Tổng tiền");
		phoneLb.setBounds(25, 140, 120, 25);
		phoneLb.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(phoneLb);

		txtTotalPrice = new JTextField();
		txtTotalPrice.setEditable(false);
		txtTotalPrice.setBounds(170, 140, 150, 25);
		txtTotalPrice.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(txtTotalPrice);

		JLabel addressLb = new JLabel("Ngày thanh toán");
		addressLb.setBounds(25, 180, 150, 25);
		addressLb.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(addressLb);

		txtDayBilled = new JTextField();
		txtDayBilled.setText(ChangeAddressController.diachi);
		txtDayBilled.setEditable(false);
		txtDayBilled.setBounds(170, 180, 150, 25);
		txtDayBilled.setFont(new Font("arial", Font.ROMAN_BASELINE, 20));
		frame.add(txtDayBilled);

		ConfirmBt = new JButton("Xác nhận đơn");
		ConfirmBt.setBounds(155, 270, 155, 25);
		ConfirmBt.setFont(new Font("arial", Font.ROMAN_BASELINE, 17));
		frame.add(ConfirmBt);

		JLabel IfBill = new JLabel("Thông tin hóa đơn");
		IfBill.setBounds(650, 10, 150, 25);
		IfBill.setFont(new Font("arial", Font.ROMAN_BASELINE, 17));
		frame.add(IfBill);

		// tạo
		BillTb = new DefaultTableModel();
		BillTb.addColumn("Mã hóa đơn");
		BillTb.addColumn("Tổng tiền");
		BillTb.addColumn("Ngày thanh toán");
		Btable = new JTable(BillTb);
		Btable.setRowHeight(25);
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

	public void addBuyListener(ActionListener buy) {
		ConfirmBt.addActionListener(buy);
	}

}
