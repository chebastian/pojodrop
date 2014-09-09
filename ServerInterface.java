package com.example.pojodrop;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class ServerInterface {
	
	protected String SERVER_URL = "http://sebastianferngren.com/connectToDB.php";
	public ServerInterface() { 

	}
	
	public String getServerList()
	{
		String data = "";
		//try {
			//data = URLEncoder.encode("command","UTF-8")+ "=" +URLEncoder.encode("getList","UTF-8");
			//data = "";
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("command", "getList"));
			executeRequest(this.SERVER_URL,params);
		//}
		return data;
	}
	
	public void addScore(String name, int score)
	{
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("command", "addNewScore"));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("score", ""+score));
		ServerInterface.executeRequest(this.SERVER_URL, params);
	}
	
	public static String executeRequest(String _url, ArrayList<NameValuePair> commands)
	{
		String ret = "";
		try{ 
			

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(_url);
			post.setEntity(new UrlEncodedFormEntity(commands));
			client.execute(post);
			
			ret = EntityUtils.toString(post.getEntity());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public static String executeHttpRequest(String _url, String data)
	{
		String str = "";
		try {
			URL url = new URL(_url);
			URLConnection connection = url.openConnection();
			
			connection.setDoInput(true);
			connection.setDoOutput(true);
            
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(data);
            wr.flush();

            // Send the POST data
            /*DataOutputStream dataOut = new DataOutputStream(connection.getOutputStream());
            dataOut.writeBytes(data);
            dataOut.flush();
            dataOut.close();*/

            // get the response from the server and store it in result
            DataInputStream dataIn = new DataInputStream(connection.getInputStream()); 
            String inputLine;
            /*while ((inputLine = dataIn.readLine()) != null) {
                    str += inputLine;
            }*/
            
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while((inputLine = br.readLine()) != null)
            {
            	sb.append(inputLine);
            }
            
            str = sb.toString();
            dataIn.close();
			
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
