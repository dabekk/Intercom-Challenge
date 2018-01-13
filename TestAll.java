import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestAll {
	
	@Test
	public void testCalculateDistance()
	{
		//comparison distance calculated at 
		//https://www.movable-type.co.uk/scripts/latlong.html
		JSONArray customerArray = Driver.readInput();	//read all customers
		JSONObject customer = (JSONObject) customerArray.get(0);	//select random customers
		//get results from trusted online resources to check if distance is correct
		assertEquals(Math.round(Driver.calculateDistance(customer)), 42);	
		customer = (JSONObject) customerArray.get(3);
		assertEquals(Math.round(Driver.calculateDistance(customer)), 189);
		customer = (JSONObject) customerArray.get(16);
		assertEquals(Math.round(Driver.calculateDistance(customer)), 62);
		customer = (JSONObject) customerArray.get(24);
		assertEquals(Math.round(Driver.calculateDistance(customer)), 224);
		customer = (JSONObject) customerArray.get(10);
		assertEquals(Math.round(Driver.calculateDistance(customer)), 133);
		//calculate distance for same location
		customer.put("latitude", "53.339428");
		customer.put("longitude", "-6.257664");
		assertEquals(Math.round(Driver.calculateDistance(customer)), 0);
	}
	
	@Test
	public void testInviteesAscending()
	{
		//load in different HashMap and ArrayList to test if it will return sorted list
		HashMap<Integer, String> dummyMap = new HashMap<Integer, String>();
		ArrayList<Integer> dummyList = new ArrayList<Integer>();
		dummyMap.put(10, "John");	dummyList.add(10);
		dummyMap.put(22, "Kevin");	dummyList.add(22);
		dummyMap.put(2, "Sarah");	dummyList.add(2);
		dummyMap.put(101, "Joan");	dummyList.add(101);
		
		String test = Driver.inviteesAscending(dummyMap, dummyList);
		String compare = "Customer ID : 2, Customer Name : Sarah\nCustomer ID : 10, Customer Name : John" +
				"\nCustomer ID : 22, Customer Name : Kevin\nCustomer ID : 101, Customer Name : Joan\n";
		assertEquals(test, compare);
		
		dummyMap.put(2, "Sarah");	dummyList.add(2);
		dummyMap.put(101, "Joan");	dummyList.add(101);
		test = Driver.inviteesAscending(dummyMap, dummyList);
		compare = "Customer ID : 2, Customer Name : Sarah\nCustomer ID : 2, Customer Name : Sarah\n"
				+ "Customer ID : 10, Customer Name : John" +
				"\nCustomer ID : 22, Customer Name : Kevin\nCustomer ID : 101, Customer Name : Joan\n"
				+ "Customer ID : 101, Customer Name : Joan\n";
		assertEquals(test, compare);
	}
	
	@Test
	public void testReadInput() 
	{
		JSONArray testArray = Driver.readInput();
		JSONParser parser1 = new JSONParser();
		Iterator<JSONObject> iter = testArray.iterator();
		try
		{
			BufferedReader buffer = new BufferedReader(new FileReader("C:\\Users\\Kamil"
					+ "\\Documents\\customers1.json"));	//directory where JSON file is saved
			String currentLine = null;		
			while ((currentLine = buffer.readLine()) != null) {	//read in each line from file
				JSONObject testCase = new JSONObject();
				testCase = (JSONObject) parser1.parse(currentLine);	//create a JSONObject
				assertEquals(testCase, iter.next());	
				//compare each JSONObject created to JSONObjects in test Array - should be the exact same
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
