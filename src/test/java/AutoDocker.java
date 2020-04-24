import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AutoDocker 
{
	//Create and read the server logs, to verify automatically whether the server is up and running
	String outputLogs = "serverLogs.txt";
	
	public void cmdDocker(String cmd) throws IOException, InterruptedException {
		//Execute the Batch File Command using Runtime Java class
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec(cmd);

		//To wait until the command is executed.
		System.out.println("Sleeping for 25 seconds...");
		Thread.sleep(25000);
		System.out.println("Sleeping complete...");
		
		//Check & Confirm whether the command execution is successful.
		if (p.exitValue() == 0)
			System.out.println(cmd + " execution successful.");
		else
			System.out.println(cmd + " execution unsuccessful.");
	}
	
	@BeforeTest
	public void startDocker() throws IOException, InterruptedException 
	{
		System.out.println("Inside startDocker()...");
		
		//To flush out the Server Logs file
		File file = new File(outputLogs);
		if (file.delete())
			System.out.println(outputLogs + " is successfully flushed out.");
		else
			System.out.println(outputLogs + " Flush is NOT successful.");
		
		//Initiate Docker Containers
		String cmd = "cmd /c start C:\\Raj\\eclipse-workspace\\WebDockers\\dockerUp.bat";
		cmdDocker(cmd);

		
		//Using Calendar class to break the possible infinite loop.
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 120);
		long maxTimeOut = cal.getTimeInMillis();
		Thread.sleep(5000);
		
		//Use BufferedReader to read the lines of the file, then parse it for the right text, to ensure the server is up and running.
		BufferedReader reader = new BufferedReader (new FileReader(outputLogs));
		String currentLine = reader.readLine();
		Boolean flag = false;
		
		while (System.currentTimeMillis() < maxTimeOut && !flag) 
		{
			while (currentLine != null && !flag) 
			{
				if (currentLine.contains("registered to the hub and ready to use")) 
				{
					flag = true;
					System.out.println("Docker hub REGISTERED successfully.");
					break;
				}
				currentLine = reader.readLine();			
			}
		}
		reader.close();
		
		//TO flush out the Server Logs at the end of the run.
		//FileOutputStream writer = new FileOutputStream(outputLogs);
		
		//To assert whether the Docker started HUB & NODES.
		Assert.assertTrue(flag, "Docker hub & nodes are verified to be successfully started.");
		
		//Scaling the Chrome browser by 5
		String scale = "cmd /c start C:\\Raj\\eclipse-workspace\\WebDockers\\dockerScale.bat";
		cmdDocker(scale);
		Thread.sleep(5000);
	}
	
	@AfterTest
	public void stopDocker() throws IOException, InterruptedException 
	{
		System.out.println("Inside stoptDocker()...");
		//Initiate Docker Containers
		String cmd2 = "cmd /c start C:\\Raj\\eclipse-workspace\\WebDockers\\dockerDown.bat";
		cmdDocker(cmd2);
		
		//Using Calendar class to break the possible infinite loop.
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 120);
		long maxTimeOut = cal.getTimeInMillis();
		Thread.sleep(5000);
		
		//Use BufferedReader to read the lines of the file, then parse it for the right text, to ensure the server is up and running.
		BufferedReader reader = new BufferedReader (new FileReader(outputLogs));
		String currentLine = reader.readLine();
		Boolean flag = false;
		
		while (System.currentTimeMillis() < maxTimeOut && !flag) 
		{
			while (currentLine != null && !flag) 
			{
				if (currentLine.contains("selenium-hub exited with code 143")) 
				{
					flag = true;
					System.out.println("Docker hub EXITED successfully.");
					break;
				}
				currentLine = reader.readLine();			
			}
		}
		reader.close();
		
		//TO flush out the Server Logs at the end of the run.
		//FileOutputStream writer = new FileOutputStream(outputLogs);
		
		//To assert whether the Docker started HUB & NODES.
		Assert.assertTrue(flag, "Docker hub & nodes are verified to be successfully started.");

	}
	
}
