package com.sample.jersey.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/create")
public class RestCreateApi {

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
    public void createBlog(String strRequest) throws Exception {
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "CREATE TABLE BLOG " +
	                   "(POST_ID INT PRIMARY KEY     NOT NULL," +
	                   " BODY           TEXT    NOT NULL, " + 
	                   " TITLE        CHAR(50)) " ;
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    }
	    System.out.println("Table created successfully>>>>>>>>>>>>>>>>>>");
		
     }
    
   

}
