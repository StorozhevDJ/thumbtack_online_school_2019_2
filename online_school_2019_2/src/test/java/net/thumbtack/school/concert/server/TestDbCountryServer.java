package net.thumbtack.school.concert.server;


import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import net.thumbtack.school.database.jdbc.JdbcUtils;


/**
 * В базе данных MySQL создать список некоторых городов - столиц стран.
 * Написать метод, которые загружает этот список, для каждого города выводит страну
 * и национальную валюту используя сервис http://restcountries.eu
 * (например http://restcountries.eu/rest/v2/capital/london ).
 * Написать тесты для этого метода.
 */


@RunWith(PowerMockRunner.class)
@PrepareForTest(JdbcUtils.class)
public class TestDbCountryServer {

    private static Connection spyConnection;

    @BeforeClass()
    public static void setUp() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        JdbcUtils.createConnection();
        Connection connection = JdbcUtils.getConnection();
        spyConnection = spy(connection);
        PowerMockito.mockStatic(JdbcUtils.class);
        when(JdbcUtils.getConnection()).thenReturn(spyConnection);
    }

    @Test
    public void testSave() throws SQLException {
        reset(spyConnection);
        CountryServer.insertData("country", "capital", "currenciesName");
        verify(spyConnection, never()).createStatement();
        verify(spyConnection).prepareStatement(anyString(), anyInt());

        try {
            CountryServer.insertData(null, "capital", "currenciesName");
            fail();
        } catch (Exception e) {

        }

        reset(spyConnection);
        CountryServer.deleteData();
        verify(spyConnection, never()).createStatement();
        verify(spyConnection).prepareStatement(anyString());
    }


    @Test
    public void testGetCapitalById() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getString("capital")).thenReturn("london");

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeQuery()).thenReturn(resultSet);

        Connection jdbcConnection = mock(Connection.class);
        when(jdbcConnection.prepareStatement(anyString())).thenReturn(statement);

        mockStatic(JdbcUtils.class);
        when(JdbcUtils.getConnection()).thenReturn(jdbcConnection);

        when(resultSet.next()).thenReturn(true).thenReturn(false);

        String capital = CountryServer.getCapitalById(0);
        assertEquals("london", capital);

        verify(jdbcConnection, times(0)).createStatement();
        verify(jdbcConnection, times(1)).prepareStatement(anyString());
    }

    @Test
    public void testStartServer() {
        //ToDo test
    }

}
