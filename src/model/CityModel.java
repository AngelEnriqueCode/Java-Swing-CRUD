package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import entity.City;


public class CityModel {
	
	/**
	 * Method to find all record present in the DDBB. 
	 * It creates a new City object for each record and store it the information.
	 * @return an ArrayList of City objects
	 */
	public List<City> findAll() {
		List<City> cities = new ArrayList<City>();
		try {
			PreparedStatement preparedStatement = ConnectToDB.getConnection().prepareStatement("SELECT * FROM CITY");
			
			//Store the query into a resulset for moving along it
			ResultSet resulSet = preparedStatement.executeQuery();
			while(resulSet.next()) {
				City city = new City();
				city.setId(resulSet.getString("id"));
				city.setName(resulSet.getString("name"));
				city.setText(resulSet.getString("text"));
				city.setFavourite(resulSet.getString("favourite"));
				cities.add(city);
			}
		} catch (Exception e) {
			cities = null;
		}
		return cities;
	}
	
	/**
	 * Method to fin just a particular record in the DDBB. It receives an ID as input parameter.
	 * @param _id
	 * @return a City object
	 */
	public City findOne(String _id) {
		City city = null;
		
		try {
			PreparedStatement preparedStatement = ConnectToDB.getConnection().prepareStatement("SELECT * FROM CITY WHERE id = ?");
			preparedStatement.setString(1, _id);
			
			//Store the query into a resulset for moving along it
			ResultSet resulSet = preparedStatement.executeQuery();
			city = new City();
			city.setId(resulSet.getString("id"));
			city.setName(resulSet.getString("name"));
			city.setText(resulSet.getString("text"));
			city.setFavourite(resulSet.getString("favourite"));

		} catch (Exception e) {
			return city;
		}
		return city;
	}
	
	/**
	 * MEthod to add a new record into the DDBB. It receives a City object.
	 * @param city
	 * @return String as all is okay or not.
	 */
	public String Add(City city) {
		try {
			String sql = "INSERT INTO CITY (id, name , text, favourite) VALUES (?, ? , ? , ?)";
	        PreparedStatement pstmt =  ConnectToDB.getConnection().prepareStatement(sql);
	        pstmt.setString(1, city.getId());
	        pstmt.setString(2, city.getName());
	        pstmt.setString(3, city.getText());
	        pstmt.setString(4, city.getFavourite());
	        pstmt.executeUpdate();

		} catch (Exception e) {
			return "Problem in Add" + e;
		}
		return "Record added succesfully";
	}
	
	/**
	 * This method removes a record using an ID as input parameter
	 * @param _id
	 * @return an String as all is okay or not.
	 */
	public String Delete(String _id) {
		try {
			
			String sql = "DELETE FROM CITY WHERE id = ?";
	        PreparedStatement pstmt =  ConnectToDB.getConnection().prepareStatement(sql);
	        pstmt.setString(1, _id);
	        pstmt.executeUpdate();
		} catch (Exception e) {
			return "Problem in Delete" + e;
		}
		return "Delete succesfully";
	}
	
	/**
	 * Update the record using an ID as input
	 * @param _id
	 * @return an String as all is okay or not.
	 */
	public String Favourite(String _id) {
		try {
			String sql = "UPDATE CITY SET favourite = 'Y' WHERE id = ?";
	        PreparedStatement pstmt =  ConnectToDB.getConnection().prepareStatement(sql);
	        pstmt.setString(1, _id);
	        pstmt.executeUpdate();
		} catch (Exception e) {
			return "Problem in Update" + e;
		}
		return "Update succesfully";
	}
}
