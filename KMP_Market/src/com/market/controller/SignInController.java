package com.market.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.market.dao.AdminDAO;
import com.market.dao.CustomerDao;
import com.market.dao.UserDAO;
import com.market.entity.User;
import com.market.jdbc.Connect;
import com.market.services.Md5;
import com.market.view.CusSUView;
import com.market.view.HomePageView;
import com.market.view.ManagermentView;
import com.market.view.SignInView;
import com.market.view.UserSUView;

public class SignInController {
	Connection conn = Connect.getConnect();

	private AdminDAO adminDAO;
	private UserDAO userDAO;
	private CustomerDao cusDAO;
	private SignInView signInView;
	private BufferedReader readfile;
	private Md5 mh = new Md5();
	private File filesave = new File("FileSaveData\\filesavepass.txt");

	public SignInController(SignInView view) throws SQLException {
		this.signInView = view;
		this.userDAO = new UserDAO();
		this.adminDAO = new AdminDAO();
		this.cusDAO = new CustomerDao();
		view.addSignInListener(new signInListener()); // login button
		view.addSignUpListener(new signupListener()); // signup button
//		view.getSeepass().setVisible(false);
		view.getDontseepass().setVisible(false);
		view.addseepass(new Seepass());
		view.adddontseepass(new Dontseepass());
	}

	public void showSignInView() {
		try {
			// nếu có tài khoản đã lưu mk , thì sẽ hiện account
			if (filesave.exists()) {
				// khai báo để đọc file
				FileInputStream read = new FileInputStream("FileSaveData\\filesavepass.txt");
				readfile = new BufferedReader(new InputStreamReader(read));
				String readacc = readfile.readLine();// đọc dòng 1
				String readpass = readfile.readLine();// đọc dòng 2
				signInView.getSaveCheckbox().setSelected(true);
				signInView.getAccountField().setText(readacc);
				signInView.getPasswordField().setText(readpass);
			}
			signInView.showWindow();
		} catch (Exception e) {
			System.out.println("SignInController.showSignInView()" + e);
		}
	}

	// action : signup button
	class signupListener implements ActionListener {
		public void actionPerformed(ActionEvent e1) {
			signInView.dispose();
			// gọi hàm Mạnh
			UserSUView suView = new UserSUView();
			UserSignUpController SuCt = new UserSignUpController(suView);
			SuCt.showSignupView();
		}
	}

	// action : login button
	class signInListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				User user = signInView.getUser();

				if (adminDAO.checkSignInAdmin(user, mh) != null) {
					signInView.showMessage("Đăng nhập thành công", "Thông báo", -1);
					// gọi hàm savepass để lưu mật khẩu
					savepass(user);
					// gọi hàm Tộc => manager
					ManagermentView mgv = new ManagermentView();
					mgv.showWindow();
					// truyeenf idAdmin cho

				} else if (userDAO.checkSignInUser(user, mh) != null) {
					String sql = "select * from virtual.v_showFullCus where username = '" + user.getUserName() + "'";
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(sql);
					if (rs.next()) {
						String idUser = rs.getString("idUser");
						if (rs.getBoolean("permission")) {
							System.out.println("user login success");
							signInView.showMessage("Đăng nhập thành công \n" + "Welcome : " + cusDAO.getnameCus(idUser),
									"Thông báo", -1);
							signInView.dispose();
							savepass(user);
							// gọi hàm Phát = > gọi Trang chủ
							HomePageView view = new HomePageView();
							HomePageController controller = new HomePageController(view);
							controller.showHomePageView();
							view.displayProductInCart(idUser);
							controller.setIdx(idUser);
						}
						if (rs.getBoolean("permission") == false) {
							if (rs.getString("phone") == null) {
								// gọi hàm đăng kí thông tin user
								CusSUView customerView = new CusSUView();
								CusSignUpController customerController = new CusSignUpController(customerView);
								customerController.showCustomer();
								customerController.setID(idUser);
								System.out.println("đăng kí thông tin");
							} else {
								System.out.println("bị khóa tài khoản");
								JOptionPane.showMessageDialog(null, "Tài khoản của bạn đã bị khóa", "Thông báo",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				} else {
					signInView.showMessage("Username hoặc Password ko đúng", "Thông báo", 2);
				}
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}

	}

	// hàm lưu mật khẩu
	private void savepass(User user) {
		try {
			/*
			 * nếu filesave tồn tại thì kiểm tra account đăng nhập với tài khoản đã lưu
			 * trước đó nếu filesave chưa tồn tại thì tạo file rồi account
			 */
			if (signInView.getSaveCheckbox().isSelected()) {
				if (filesave.exists()) {
					// khai báo để đọc file
					FileInputStream read = new FileInputStream(filesave);
					readfile = new BufferedReader(new InputStreamReader(read));
					String readacc = readfile.readLine();// đọc dòng 1
					String readpass = readfile.readLine();// đọc dòng 2

					if (readacc != null && readpass != null) {
						if (readacc.compareTo(user.getUserName()) != 0 || readpass.compareTo(user.getPassWord()) != 0) {
							signInView.showConfirm("Bạn có muốn lưu mật khẩu không", "Thông báo", 0);
							if (signInView.getInput() == 0) {
								FileWriter filesave = new FileWriter("FileSaveData\\filesavepass.txt");
								filesave.write(user.getUserName() + "\n");
								filesave.write(user.getPassWord());
								System.out.println("lưu đè khác null thành công");
								filesave.close();
							}
						} else {
							System.out.println("tài khoản đã được lưu trước đó");
						}
					} else {
						signInView.showConfirm("Bạn có muốn lưu mật khẩu không", "Thông báo", 0);
						if (signInView.getInput() == 0) {
							FileWriter filesave = new FileWriter("FileSaveData\\filesavepass.txt");
							filesave.write(user.getUserName() + "\n");
							filesave.write(user.getPassWord());
							System.out.println("lưu đè = null thành công");
							filesave.close();
						}
					}
				} else {
					FileWriter filesave = new FileWriter("FileSaveData\\filesavepass.txt");
					filesave.write(user.getUserName() + "\n");
					filesave.write(user.getPassWord());
					signInView.showMessage("Bạn đã lưu mật khẩu", "Thông báo", -1);
					System.out.println("lưu thành công");
					filesave.close();
				}
			} else {
				FileWriter filesave = new FileWriter("FileSaveData\\filesavepass.txt");
				filesave.write("");
				filesave.write("");
				System.out.println("tài khoản ko đc lưu");
				filesave.close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// action : seepass : xem mật khẩu
	class Seepass implements MouseListener {
		public void mousePressed(MouseEvent e) {
			signInView.getDontseepass().setVisible(true);
			signInView.getSeepass().setVisible(false);
			signInView.getPasswordField().setEchoChar((char) 0);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	class Dontseepass implements MouseListener {
		public void mousePressed(MouseEvent e) {
			signInView.getSeepass().setVisible(true);
			signInView.getDontseepass().setVisible(false);
			signInView.getPasswordField().setEchoChar('*');
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}
}
