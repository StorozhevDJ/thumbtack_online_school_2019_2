package net.thumbtack.school.concert.server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.thumbtack.school.concert.dto.response.GetSongsDtoResponse;

/**
 * В базе данных MySQL создать список некоторых городов - столиц стран. Написать
 * метод, которые загружает этот список, для каждого города выводит страну и
 * национальную валюту используя сервис http://restcountries.eu (например
 * http://restcountries.eu/rest/v2/capital/london ). Написать тесты для этого
 * метода.
 */

public class BotAndCountryServer {

	CountryServer cs = new CountryServer();

	Server server = new Server();

	public BotAndCountryServer(CountryServer cs) {
		this.cs = cs;
	}

	public BotAndCountryServer(Server server) {
		this.server = server;
	}

	public void startCountryServer() throws SQLException, IOException {
		for (int i = 0; i < 3; i++) {
			String capital = cs.getCapitalById(i);
			String json = cs.downloadJson(capital);
			String countryName = cs.getStringValueByJsonName(json, "name");
			String currenciesName = cs.getCurrenciesName(json);
			cs.insertData(countryName, capital, currenciesName);
		}
	}


	public int runConcertBot() {
		String tockenJson = server.registerUser("{\"firstName\":\"fname\", \"lastName\":\"lname\", \"login\":\"login\", \"password\":\"pswd\"}");
		String songsJson = server.getSongs(tockenJson);//Get all songs
		server.deleteUser(tockenJson);
		List<GetSongsDtoResponse> r = new Gson().fromJson(songsJson, new TypeToken<List<GetSongsDtoResponse>>() {
		}.getType());
		return r.size();
	}

}
