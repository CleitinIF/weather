package app.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WeekWeatherRequest {
	public static JsonObject request(String lat, String lon) throws IOException {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&units=metric&exclude=current,minute,hourly&APPID=8b3538109fbb0da4554d4d6cf409d6e8";
		HttpGet request = new HttpGet(uri);

		try {
			HttpResponse response = client.execute(request);
			boolean isABadRequest = response.getStatusLine().getStatusCode() == HttpStatus.SC_BAD_REQUEST;

			if (isABadRequest) {
				throw new Error("Algo deu errado, tente novamente mais tarde!");
			}

			BufferedReader rd = new BufferedReader
				(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuilder textView = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				textView.append(line);
			}

			rd.close();

			JsonObject obj = new Gson().fromJson(textView.toString(), JsonObject.class);

			return obj;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new Error("Algo deu errado, tente novamente mais tarde!");
		}
	}
}
