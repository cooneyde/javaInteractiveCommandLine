package dbService;

import model.Server;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.*;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class DBServiceTest {

    @InjectMocks
    DBService dbService;


    @Mock
    Connection mockConn;

    @Mock
    Statement mockStatement;

    @Mock
    ResultSet mockResultSet;

    @Before
    public void setUp() throws SQLException {

       // when(dbConnection.createConnection()).thenReturn(mockConn);
        when(mockConn.createStatement()).thenReturn(mockStatement);

        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("ID")).thenReturn(1).thenReturn(2);

        when(mockResultSet.getString("NAME")).thenReturn("table_r3").thenReturn("table_r1");

        when(mockStatement.executeQuery("SELECT * FROM test")).thenReturn(mockResultSet);
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getServerList() {
        DBService dbService = new DBService();

        try {
           List<Server> result = dbService.getServerList();
           System.out.println(result.get(0).name);
           assertTrue(result instanceof List);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}