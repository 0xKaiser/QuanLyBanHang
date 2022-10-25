package com.market.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.market.jdbc.Connect;

public class Auto_Increase_Id {
	Connection cnn = null;

	public Auto_Increase_Id() {
//		// nếu lấy id từ bảng sản phẩm: truyền vào mã loại sản phẩm
//		getIdProduct("rc");
//		// nếu lấy id từ các bảng khác: truyền vào tên bảng
//		getId("production.ImportDetails");

	}

	public String getIdProduct(String typeProduct) {
		cnn = Connect.getConnect();
		try {
			String sql1 = "select * from production.Product where type= '" + typeProduct + "'";
			PreparedStatement preStatement = cnn.prepareStatement(sql1, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = preStatement.executeQuery();

			// trỏ đến id lớn nhất => id cuối cùng
			rs.last();
			// lấy cột id là cột đầu tiên
			String id = rs.getString(1);
			// lấy 2 chữ cái đầu trong id, vd: gv022 => gv
			String headOfId = id.substring(0, 2);
			// lấy 3 kí tự sau và đổi thành số rồi cộng 1, vd: gv022 => 23
			int numOfId = Integer.parseInt(id.substring(2, 5)) + 1;

			// trả về id có định dạng gv023
			if (numOfId >= 99)
				return id = headOfId + String.valueOf(numOfId);
			if (numOfId >= 9)
				return id = headOfId + "0" + String.valueOf(numOfId);
			if (numOfId < 9 && numOfId >= 0)
				return id = headOfId + "00" + String.valueOf(numOfId);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "Error...";
	}

	public String getId(String table) {
		cnn = Connect.getConnect();
		try {
			String sql1 = "select * from " + table;
			PreparedStatement preStatement = cnn.prepareStatement(sql1, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = preStatement.executeQuery();
			rs.last();
			String id = rs.getString(1);
			String headOfId = id.substring(0, 2);
			int numOfId = Integer.parseInt(id.substring(2, 5)) + 1;
			if (numOfId >= 99)
				return id = headOfId + String.valueOf(numOfId);
			if (numOfId >= 9)
				return id = headOfId + "0" + String.valueOf(numOfId);
			if (numOfId < 9 && numOfId >= 0)
				return id = headOfId + "00" + String.valueOf(numOfId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "Error...";
	}
}
