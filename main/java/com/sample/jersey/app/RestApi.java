package com.sample.jersey.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

@Path("/post")
public class RestApi {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    public void postBlog(String strRequest) throws Exception {
		System.out.println("Method postBlog>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		Connection c = null;
	    Statement stmt = null;
	    try {
	      ObjectMapper mapper = new ObjectMapper();
		  BlogRequest requestObj = mapper.readValue(strRequest, BlogRequest.class);	
		  System.out.println(requestObj.getTitle()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "INSERT INTO BLOG (POST_ID,TITLE,BODY) " +
	                   "VALUES ("+Integer.parseInt(requestObj.getPid())+",'"+requestObj.getTitle()+"', '"+requestObj.getBody()+"');"; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    }
	    System.out.println("Records inserted successfully>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
     }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public List<Blog> getBlog(String strRequest) throws Exception {
    	BlogResponse blogResponse=new BlogResponse();	
    	List<Blog> blogs=new ArrayList<Blog>();
    	 Connection c = null;
    	    Statement stmt = null;
    	    try {
    	      Class.forName("org.sqlite.JDBC");
    	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
    	      c.setAutoCommit(false);
    	      System.out.println("Opened database successfully");

    	      stmt = c.createStatement();
    	      ResultSet rs = stmt.executeQuery( "SELECT * FROM BLOG;" );
    	      while ( rs.next() ) {
    	    	  Blog blog=new Blog();
    	         int id = rs.getInt("POST_ID");
    	         String  title = rs.getString("TITLE");
    	         String  body  = rs.getString("BODY");
    	         blog.setId(id+"");
    	         blog.setTitle(title);
    	         blog.setBody(body);
    	         blogs.add(blog);
    	         System.out.println( "POST_ID = " + id );
    	         System.out.println( "title = " + title );
    	         System.out.println( "body = " + body );
    	      }
    	      rs.close();
    	      stmt.close();
    	      c.close();
    	    } catch ( Exception e ) {
    	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    	    }
    	    System.out.println("Operation done successfully");
    	    System.out.println(blogs);
    	    blogResponse.setBlogs(blogs);
    	    return blogs;
     }

  

}
