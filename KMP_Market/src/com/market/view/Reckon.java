package com.market.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Reckon extends JFrame {
	DefaultTableModel dtmReckon;
	JTable tblReckon;

	Connection conn = null;
	PreparedStatement preStatement = null;
	ResultSet result = null;

	public Reckon() {
		addControls();
		connectDatabase();
		ReckonPro();
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

	private void ReckonPro() {
		try {
			String sql = "select * from virtual.v_showReckon order by tong_mua desc";
			preStatement = conn.prepareStatement(sql);
			result = preStatement.executeQuery();
			dtmReckon.setRowCount(0);
			while (result.next()) {
				Vector<Object> vec = new Vector<Object>();
				vec.add(result.getString("idProduct"));
				vec.add(result.getString("nameProduct"));
				vec.add(result.getInt("tong_mua"));
				dtmReckon.addRow(vec);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void addControls() {
		Container con = getContentPane();
		con.setLayout(new BorderLayout());

		JPanel pnThongTin = new JPanel();
		pnThongTin.setLayout(new BoxLayout(pnThongTin, BoxLayout.Y_AXIS));
		con.add(pnThongTin, BorderLayout.NORTH);

		dtmReckon = new DefaultTableModel();
		dtmReckon.addColumn("Mã sản phẩm");
		dtmReckon.addColumn("Tên sản phẩm");
		dtmReckon.addColumn("Số lượng đã bán");
		tblReckon = new JTable(dtmReckon);

		JScrollPane scTable = new JScrollPane(tblReckon, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		con.add(scTable, BorderLayout.CENTER);

		pnThongTin.add(scTable, BorderLayout.CENTER);
		con.add(pnThongTin, BorderLayout.CENTER);

		JPanel pnThongTinChiTiet = new JPanel();
		pnThongTinChiTiet.setLayout(new BoxLayout(pnThongTinChiTiet, BoxLayout.Y_AXIS));
		pnThongTin.add(pnThongTinChiTiet, BorderLayout.SOUTH);
	}

	public void showWindow() {
		this.setTitle("THỐNG KÊ BÁN HÀNG");
		this.setSize(800, 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
//		this.setModal(true);
		this.setVisible(true);
	}

}
