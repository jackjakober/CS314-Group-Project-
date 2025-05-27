/* pom.xml must have necessary dependency */
package com.tco.misc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Exception;

public final class sqlGuide {
    
    private final static String TABLE = "world INNER JOIN region ON world.iso_region = region.id INNER JOIN country ON world.iso_country = country.id";
    private final static String COLUMNS =  "world.id,world.name,world.municipality,region.name,country.name,world.continent,world.latitude,world.longitude,world.altitude";
    private final static String COLUMN_LABELS =  "id,name,municipality,region,country,continent,latitude,longitude,altitude";


    public static ArrayList<HashMap<String,String>> getPlaces(String match, Long limit)
    {
        try 
        { 
            return SQL_DataBase.places(match,limit); 
        }
        catch (Exception e) 
        { 
            return new ArrayList<HashMap<String,String>>();
        }
    }

    public static Long getFound(String match)
    {
        try 
        { 
            return SQL_DataBase.found(match);
        }
        catch (Exception e) 
        { 
            return 0L; 
        }
    }

    static class SQL_DataBase {
        
        static ArrayList<HashMap<String,String>> places(String match, Long limit) throws Exception
        {
            String sql      = Select.match(match, limit);
			String url      = Credential.url();
			String user     = Credential.USER;
			String password = Credential.PASSWORD;
			try (
				// connect to the database and query
				Connection conn    = DriverManager.getConnection(url, user, password);
				Statement  query   = conn.createStatement();
				ResultSet  results = query.executeQuery(sql)
			) {
				return convertQueryResultsToPlaces(results, COLUMNS, COLUMN_LABELS);
			} catch (Exception e) {
				throw e;
			}
        }
        
        static Long found(String match) throws Exception {
			String sql = Select.found(match);
			try (
				// connect to the database and query
				Connection conn = DriverManager.getConnection(Credential.url(), 
                    Credential.USER, Credential.PASSWORD);
				Statement query = conn.createStatement();
				ResultSet results = query.executeQuery(sql)
			) {
				return count(results);
			} catch (Exception e) {
				throw e;
			}
		}

        private static Long count(ResultSet res)throws Exception{
            if(res.next()){
                return res.getLong("count");
            }
            throw new Exception("No count results in found query");
        }
        
        private static ArrayList<HashMap<String,String>> convertQueryResultsToPlaces(ResultSet results, String columns, String column_labels) throws Exception
        {
            int count = 0;
            String[] cols = columns.split(",");
            String[] col_labels = column_labels.split(",");
            ArrayList<HashMap<String,String>> places = new ArrayList<HashMap<String,String>>();
            while (results.next())
            {
                HashMap<String,String> place = new HashMap<String,String>();
                for (int i = 0; i < cols.length; i++)
                {
                    place.put(col_labels[i],results.getString(cols[i]));
                }
                place.put("index", String.format("%d", ++count));
                places.add(place);
            }
            return places;
        }    
    }

    static class Credential {
        final static String USER = "cs314-db";
        final static String PASSWORD = "eiK5liet1uej";
        static String URL = "jdbc:mariadb://127.0.0.1:56247/cs314";

        static String url(){

            String useTunnel = System.getenv("CS314_USE_DATABASE_TUNNEL");

            // When using Locally (by Port Forwarding)
            if(useTunnel != null && useTunnel.equals("true")) {
                return "jdbc:mariadb://127.0.0.1:56247/cs314";
            }
            // Else, we must be running against the production database directly
            else {
                return "jdbc:mariadb://faure.cs.colostate.edu/cs314";
            }

        }
        
    }

    static class Select 
    {
        static String match(String match, long limit) {
			      return statement(match, "DISTINCT " + COLUMNS, "LIMIT " + limit);
		    }
            
        static String found(String match) {
			return statement(match, "COUNT(*) AS count ", "");
		}


        static String statement(String match, String data, String limit)
        {
            return "SELECT "
            + data
            + " FROM " + TABLE
            +" WHERE world.name LIKE \"%" + match + "%\" "
            +"OR world.id LIKE \"%" + match + "%\" "
            +"OR world.municipality LIKE \"%" + match + "%\" "
            +"OR world.continent LIKE \"%" + match + "%\" "
            +"OR country.name LIKE \"%" + match + "%\" "
            +"OR region.name LIKE \"%" + match + "%\" "
            + limit+ " ;";
        }
    }   
}
