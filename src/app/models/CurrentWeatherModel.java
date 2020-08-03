package app.models;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CurrentWeatherModel {
	public String city;
	public String date;

	public String wind;
	public String humidity;
	public String temp;
	public String tempMin;
	public String tempMax;
	public String weatherDescription;

	public String lon;
	public String lat;

	public String iconName;

	public CurrentWeatherModel(JsonObject object) {
		this.city = object.get("name").getAsString();
		JsonObject temp = object.get("main").getAsJsonObject();

		this.temp = Integer.toString(temp.get("temp").getAsInt());
		this.tempMin = Integer.toString(temp.get("temp_min").getAsInt());
		this.tempMax = Integer.toString(temp.get("temp_max").getAsInt());

		this.humidity = Integer.toString(temp.get("humidity").getAsInt());

		JsonObject wind = object.get("wind").getAsJsonObject();

		int windSpeed = (int) (wind.get("speed").getAsFloat() * 3.6);
		this.wind = Integer.toString(windSpeed);

		JsonObject coord = object.get("coord").getAsJsonObject();

		Date date = new Date(object.get("dt").getAsLong() * 1000L);
		SimpleDateFormat formatter = new java.text.SimpleDateFormat("EEE HH:mm");

//		formatter.setTimeZone(TimeZone.getTimeZone(object.get("timezone").getAsString()));

		this.date = formatter.format(date);

		this.lon = Float.toString(coord.get("lon").getAsFloat());
		this.lat = Float.toString(coord.get("lat").getAsFloat());

		JsonObject weather = object.get("weather").getAsJsonArray().get(0).getAsJsonObject();

		this.iconName = weather.get("icon").getAsString();

		String weaterDescription = weather.get("description").getAsString();
		this.weatherDescription = weaterDescription;
	}
}
