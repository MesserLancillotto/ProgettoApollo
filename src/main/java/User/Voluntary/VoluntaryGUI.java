package User.Voluntary;

import User.Interfaces.UserViewInterface;

import org.json.JSONObject;

public class VoluntaryGUI implements UserViewInterface {
	@Override
	public void paint(JSONObject jsonObject) {
		System.out.println("Paint VoluntaryGUI");
	}
}
