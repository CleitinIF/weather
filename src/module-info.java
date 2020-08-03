module Weather {
	requires javafx.fxml;
	requires javafx.controls;
	requires httpclient;
	requires httpcore;
	requires com.google.gson;
	requires junit;

	opens app.ui;

	exports tests;
}