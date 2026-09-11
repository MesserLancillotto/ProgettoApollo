package User.User;

import User.Interfaces.UserViewInterface;

import org.json.JSONObject;

public class UserGUI implements UserViewInterface {
	@Override
	public void paint(JSONObject jsonObject) {
		System.out.println("Paint UserGUI");
	}
}
