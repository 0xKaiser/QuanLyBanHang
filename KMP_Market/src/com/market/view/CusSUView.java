package com.market.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.market.entity.Customer;

public class CusSUView extends JFrame implements ActionListener {
	private JLabel lbName;
	private JLabel lbPhone;
	private JLabel lbSex;
	private JLabel lbBirthday;
	private JLabel lbAddress;
	private JLabel lbtitle;
	private JLabel lbDay;
	private JLabel lbMonth;
	private JLabel lbYear;

	JTextField txtName = new JTextField();
	JTextField txtPhone = new JTextField();
	JTextField txtAddress = new JTextField();

	JButton btnReturn;
	JButton btnSignUp;

	private String[] d = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
			"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
	private String[] m = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
//	private String[] y = { "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983",
//			"1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996",
//			"1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
//			"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", };
	private String[] s = { "NAM", "NỮ", "KHÁC" };

	JComboBox cbSex;
	JComboBox cbDay;
	JComboBox cbMonth;
	JComboBox cbYear;

//	private String Name, phone, day, month, year, address;
//	private int sex;
//	private String dateInString;
//	java.util.Date date;
//	java.sql.Date sqlDate;

	public CusSUView() {
		addControl();
	}

	private void addControl() {

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		JPanel pnTTin = new JPanel();
		pnTTin.setLayout(new BoxLayout(pnTTin, BoxLayout.Y_AXIS));
		con.add(pnTTin);

		JPanel pnTitle = new JPanel();
		pnTitle.setLayout(new FlowLayout());
		JLabel lblTieude = new JLabel("THÔNG TIN CÁ NHÂN");
		pnTitle.add(lblTieude);
		pnTTin.add(pnTitle);
		lblTieude.setForeground(Color.BLACK);
		Font fontTieuDe = new Font("arial", Font.BOLD, 20);
		lblTieude.setFont(fontTieuDe);

		JPanel pnName = new JPanel();
		pnName.setLayout(new FlowLayout());
		lbName = new JLabel("Họ tên ");
		txtName = new JTextField(20);
		pnName.add(lbName);
		pnName.add(txtName);
		pnTTin.add(pnName);

		JPanel pnPhone = new JPanel();
		pnPhone.setLayout(new FlowLayout());
		lbPhone = new JLabel("Số điện thoại ");
		txtPhone = new JTextField(20);
		pnPhone.add(lbPhone);
		pnPhone.add(txtPhone);
		pnTTin.add(pnPhone);

		JPanel pnAddress = new JPanel();
		pnAddress.setLayout(new FlowLayout());
		lbAddress = new JLabel("Địa chỉ ");
		txtAddress = new JTextField(20);
		pnAddress.add(lbAddress);
		pnAddress.add(txtAddress);
		pnTTin.add(pnAddress);
		// jcombobox Sex
		JPanel pnCk = new JPanel();
		pnCk.setLayout(new BoxLayout(pnCk, BoxLayout.X_AXIS));
		pnTTin.add(pnCk);

		JPanel pnSex = new JPanel();
		pnSex.setLayout(new FlowLayout());
		lbSex = new JLabel("Giới tính ");
		cbSex = new JComboBox(s);
		pnSex.add(lbSex);
		pnSex.add(cbSex);
		pnCk.add(pnSex);

		JPanel pnBirth = new JPanel();
		pnBirth.setLayout(new FlowLayout());
		JLabel lbBirth = new JLabel("Sinh nhật ");
		pnBirth.add(lbBirth);
		pnTTin.add(pnBirth);

		// tạo jcombobox birthday
		JPanel pnTin = new JPanel();
		pnTin.setLayout(new BoxLayout(pnTin, BoxLayout.X_AXIS));
		pnTTin.add(pnTin);

		JPanel pnDay = new JPanel();
		pnDay.setLayout(new FlowLayout());
		lbDay = new JLabel("Ngày ");
		cbDay = new JComboBox(d);
		pnDay.add(lbDay);
		pnDay.add(cbDay);
		pnTin.add(pnDay);

		JPanel pnMonth = new JPanel();
		pnMonth.setLayout(new FlowLayout());
		lbMonth = new JLabel("Tháng ");
		cbMonth = new JComboBox(m);
		pnMonth.add(lbMonth);
		pnMonth.add(cbMonth);
		pnTin.add(pnMonth);

		JPanel pnYear = new JPanel();
		pnYear.setLayout(new FlowLayout());
		lbYear = new JLabel("Năm ");
		cbYear = new JComboBox();
		for (int i = 2021; i >= 1940; i--) {
			cbYear.addItem(i);
		}
		pnYear.add(lbYear);
		pnYear.add(cbYear);
		pnTin.add(pnYear);

		lbName.setPreferredSize(lbPhone.getPreferredSize());
		lbAddress.setPreferredSize(lbPhone.getPreferredSize());
		lbSex.setPreferredSize(lbPhone.getPreferredSize());

		// tạo button trở lại và đăng kí

		JPanel pnButton = new JPanel();
		pnButton.setLayout(new FlowLayout());
		btnReturn = new JButton("Trở lại ");
		btnSignUp = new JButton("Đăng kí ");
		pnButton.add(btnReturn);
		pnButton.add(btnSignUp);
		pnTTin.add(pnButton);

		this.setTitle("Customer");
		this.setSize(350, 350);
//		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
//		this.setModal(true);
		this.setResizable(false);
//		this.setVisible(true);

		btnSignUp.addActionListener(this);
		btnReturn.addActionListener(this);

	}

	public Customer getCustomer() {
		return new Customer(txtName.getText().trim(), txtPhone.getText().trim(), txtAddress.getText().trim(),
				cbSex.getSelectedIndex(), cbDay.getSelectedItem().toString(), cbMonth.getSelectedItem().toString(),
				cbYear.getSelectedItem().toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void addCustomerListner(ActionListener customer) {
		btnSignUp.addActionListener(customer);
	}

	public void addreturnListener(ActionListener trlai) {
		btnReturn.addActionListener(trlai);
	}
}
