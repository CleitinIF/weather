package app.utils;

import javafx.scene.control.Alert;

public class CallAlert {
	Alert alert;

	public CallAlert() {
		this.alert = new Alert(Alert.AlertType.ERROR);
	}

	private void run(String message) {
		this.alert.setTitle("ERRO");
		this.alert.setHeaderText(null);
		this.alert.setContentText(message);
		this.alert.showAndWait();
	}
}
