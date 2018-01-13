import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Driver {
	
	public final static int MAX_DISTANCE = 100;		//max distance from Intercom offices which we will invite customers
	public final static int EARTH_RADIUS = 6371;	//Earth radius in km
	//co-ordinates of Intercom offices 
	public final static double OFFICE_LAT = 53.339428;		
	public final static double OFFICE_LONG = -6.257664;
	
	public static void main(String[] args) {
		JSONArray customers = readInput();
		ArrayList<Integer> idArrayList = new ArrayList<Integer>();	//list to store IDs of invited customers
		HashMap<Integer, String> customerMap = new HashMap<Integer, String>();	//map to store IDs paired with names of invited customers
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iter = customers.iterator();
		while(iter.hasNext())
		{
			//must assign customer to variable to only use iter.next() once per iteration
			JSONObject customer1 = iter.next();	
			double distance = calculateDistance(customer1);
			if(distance <= MAX_DISTANCE)
			{
				long temp = (long) customer1.get("user_id");
				Integer id = (int) (long) temp;
				String name = (String) customer1.get("name");
				customerMap.put(id, name);	//insert ID and name into map
				idArrayList.add(id);	//add invited customer ID to List
			}
		}
		System.out.println(inviteesAscending(customerMap, idArrayList));
	}
	
	/*
	Description : Reads input from JSON file. Takes in everything into BufferedReader, 
		seperates each line, creates an object from each line, adds each object to JSONArray
	Return : array of customers
	*/
	@SuppressWarnings("unchecked")
	public static JSONArray readInput()
	{
		JSONParser parser = new JSONParser();
		JSONArray customerArray = new JSONArray();
		try
		{
			//read everything in
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Kamil\\Documents\\customers1.json"));
			String currentLine = null;		//seperate each line/element
			while ((currentLine = br.readLine()) != null) {
				JSONObject customer1 = new JSONObject();
				customer1 = (JSONObject) parser.parse(currentLine);
				customerArray.add(customer1);	//add each element/customer into JSONArray
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return customerArray;
	}
	
	/*
	Description : Calculates distance between customer and Intercom offices using the Haversine method
	Takes : JSONObject customer 
	Return : distance between customer and Intercom offices
	*/
	public static double calculateDistance(JSONObject customer)
	{
		String temp = (String) customer.get("latitude");
		double latitude = Double.parseDouble(temp);
		temp = (String) customer.get("longitude");
		double longitude = Double.parseDouble(temp);
		
		//N.B. Must convert longitude and latitude to Radians for formula to work
		double latitudeDifference = Math.toRadians(OFFICE_LAT - latitude);
		double longitudeDifference = Math.toRadians(OFFICE_LONG - longitude);
		//formula to calculate distance between customer latitude and longitude location and Intercom offices
		double calculation = Math.sin(latitudeDifference/2) * Math.sin(latitudeDifference/2) +
				Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(OFFICE_LAT)) *
				Math.sin(longitudeDifference/2) * Math.sin(longitudeDifference/2);
		double centralAngle = 2 * Math.atan2(Math.sqrt(calculation), Math.sqrt(1 - calculation));
		
		return EARTH_RADIUS * centralAngle;
	}
	
	/*
	Description : arranges all the invited customers in ascending order based on ID number
	Takes : HashMap of ID and name pairing, array of invited customer IDs
	Returns : String with invited clients in ascending order of ID number
	*/
	public static String inviteesAscending(HashMap<Integer, String> map, ArrayList<Integer> idArrayList)
	{
		String orderedList = "";
		idArrayList.sort(null);		//sorts IDs in ascending order
		for(int i = 0; i < idArrayList.size(); i++)
		{
			//pulls customers based on IDs in ascending order
			orderedList = orderedList + "Customer ID : " + idArrayList.get(i) + 
					", Customer Name : " + map.get(idArrayList.get(i)) + "\n";
		}
		return orderedList;
	}

}
