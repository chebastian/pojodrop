package com.example.pojodrop;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnConnectionParamBean;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.net.Uri;
import android.util.Log;

public class ServerInterface {
	
	protected String SERVER_URL = "http://sebastianferngren.com/connectToDB.php";
	//protected String SERVER_URL = "http://sebastianferngren.com/index2.html";
	public ServerInterface() { 

	}
	
	public String getServerList()
	{
		String data = "";
		//try {
			try {
				data = URLEncoder.encode("command","UTF-8")+ "=" +URLEncoder.encode("getList","UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//data = "";
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("command", "getList"));
			//executeRequest(this.SERVER_URL,params);
			executeHttpRequest(this.SERVER_URL, data);
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
	
	public String sendClientRequest(String urlReq)
	{
		String response = "";
		try
		{
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(); 
			get.setURI(new URI(urlReq));

			HttpResponse resp = client.execute(get);

            String inputLine;
            
            BufferedReader br = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();

            while((inputLine = br.readLine()) != null)
            {
            	sb.append(inputLine);
            }
            
            response = sb.toString();
            br.close();
			 
		}
		catch(IOException e)
		{
			Log.d("",e.getMessage());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			//connection.disconnect();
		}
		
		return response;
	}
	
	public String getList() throws IOException
	{
        String res = "";
			 String url_str =  Uri.parse(this.SERVER_URL).buildUpon()
					.appendQueryParameter("command", "getList")
					.build().toString(); 
			 
			 res = sendClientRequest(url_str);
		
		return res;
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
            
            /*OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(data);
            wr.flush();*/

            // get the response from the server and store it in result
            DataInputStream dataIn = new DataInputStream(connection.getInputStream()); 
            String inputLine;
            
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
