package com.market.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.market.services.Auto_Increase_Id;

public class ManagermentView extends JFrame {
	JTextField txtIdPro, txtNamePro, txtTypePro, txtCostPro, txtPricePro, txtQuantityPro, txtUnitPro, txtDescription;
	JTextField txtSearchPro, txtSearchCus;
	JTextField txtIdCus, txtNameCus, txtPhone, txtBirth, txtSex, txtAddress, txtDayReg, txtPermission;
	JTextField txtIdAd, txtNameAd, txtUserName, txtPass, txtPhoneAd, txtBirthAd, txtSexAd, txtNative;

	JButton btRefresh, btSearchPro, btSearchCus, btAddPro, btAddAd, btUpdatePro, btUpdatePerCus, btUpdateAd,
			btDeletePro, btDeleteCus, btDeleteAd, btReckon, btTopOrder, btLogo, btConfirmBill;
	JButton btAddType, btImport, btRefreshAd;

	DefaultTableModel dtmProduct, dtmCustomer, dtmAdministrator;
	DefaultTableModel dtmTest;
	JTable tblProduct, tblCustomer, tblAdministrator;

	JComboBox cbFilterTP = new JComboBox();
	JComboBox cbChooseSort = new JComboBox();

	Connection conn = null;
	PreparedStatement preStatement = null;
	Statement statement = null;
	ResultSet result = null;

	Auto_Increase_Id aii = new Auto_Increase_Id();

	Font fGeneral = new Font("Arial", Font.BOLD, 20); // đây là font chữ chung

	public ManagermentView() {
		addControls();
		addEvents();
		connectDatabase();
		FillFilter();
		FillSort();
//		sortBy();
		showAllPro();
		showAllCus();
		showAllAd();

	}

	private void FillSort() {
		cbChooseSort.addItem("Mã");
		cbChooseSort.addItem("Tên");
		cbChooseSort.addItem("Giá bán");
		cbChooseSort.addItem("Giá nhập");
	}

