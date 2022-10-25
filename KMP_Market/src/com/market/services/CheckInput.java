package com.market.services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import com.market.entity.Customer;
import com.market.entity.User;
import com.market.jdbc.Connect;

public class CheckInput {
	public boolean checkExistUser(User user) {
		try {
			Connection con = Connect.getConnect();
			CallableStatement callStatement = con.prepareCall("{call virtual.proc_showUserbyUsername(?)}");
			callStatement.setString(1, user.getUserName());
			ResultSet result = callStatement.executeQuery();
			return result.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean validateUser(User user) {
		if (user.getUserName() == null || "".equals(user.getUserName())) {
			return true;
		}
		return false;
	}

	public boolean checkUser(User user) {
		String userPattern = "^(?=.*[a-z])(?=.*[0-9])[a-z[0-9]]{5,}$";
		return Pattern.matches(userPattern, user.getUserName());
	}

	public boolean validatePass(User user) {
		if (user.getPassWord() == null || "".equals(user.getPassWord())) {
			return true;
		}
		return false;
	}

	public boolean checkPass(User user) {
//		String passPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z[0-9]@$!%*?&]{8,}$";
		String passPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[`~!@#$%^&*+=|:;?/><.,])[A-Za-z[0-9]`~!@#$%^&*+=|:;?/><.,]{8,}$";
		return Pattern.matches(passPattern, user.getPassWord());
	}

	public boolean checkConfirmPass(User user) {
		return user.getPassWord().equals(user.getConfirmPass());

	}

	public boolean checkName(Customer cus) {
		if (cus.getName() == null || "".equals(cus.getName())) {
			return true;
		}
		return false;
	}

	public boolean checkPhone(Customer cus) {
		if (cus.getPhone() == null || "".equals(cus.getPhone())) {
			return true;
		}
		return false;
	}

	public boolean checkExistPhone(Customer cus) {
		Connection con = Connect.getConnect();
		try {
//			Statement sta = con.createStatement();
//			String sql1 = "SELECT * FROM [dbo].[v_showCustomerPhone]";
//			ResultSet rp = sta.executeQuery(sql1);
//			while (rp.next()) {
//				if (cus.getPhone().compareToIgnoreCase(rp.getString("phone")) == 0) {
////					System.out.println("Số ĐT đã được sử dụng ");
//					return true;
//				}
//			}

			CallableStatement callStatement = con.prepareCall("{call virtual.proc_checkPhoneExisted(?)}");
			callStatement.setString(1, cus.getPhone());
			ResultSet result = callStatement.executeQuery();
			return result.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean validateName(Customer cus) {
		String namePattern = "^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶ"
				+ "ẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợ" + "ụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$";
		return Pattern.matches(namePattern, cus.getName());
	}

	public boolean validatephone(Customer cus) {
		String phonePattern = "^(03|05|07|09|08)[0-9]{8}$";
		return Pattern.matches(phonePattern, cus.getPhone());
	}

	public String chuyenInHoa(String str) {
		String s, strOutput;
		s = str.substring(0, 1);
		strOutput = str.replaceFirst(s, s.toUpperCase());
		return (strOutput);
	}

	public String chuanHoa(String Name) {
		String strOutput = "";
		StringTokenizer strToken = new StringTokenizer(Name, " ,\t,\r");
		strOutput += "" + chuyenInHoa(strToken.nextToken());
		while (strToken.hasMoreTokens()) {
			strOutput += " " + chuyenInHoa(strToken.nextToken());
		}
		return (strOutput);
	}
}
