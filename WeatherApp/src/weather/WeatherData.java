package weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.reflect.*;

public class WeatherData 
{
	//make the API data something actually usable
	public static Map<String, Object> jsonToMap(String str)
	{
		Map<String, Object> map = new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>(){}.getType());
		return map;
	}

	//retrieve data
	public static boolean weatherData(String city, String units, String[] dates, String[] forecast, String[] current, String label) 
	{
		//parameters for API call
		String API_KEY = "6fc10287d6a656731ef6c8ada7d764e8"; //if someone is reading this, please don't steal my key :)
		String LOCATION = city;
		String UNITS = units;
		String urlString = "http://api.openweathermap.org/data/2.5/forecast?q=" + LOCATION + "&appid=" + API_KEY + 
				"&units=" + UNITS;
		
		//retrieve data from API call
		StringBuilder result = new StringBuilder();
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null)
			{
				result.append(line);
			}
			rd.close();
			System.out.println(result);
			
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
			
		//format data out of json format
		Map<String, Object> respMap = jsonToMap(result.toString());
		
		//check if this worked
		if(respMap == null)
		{
			return false;
		}
		
		//CURRENT WEATHER
		//System.out.println("RespMap: "+ respMap.toString());
		String main = respMap.get("list").toString();
		String weather = respMap.get("list").toString();
		main = main.substring(main.indexOf("main"), main.indexOf("weather"));
		weather = weather.substring(weather.indexOf("weather"), weather.indexOf("dt_txt=" + 20));
			
		//this is where we get the info. Whatever the json map is, it's useless from here on
		//System.out.println(main);
		//System.out.println(weather);
		
		String temp = main.substring(main.indexOf("temp=") + 5, main.indexOf(",")); // + 5 to index past text
		String desc = weather.substring(weather.indexOf("main="), weather.length());
		desc = desc.substring(desc.indexOf("=") + 1, desc.indexOf(","));
		String hum = main.substring(main.indexOf("humidity="), main.length());
		hum = hum.substring(hum.indexOf("=") + 1, hum.indexOf(","));
		String wind = weather.substring(weather.indexOf("wind={speed="), weather.length());
		wind = wind.substring(wind.indexOf("=") + 8, wind.indexOf(",")); // + 8 to index past text
		
		/*System.out.println("\nWeather in " + LOCATION + "\n");
		System.out.println("Current Temperature: " + temp);
		System.out.println("Current Humidity: " + hum);
		System.out.println("Wind Speeds: " + wind);
		System.out.println("Description: " + desc);*/
		current[0] = temp;
		current[1] = hum;
		current[2] = wind;
		current[3] = desc;
		
		//now for 5-day forecast
		String day1, day2, day3, day4, day5;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//fill dates
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		day1 = dateFormat.format(calendar.getTime());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		day2 = dateFormat.format(calendar.getTime());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		day3 = dateFormat.format(calendar.getTime());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		day4 = dateFormat.format(calendar.getTime());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		day5 = dateFormat.format(calendar.getTime());
		dates[0] = day1;
		dates[1] = day2;
		dates[2] = day3;
		dates[3] = day4;
		dates[4] = day5;
		
		//DAY 1
		String currentDate, morning, afternoon, evening, night;
		String mornTemp, mornDesc, afterTemp, afterDesc, evenTemp, evenDesc, nightTemp, nightDesc;
		//get data for date
		currentDate = respMap.toString();
		currentDate = currentDate.substring(currentDate.indexOf(day1), currentDate.indexOf(day2));
		
		//get times
		morning = currentDate.substring(currentDate.indexOf("06:00:00") + 11, currentDate.indexOf("09:00:00") + 8);
		afternoon = currentDate.substring(currentDate.indexOf("09:00:00") + 11, currentDate.indexOf("12:00:00") + 8);
		evening = currentDate.substring(currentDate.indexOf("15:00:00") + 11, currentDate.indexOf("18:00:00") + 8);
		night = currentDate.substring(currentDate.indexOf("18:00:00") + 11, currentDate.indexOf("21:00:00") + 8);
		
		//setup strings for data retrieval
		main = currentDate.substring(currentDate.indexOf("main"), currentDate.indexOf("weather"));
		weather = currentDate.substring(currentDate.indexOf("weather"), currentDate.indexOf("dt_txt=" + 20));
		
		//retrieve specific data for times
		mornTemp = morning.substring(morning.indexOf("temp=") + 4, morning.length()); // + 5 to index past text
		mornTemp = mornTemp.substring(mornTemp.indexOf("=") + 1, mornTemp.indexOf(","));
		mornDesc = morning.substring(morning.indexOf("weather="), morning.length());
		mornDesc = mornDesc.substring(mornDesc.indexOf("main="), mornDesc.length());
		mornDesc = mornDesc.substring(mornDesc.indexOf("=") + 1, mornDesc.indexOf(","));
		
		afterTemp = afternoon.substring(afternoon.indexOf("temp=") + 4, afternoon.length()); // + 5 to index past text
		afterTemp = afterTemp.substring(afterTemp.indexOf("=") + 1, afterTemp.indexOf(","));
		afterDesc = afternoon.substring(afternoon.indexOf("weather="), afternoon.length());
		afterDesc = afterDesc.substring(afterDesc.indexOf("main="), afterDesc.length());
		afterDesc = afterDesc.substring(afterDesc.indexOf("=") + 1, afterDesc.indexOf(","));
		
		evenTemp = evening.substring(evening.indexOf("temp=") + 4, evening.length()); // + 5 to index past text
		evenTemp = evenTemp.substring(evenTemp.indexOf("=") + 1, evenTemp.indexOf(","));
		evenDesc = evening.substring(evening.indexOf("weather="), evening.length());
		evenDesc = evenDesc.substring(evenDesc.indexOf("main="), evenDesc.length());
		evenDesc = evenDesc.substring(evenDesc.indexOf("=") + 1, evenDesc.indexOf(","));
		
		nightTemp = night.substring(night.indexOf("temp=") + 4, night.length()); // + 5 to index past text
		nightTemp = nightTemp.substring(nightTemp.indexOf("=") + 1, nightTemp.indexOf(","));
		nightDesc = night.substring(night.indexOf("weather="), night.length());
		nightDesc = nightDesc.substring(nightDesc.indexOf("main="), nightDesc.length());
		nightDesc = nightDesc.substring(nightDesc.indexOf("=") + 1, nightDesc.indexOf(","));
		
		//now just hum and wind only once from beginning
		hum = main.substring(main.indexOf("humidity="), main.length());
		hum = hum.substring(hum.indexOf("=") + 1, hum.indexOf(","));
		wind = weather.substring(weather.indexOf("wind={speed="), weather.length());
		wind = wind.substring(wind.indexOf("=") + 8, wind.indexOf(",")); // + 8 to index past text
		
		//display
		/*System.out.println("\nForecast on: " + day1);
		System.out.println("Morning: " + mornTemp + ", " + mornDesc);
		System.out.println("Afternoon: " + afterTemp + ", " + afterDesc);
		System.out.println("Evening: " + evenTemp + ", " + evenDesc);
		System.out.println("Night: " + nightTemp + ", " + nightDesc);
		System.out.println("Humidity: " + hum);
		System.out.println("Wind Speeds: " + wind);*/
		forecast[0] = mornTemp + label + " , " + mornDesc;
		forecast[1] = afterTemp + label + " , " + afterDesc;
		forecast[2] = evenTemp + label + " , " + evenDesc;
		forecast[3] = nightTemp + label + " , " + nightDesc;
		forecast[4] = hum;
		forecast[5] = wind;
		
		//DAY 2
		//get data for date
		currentDate = respMap.toString();
		currentDate = currentDate.substring(currentDate.indexOf(day2), currentDate.indexOf(day3));
				
		//get times
		morning = currentDate.substring(currentDate.indexOf("06:00:00") + 11, currentDate.indexOf("09:00:00") + 8);
		afternoon = currentDate.substring(currentDate.indexOf("09:00:00") + 11, currentDate.indexOf("12:00:00") + 8);
		evening = currentDate.substring(currentDate.indexOf("15:00:00") + 11, currentDate.indexOf("18:00:00") + 8);
		night = currentDate.substring(currentDate.indexOf("18:00:00") + 11, currentDate.indexOf("21:00:00") + 8);
		
		//setup strings for data retrieval
		main = currentDate.substring(currentDate.indexOf("main"), currentDate.indexOf("weather"));
		weather = currentDate.substring(currentDate.indexOf("weather"), currentDate.indexOf("dt_txt=" + 20));
		
		//retrieve specific data for times
		mornTemp = morning.substring(morning.indexOf("temp=") + 4, morning.length()); // + 5 to index past text
		mornTemp = mornTemp.substring(mornTemp.indexOf("=") + 1, mornTemp.indexOf(","));
		mornDesc = morning.substring(morning.indexOf("weather="), morning.length());
		mornDesc = mornDesc.substring(mornDesc.indexOf("main="), mornDesc.length());
		mornDesc = mornDesc.substring(mornDesc.indexOf("=") + 1, mornDesc.indexOf(","));
				
		afterTemp = afternoon.substring(afternoon.indexOf("temp=") + 4, afternoon.length()); // + 5 to index past text
		afterTemp = afterTemp.substring(afterTemp.indexOf("=") + 1, afterTemp.indexOf(","));
		afterDesc = afternoon.substring(afternoon.indexOf("weather="), afternoon.length());
		afterDesc = afterDesc.substring(afterDesc.indexOf("main="), afterDesc.length());
		afterDesc = afterDesc.substring(afterDesc.indexOf("=") + 1, afterDesc.indexOf(","));
				
		evenTemp = evening.substring(evening.indexOf("temp=") + 4, evening.length()); // + 5 to index past text
		evenTemp = evenTemp.substring(evenTemp.indexOf("=") + 1, evenTemp.indexOf(","));
		evenDesc = evening.substring(evening.indexOf("weather="), evening.length());
		evenDesc = evenDesc.substring(evenDesc.indexOf("main="), evenDesc.length());
		evenDesc = evenDesc.substring(evenDesc.indexOf("=") + 1, evenDesc.indexOf(","));
				
		nightTemp = night.substring(night.indexOf("temp=") + 4, night.length()); // + 5 to index past text
		nightTemp = nightTemp.substring(nightTemp.indexOf("=") + 1, nightTemp.indexOf(","));
		nightDesc = night.substring(night.indexOf("weather="), night.length());
		nightDesc = nightDesc.substring(nightDesc.indexOf("main="), nightDesc.length());
		nightDesc = nightDesc.substring(nightDesc.indexOf("=") + 1, nightDesc.indexOf(","));
				
		//now just hum and wind only once from beginning
		hum = main.substring(main.indexOf("humidity="), main.length());
		hum = hum.substring(hum.indexOf("=") + 1, hum.indexOf(","));
		wind = weather.substring(weather.indexOf("wind={speed="), weather.length());
		wind = wind.substring(wind.indexOf("=") + 8, wind.indexOf(",")); // + 8 to index past text
				
		//display
		/*System.out.println("\nForecast on: " + day2);
		System.out.println("Morning: " + mornTemp + ", " + mornDesc);
		System.out.println("Afternoon: " + afterTemp + ", " + afterDesc);
		System.out.println("Evening: " + evenTemp + ", " + evenDesc);
		System.out.println("Night: " + nightTemp + ", " + nightDesc);
		System.out.println("Humidity: " + hum);
		System.out.println("Wind Speeds: " + wind);*/
		forecast[6] = mornTemp + label + " , " + mornDesc;
		forecast[7] = afterTemp + label + " , " + afterDesc;
		forecast[8] = evenTemp + label + " , " + evenDesc;
		forecast[9] = nightTemp + label + " , " + nightDesc;
		forecast[10] = hum;
		forecast[11] = wind;
		
		//DAY 3
		//get data for date
		currentDate = respMap.toString();
		currentDate = currentDate.substring(currentDate.indexOf(day3), currentDate.indexOf(day4));
				
		//get times
		morning = currentDate.substring(currentDate.indexOf("06:00:00") + 11, currentDate.indexOf("09:00:00") + 8);
		afternoon = currentDate.substring(currentDate.indexOf("09:00:00") + 11, currentDate.indexOf("12:00:00") + 8);
		evening = currentDate.substring(currentDate.indexOf("15:00:00") + 11, currentDate.indexOf("18:00:00") + 8);
		night = currentDate.substring(currentDate.indexOf("18:00:00") + 11, currentDate.indexOf("21:00:00") + 8);
		
		//setup strings for data retrieval
		main = currentDate.substring(currentDate.indexOf("main"), currentDate.indexOf("weather"));
		weather = currentDate.substring(currentDate.indexOf("weather"), currentDate.indexOf("dt_txt=" + 20));
		
		//retrieve specific data for times
		mornTemp = morning.substring(morning.indexOf("temp=") + 4, morning.length()); // + 5 to index past text
		mornTemp = mornTemp.substring(mornTemp.indexOf("=") + 1, mornTemp.indexOf(","));
		mornDesc = morning.substring(morning.indexOf("weather="), morning.length());
		mornDesc = mornDesc.substring(mornDesc.indexOf("main="), mornDesc.length());
		mornDesc = mornDesc.substring(mornDesc.indexOf("=") + 1, mornDesc.indexOf(","));
				
		afterTemp = afternoon.substring(afternoon.indexOf("temp=") + 4, afternoon.length()); // + 5 to index past text
		afterTemp = afterTemp.substring(afterTemp.indexOf("=") + 1, afterTemp.indexOf(","));
		afterDesc = afternoon.substring(afternoon.indexOf("weather="), afternoon.length());
		afterDesc = afterDesc.substring(afterDesc.indexOf("main="), afterDesc.length());
		afterDesc = afterDesc.substring(afterDesc.indexOf("=") + 1, afterDesc.indexOf(","));
				
		evenTemp = evening.substring(evening.indexOf("temp=") + 4, evening.length()); // + 5 to index past text
		evenTemp = evenTemp.substring(evenTemp.indexOf("=") + 1, evenTemp.indexOf(","));
		evenDesc = evening.substring(evening.indexOf("weather="), evening.length());
		evenDesc = evenDesc.substring(evenDesc.indexOf("main="), evenDesc.length());
		evenDesc = evenDesc.substring(evenDesc.indexOf("=") + 1, evenDesc.indexOf(","));
				
		nightTemp = night.substring(night.indexOf("temp=") + 4, night.length()); // + 5 to index past text
		nightTemp = nightTemp.substring(nightTemp.indexOf("=") + 1, nightTemp.indexOf(","));
		nightDesc = night.substring(night.indexOf("weather="), night.length());
		nightDesc = nightDesc.substring(nightDesc.indexOf("main="), nightDesc.length());
		nightDesc = nightDesc.substring(nightDesc.indexOf("=") + 1, nightDesc.indexOf(","));
				
		//now just hum and wind only once from beginning
		hum = main.substring(main.indexOf("humidity="), main.length());
		hum = hum.substring(hum.indexOf("=") + 1, hum.indexOf(","));
		wind = weather.substring(weather.indexOf("wind={speed="), weather.length());
		wind = wind.substring(wind.indexOf("=") + 8, wind.indexOf(",")); // + 8 to index past text
				
		//display
		/*System.out.println("\nForecast on: " + day3);
		System.out.println("Morning: " + mornTemp + ", " + mornDesc);
		System.out.println("Afternoon: " + afterTemp + ", " + afterDesc);
		System.out.println("Evening: " + evenTemp + ", " + evenDesc);
		System.out.println("Night: " + nightTemp + ", " + nightDesc);
		System.out.println("Humidity: " + hum);
		System.out.println("Wind Speeds: " + wind);*/
		forecast[12] = mornTemp + label + " , " + mornDesc;
		forecast[13] = afterTemp + label + " , " + afterDesc;
		forecast[14] = evenTemp + label + " , " + evenDesc;
		forecast[15] = nightTemp + label + " , " + nightDesc;
		forecast[16] = hum;
		forecast[17] = wind;
		
		//DAY 4
		//get data for date
		currentDate = respMap.toString();
		currentDate = currentDate.substring(currentDate.indexOf(day4), currentDate.indexOf(day5));
				
		//get times
		morning = currentDate.substring(currentDate.indexOf("06:00:00") + 11, currentDate.indexOf("09:00:00") + 8);
		afternoon = currentDate.substring(currentDate.indexOf("09:00:00") + 11, currentDate.indexOf("12:00:00") + 8);
		evening = currentDate.substring(currentDate.indexOf("15:00:00") + 11, currentDate.indexOf("18:00:00") + 8);
		night = currentDate.substring(currentDate.indexOf("18:00:00") + 11, currentDate.indexOf("21:00:00") + 8);
		
		//setup strings for data retrieval
		main = currentDate.substring(currentDate.indexOf("main"), currentDate.indexOf("weather"));
		weather = currentDate.substring(currentDate.indexOf("weather"), currentDate.indexOf("dt_txt=" + 20));
		
		//retrieve specific data for times
		mornTemp = morning.substring(morning.indexOf("temp=") + 4, morning.length()); // + 5 to index past text
		mornTemp = mornTemp.substring(mornTemp.indexOf("=") + 1, mornTemp.indexOf(","));
		mornDesc = morning.substring(morning.indexOf("weather="), morning.length());
		mornDesc = mornDesc.substring(mornDesc.indexOf("main="), mornDesc.length());
		mornDesc = mornDesc.substring(mornDesc.indexOf("=") + 1, mornDesc.indexOf(","));
				
		afterTemp = afternoon.substring(afternoon.indexOf("temp=") + 4, afternoon.length()); // + 5 to index past text
		afterTemp = afterTemp.substring(afterTemp.indexOf("=") + 1, afterTemp.indexOf(","));
		afterDesc = afternoon.substring(afternoon.indexOf("weather="), afternoon.length());
		afterDesc = afterDesc.substring(afterDesc.indexOf("main="), afterDesc.length());
		afterDesc = afterDesc.substring(afterDesc.indexOf("=") + 1, afterDesc.indexOf(","));
				
		evenTemp = evening.substring(evening.indexOf("temp=") + 4, evening.length()); // + 5 to index past text
		evenTemp = evenTemp.substring(evenTemp.indexOf("=") + 1, evenTemp.indexOf(","));
		evenDesc = evening.substring(evening.indexOf("weather="), evening.length());
		evenDesc = evenDesc.substring(evenDesc.indexOf("main="), evenDesc.length());
		evenDesc = evenDesc.substring(evenDesc.indexOf("=") + 1, evenDesc.indexOf(","));
				
		nightTemp = night.substring(night.indexOf("temp=") + 4, night.length()); // + 5 to index past text
		nightTemp = nightTemp.substring(nightTemp.indexOf("=") + 1, nightTemp.indexOf(","));
		nightDesc = night.substring(night.indexOf("weather="), night.length());
		nightDesc = nightDesc.substring(nightDesc.indexOf("main="), nightDesc.length());
		nightDesc = nightDesc.substring(nightDesc.indexOf("=") + 1, nightDesc.indexOf(","));
				
		//now just hum and wind only once from beginning
		hum = main.substring(main.indexOf("humidity="), main.length());
		hum = hum.substring(hum.indexOf("=") + 1, hum.indexOf(","));
		wind = weather.substring(weather.indexOf("wind={speed="), weather.length());
		wind = wind.substring(wind.indexOf("=") + 8, wind.indexOf(",")); // + 8 to index past text
				
		//display
		/*System.out.println("\nForecast on: " + day4);
		System.out.println("Morning: " + mornTemp + ", " + mornDesc);
		System.out.println("Afternoon: " + afterTemp + ", " + afterDesc);
		System.out.println("Evening: " + evenTemp + ", " + evenDesc);
		System.out.println("Night: " + nightTemp + ", " + nightDesc);
		System.out.println("Humidity: " + hum);
		System.out.println("Wind Speeds: " + wind);*/
		forecast[18] = mornTemp + label + " , " + mornDesc;
		forecast[19] = afterTemp + label + " , " + afterDesc;
		forecast[20] = evenTemp + label + " , " + evenDesc;
		forecast[21] = nightTemp + label + " , " + nightDesc;
		forecast[22] = hum;
		forecast[23] = wind;
		
		//DAY 5
		//NOTE: Only morning and afternoon are available. Ask the OWM people
		//get data for date
		currentDate = respMap.toString();
		currentDate = currentDate.substring(currentDate.indexOf(day5), currentDate.length());
				
		//get times
		morning = currentDate.substring(currentDate.indexOf("06:00:00") + 11, currentDate.indexOf("09:00:00") + 8);
		afternoon = currentDate.substring(currentDate.indexOf("09:00:00") + 11, currentDate.indexOf("12:00:00") + 8);
		
		//setup strings for data retrieval
		main = currentDate.substring(currentDate.indexOf("main"), currentDate.indexOf("weather"));
		weather = currentDate.substring(currentDate.indexOf("weather"), currentDate.indexOf("dt_txt=" + 20));
		
		//retrieve specific data for times
		mornTemp = morning.substring(morning.indexOf("temp=") + 4, morning.length()); // + 5 to index past text
		mornTemp = mornTemp.substring(mornTemp.indexOf("=") + 1, mornTemp.indexOf(","));
		mornDesc = morning.substring(morning.indexOf("weather="), morning.length());
		mornDesc = mornDesc.substring(mornDesc.indexOf("main="), mornDesc.length());
		mornDesc = mornDesc.substring(mornDesc.indexOf("=") + 1, mornDesc.indexOf(","));
				
		afterTemp = afternoon.substring(afternoon.indexOf("temp=") + 4, afternoon.length()); // + 5 to index past text
		afterTemp = afterTemp.substring(afterTemp.indexOf("=") + 1, afterTemp.indexOf(","));
		afterDesc = afternoon.substring(afternoon.indexOf("weather="), afternoon.length());
		afterDesc = afterDesc.substring(afterDesc.indexOf("main="), afterDesc.length());
		afterDesc = afterDesc.substring(afterDesc.indexOf("=") + 1, afterDesc.indexOf(","));
				
		//now just hum and wind only once from beginning
		hum = main.substring(main.indexOf("humidity="), main.length());
		hum = hum.substring(hum.indexOf("=") + 1, hum.indexOf(","));
		wind = weather.substring(weather.indexOf("wind={speed="), weather.length());
		wind = wind.substring(wind.indexOf("=") + 8, wind.indexOf(",")); // + 8 to index past text
				
		//display
		/*System.out.println("\nForecast on: " + day5);
		System.out.println("Morning: " + mornTemp + ", " + mornDesc);
		System.out.println("Afternoon: " + afterTemp + ", " + afterDesc);;
		System.out.println("Humidity: " + hum);
		System.out.println("Wind Speeds: " + wind);*/
		forecast[24] = mornTemp + label + " , " + mornDesc;
		forecast[25] = afterTemp + label + " , " + afterDesc;
		forecast[26] = evenTemp + label + " , " + evenDesc;
		forecast[27] = nightTemp + label + " , " + nightDesc;
		forecast[28] = hum;
		forecast[29] = wind;
		
		
				
		return true;	
	}

}
