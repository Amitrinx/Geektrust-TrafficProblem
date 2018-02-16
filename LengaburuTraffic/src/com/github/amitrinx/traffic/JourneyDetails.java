package com.github.amitrinx.traffic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JourneyDetails {
	public ArrayList<String> getTimeAndVehicleForFirstDest(String weather, HashMap<String, Integer> currentOrbitDetails )
	{
		int iIncreaseInCrater = getIncreaseInCrater(weather);
		Integer dspeed=currentOrbitDetails.get("MaxSpeed");
		int iVehicleTimeMinutes= Integer.MAX_VALUE;
		ArrayList<String>  strBestVehicle=new ArrayList<String>();
		for(int iVehicle=0;iVehicle<Constants.iNoOfVehicles;iVehicle++)
		{	 
			HashMap<String, String> v=getVehiclesDetails(iVehicle);
			if(Arrays.asList(v.get("Weather").split(",")).contains(weather))		   
			{		
				int iVehicleMaxSpeed=dspeed > Integer.parseInt(v.get("Speed"))
						? Integer.parseInt(v.get("Speed")) : dspeed;
														
					int iVehicleTimeTemp=(int) (((float)currentOrbitDetails.get("Distance") / iVehicleMaxSpeed) * 60
						+ ((currentOrbitDetails.get("CraterCount"))
								- (currentOrbitDetails.get("CraterCount")) * iIncreaseInCrater / 100))
								* Integer.parseInt(v.get("PotholeTime"));
				if(iVehicleTimeMinutes>iVehicleTimeTemp)
				{
					strBestVehicle=new ArrayList<String>();
					strBestVehicle.add(v.get("Name"));
					strBestVehicle.add(Integer.toString(iVehicleTimeTemp));
					strBestVehicle.add(Integer.toString(iVehicle));
					iVehicleTimeMinutes=iVehicleTimeTemp;
				}
			}
		}		
		return strBestVehicle;		
	}
	public int getTimeTakenForSecondDestination(HashMap<String, Integer> currentOrbitDetails , String weather, int iVehicle)
	{
		int iIncreaseInCrater = getIncreaseInCrater(weather);
		Integer dspeed=currentOrbitDetails.get("MaxSpeed");
		HashMap<String, String> v=getVehiclesDetails(iVehicle);
		int iVehicleMaxSpeed=dspeed > Integer.parseInt(v.get("Speed"))
				? Integer.parseInt(v.get("Speed")) : dspeed;
												
			int iVehicleTimeTemp=(int) (((float)currentOrbitDetails.get("Distance") / iVehicleMaxSpeed) * 60
				+ ((currentOrbitDetails.get("CraterCount"))
						- (currentOrbitDetails.get("CraterCount")) * iIncreaseInCrater / 100))
						* Integer.parseInt(v.get("PotholeTime"));
		return iVehicleTimeTemp;	
	}
	private static int getIncreaseInCrater(String weather)
	{
		int iIncreaseInCrater=0;
		switch(weather)	
		{				
		case "Sunny":		
			iIncreaseInCrater=10;				
			break;
		case "Rainy":
			iIncreaseInCrater=20;
			break;
		case "Windy":
			iIncreaseInCrater=0;
			break;	 
		}
		return iIncreaseInCrater;
	}
	private static HashMap<String, String> getVehiclesDetails(int iVehicle)
	{	
		HashMap<String, String> currentOrbitDetails=new HashMap<String, String>();
		try{			
			Constants constants = new Constants();
			
			String sVehicleName = Constants.class.getDeclaredField("VEHICLE_"+(iVehicle+1)+"_NAME").get(constants).toString();
			String sVehicleSpeed = Constants.class.getDeclaredField("VEHICLE_"+(iVehicle+1)+"_SPEED").get(constants).toString();
			String sVehicleCraterTime = Constants.class.getDeclaredField("VEHICLE_"+(iVehicle+1)+"_CRATERTIME").get(constants).toString();
			String sVehicleWeather = Constants.class.getDeclaredField("VEHICLE_"+(iVehicle+1)+"_WEATHER").get(constants).toString();
			
			currentOrbitDetails.put("Name",sVehicleName);
			currentOrbitDetails.put("Speed",sVehicleSpeed);
			currentOrbitDetails.put("PotholeTime",sVehicleCraterTime);
			currentOrbitDetails.put("Weather",sVehicleWeather);			
		}
		catch(Exception e){
			System.out.println("Exception in getVehiclesDetails..."+e.toString());
		}

		return currentOrbitDetails;
	}
}
