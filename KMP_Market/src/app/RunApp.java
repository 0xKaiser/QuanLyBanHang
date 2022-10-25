package app;

import java.sql.SQLException;

import com.market.controller.SignInController;
import com.market.view.SignInView;

public class RunApp {

	public static void main(String[] args) {
		try {
			SignInView sv = new SignInView();
			SignInController sc = new SignInController(sv);
			sc.showSignInView();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
