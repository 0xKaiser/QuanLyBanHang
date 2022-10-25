package com.market.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.market.jdbc.Connect;

public class ProductServices {

	public ProductServices() {
	}

	public boolean checkProAvailable(String idProduct, int Quantity) {
		try {
			Connection cnn = Connect.getConnect();
			String sql = "select * from virtual.v_showAllProduct " + "where idProduct = '" + idProduct + "'";
			PreparedStatement ps;
			ps = cnn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return (rs.getInt("quantityOnHand") > Quantity);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
