package dash.icp.tests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ibm.ra.util.DBConnection;


/**
 * Test access to dashDB deployed to ICP
 * @author jeromeboyer
 *
 */
public class ConnectDashDb {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testDasjDB() throws SQLException {
		DBConnection dash = new DBConnection();
		Connection conn=dash.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMER WHERE id = 2300");
		while (rs.next()) {
			Assert.assertTrue("2300".equals(rs.getString("ID")));
			Assert.assertTrue(18==rs.getInt("AGE"));
			System.out.println(rs.getString("ID")+" "+ rs.getString("USAGE")+" "+rs.getInt("AGE"));
		}
		
	}

}
