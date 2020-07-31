package sample;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Controller {
	public Button btnSearch;
	public TextField tfCity;

	public Label lblCity;
	public Label lblTemp;
	public Label lblTempMin;
	public Label lblTempMax;

	public void apiRequest(String city) throws IOException {
		String uri = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&APPID=8b3538109fbb0da4554d4d6cf409d6e8";
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(uri);
		HttpResponse response = client.execute(request);

		try {
			BufferedReader rd = new BufferedReader
				(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuilder textView = new StringBuilder();
			String line = null;
			while ((line = rd.readLine()) != null) {
				textView.append(line);
			}

			JsonObject obj = new Gson().fromJson(textView.toString(), JsonObject.class);

//			System.out.println(obj.get("coord").getAsString());

			String cityName = obj.get("name").getAsString();
			JsonObject main = obj.get("main").getAsJsonObject();

			lblCity.setText(cityName);
			lblTemp.setText(main.get("temp").getAsString());
			lblTempMin.setText(main.get("temp_min").getAsString());
			lblTempMax.setText(main.get("temp_max").getAsString());


			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}



//		System.out.println(textView);
	}

	public void handleSearch() throws IOException {
		this.apiRequest(tfCity.getText());
	}
}
