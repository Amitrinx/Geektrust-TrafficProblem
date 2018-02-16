package com.github.amitrinx.traffic;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class OrbitSelection {

	JourneyDetails bestVehicleOnOrbit= new JourneyDetails();
	public static String[] getBestOrbit(int iProblemNumber,String weather) throws IOException
	{
		int iOrbitBestSpeed= Integer.MAX_VALUE;	
		int iBestOrbit=0;
		String strBestVehicle="";
		String[] bestOrbitDetails=new String[2];
		ArrayList<Integer> OrbitSpeed = getOrbitSpeed(iProblemNumber);
		JourneyDetails bestVehicleOnOrbit= new JourneyDetails();
		for(int iOrbit=0; iOrbit<OrbitSpeed.size();iOrbit++)
		{	 
			HashMap<String, Integer> currentOrbitDetails = new HashMap<String, Integer>();
			int iTimeTaken=0;
			currentOrbitDetails=getOrbitDetails(iOrbit, OrbitSpeed.get(iOrbit));	
			ArrayList<String> strBestVehicleForOrbit= bestVehicleOnOrbit.getTimeAndVehicleForFirstDest(weather, currentOrbitDetails);	
			if(iProblemNumber==2)
			{	
				currentOrbitDetails=getOrbitDetails(3, OrbitSpeed.get(iOrbit));
				int iTimeTakenforSendDest=bestVehicleOnOrbit.getTimeTakenForSecondDestination(currentOrbitDetails,  weather,Integer.parseInt(strBestVehicleForOrbit.get(2)));
				iTimeTaken= Integer.parseInt(strBestVehicleForOrbit.get(1))+iTimeTakenforSendDest;
			}
			else 
			{
				iTimeTaken=Integer.parseInt(strBestVehicleForOrbit.get(1));
			}
			if(iTimeTaken<iOrbitBestSpeed)
			{		
				iBestOrbit=iOrbit;
				iOrbitBestSpeed=iTimeTaken;
				strBestVehicle=strBestVehicleForOrbit.get(0);
			}	
			if(iOrbit==2)
				break;
		}
		bestOrbitDetails[0]=String.valueOf(iBestOrbit);
		bestOrbitDetails[1]=strBestVehicle;
		return bestOrbitDetails;
	}
	private static ArrayList<Integer> getOrbitSpeed(int iProblemNumber) throws IOException
	{
		ArrayList<Integer> OrbitSpeed = new ArrayList<Integer>();
		BufferedReader bfrInput= new BufferedReader(new InputStreamReader(System.in));
		for(int iInput=0; iInput<iProblemNumber*2;iInput++)
		{
			String OrbitInput= bfrInput.readLine();
			OrbitSpeed.add(Integer.parseInt(OrbitInput.substring(10,OrbitInput.length()-1).replaceAll("[^0-9]","")));
		}
		return OrbitSpeed;
	}
	private static HashMap<String, Integer> getOrbitDetails(int i, Integer MaxSpeed)
	{
		HashMap<String, Integer> currentOrbitDetails=new HashMap<String, Integer>();
		try{			
			Constants constants = new Constants();
			
			int iOrbitDistance = Integer.parseInt(Constants.class.getDeclaredField("ORBIT_"+(i+1)+"_DISTANCE").get(constants).toString());
			int iOrbitCraterCount = Integer.parseInt(Constants.class.getDeclaredField("ORBIT_"+(i+1)+"_CRATERCOUNT").get(constants).toString());
			
			currentOrbitDetails.put("Distance", iOrbitDistance);
			currentOrbitDetails.put("CraterCount", iOrbitCraterCount);
			currentOrbitDetails.put("MaxSpeed", MaxSpeed);	
		}
		catch(Exception e){
			System.out.println("Exception in getOrbitDetails..."+e.toString());
		}
		
		return currentOrbitDetails;
	}
}
