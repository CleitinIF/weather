package app.ui;

import app.models.CurrentWeatherModel;
import app.models.WeekWeatherModel;
import app.utils.CurrentWeatherRequest;
import app.utils.StringUtils;
import app.utils.WeekWeatherRequest;
import com.google.gson.JsonObject;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Controller {
	public Button btnSearch;
	public TextField tfCity;

	public Pane pnInfo;
	public Label lblCity;
	public Label lblDay;
	public Label lblTemp;
	public Label lblWind;
	public Label lblHumidity;
	public ImageView imgMainIcon;
	public Label lblDescription;

	public Label lblWDay0;
	public Label lblWDay1;
	public Label lblWDay2;
	public Label lblWDay3;
	public Label lblWDay4;
	public Label lblWDay5;

	public ImageView imgWIcon0;
	public ImageView imgWIcon1;
	public ImageView imgWIcon2;
	public ImageView imgWIcon3;
	public ImageView imgWIcon4;
	public ImageView imgWIcon5;

	public Label lblWTempMin0;
	public Label lblWTempMin1;
	public Label lblWTempMin2;
	public Label lblWTempMin3;
	public Label lblWTempMin4;
	public Label lblWTempMin5;

	public Label lblWTempMax0;
	public Label lblWTempMax1;
	public Label lblWTempMax2;
	public Label lblWTempMax3;
	public Label lblWTempMax4;
	public Label lblWTempMax5;

	private Alert alert = new Alert(Alert.AlertType.ERROR);

	private CurrentWeatherModel currentWeather;

	private void callAlert(String message) {
		alert.setTitle("ERRO");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void currentWeatherRender(String city) {
		CurrentWeatherModel currentWeather;
		try {
			currentWeather = new CurrentWeatherModel(CurrentWeatherRequest.request(city));

			this.lblCity.setText(currentWeather.city);
			this.lblTemp.setText(currentWeather.temp);
			this.lblDay.setText(currentWeather.date);
			this.lblWind.setText(currentWeather.wind + " km/h");
			this.lblHumidity.setText(currentWeather.humidity + "%");

			Image image = new Image(getClass().getResourceAsStream("icons/"+ currentWeather.iconName +"@2x.png"));
			imgMainIcon.setImage(image);

			this.lblDescription.setText(currentWeather.weatherDescription);

			this.currentWeather = currentWeather;

			this.weekWeatherRender();

			pnInfo.setVisible(true);
		} catch (Error error) {
			callAlert(error.getMessage());
			this.pnInfo.setVisible(false);
		}
	}

	private void weekWeatherRender() {
		WeekWeatherModel weekWeather;
		try {
			weekWeather = new WeekWeatherModel(WeekWeatherRequest.request(this.currentWeather.lat, this.currentWeather.lon));
			SimpleDateFormat formatter = new java.text.SimpleDateFormat("EEE");
			Date date;


			JsonObject week0 = weekWeather.days.get(1).getAsJsonObject();
			date = new Date(week0.get("dt").getAsLong() * 1000L);

			this.lblWDay0.setText(formatter.format(date));
			this.imgWIcon0.setImage(new Image(getClass().getResourceAsStream("icons/"+ week0.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString() +"@2x.png")));
			this.lblWTempMax0.setText(week0.get("temp").getAsJsonObject().get("max").getAsInt() + "º");
			this.lblWTempMin0.setText(week0.get("temp").getAsJsonObject().get("min").getAsInt() + "º");

			JsonObject week1 = weekWeather.days.get(2).getAsJsonObject();
			date = new Date(week1.get("dt").getAsLong() * 1000L);

			this.lblWDay1.setText(formatter.format(date));
			this.imgWIcon1.setImage(new Image(getClass().getResourceAsStream("icons/"+ week1.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString() +"@2x.png")));
			this.lblWTempMax1.setText(week1.get("temp").getAsJsonObject().get("max").getAsInt() + "º");
			this.lblWTempMin1.setText(week1.get("temp").getAsJsonObject().get("min").getAsInt() + "º");

			JsonObject week2 = weekWeather.days.get(3).getAsJsonObject();
			date = new Date(week2.get("dt").getAsLong() * 1000L);

			this.lblWDay2.setText(formatter.format(date));
			this.imgWIcon2.setImage(new Image(getClass().getResourceAsStream("icons/"+ week2.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString() +"@2x.png")));
			this.lblWTempMax2.setText(week2.get("temp").getAsJsonObject().get("max").getAsInt() + "º");
			this.lblWTempMin2.setText(week2.get("temp").getAsJsonObject().get("min").getAsInt() + "º");

			JsonObject week3 = weekWeather.days.get(4).getAsJsonObject();
			date = new Date(week3.get("dt").getAsLong() * 1000L);

			this.lblWDay3.setText(formatter.format(date));
			this.imgWIcon3.setImage(new Image(getClass().getResourceAsStream("icons/"+ week3.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString() +"@2x.png")));
			this.lblWTempMax3.setText(week3.get("temp").getAsJsonObject().get("max").getAsInt() + "º");
			this.lblWTempMin3.setText(week3.get("temp").getAsJsonObject().get("min").getAsInt() + "º");

			JsonObject week4 = weekWeather.days.get(5).getAsJsonObject();
			date = new Date(week4.get("dt").getAsLong() * 1000L);

			this.lblWDay4.setText(formatter.format(date));
			this.imgWIcon4.setImage(new Image(getClass().getResourceAsStream("icons/"+ week4.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString() +"@2x.png")));
			this.lblWTempMax4.setText(week4.get("temp").getAsJsonObject().get("max").getAsInt() + "º");
			this.lblWTempMin4.setText(week4.get("temp").getAsJsonObject().get("min").getAsInt() + "º");

			JsonObject week5 = weekWeather.days.get(6).getAsJsonObject();
			date = new Date(week5.get("dt").getAsLong() * 1000L);

			this.lblWDay5.setText(formatter.format(date));
			this.imgWIcon5.setImage(new Image(getClass().getResourceAsStream("icons/"+ week5.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString() +"@2x.png")));
			this.lblWTempMax5.setText(week5.get("temp").getAsJsonObject().get("max").getAsInt() + "º");
			this.lblWTempMin5.setText(week5.get("temp").getAsJsonObject().get("min").getAsInt() + "º");
		} catch (IOException err) {
			callAlert("Algum erro aconteceu, tente novamente mais tarde");
			this.pnInfo.setVisible(false);
		}
	}

	private String formatTextInput(String text) {
		text = StringUtils.removeSpecialCharacters(text);
		text = StringUtils.convertSpaces(text);
		text = StringUtils.replaceComma(text);

		return text;
	}

	public void handleSearch() {
		String text = tfCity.getText();

		if(text.isEmpty()) {
			callAlert("O campo não pode ficar em branco!");
			tfCity.setText("");
			pnInfo.setVisible(false);
			return;
		}

		String formattedText = formatTextInput(text);

		this.currentWeatherRender(formattedText);
	}

	public void handleKeyPress(KeyEvent e) {
		if(e.getCode() == KeyCode.ENTER) this.handleSearch();
	}
}
