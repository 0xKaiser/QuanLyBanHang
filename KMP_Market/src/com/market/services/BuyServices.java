package com.market.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.market.jdbc.Connect;

public class BuyServices {
	String idBill;
	long totalPrice = 0;

	Auto_Increase_Id aii = new Auto_Increase_Id();
	ProductServices proServices = new ProductServices();

	public BuyServices() {
	}

	public boolean checkBillCreatSuccess(String idCus) {
		if (createNewBill(idCus))
			if (replaceCartToBillDetails(idCus))
				if (upTotalPriceToBill())
					if (deleteCart(idCus))
						return true;
		return false;
	}

	private boolean createNewBill(String idCus) {
		this.idBill = aii.getId("sales.Bill");
		try {
			if (idBill.compareTo("Error...") != 0) {
				Connection cnn = Connect.getConnect();
				String sql = "INSERT INTO sales.Bill(idBill,idCustomer,totalPrice,stt) values(?,?,?,?)";
				PreparedStatement ps = cnn.prepareStatement(sql);
				ps.setString(1, this.idBill);
				ps.setString(2, idCus);
				ps.setInt(3, 0);
				ps.setString(4, "0");
				int insertBill = ps.executeUpdate();
				if (insertBill > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean replaceCartToBillDetails(String idCus) {
		try {
			Connection cnn = Connect.getConnect();
			String sql1 = "select * from virtual.v_showCart where idCustomer = '" + idCus + "'";
			PreparedStatement ps1 = cnn.prepareStatement(sql1);
			ResultSet rs = ps1.executeQuery();
			while (rs.next()) {

				String idProduct = rs.getString("idProduct");
				int quantity = rs.getInt("quantity");
				long totalPrice = rs.getLong("price");

				if (proServices.checkProAvailable(idProduct, quantity)) {
					if (createNewBillDetails(idProduct, quantity, totalPrice)) {
						if (!(upNewQuantityInProduct(idProduct, quantity)))
							return false;
					} else
						return false;
				} else
					System.out.println("het hang");
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private boolean upNewQuantityInProduct(String idProduct, int quantity) {
		try {
			String upTotalPrice = "update production.Product set quantityOnHand = quantityOnHand - " + quantity
					+ " where idProduct = '" + idProduct + "'";
			Connection cnn = Connect.getConnect();
			PreparedStatement ps = cnn.prepareStatement(upTotalPrice);
			int upQuantity = ps.executeUpdate();
			if (upQuantity > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean createNewBillDetails(String idProduct, int quantity, long price) {
		String idBillDetails = aii.getId("sales.BillDetails");
		try {
			Connection cnn = Connect.getConnect();
			String sql = "INSERT INTO sales.BillDetails(idBillDetails,idBill,idProduct,quantity,current_price,price) "
					+ "values(?,?,?,?,?,?)";
			PreparedStatement ps = cnn.prepareStatement(sql);
			ps.setString(1, idBillDetails);
			ps.setString(2, this.idBill);
			ps.setString(3, idProduct);
			ps.setInt(4, quantity);
			ps.setLong(5, price);
			ps.setLong(6, price * quantity);
			int insertBill = ps.executeUpdate();
			if (insertBill > 0) {
				this.totalPrice += price * quantity;
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean upTotalPriceToBill() {
		try {
			String upTotalPrice = "update sales.Bill set totalPrice = '" + this.totalPrice + "' where idBill = '"
					+ this.idBill + "'";
			Connection cnn = Connect.getConnect();
			PreparedStatement ps = cnn.prepareStatement(upTotalPrice);
			int upTP = ps.executeUpdate();
			if (upTP > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean deleteCart(String idCus) {
		try {
			Connection cnn = Connect.getConnect();
			String sqlDelete = "delete sales.Cart where idCustomer = '" + idCus + "'";
			PreparedStatement ps = cnn.prepareStatement(sqlDelete);
			int delete = ps.executeUpdate();
			if (delete > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
