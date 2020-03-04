package net.thumbtack.school.concert.server;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.gson.Gson;

import net.thumbtack.school.concert.dto.response.GetSongsDtoResponse;
import net.thumbtack.school.concert.exception.ServerException;

/**
 * В базе данных MySQL создать список некоторых городов - столиц стран. Написать
 * метод, которые загружает этот список, для каждого города выводит страну и
 * национальную валюту используя сервис http://restcountries.eu (например
 * http://restcountries.eu/rest/v2/capital/london ). Написать тесты для этого
 * метода.
 */

public class TestBotAndCountryServer {

	@Test
	public void testConcertBot() {
		String tockenJson = "{\"token\":\"aeb9610c-6053-4061-bea8-d9282a42ba48\"}";

		GetSongsDtoResponse getSongsDtoResponse = new GetSongsDtoResponse();

		List<GetSongsDtoResponse> songList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			getSongsDtoResponse.setSongName("SongName" + i);
			getSongsDtoResponse.setSongId("songid" + i);
			getSongsDtoResponse.setRating(i % 5 + 1);
			songList.add(getSongsDtoResponse);
		}

		Server server = mock(Server.class);
		when(server.registerUser(anyString())).thenReturn(tockenJson);
		when(server.getSongs(tockenJson)).thenReturn(new Gson().toJson(songList));

		ArgumentCaptor<String> tockenToDelete = ArgumentCaptor.forClass(String.class);

		int songCnt = new BotAndCountryServer(server).runConcertBot();

		verify(server).deleteUser(tockenToDelete.capture());