	private void sortBy(int type) {
		if (cbChooseSort.getSelectedItem().equals("Tên")) {
			try {
				String sql = "select * from production.Product where nameProduct like '%"
						+ getNameProduct(txtSearchPro.getText()) + "%' and type ='" + determindType(type)
						+ "' order by nameProduct";
				PreparedStatement pre = conn.prepareStatement(sql);
				ResultSet result = pre.executeQuery();
				dtmProduct.setRowCount(0);
				while (result.next()) {
					Vector<Object> vec = new Vector<Object>();
					vec.add(result.getString("idProduct"));
					vec.add(result.getString("nameProduct"));
					vec.add(getNameType(result.getString("type")));
					vec.add(result.getString("unit"));
					vec.add(result.getInt("cost"));
					vec.add(result.getInt("price"));
					vec.add(result.getInt("quantityOnHand"));
					vec.add(result.getString("description"));
					dtmProduct.addRow(vec);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (cbChooseSort.getSelectedItem().equals("Giá bán")) {
			try {
				String sql = "select * from production.Product where nameProduct like '%"
						+ getNameProduct(txtSearchPro.getText()) + "%' order by price";
				PreparedStatement pre = conn.prepareStatement(sql);
				ResultSet result = pre.executeQuery();
				dtmProduct.setRowCount(0);
				while (result.next()) {
					Vector<Object> vec = new Vector<Object>();
					vec.add(result.getString("idProduct"));
					vec.add(result.getString("nameProduct"));
					vec.add(getNameType(result.getString("type")));// Hiển thị loại hàng viết tắt bằng tên đầy đủ
					vec.add(result.getString("unit"));
					vec.add(result.getInt("cost"));
					vec.add(result.getInt("price"));
					vec.add(result.getInt("quantityOnHand"));
					vec.add(result.getString("description"));
					dtmProduct.addRow(vec);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (cbChooseSort.getSelectedItem().equals("Giá nhập")) {
			try {
				String sql = "select * from production.Product where nameProduct like '%"
						+ getNameProduct(txtSearchPro.getText()) + "%' order by cost";
				PreparedStatement pre = conn.prepareStatement(sql);
				ResultSet result = pre.executeQuery();
				dtmProduct.setRowCount(0);
				while (result.next()) {
					Vector<Object> vec = new Vector<Object>();
					vec.add(result.getString("idProduct"));
					vec.add(result.getString("nameProduct"));
					vec.add(getNameType(result.getString("type"))); // Hiển thị loại hàng viết tắt bằng tên đầy đủ
					vec.add(result.getString("unit"));
					vec.add(result.getInt("cost"));
					vec.add(result.getInt("price"));
					vec.add(result.getInt("quantityOnHand"));
					vec.add(result.getString("description"));
					dtmProduct.addRow(vec);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				String sql = "select * from production.Product where nameProduct like '%"
						+ getNameProduct(txtSearchPro.getText()) + "%'order by idProduct";
				PreparedStatement pre = conn.prepareStatement(sql);
				ResultSet result = pre.executeQuery();
				dtmProduct.setRowCount(0);
				while (result.next()) {
					Vector<Object> vec = new Vector<Object>();
					vec.add(result.getString("idProduct"));
					vec.add(result.getString("nameProduct"));
					vec.add(getNameType(result.getString("type")));
					vec.add(result.getString("unit"));
					vec.add(result.getInt("cost"));
					vec.add(result.getInt("price"));
					vec.add(result.getInt("quantityOnHand"));
					vec.add(result.getString("description"));
					dtmProduct.addRow(vec);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String getNameProduct(String text) {
		if (text == null || text.equals("Nhập tên sản phẩm")) {
			return "";
		} else {
			return text;
		}
	}

	public void FillFilter() {
		try {
			String sql = "select * from production.TypeProduct";
			preStatement = conn.prepareStatement(sql);
			ResultSet rs = preStatement.executeQuery();
			cbFilterTP.addItem("TẤT CẢ"); // Tự thêm (không có trong CSDL)
			while (rs.next()) {
				this.cbFilterTP.addItem(rs.getString("nameType"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Xác định từng kiểu lựa chọn ( từ chỉ số chuyển sang chuỗi)
	private String determindType(int type) {
		try {
			String sql = "select * from production.TypeProduct";
			PreparedStatement preStatement = conn.prepareStatement(sql);
			ResultSet result = preStatement.executeQuery();

			for (int i = 0; i <= type; i++) {
				result.next();
			}
			return result.getString("idType"); // trả về chuỗi
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Hiển thị sản phẩm sau khi lọc
	private void showTypePro(int type) {
		try {
			String sql = "select * from production.Product where type='" + determindType(type - 1)
					+ "' and nameProduct like '%" + txtSearchPro.getText() + "%'";
			// TYPE - 1: là số dòng trong CSDL ( Dòng TẤT CẢ không tính trong CSDL)
			preStatement = conn.prepareStatement(sql);
			result = preStatement.executeQuery();
			dtmProduct.setRowCount(0);
			while (result.next()) {
				Vector<Object> vec = new Vector<Object>();
				vec.add(result.getString("idProduct"));
				vec.add(result.getString("nameProduct"));
				vec.add(getNameType(result.getString("type")));
				vec.add(result.getString("unit"));
				vec.add(result.getInt("cost"));
				vec.add(result.getInt("price"));
				vec.add(result.getInt("quantityOnHand"));
				vec.add(result.getString("description"));
				dtmProduct.addRow(vec);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Sản phẩm
	public boolean checkExIdPro(String ma) {
		try {
			String sql = "select * from production.Product where idProduct=?";
			preStatement = conn.prepareStatement(sql);
			preStatement.setString(1, ma);
			ResultSet rs = preStatement.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Khách hàng
	public boolean checkExIdCus(String ma) {
		try {
			String sql = "select * from person.Customer where idCustomer=?";
			preStatement = conn.prepareStatement(sql);
			preStatement.setString(1, ma);
			ResultSet rs = preStatement.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Quản trị viên
	public boolean checkExIdAd(String ma) {
		try {
			String sql = "select * from person.Administrator where idAdmin=?";
			preStatement = conn.prepareStatement(sql);
			preStatement.setString(1, ma);
			ResultSet rs = preStatement.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private String getIdType(String string) {
		try {
			String sqlType = "select * from production.TypeProduct where nameType = N'" + string + "'";
			PreparedStatement pStatement = conn.prepareStatement(sqlType);
			ResultSet rs = pStatement.executeQuery();
			rs.next();
			return rs.getString("idType");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// SẢN PHẨM
	private void showDetailsPro(String ma) {
		try {
			String sql1 = "select * from production.Product where idProduct=?";
			preStatement = conn.prepareStatement(sql1);
			preStatement.setString(1, ma);
			ResultSet rs = preStatement.executeQuery();
			if (rs.next()) {
				txtIdPro.setText(rs.getString("idProduct"));
				txtNamePro.setText(rs.getString("nameProduct"));
				txtTypePro.setText(getNameType(rs.getString("type")));
				txtUnitPro.setText(rs.getString("unit"));
				txtCostPro.setText(rs.getInt("cost") + "");
				txtPricePro.setText(rs.getInt("price") + "");
				txtQuantityPro.setText(rs.getInt("quantityOnHand") + "");
				txtDescription.setText(rs.getString("description"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showAllPro() {
		try {
			String sql = "select * from production.Product";
			preStatement = conn.prepareStatement(sql);
			result = preStatement.executeQuery();
			dtmProduct.setRowCount(0);
			while (result.next()) {
				Vector<Object> vec = new Vector<Object>();
				vec.add(result.getString("idProduct"));
				vec.add(result.getString("nameProduct"));
				vec.add(getNameType(result.getString("type")));
				vec.add(result.getString("unit"));
				vec.add(result.getInt("cost"));
				vec.add(result.getInt("price"));
				vec.add(result.getInt("quantityOnHand"));
				vec.add(result.getString("description"));
				dtmProduct.addRow(vec);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String getNameType(String string) {
		try {
			String sqlType = "select * from production.TypeProduct where idType = '" + string + "'";
			PreparedStatement pStatement = conn.prepareStatement(sqlType);
			ResultSet rs = pStatement.executeQuery();
			rs.next();
			return rs.getString("nameType");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// KHÁCH HÀNG
	private void showDetailsCus(String ma) {
		try {
			String sql1 = "select * from virtual.v_showFullCus where idCustomer = ?";
			preStatement = conn.prepareStatement(sql1);
			preStatement.setString(1, ma);
			ResultSet rs = preStatement.executeQuery();
			if (rs.next()) {
				txtIdCus.setText(rs.getString("idCustomer"));
				txtNameCus.setText(rs.getString("nameCustomer"));
				txtPhone.setText(rs.getString("phone"));
				txtBirth.setText(rs.getString("birthday"));
				if (rs.getInt("sex") == 0) {
					txtSex.setText("Nam");
				} else {
					txtSex.setText("Nữ");
				}
				txtAddress.setText(rs.getString("address"));
				txtDayReg.setText(rs.getString("dayRegister"));
				if (rs.getInt("permission") == 0) {
					txtPermission.setText("Không");
				} else {
					txtPermission.setText("Có");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showAllCus() {
		try {
			String sql = "select * from virtual.v_showFullCus";
			preStatement = conn.prepareStatement(sql);
			result = preStatement.executeQuery();
			dtmCustomer.setRowCount(0);
			while (result.next()) {
				Vector<Object> vec = new Vector<Object>();
				vec.add(result.getString("idCustomer"));
				vec.add(result.getString("nameCustomer"));
				vec.add(result.getString("phone"));
				vec.add(result.getString("birthday"));
				if (result.getInt("sex") == 0) {
					vec.add("Nam");
				} else {
					vec.add("Nữ");
				}
				vec.add(result.getString("address"));
				vec.add(result.getString("dayRegister"));
				if (result.getInt("permission") == 0) {
					vec.add("Không");
				} else {
					vec.add("Có");
				}
				dtmCustomer.addRow(vec);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Quản trị viên
	private void showDetailsAd(String ma) {
		try {
			String sql1 = "select * from person.Administrator where idAdmin = ?";
			preStatement = conn.prepareStatement(sql1);
			preStatement.setString(1, ma);
			ResultSet rs = preStatement.executeQuery();
			if (rs.next()) {
				txtIdAd.setText(rs.getString("idAdmin"));
				txtNameAd.setText(rs.getString("nameAdmin"));
				txtUserName.setText(rs.getString("username"));
				txtPass.setText(rs.getString("password"));
				txtPhoneAd.setText(rs.getString("phone"));
				txtBirthAd.setText(rs.getString("birthday"));
				if (rs.getInt("sex") == 0) {
					txtSexAd.setText("Nam");
				} else {
					txtSexAd.setText("Nữ");
				}
				txtNative.setText(rs.getString("address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showAllAd() {
		try {
			String sql = "select * from person.Administrator";
			preStatement = conn.prepareStatement(sql);
			result = preStatement.executeQuery();
			dtmAdministrator.setRowCount(0);
			while (result.next()) {
				Vector<Object> vec = new Vector<Object>();
				vec.add(result.getString("idAdmin"));
				vec.add(result.getString("nameAdmin"));
				vec.add(result.getString("username"));
				vec.add(result.getString("password"));
				vec.add(result.getString("phone"));
				vec.add(result.getString("birthday"));
				if (result.getInt("sex") == 0) {
					vec.add("Nam");
				} else {
					vec.add("Nữ");
				}
				vec.add(result.getString("address"));
				dtmAdministrator.addRow(vec);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// KẾT NỐI CSDL
	private void connectDatabase() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyBanHang;integratedSecurity=true;";
			conn = DriverManager.getConnection(connectionUrl);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void addEvents() {

		cbFilterTP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbFilterTP.getSelectedIndex() == 0) {
					showAllPro();
				} else {
					showTypePro(cbFilterTP.getSelectedIndex());
				}
			}
		});

		cbChooseSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sortBy(cbFilterTP.getSelectedIndex());
			}
		});
		btImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImportPro importPro = new ImportPro();
				importPro.showWindow();
			}
		});
		btRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAllCus();
			}
		});
		btRefreshAd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAllAd();
			}
		});
		btAddType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddType adt = new AddType();
				adt.showWindow();
			}
		});

		btAddPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddPro();
			}
		});

		btUpdatePro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdatePro();
			}
		});

		btDeletePro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeletePro();
			}
		});
		btReckon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Reckon rec = new Reckon();
				rec.showWindow();
			}
		});
		btTopOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TopOrder top = new TopOrder();
				top.showWindow();
			}
		});

		btSearchPro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchPro();
			}
		});

		btSearchCus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchCus();
			}
		});
		btConfirmBill.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConfirmBill cfb = new ConfirmBill();
				cfb.showWindow();
				cfb.showBillNotConfirm();
			}
		});

		btDeleteCus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DeleteCus();
			}

		});

		btUpdatePerCus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdatePermission upPer = new UpdatePermission();
				upPer.showWindow();
			}
		});
		// Quản trị viên
		btAddAd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddAd();
			}
		});

		btUpdateAd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateAd();
			}
		});

		btDeleteAd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteAd();
			}
		});
		// Sản phẩm
		tblProduct.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tblProduct.getSelectedRow();
				if (row == -1)
					return;
				String ma = (String) tblProduct.getValueAt(row, 0); // số 0 là lấy chỉ số đầu tiên của hàng
				showDetailsPro(ma);
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
		// Khách hàng
		tblCustomer.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tblCustomer.getSelectedRow();
				if (row == -1)
					return;
				String ma = (String) tblCustomer.getValueAt(row, 0); // số 0 là lấy chỉ số đầu tiên của hàng
				showDetailsCus(ma);
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

		// Quản trị viên
		tblAdministrator.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tblAdministrator.getSelectedRow();
				if (row == -1)
					return;
				String ma = (String) tblAdministrator.getValueAt(row, 0); // số 0 là lấy chỉ số đầu tiên của hàng
				showDetailsAd(ma);
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

	// Sản phẩm
	private void AddPro() {
		if (txtIdPro.getText().equals(""))
			JOptionPane.showMessageDialog(null, "Vui lòng nhập mã sản phẩm", "Thông báo", JOptionPane.ERROR_MESSAGE);
		else if (checkExIdPro(txtIdPro.getText())) {
			JOptionPane.showMessageDialog(null, "Mã [" + txtIdPro.getText() + "] đã tồn tại. Hãy nhập lại!");
		} else {
			try {
				String sql = "insert into production.Product values (?,?,?,?,?,?,?,?)";
				preStatement = conn.prepareStatement(sql);
				if (txtIdPro.getText().equals("")) {
					preStatement.setString(1, aii.getIdProduct(getIdType(txtTypePro.getText())));
				} else {
					preStatement.setString(1, txtIdPro.getText());
				}
				preStatement.setString(2, txtNamePro.getText());
				preStatement.setString(3, getIdType(txtTypePro.getText()));
				preStatement.setString(4, txtUnitPro.getText());
				preStatement.setInt(5, Integer.parseInt(txtCostPro.getText()));
				preStatement.setInt(6, Integer.parseInt(txtPricePro.getText()));
				preStatement.setInt(7, Integer.parseInt(txtQuantityPro.getText()));
				preStatement.setString(8, txtDescription.getText());

				int x = preStatement.executeUpdate();
				if (x > 0) {
					showAllPro();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void UpdatePro() {
		if (!checkExIdPro(txtIdPro.getText())) {
			JOptionPane.showMessageDialog(null, "Mã [" + txtIdPro.getText() + "] chưa tồn tại. Kiểm tra lại!");
		} else {
			int NotifyUp = JOptionPane.showConfirmDialog(null,
					"Bạn có muốn sửa mã [" + txtIdPro.getText() + "] này không ?", "Xác nhận sửa",
					JOptionPane.YES_NO_OPTION);
			if (NotifyUp == JOptionPane.YES_OPTION)
				try {
					String sql = "update production.Product set nameProduct=?, type=?, unit=?, cost=?, price=?, quantityOnHand=?, description=? where idProduct=?";
					preStatement = conn.prepareStatement(sql);
					preStatement.setString(1, txtNamePro.getText());
					preStatement.setString(2, getIdType(txtTypePro.getText()));
					preStatement.setString(3, txtUnitPro.getText());
					preStatement.setInt(4, Integer.parseInt(txtCostPro.getText()));
					preStatement.setInt(5, Integer.parseInt(txtPricePro.getText()));
					preStatement.setInt(6, Integer.parseInt(txtQuantityPro.getText()));
					preStatement.setString(7, txtDescription.getText());
					preStatement.setString(8, txtIdPro.getText());
					int x = preStatement.executeUpdate();
					if (x > 0) {
						showAllPro();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	private void DeleteCus() {
		if (!checkExIdCus(txtIdCus.getText())) {
			JOptionPane.showMessageDialog(null, "Mã không tồn tại, vui lòng chọn lại đối tượng", "Thông báo xóa",
					JOptionPane.ERROR_MESSAGE);
		} else {
			int ret = JOptionPane.showConfirmDialog(null,
					"Bạn có chắc chắn muốn xóa mã [" + txtIdCus.getText() + "] này không?", "Xác nhận xóa?",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (ret == JOptionPane.YES_OPTION) {
				try {
					String sql = "delete from person.Users where idUser=?";
					preStatement = conn.prepareStatement(sql);
					preStatement.setString(1, txtIdCus.getText());
					int x = preStatement.executeUpdate();
					if (x > 0) {
						showAllCus();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private void DeletePro() {
		if (!checkExIdPro(txtIdPro.getText())) {
			JOptionPane.showMessageDialog(null, "Mã không tồn tại, vui lòng chọn sản phẩm", "Thông báo xóa",
					JOptionPane.ERROR_MESSAGE);
		} else {
			int ret = JOptionPane.showConfirmDialog(null,
					"Bạn có chắc chắn muốn xóa mã [" + txtIdPro.getText() + "] này không?", "Xác nhận xóa?",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (ret == JOptionPane.YES_OPTION) {
				try {
					String sql = "delete from production.Product where idProduct=?";
					preStatement = conn.prepareStatement(sql);
					preStatement.setString(1, txtIdPro.getText());
					int x = preStatement.executeUpdate();
					if (x > 0) {
						showAllPro();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

	}

	// Quản trị viên
	private void AddAd() {
		if (checkExIdAd(txtIdAd.getText())) {
			JOptionPane.showMessageDialog(null, "Mã [" + txtIdPro.getText() + "] đã tồn tại. Hãy nhập lại!");
		} else {
			try {
				String sql = "insert into person.Administrator values (?,?,?,?,?,?,?,?)";
				preStatement = conn.prepareStatement(sql);
				preStatement.setString(1, txtIdAd.getText());
				preStatement.setString(2, txtNameAd.getText());
				preStatement.setString(3, txtUserName.getText());
				preStatement.setString(4, txtPass.getText());
				preStatement.setString(5, txtPhoneAd.getText());
				preStatement.setString(6, txtBirthAd.getText());
				if (txtSexAd.getText().equals("Nam")) {
					preStatement.setInt(7, 0);
				} else {
					preStatement.setInt(7, 1);
				}
				preStatement.setString(8, txtNative.getText());
				int x = preStatement.executeUpdate();
				if (x > 0) {
					showAllAd();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void UpdateAd() {
		if (!checkExIdAd(txtIdAd.getText())) {
			JOptionPane.showMessageDialog(null, "Mã [" + txtIdAd.getText() + "] chưa tồn tại. Kiểm tra lại!");
		} else {
			int NotifyUp = JOptionPane.showConfirmDialog(null,
					"Bạn có muốn sửa mã [" + txtIdAd.getText() + "] này không ?", "Xác nhận sửa",
					JOptionPane.YES_NO_OPTION);
			if (NotifyUp == JOptionPane.YES_OPTION)
				try {
					String sql = "update person.Administrator set nameAdmin=?, username=?, password=?, phone=?, birthday=?, sex=?, address=? where idAdmin=?";
					preStatement = conn.prepareStatement(sql);
					preStatement.setString(1, txtNameAd.getText());
					preStatement.setString(2, txtUserName.getText());
					preStatement.setString(3, txtPass.getText());
					preStatement.setString(4, txtPhoneAd.getText());
					preStatement.setString(5, txtBirthAd.getText());
					if (txtSexAd.getText().equals("Nam")) {
						preStatement.setInt(6, 0);
					} else {
						preStatement.setInt(6, 1);
					}
					preStatement.setString(7, txtNative.getText());
					preStatement.setString(8, txtIdAd.getText());
					int x = preStatement.executeUpdate();
					if (x > 0) {
						showAllAd();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	private void DeleteAd() {
		// DeleteDTM(dtmProduct);
		if (!checkExIdAd(txtIdAd.getText())) {
			JOptionPane.showMessageDialog(null, "Mã không tồn tại. Hãy nhập lại!", "Thông báo xóa",
					JOptionPane.OK_OPTION);
		} else {
			int ret = JOptionPane.showConfirmDialog(null,
					"Bạn có chắc chắn muốn xóa mã [" + txtIdAd.getText() + "] này không?", "Xác nhận xóa?",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (ret == JOptionPane.YES_OPTION) {
				try {
					String sql = "delete from person.Administrator where idAdmin=?";
					preStatement = conn.prepareStatement(sql);
					preStatement.setString(1, txtIdAd.getText());
					int x = preStatement.executeUpdate();
					if (x > 0) {
						showAllPro();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

	}

	// Tìm kiếm sản phẩm theo tên
	protected void SearchPro() {
		try {
			CallableStatement callStatement = conn.prepareCall("{call virtual.proc_searchByName(?)}");
			callStatement.setString(1, txtSearchPro.getText());
			result = callStatement.executeQuery();
			dtmProduct.setRowCount(0);
			while (result.next()) {
				Vector<Object> vec = new Vector<Object>();
				vec.add(result.getString("idProduct"));
				vec.add(result.getString("nameProduct"));
				vec.add(getNameType(result.getString("type")));
				vec.add(result.getString("unit"));
				vec.add(result.getInt("cost"));
				vec.add(result.getInt("price"));
				vec.add(result.getInt("quantityOnHand"));
				vec.add(result.getString("description"));
				dtmProduct.addRow(vec);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Tìm kiếm khách hàng theo tên
	protected void SearchCus() {
		try {
//			CallableStatement callStatement = conn.prepareCall("{call TimKiemKhachHangTheoTenn(?)}");
//			callStatement.setString(1, txtSearchCus.getText());
//			result = callStatement.executeQuery();
			String sql = "select * from virtual.v_showFullCus where nameCustomer like '%" + txtSearchCus.getText()
					+ "%'";
			preStatement = conn.prepareStatement(sql);
			result = preStatement.executeQuery();
			dtmCustomer.setRowCount(0);
			while (result.next()) {
				Vector<Object> vec = new Vector<Object>();
				vec.add(result.getString("idCustomer"));
				vec.add(result.getString("nameCustomer"));
				vec.add(result.getString("phone"));
				vec.add(result.getString("birthday"));
				if (result.getInt("sex") == 0) {
					vec.add("Nam");
				} else {
					vec.add("Nữ");
				}
				vec.add(result.getString("address"));
				vec.add(result.getString("dayRegister"));
				if (result.getInt("permission") == 0) {
					vec.add("Không");
				} else {
					vec.add("Có");
				}
				dtmCustomer.addRow(vec);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void addControls() {

		JPanel pnProduct = new JPanel();
		JPanel pnCustomer = new JPanel();
		JPanel pnAdministrator = new JPanel();

		JTabbedPane tbManager = new JTabbedPane();
		tbManager.setBounds(0, 0, 1190, 900);
		tbManager.add("SẢN PHẨM", pnProduct);
		tbManager.add("KHÁCH HÀNG", pnCustomer);
		tbManager.add("QUẢN TRỊ VIÊN", pnAdministrator);
		tbManager.setFont(fGeneral);
		this.add(tbManager);
		pnProduct.setLayout(new BoxLayout(pnProduct, BoxLayout.Y_AXIS));

		// BẢNG 1: SẢN PHẨM
		// Dong 1: LOGO + TÊN (CHƯA ĐẶT)
		JPanel pnLogo = new JPanel();
		pnLogo.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnLogo.setPreferredSize(new Dimension(100, 100));
		pnProduct.add(pnLogo);
		ImageIcon logo = new ImageIcon(
				new ImageIcon("Image\\xeday.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		JLabel lblLogo = new JLabel();
		lblLogo.setIcon(logo);
		pnLogo.add(lblLogo);
		JLabel lblNameProgram = new JLabel("KMP Market");
		lblNameProgram.setFont(new Font("arial", Font.ITALIC, 40));
		lblNameProgram.setForeground(Color.BLACK);
		pnLogo.add(lblNameProgram);

		// Dong 2
		Font fComboBox = new Font("Arial", Font.BOLD, 18);
		JPanel pnFunction1 = new JPanel();
		pnFunction1.setLayout(new BoxLayout(pnFunction1, BoxLayout.X_AXIS));
		pnProduct.add(pnFunction1);

		// 2.1 Chuc nang loc
		JPanel pnFilterTP = new JPanel();
		pnFilterTP.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnFunction1.add(pnFilterTP);

		// Hiển thị chữ lọc
		JLabel lblFilterTP = new JLabel("Lọc theo: ");
		lblFilterTP.setFont(fComboBox);
		pnFilterTP.add(lblFilterTP);

		// Hiện các lựa chọn
		cbFilterTP.setFont(fComboBox);
		pnFilterTP.add(cbFilterTP);

		// 2.2 Chuc nang tim kiem
		JPanel pnSearchPro = new JPanel();
		pnSearchPro.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnFunction1.add(pnSearchPro);
		// Tạo ô tìm kiếm
		txtSearchPro = new JTextField(30);
		txtSearchPro.setText("Nhập tên sản phẩm");

		txtSearchPro.setFont(fGeneral);
		pnSearchPro.add(txtSearchPro);
		// Tạo nút tìm kiếm
		btSearchPro = new JButton("Tìm kiếm");
		btSearchPro.setFont(fComboBox);
		pnSearchPro.add(btSearchPro);

		// 2.3 Chuc nang sap xep
		JPanel pnSort = new JPanel();
		pnSort.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnFunction1.add(pnSort);

		// Hiển thị chữ sắp xếp theo
		JLabel lblSort = new JLabel("Sắp xếp theo: ");
		lblSort.setFont(fComboBox);
		pnSort.add(lblSort);

		// Các lựa chọn
//		String chooseSort[] = { "Mã", "Tên", "Giá nhập", "Giá bán" };
//		JComboBox cbChooseSort = new JComboBox();

		cbChooseSort.setFont(fComboBox);
		pnSort.add(cbChooseSort);

		// Dong 3
		JPanel pnFunction2 = new JPanel();
		pnFunction2.setLayout(new FlowLayout());
		pnProduct.add(pnFunction2);

		// Chia để trị
		JPanel pnSeperate = new JPanel();
		pnSeperate.setLayout(new BoxLayout(pnSeperate, BoxLayout.X_AXIS));
		pnFunction2.add(pnSeperate);

		// Tạo khung bên trái
		JPanel pnSeperateLeft = new JPanel();
		pnSeperateLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnSeperate.add(pnSeperateLeft);

		// Tạo các thao tác xử lý
		JPanel pnHandlePro = new JPanel();
		pnHandlePro.setLayout(new BoxLayout(pnHandlePro, BoxLayout.Y_AXIS));
		pnSeperateLeft.add(pnHandlePro);

		// Tạo hiển thị thông tin
		JPanel pnInfoPro = new JPanel();
		pnInfoPro.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblInfoPro = new JLabel("Thông tin chi tiết của từng sản phẩm");
		pnInfoPro.add(lblInfoPro);
		pnHandlePro.add(pnInfoPro);

		// Tạo hiển thị mã
		JPanel pnIdPro = new JPanel();
		pnIdPro.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblIdPro = new JLabel("Mã: ");
		txtIdPro = new JTextField(15);
		pnIdPro.add(lblIdPro);
		pnIdPro.add(txtIdPro);
//		txtIdPro.setEditable(false);
		pnHandlePro.add(pnIdPro);

		// Tạo hiển thị tên
		JPanel pnNamePro = new JPanel();
		pnNamePro.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNamePro = new JLabel("Tên: ");
		txtNamePro = new JTextField(15);
		pnNamePro.add(lblNamePro);
		pnNamePro.add(txtNamePro);
		pnHandlePro.add(pnNamePro);

		// Tạo hiển thị loại
		JPanel pnTypePro = new JPanel();
		pnTypePro.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTypePro = new JLabel("Loại: ");
		txtTypePro = new JTextField(15);
		pnTypePro.add(lblTypePro);
		pnTypePro.add(txtTypePro);
//		txtTypePro.setEditable(false);
		pnHandlePro.add(pnTypePro);

		// Tạo hiển thị giá nhập
		JPanel pnCostPro = new JPanel();
		pnCostPro.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblCostPro = new JLabel("Giá nhập: ");
		txtCostPro = new JTextField(15);
		pnCostPro.add(lblCostPro);
		pnCostPro.add(txtCostPro);
		pnHandlePro.add(pnCostPro);

		// Tạo hiển thị giá bán
		JPanel pnPricePro = new JPanel();
		pnPricePro.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPricePro = new JLabel("Giá bán: ");
		txtPricePro = new JTextField(15);
		pnPricePro.add(lblPricePro);
		pnPricePro.add(txtPricePro);
		pnHandlePro.add(pnPricePro);

		// Tạo hiển thị số lượng
		JPanel pnQuantityPro = new JPanel();
		pnQuantityPro.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblQuantityPro = new JLabel("Số lượng: ");
		txtQuantityPro = new JTextField(15);
		pnQuantityPro.add(lblQuantityPro);
		pnQuantityPro.add(txtQuantityPro);
		txtQuantityPro.setEditable(false);
		pnHandlePro.add(pnQuantityPro);

		// Tạo hiển thị Đơn vị tính
		JPanel pnUnitPro = new JPanel();
		pnUnitPro.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblUnitPro = new JLabel("Đơn vị tính: ");
		txtUnitPro = new JTextField(15);
		pnUnitPro.add(lblUnitPro);
		pnUnitPro.add(txtUnitPro);
		pnHandlePro.add(pnUnitPro);

		// Tạo hiển thị mô tả
		JPanel pnDescription = new JPanel();
		pnDescription.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblDescription = new JLabel("Mô tả: ");
		txtDescription = new JTextField(15);
		pnDescription.add(lblDescription);
		pnDescription.add(txtDescription);
		pnHandlePro.add(pnDescription);

		lblIdPro.setPreferredSize(lblUnitPro.getPreferredSize());
		lblNamePro.setPreferredSize(lblUnitPro.getPreferredSize());
		lblTypePro.setPreferredSize(lblUnitPro.getPreferredSize());
		lblCostPro.setPreferredSize(lblUnitPro.getPreferredSize());
		lblPricePro.setPreferredSize(lblUnitPro.getPreferredSize());
		lblQuantityPro.setPreferredSize(lblUnitPro.getPreferredSize());
		lblDescription.setPreferredSize(lblUnitPro.getPreferredSize());

		// Nhập hàng
		JPanel pnImport = new JPanel();
		pnImport.setPreferredSize(new Dimension(200, 40));
		pnHandlePro.add(pnImport);
		// Tạo nút nhập hàng
		btImport = new JButton("Nhập hàng");
		btImport.setFont(fGeneral);
		pnImport.add(btImport);

		// Xử lý thêm sản phẩm mới
		JPanel pnAdd = new JPanel();
		pnAdd.setPreferredSize(new Dimension(200, 40));
		pnHandlePro.add(pnAdd);
		// Tạo nút thêm sản phẩm mới
		btAddPro = new JButton("Thêm sản phẩm");
		btAddPro.setFont(fGeneral);
		pnAdd.add(btAddPro);

		// Xử lý thêm loại sản phẩm mới
		JPanel pnAddType = new JPanel();
		pnAddType.setPreferredSize(new Dimension(200, 40));
		pnHandlePro.add(pnAddType);
		// Tạo nút thêm loại sản phẩm mới
		btAddType = new JButton("Thêm loại mới");
		btAddType.setFont(fGeneral);
		pnAddType.add(btAddType);

		// Xử lý sửa
		JPanel pnUpdate = new JPanel();
		pnUpdate.setPreferredSize(new Dimension(200, 40));
		pnHandlePro.add(pnUpdate);
		// Tạo nút sửa
		btUpdatePro = new JButton("Sửa sản phẩm");
		btUpdatePro.setFont(fGeneral);
		pnUpdate.add(btUpdatePro);

		// Xử lý xóa
		JPanel pnDeletePro = new JPanel();
		pnDeletePro.setPreferredSize(new Dimension(200, 40));
		pnHandlePro.add(pnDeletePro);
		// Tạo nút xóa
		btDeletePro = new JButton("Xóa sản phẩm");
		btDeletePro.setFont(fGeneral);
		pnDeletePro.add(btDeletePro);

		// Xử lý thống kê
		JPanel pnReckon = new JPanel();
		pnReckon.setPreferredSize(new Dimension(200, 40));
		pnHandlePro.add(pnReckon);
		// Tạo nút thống kê
		btReckon = new JButton("Thống kê");
		btReckon.setFont(fGeneral);
		pnReckon.add(btReckon);

		// Tạo ghi chú tác giả
//		JPanel pnNote1 = new JPanel();
//		pnNote1.setLayout(new FlowLayout(FlowLayout.CENTER));
//		JLabel lblNote1 = new JLabel("Thiết kế bởi Chu Quý Tộc");
//		pnNote1.add(lblNote1);
//		pnHandlePro.add(pnNote1);
//
//		JPanel pnNote2 = new JPanel();
//		pnNote2.setLayout(new FlowLayout(FlowLayout.CENTER));
//		JLabel lblNote2 = new JLabel("@Chu");
//		pnNote2.add(lblNote2);
//		pnHandlePro.add(pnNote2);

		// Tạo khung bên phải
		JPanel pnSeperateRight = new JPanel();
		pnSeperateRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnSeperateRight.setBackground(Color.BLACK);
		pnSeperate.add(pnSeperateRight);

		dtmProduct = new DefaultTableModel();
		dtmProduct.addColumn("Mã");
		dtmProduct.addColumn("Tên");
		dtmProduct.addColumn("Loại");
		dtmProduct.addColumn("Đơn vị tính");
		dtmProduct.addColumn("Giá nhập");
		dtmProduct.addColumn("Giá bán");
		dtmProduct.addColumn("Số lượng");
		dtmProduct.addColumn("Mô tả");
		tblProduct = new JTable(dtmProduct);

		JScrollPane scTable = new JScrollPane(tblProduct, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scTable.setPreferredSize(new Dimension(896, 520));
		pnSeperateRight.add(scTable);

		// BẢNG 2: KHÁCH HÀNG
		// Dong 1
		JPanel pnLine1 = new JPanel();
		pnLine1.setLayout(new FlowLayout());
		pnLine1.setPreferredSize(new Dimension(1200, 122));
		pnCustomer.add(pnLine1);

		// Chia để trị
		JPanel pnSeperateCus = new JPanel();
		pnSeperateCus.setLayout(new BoxLayout(pnSeperateCus, BoxLayout.X_AXIS));
		pnLine1.add(pnSeperateCus);

		// Tạo khung bên trái chứa logo
		JPanel pnSeperateCusLeft = new JPanel();
		pnSeperateCusLeft.setPreferredSize(new Dimension(300, 120));
		pnSeperateCusLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnSeperateCus.add(pnSeperateCusLeft);

		// Bắt đầu tạo logo
		JLabel lblLogoCus = new JLabel();
		lblLogoCus.setIcon(logo);
		pnSeperateCusLeft.add(lblLogoCus);

		// Tạo khung bên phải chứa ô tìm kiếm
		JPanel pnSeperateCusRight = new JPanel();
		pnSeperateCusRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnSeperateCus.add(pnSeperateCusRight);

		// TÌM KIẾM
		JPanel pnSearchCus = new JPanel();
		pnSearchCus.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnSeperateCusRight.add(pnSearchCus);

		// Tạo ô tìm kiếm
		txtSearchCus = new JTextField(20);
		txtSearchCus.setText("Nhập tên khách hàng");

		txtSearchCus.setFont(fGeneral);
		pnSearchCus.add(txtSearchCus);

		// Tạo nút tìm kiếm
		btSearchCus = new JButton("Tìm kiếm");
		btSearchCus.setFont(fComboBox);
		pnSearchCus.add(btSearchCus);

		// Làm mới
		JPanel pnRefresh = new JPanel();
		pnRefresh.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnSeperateCusRight.add(pnRefresh);

		// Tạo nút làm mới
		btRefresh = new JButton("Làm mới");
		btRefresh.setFont(fComboBox);
		pnRefresh.add(btRefresh);

		// Dong 2
		JPanel pnLine2 = new JPanel();
		pnLine2.setLayout(new FlowLayout());
		pnCustomer.add(pnLine2);

		// Chia để trị
		JPanel pnSeperate2 = new JPanel();
		pnSeperate2.setLayout(new BoxLayout(pnSeperate2, BoxLayout.X_AXIS));
		pnLine2.add(pnSeperate2);

		// Tạo khung bên trái chứa chức năng khách hàng
		JPanel pnSeperateLeft2 = new JPanel();
		pnSeperateLeft2.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnSeperate2.add(pnSeperateLeft2);

		// Tạo các thao tác xử lý
		JPanel pnHandleCus = new JPanel();
		pnHandleCus.setLayout(new BoxLayout(pnHandleCus, BoxLayout.Y_AXIS));
		pnSeperateLeft2.add(pnHandleCus);

		// Tạo hiển thị thông tin khách hàng
		JPanel pnInfoCus = new JPanel();
		pnInfoCus.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblInfoCus = new JLabel("Thông tin chi tiết của từng khách hàng");
		pnInfoCus.add(lblInfoCus);
		pnHandleCus.add(pnInfoCus);

		// Tạo hiển thị mã
		JPanel pnIdCus = new JPanel();
		pnIdCus.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblIdCus = new JLabel("Mã: ");
		txtIdCus = new JTextField(15);
		pnIdCus.add(lblIdCus);
		pnIdCus.add(txtIdCus);
		pnHandleCus.add(pnIdCus);

		// Tạo hiển thị tên
		JPanel pnNameCus = new JPanel();
		pnNameCus.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNameCus = new JLabel("Tên: ");
		txtNameCus = new JTextField(15);
		pnNameCus.add(lblNameCus);
		pnNameCus.add(txtNameCus);
		pnHandleCus.add(pnNameCus);

		// Tạo hiển thị số điện thoại
		JPanel pnPhone = new JPanel();
		pnPhone.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPhone = new JLabel("Số điện thoại: ");
		txtPhone = new JTextField(15);
		pnPhone.add(lblPhone);
		pnPhone.add(txtPhone);
		pnHandleCus.add(pnPhone);

		// Tạo hiển thị ngày sinh
		JPanel pnBirth = new JPanel();
		pnBirth.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblBirth = new JLabel("Ngày sinh: ");
		txtBirth = new JTextField(15);
		pnBirth.add(lblBirth);
		pnBirth.add(txtBirth);
		pnHandleCus.add(pnBirth);

		// Tạo hiển thị giới tính
		JPanel pnSex = new JPanel();
		pnSex.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSex = new JLabel("Giới tính: ");
		txtSex = new JTextField(15);
		pnSex.add(lblSex);
		pnSex.add(txtSex);
		pnHandleCus.add(pnSex);

		// Tạo hiển thị địa chỉ
		JPanel pnAddress = new JPanel();
		pnAddress.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblAddress = new JLabel("Địa chỉ: ");
		txtAddress = new JTextField(15);
		pnAddress.add(lblAddress);
		pnAddress.add(txtAddress);
		pnHandleCus.add(pnAddress);

		// Tạo hiển thị ngày đăng kí
		JPanel pnDayReg = new JPanel();
		pnDayReg.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblDayReg = new JLabel("Ngày đăng kí: ");
		txtDayReg = new JTextField(15);
		pnDayReg.add(lblDayReg);
		pnDayReg.add(txtDayReg);
		pnHandleCus.add(pnDayReg);

		// Tạo hiển thị cấp phép
		JPanel pnPermission = new JPanel();
		pnPermission.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPermission = new JLabel("Cấp phép: ");
		txtPermission = new JTextField(15);
		pnPermission.add(lblPermission);
		pnPermission.add(txtPermission);
		pnHandleCus.add(pnPermission);

		lblIdCus.setPreferredSize(lblPhone.getPreferredSize());
		lblNameCus.setPreferredSize(lblPhone.getPreferredSize());
		lblBirth.setPreferredSize(lblPhone.getPreferredSize());
		lblSex.setPreferredSize(lblPhone.getPreferredSize());
		lblAddress.setPreferredSize(lblPhone.getPreferredSize());
		lblDayReg.setPreferredSize(lblPhone.getPreferredSize());
		lblPermission.setPreferredSize(lblPhone.getPreferredSize());

		// xử lý xác nhận đơn
		JPanel pnConfirmBill = new JPanel();
		pnHandleCus.add(pnConfirmBill);
		// tao nut xac nhan don
		btConfirmBill = new JButton("Xác nhận đơn hàng");
		btConfirmBill.setFont(fGeneral);
		pnConfirmBill.add(btConfirmBill);

		// Xử lý sửa quyền truy cập
		JPanel pnUpdatePerCus = new JPanel();
		pnHandleCus.add(pnUpdatePerCus);
		// Tạo nút sửa
		btUpdatePerCus = new JButton("Sửa quyền truy cập");
		btUpdatePerCus.setFont(fGeneral);
		pnUpdatePerCus.add(btUpdatePerCus);

		// Xử lý xóa
		JPanel pnDeleteCus = new JPanel();
		pnHandleCus.add(pnDeleteCus);
		// Tạo nút xóa
		btDeleteCus = new JButton("Xóa khách hàng");
		btDeleteCus.setFont(fGeneral);
		pnDeleteCus.add(btDeleteCus);

		// Xử lý thống kê tiêu dùng
		JPanel pnTopOder = new JPanel();
		pnTopOder.setPreferredSize(new Dimension(200, 100));
		pnHandleCus.add(pnTopOder);
		// Tạo nút thống kê tiêu dùng
		btTopOrder = new JButton("Top tiêu dùng");
		btTopOrder.setFont(fGeneral);
		pnTopOder.add(btTopOrder);

		// Tạo ghi chú tác giả
		JPanel pnNote3 = new JPanel();
		pnNote3.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblNote3 = new JLabel("Thiết kế bởi Chu Quý Tộc");
		pnNote3.add(lblNote3);
		pnHandleCus.add(pnNote3);

		JPanel pnNote4 = new JPanel();
		pnNote4.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblNote4 = new JLabel("@Chu");
		pnNote4.add(lblNote4);
		pnHandleCus.add(pnNote4);

		// Tạo khung bên phải chứa bảng khách hàng
		JPanel pnSeperateRight2 = new JPanel();
		pnSeperateRight2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnSeperateRight2.setBackground(Color.BLACK);
		pnSeperate2.add(pnSeperateRight2);

		dtmCustomer = new DefaultTableModel();
		dtmCustomer.addColumn("Mã");
		dtmCustomer.addColumn("Tên");
		dtmCustomer.addColumn("Số điện thoại");
		dtmCustomer.addColumn("Ngày sinh");
		dtmCustomer.addColumn("Giới tính");
		dtmCustomer.addColumn("Địa chỉ");
		dtmCustomer.addColumn("Ngày đăng kí");
		dtmCustomer.addColumn("Cấp phép");
		tblCustomer = new JTable(dtmCustomer);

		JScrollPane scTableCus = new JScrollPane(tblCustomer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scTableCus.setPreferredSize(new Dimension(880, 530));
		pnSeperateRight2.add(scTableCus);

		// BẢNG 3: QUẢN TRỊ VIÊN
		pnAdministrator.setLayout(new BorderLayout());

		// Tạo khung bến trái chứa thao tác
		JPanel pnWest = new JPanel();
		pnWest.setLayout(new FlowLayout());
		pnAdministrator.add(pnWest, BorderLayout.WEST);

		// Tạo thao tác xử lý quản trị viên
		JPanel pnHandleAd = new JPanel();
		pnHandleAd.setLayout(new BoxLayout(pnHandleAd, BoxLayout.Y_AXIS));
		pnWest.add(pnHandleAd);

		// Tạo hiển thị thông tin quản trị viên
		JPanel pnInfoAd = new JPanel();
		pnInfoAd.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblInfoAd = new JLabel("Thông tin chi tiết của từng quản trị viên");
		pnInfoAd.add(lblInfoAd);
		pnHandleAd.add(pnInfoAd);

		// Tạo hiển thị mã
		JPanel pnIdAd = new JPanel();
		pnIdAd.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblIdAd = new JLabel("Mã: ");
		txtIdAd = new JTextField(15);
		pnIdAd.add(lblIdAd);
		pnIdAd.add(txtIdAd);
		pnHandleAd.add(pnIdAd);

		// Tạo hiển thị tên
		JPanel pnNameAd = new JPanel();
		pnNameAd.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNameAd = new JLabel("Tên: ");
		txtNameAd = new JTextField(15);
		pnNameAd.add(lblNameAd);
		pnNameAd.add(txtNameAd);
		pnHandleAd.add(pnNameAd);

		// Tạo hiển thị tên đăng nhập
		JPanel pnUserName = new JPanel();
		pnUserName.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblUserName = new JLabel("Tên đăng nhập: ");
		txtUserName = new JTextField(15);
		pnUserName.add(lblUserName);
		pnUserName.add(txtUserName);
		pnHandleAd.add(pnUserName);

		// Tạo hiển thị mật khẩu
		JPanel pnPass = new JPanel();
		pnPass.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPass = new JLabel("Mật khẩu: ");
		txtPass = new JTextField(15);
		pnPass.add(lblPass);
		pnPass.add(txtPass);
		pnHandleAd.add(pnPass);

		// Tạo hiển thị số điện thoại
		JPanel pnPhoneAd = new JPanel();
		pnPhoneAd.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPhoneAd = new JLabel("Số điện thoại: ");
		txtPhoneAd = new JTextField(15);
		pnPhoneAd.add(lblPhoneAd);
		pnPhoneAd.add(txtPhoneAd);
		pnHandleAd.add(pnPhoneAd);

		// Tạo hiển thị ngày sinh
		JPanel pnBirthAd = new JPanel();
		pnBirthAd.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblBirthAd = new JLabel("Ngày sinh: ");
		txtBirthAd = new JTextField(15);
		pnBirthAd.add(lblBirthAd);
		pnBirthAd.add(txtBirthAd);
		pnHandleAd.add(pnBirthAd);

		// Tạo hiển thị giới tính
		JPanel pnSexAd = new JPanel();
		pnSexAd.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSexAd = new JLabel("Giới tính: ");
		txtSexAd = new JTextField(15);
		pnSexAd.add(lblSexAd);
		pnSexAd.add(txtSexAd);
		pnHandleAd.add(pnSexAd);

		// Tạo hiển thị quê quán
		JPanel pnNative = new JPanel();
		pnNative.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNative = new JLabel("Quê quán: ");
		txtNative = new JTextField(15);
		pnNative.add(lblNative);
		pnNative.add(txtNative);
		pnHandleAd.add(pnNative);

		lblIdAd.setPreferredSize(lblUserName.getPreferredSize());
		lblNameAd.setPreferredSize(lblUserName.getPreferredSize());
		lblPass.setPreferredSize(lblUserName.getPreferredSize());
		lblPhoneAd.setPreferredSize(lblUserName.getPreferredSize());
		lblBirthAd.setPreferredSize(lblUserName.getPreferredSize());
		lblSexAd.setPreferredSize(lblUserName.getPreferredSize());
		lblNative.setPreferredSize(lblUserName.getPreferredSize());

		// Xử lý thêm
		JPanel pnAddAd = new JPanel();
		pnAddAd.setLayout(new FlowLayout());
		pnHandleAd.add(pnAddAd);
		// Tạo nút thêm
		btAddAd = new JButton("Thêm quản trị viên");
		btAddAd.setFont(fGeneral);
		pnAddAd.add(btAddAd);

		// Xử lý sửa
		JPanel pnUpdateAd = new JPanel();
		pnHandleAd.add(pnUpdateAd);
		// Tạo nút sửa
		btUpdateAd = new JButton("Sửa quản trị viên");
		btUpdateAd.setFont(fGeneral);
		pnUpdateAd.add(btUpdateAd);

		// Xử lý xóa
		JPanel pnDeleteAd = new JPanel();
		pnDeleteAd.setLayout(new FlowLayout());
//		pnDeleteAd.setPreferredSize(new Dimension(200, 220));
		pnHandleAd.add(pnDeleteAd);
		// Tạo nút xóa
		btDeleteAd = new JButton("Xóa quản trị viên");
		btDeleteAd.setFont(fGeneral);
		pnDeleteAd.add(btDeleteAd);

		// Xử lý làm mới
		JPanel pnRefreshAd = new JPanel();
		pnRefreshAd.setLayout(new FlowLayout());
		pnRefreshAd.setPreferredSize(new Dimension(200, 120));
		pnHandleAd.add(pnRefreshAd);
		// Tạo nút làm mới
		btRefreshAd = new JButton("Làm mới");
		btRefreshAd.setFont(fGeneral);
		pnRefreshAd.add(btRefreshAd);

		// Tạo ghi chú tác giả
		JPanel pnNote5 = new JPanel();
		pnNote5.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblNote5 = new JLabel("Thiết kế bởi Chu Quý Tộc");
		pnNote5.add(lblNote5);
		pnHandleAd.add(pnNote5);

		JPanel pnNote6 = new JPanel();
		pnNote6.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblNote6 = new JLabel("@Chu");
		pnNote6.add(lblNote6);
		pnHandleAd.add(pnNote6);

		// Tạo khung trung tâm chứa bảng quản trị viên
		JPanel pnCenter = new JPanel();
		pnCenter.setBackground(Color.BLACK);
		pnAdministrator.add(pnCenter, BorderLayout.CENTER);
		dtmAdministrator = new DefaultTableModel();
		dtmAdministrator.addColumn("Mã");
		dtmAdministrator.addColumn("Tên");
		dtmAdministrator.addColumn("Tên đăng nhập");
		dtmAdministrator.addColumn("Mật khẩu");
		dtmAdministrator.addColumn("Số điện thoại");
		dtmAdministrator.addColumn("Ngày sinh");
		dtmAdministrator.addColumn("Giới tính");
		dtmAdministrator.addColumn("Quê quán");
		tblAdministrator = new JTable(dtmAdministrator);
		JScrollPane scTableAd = new JScrollPane(tblAdministrator, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scTableAd.setPreferredSize(new Dimension(888, 676));
		pnCenter.add(scTableAd);
	}

	public void showWindow() {
		this.setTitle("HỆ THỐNG QUẢN LÝ");
		this.setSize(1200, 760);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
