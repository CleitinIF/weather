package app.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class WeekWeatherModel {
	public JsonArray days;

	public WeekWeatherModel(JsonObject object) {
		this.days = object.get("daily").getAsJsonArray();
	}
}