		assertEquals(tockenJson, tockenToDelete.getValue());
		assertEquals(songList.size(), songCnt);
	}

	@Test
	public void testCountryServer() throws SQLException, IOException, ServerException {
		CountryServer server = mock(CountryServer.class);

		when(server.getCapitalById(0)).thenReturn("london");
		when(server.getCapitalById(1)).thenReturn("berlin");
		when(server.getCapitalById(2)).thenReturn("paris");

		String londonJson = "[{\"name\":\"United Kingdom of Great Britain and Northern Ireland\",\"topLevelDomain\":[\".uk\"],\"alpha2Code\":\"GB\",\"alpha3Code\":\"GBR\",\"callingCodes\":[\"44\"],\"capital\":\"London\",\"altSpellings\":[\"GB\",\"UK\",\"Great Britain\"],\"region\":\"Europe\",\"subregion\":\"Northern Europe\",\"population\":65110000,\"latlng\":[54.0,-2.0],\"demonym\":\"British\",\"area\":242900.0,\"gini\":34.0,\"timezones\":[\"UTC-08:00\",\"UTC-05:00\",\"UTC-04:00\",\"UTC-03:00\",\"UTC-02:00\",\"UTC\",\"UTC+01:00\",\"UTC+02:00\",\"UTC+06:00\"],\"borders\":[\"IRL\"],\"nativeName\":\"United Kingdom\",\"numericCode\":\"826\",\"currencies\":[{\"code\":\"GBP\",\"name\":\"British pound\",\"symbol\":\"£\"}],\"languages\":[{\"iso639_1\":\"en\",\"iso639_2\":\"eng\",\"name\":\"English\",\"nativeName\":\"English\"}],\"translations\":{\"de\":\"Vereinigtes Königreich\",\"es\":\"Reino Unido\",\"fr\":\"Royaume-Uni\",\"ja\":\"イギリス\",\"it\":\"Regno Unito\",\"br\":\"Reino Unido\",\"pt\":\"Reino Unido\",\"nl\":\"Verenigd Koninkrijk\",\"hr\":\"Ujedinjeno Kraljevstvo\",\"fa\":\"بریتانیای کبیر و ایرلند شمالی\"},\"flag\":\"https://restcountries.eu/data/gbr.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\",\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"GBR\"}]";
		String berlinJson = "[{\"name\":\"Germany\",\"topLevelDomain\":[\".de\"],\"alpha2Code\":\"DE\",\"alpha3Code\":\"DEU\",\"callingCodes\":[\"49\"],\"capital\":\"Berlin\",\"altSpellings\":[\"DE\",\"Federal Republic of Germany\",\"Bundesrepublik Deutschland\"],\"region\":\"Europe\",\"subregion\":\"Western Europe\",\"population\":81770900,\"latlng\":[51.0,9.0],\"demonym\":\"German\",\"area\":357114.0,\"gini\":28.3,\"timezones\":[\"UTC+01:00\"],\"borders\":[\"AUT\",\"BEL\",\"CZE\",\"DNK\",\"FRA\",\"LUX\",\"NLD\",\"POL\",\"CHE\"],\"nativeName\":\"Deutschland\",\"numericCode\":\"276\",\"currencies\":[{\"code\":\"EUR\",\"name\":\"Euro\",\"symbol\":\"€\"}],\"languages\":[{\"iso639_1\":\"de\",\"iso639_2\":\"deu\",\"name\":\"German\",\"nativeName\":\"Deutsch\"}],\"translations\":{\"de\":\"Deutschland\",\"es\":\"Alemania\",\"fr\":\"Allemagne\",\"ja\":\"ドイツ\",\"it\":\"Germania\",\"br\":\"Alemanha\",\"pt\":\"Alemanha\",\"nl\":\"Duitsland\",\"hr\":\"Njemačka\",\"fa\":\"آلمان\"},\"flag\":\"https://restcountries.eu/data/deu.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\",\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"GER\"}]";
		String parisJson = "[{\"name\":\"France\",\"topLevelDomain\":[\".fr\"],\"alpha2Code\":\"FR\",\"alpha3Code\":\"FRA\",\"callingCodes\":[\"33\"],\"capital\":\"Paris\",\"altSpellings\":[\"FR\",\"French Republic\",\"République française\"],\"region\":\"Europe\",\"subregion\":\"Western Europe\",\"population\":66710000,\"latlng\":[46.0,2.0],\"demonym\":\"French\",\"area\":640679.0,\"gini\":32.7,\"timezones\":[\"UTC-10:00\",\"UTC-09:30\",\"UTC-09:00\",\"UTC-08:00\",\"UTC-04:00\",\"UTC-03:00\",\"UTC+01:00\",\"UTC+03:00\",\"UTC+04:00\",\"UTC+05:00\",\"UTC+11:00\",\"UTC+12:00\"],\"borders\":[\"AND\",\"BEL\",\"DEU\",\"ITA\",\"LUX\",\"MCO\",\"ESP\",\"CHE\"],\"nativeName\":\"France\",\"numericCode\":\"250\",\"currencies\":[{\"code\":\"EUR\",\"name\":\"Euro\",\"symbol\":\"€\"}],\"languages\":[{\"iso639_1\":\"fr\",\"iso639_2\":\"fra\",\"name\":\"French\",\"nativeName\":\"français\"}],\"translations\":{\"de\":\"Frankreich\",\"es\":\"Francia\",\"fr\":\"France\",\"ja\":\"フランス\",\"it\":\"Francia\",\"br\":\"França\",\"pt\":\"França\",\"nl\":\"Frankrijk\",\"hr\":\"Francuska\",\"fa\":\"فرانسه\"},\"flag\":\"https://restcountries.eu/data/fra.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\",\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"FRA\"}]";

		when(server.downloadJson("london")).thenReturn(londonJson);
		when(server.downloadJson("berlin")).thenReturn(berlinJson);
		when(server.downloadJson("paris")).thenReturn(parisJson);

		when(server.getStringValueByJsonName(londonJson, "name"))
				.thenReturn("\"United Kingdom of Great Britain and Northern Ireland\"");
		when(server.getStringValueByJsonName(berlinJson, "name")).thenReturn("\"Germany\"");
		when(server.getStringValueByJsonName(parisJson, "name")).thenReturn("\"France\"");

		when(server.getCurrenciesName(londonJson)).thenReturn("\"British pound\"");
		when(server.getCurrenciesName(berlinJson)).thenReturn("\"Euro\"");
		when(server.getCurrenciesName(parisJson)).thenReturn("\"Euro\"");

		ArgumentCaptor<String> captorCountry = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> captorCapital = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> captorCurrency = ArgumentCaptor.forClass(String.class);

		new BotAndCountryServer(server).startCountryServer();
		verify(server, times(3)).insertData(captorCountry.capture(), captorCapital.capture(), captorCurrency.capture());

		assertTrue(captorCountry.getAllValues().contains("\"United Kingdom of Great Britain and Northern Ireland\""));
		assertTrue(captorCountry.getAllValues().contains("\"Germany\""));
		assertTrue(captorCountry.getAllValues().contains("\"France\""));

		assertTrue(captorCapital.getAllValues().contains("london"));
		assertTrue(captorCapital.getAllValues().contains("berlin"));
		assertTrue(captorCapital.getAllValues().contains("paris"));

		assertTrue(captorCurrency.getAllValues().contains("\"British pound\""));
		assertTrue(captorCurrency.getAllValues().contains("\"Euro\""));
	}

}
