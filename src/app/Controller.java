package app;

import app.models.CurrentWeatherModel;
import app.models.WeekWeatherModel;
import app.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private WeekWeatherModel weekWeather;

	private void callAlert(String message) {
		alert.setTitle("ERRO");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void currentWeatherRequest(String city) {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&lang=pt_br&units=metric&APPID=8b3538109fbb0da4554d4d6cf409d6e8";
		HttpGet request = new HttpGet(uri);

		try {
			HttpResponse response = client.execute(request);
			boolean isABadRequest = response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND;

			if(isABadRequest) {
				this.callAlert("Cidade não encontrada!");
				pnInfo.setVisible(false);
				throw new IOException();
			}

			BufferedReader rd = new BufferedReader
				(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuilder textView = new StringBuilder();
			String line = null;
			while ((line = rd.readLine()) != null) {
				textView.append(line);
			}

			rd.close();

			JsonObject obj = new Gson().fromJson(textView.toString(), JsonObject.class);

			currentWeather = new CurrentWeatherModel(obj);

			this.lblCity.setText(currentWeather.city);
			this.lblTemp.setText(currentWeather.temp);
			this.lblDay.setText(currentWeather.date);
			this.lblWind.setText(currentWeather.wind + " km/h");
			this.lblHumidity.setText(currentWeather.humidity + "%");

			Image image = new Image(getClass().getResourceAsStream("icons/"+ currentWeather.iconName +"@2x.png"));
			imgMainIcon.setImage(image);

			this.lblDescription.setText(currentWeather.weatherDescription);

			this.weekWeatherRequest();

			pnInfo.setVisible(true);

		} catch (IOException err) {

		}
	}

	private void weekWeatherRequest() {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://api.openweathermap.org/data/2.5/onecall?lat="+ currentWeather.lat+ "&lon=" + currentWeather.lon +"&units=metric&exclude=current,minute,hourly&APPID=8b3538109fbb0da4554d4d6cf409d6e8";
		HttpGet request = new HttpGet(uri);

		try {
			HttpResponse response = client.execute(request);
			boolean isABadRequest = response.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST;

			if (isABadRequest) {
				callAlert("Algo deu errado, tente novamente mais tarde!");
				throw new IOException();
			}

			BufferedReader rd = new BufferedReader
				(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuilder textView = new StringBuilder();
			String line = null;
			while ((line = rd.readLine()) != null) {
				textView.append(line);
			}

			rd.close();

			JsonObject obj = new Gson().fromJson(textView.toString(), JsonObject.class);

			WeekWeatherModel weekWeather = new WeekWeatherModel(obj);


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

		}
	}

	private String formatTextInput(String text) {
		StringUtils stringUtils = new StringUtils();

		text = stringUtils.removeSpecialCharacters(text);
		text = stringUtils.convertSpaces(text);
		text = stringUtils.replaceComma(text);

		return text;
	}

	public void handleSearch() {
		String text = tfCity.getText();

		if(text.isBlank()) {
			callAlert("O campo não pode ficar em branco!");
			tfCity.setText("");
			return;
		}

		String formattedText = formatTextInput(text);

		this.currentWeatherRequest(formattedText);
	}

	public void handleKeyPress(KeyEvent e) {
		if(e.getCode() == KeyCode.ENTER) this.handleSearch();
	}
}
