package app.cli;

import app.models.CurrentWeatherModel;
import app.utils.CurrentWeatherRequest;
import app.utils.FormatTextInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("Informe uma cidade: (Exemplo: São Paulo, BR)");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String userInput;

		userInput = br.readLine();

		String formattedUserInput = FormatTextInput.format(userInput);

		CurrentWeatherModel currentWeather = new CurrentWeatherModel(CurrentWeatherRequest.request(formattedUserInput));

		System.out.println(currentWeather.city + " - " + currentWeather.date + " - " + currentWeather.weatherDescription);

		System.out.println("Temperatura atual: " + currentWeather.temp + "ºC");
		System.out.println("Temperatura mínima: " + currentWeather.tempMin + "ºC");
		System.out.println("Temperatura máxima: " + currentWeather.tempMax + "ºC");
	}
}
