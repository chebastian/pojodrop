package com.example.pojodrop;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ServerInterface {
	
	protected String SERVER_URL = "http://www.hassanpur.com/AndroidListServerExample/server.php";
	public ServerInterface() { 

	}
	
	public String getServerList()
	{
		String data = "command="+URLEncoder.encode("getAnimalList");
		return ServerInterface.executeHttpRequest(this.SERVER_URL, data);
	}
	
	public static String executeHttpRequest(String _url, String data)
	{
		String str = "";
		try {
			URL url = new URL(_url);
			URLConnection connection = url.openConnection();
			
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			DataOutputStream outStream = new DataOutputStream(connection.getOutputStream());
			outStream.writeBytes(data);
			outStream.flush();
			outStream.close();
			
			DataInputStream inStream = new DataInputStream(connection.getInputStream());
			String inputLine = "";
			
			while((inputLine = inStream.readLine()) != null)
			{
				str += inputLine;
			}
			
			inStream.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return str;
	}

}
