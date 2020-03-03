package net.thumbtack.school.concert.server;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


/**
 * В базе данных MySQL создать список некоторых городов - столиц стран.
 * Написать метод, которые загружает этот список, для каждого города выводит страну
 * и национальную валюту используя сервис http://restcountries.eu
 * (например http://restcountries.eu/rest/v2/capital/london ).
 * Написать тесты для этого метода.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(CountryServer.class)
public class TestDownloadCountryServer {


    @Test
    public void testRealDownload() throws IOException {
        CountryServer server = new CountryServer();

        String json = server.downloadJson("london");
        assertTrue(json.contains("United Kingdom"));
        assertTrue(json.startsWith("[{") && json.endsWith("}]"));

        String country = server.getStringValueByJsonName(json, "name");
        assertEquals("\"United Kingdom of Great Britain and Northern Ireland\"", country);
        String currencies = server.getStringValueByJsonName(json, "currencies");
        assertTrue(currencies.contains("name"));
        assertTrue(currencies.startsWith("[{") && currencies.endsWith("}]"));
        String currenciesName = server.getStringValueByJsonName(currencies, "name");
        assertEquals("\"British pound\"", currenciesName);
    }


    @Test
    public void testPMUrl() throws Exception {
        HttpURLConnection http = PowerMockito.mock(HttpURLConnection.class);
        URL url = PowerMockito.mock(URL.class);
        PowerMockito.when(url.openConnection()).thenReturn(http);
        PowerMockito.whenNew(URL.class).withAnyArguments().thenReturn(url);

        new CountryServer().downloadJson("london");

        PowerMockito.verifyNew(URL.class).withArguments("http://restcountries.eu/rest/v2/capital/london");
    }

    @Test
    public void testPMDownloadPage() throws Exception {

        InputStream stream = new ByteArrayInputStream(
                "[{\"name\":\"United Kingdom of Great Britain and Northern Ireland\",\"topLevelDomain\":[\".uk\"],\"alpha2Code\":\"GB\",\"alpha3Code\":\"GBR\",\"callingCodes\":[\"44\"],\"capital\":\"London\",\"altSpellings\":[\"GB\",\"UK\",\"Great Britain\"],\"region\":\"Europe\",\"subregion\":\"Northern Europe\",\"population\":65110000,\"latlng\":[54.0,-2.0],\"demonym\":\"British\",\"area\":242900.0,\"gini\":34.0,\"timezones\":[\"UTC-08:00\",\"UTC-05:00\",\"UTC-04:00\",\"UTC-03:00\",\"UTC-02:00\",\"UTC\",\"UTC+01:00\",\"UTC+02:00\",\"UTC+06:00\"],\"borders\":[\"IRL\"],\"nativeName\":\"United Kingdom\",\"numericCode\":\"826\",\"currencies\":[{\"code\":\"GBP\",\"name\":\"British pound\",\"symbol\":\"£\"}],\"languages\":[{\"iso639_1\":\"en\",\"iso639_2\":\"eng\",\"name\":\"English\",\"nativeName\":\"English\"}],\"translations\":{\"de\":\"Vereinigtes Königreich\",\"es\":\"Reino Unido\",\"fr\":\"Royaume-Uni\",\"ja\":\"イギリス\",\"it\":\"Regno Unito\",\"br\":\"Reino Unido\",\"pt\":\"Reino Unido\",\"nl\":\"Verenigd Koninkrijk\",\"hr\":\"Ujedinjeno Kraljevstvo\",\"fa\":\"بریتانیای کبیر و ایرلند شمالی\"},\"flag\":\"https://restcountries.eu/data/gbr.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\",\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"GBR\"}]"
                        .getBytes()
        );

        HttpURLConnection http = PowerMockito.mock(HttpURLConnection.class);
        PowerMockito.when(http.getContent()).thenReturn(stream);
        URL url = PowerMockito.mock(URL.class);
        PowerMockito.when(url.openConnection()).thenReturn(http);
        PowerMockito.whenNew(URL.class).withAnyArguments().thenReturn(url);

        String result = new CountryServer().downloadJson("london");
        assertEquals("[{\"name\":\"United Kingdom of Great Britain and Northern Ireland\",\"topLevelDomain\":[\".uk\"],\"alpha2Code\":\"GB\",\"alpha3Code\":\"GBR\",\"callingCodes\":[\"44\"],\"capital\":\"London\",\"altSpellings\":[\"GB\",\"UK\",\"Great Britain\"],\"region\":\"Europe\",\"subregion\":\"Northern Europe\",\"population\":65110000,\"latlng\":[54.0,-2.0],\"demonym\":\"British\",\"area\":242900.0,\"gini\":34.0,\"timezones\":[\"UTC-08:00\",\"UTC-05:00\",\"UTC-04:00\",\"UTC-03:00\",\"UTC-02:00\",\"UTC\",\"UTC+01:00\",\"UTC+02:00\",\"UTC+06:00\"],\"borders\":[\"IRL\"],\"nativeName\":\"United Kingdom\",\"numericCode\":\"826\",\"currencies\":[{\"code\":\"GBP\",\"name\":\"British pound\",\"symbol\":\"£\"}],\"languages\":[{\"iso639_1\":\"en\",\"iso639_2\":\"eng\",\"name\":\"English\",\"nativeName\":\"English\"}],\"translations\":{\"de\":\"Vereinigtes Königreich\",\"es\":\"Reino Unido\",\"fr\":\"Royaume-Uni\",\"ja\":\"イギリス\",\"it\":\"Regno Unito\",\"br\":\"Reino Unido\",\"pt\":\"Reino Unido\",\"nl\":\"Verenigd Koninkrijk\",\"hr\":\"Ujedinjeno Kraljevstvo\",\"fa\":\"بریتانیای کبیر و ایرلند شمالی\"},\"flag\":\"https://restcountries.eu/data/gbr.svg\",\"regionalBlocs\":[{\"acronym\":\"EU\",\"name\":\"European Union\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"GBR\"}]", result);
    }

}
