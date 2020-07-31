package app;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Controller {
	public Button btnSearch;
	public TextField tfCity;

	public AnchorPane anInfo;

	public Label lblCity;
	public Label lblTemp;
	public Label lblTempMin;
	public Label lblTempMax;
	public Label lblWind;
	public Label lblHumidity;

	public ProgressIndicator piLoading;

	private Alert alert = new Alert(Alert.AlertType.ERROR);

	public void apiRequest(String city) {
		piLoading.setVisible(true);
		String uri = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&APPID=8b3538109fbb0da4554d4d6cf409d6e8";
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(uri);

		try {
			HttpResponse response = client.execute(request);

			boolean isABadRequest = response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND;

			if(isABadRequest) {
				alert.setTitle("ERRO");
				alert.setHeaderText(null);
				alert.setContentText("Cidade não encontrada!");
				alert.showAndWait();

				anInfo.setVisible(false);

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

			String cityName = obj.get("name").getAsString();
			JsonObject temp = obj.get("main").getAsJsonObject();
			Float wind = obj.get("wind").getAsJsonObject().get("speed").getAsFloat();

			lblCity.setText(cityName);
			lblTemp.setText(temp.get("temp").getAsInt() + "ºC");
			lblTempMin.setText(temp.get("temp_min").getAsInt() + "ºC");
			lblTempMax.setText(temp.get("temp_max").getAsInt()  + "ºC");
			lblHumidity.setText(temp.get("humidity").getAsString() + "%");
			lblWind.setText((int) (wind*3.6) + " km/h");

			anInfo.setVisible(true);
		} catch (IOException e) {
		}
		piLoading.setVisible(false);
	}

	private String formatTextInput(String text) {
		StringUtils stringUtils = new StringUtils();

		text = stringUtils.RemoveSpecialCharacter(text);
		text = stringUtils.convertSpaces(text);
		text = stringUtils.replaceComma(text);

		return text;
	}

	public void handleSearch() {
		piLoading.setProgress(-1);

		String text = tfCity.getText();

		if(text.isBlank()) {
			alert.setTitle("ERRO");
			alert.setHeaderText(null);
			alert.setContentText("O campo não pode ficar em branco!");
			alert.showAndWait();
			tfCity.setText("");
			return;
		}
		String formattedText = formatTextInput(text);

		this.apiRequest(formattedText);
	}

	public void handleKeyPress(KeyEvent e) {
		if(e.getCode() == KeyCode.ENTER) this.handleSearch();
	}
}
