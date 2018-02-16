package com.github.amitrinx.traffic;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main {
	
	public static void main(String[] args) throws IOException	
	{	
		int iProblemNumber= getProblemNumberToExecute();
		BufferedReader bfrInput= new BufferedReader(new InputStreamReader(System.in));
		String weather=bfrInput.readLine().split(" ")[2];
		String[] getBestOrbitDetails=OrbitSelection.getBestOrbit(iProblemNumber, weather);
		printResults(getBestOrbitDetails,iProblemNumber);
	}		
	private static int getProblemNumberToExecute() throws IOException
	{
		BufferedReader bfrInput= new BufferedReader(new InputStreamReader(System.in));
		int iProblemNumber=2;
		System.out.println("Do you want to execute problem 1 or problem 2");
		String ProblemInput=bfrInput.readLine();
	    if(ProblemInput.equals("1") ||ProblemInput.equalsIgnoreCase("problem 1"))
	    	   iProblemNumber=1;
		return iProblemNumber;
	}
	private static void printResults(String[] getBestOrbitDetails,int iProblemNumber)
	{
		int iBestOrbit=Integer.parseInt(getBestOrbitDetails[0])+1;
		String strBestVehicle=getBestOrbitDetails[1];
		if(iProblemNumber==1)
			System.out.println("Vehicle "+ strBestVehicle+" on orbit"+iBestOrbit);
		else 
		{
			if(iBestOrbit==3)			
				System.out.println("Vehicle "+strBestVehicle+" to RK Puram via Orbit"+ iBestOrbit+" and Hallitharam via Orbit4");				
			else			
				System.out.println("Vehicle "+strBestVehicle+" to Hallitharam via Orbit"+ iBestOrbit+" and RK Puram via Orbit4");				
		}		
	}
}
