package br.com.fa7.twitter.util;

import java.io.BufferedInputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class GoogleURLShortener implements URLShortener {

	@Override
	public String shorten(String longUrl) {
		System.out.println("vai encurtar "+longUrl);
		URL simpleURL = null;
		HttpsURLConnection url = null;
		BufferedInputStream bStream = null;
		StringBuffer resultString = new StringBuffer("");
		String inputString = "{\"longUrl\":\""
				+ longUrl
				+ "\"}";
		try {
			simpleURL = new URL(
					"https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyCmygcUE8o7ldKgBK7CKu0K3NJ6464dSqo");
			url = (HttpsURLConnection) simpleURL.openConnection();
			url.setDoOutput(true);
			url.setRequestProperty("content-type", "application/json");
			PrintWriter pw = new PrintWriter(url.getOutputStream());
			pw.print(inputString);
			pw.close();
		} catch (Exception ex) {
			return "Exception in Connecting to API";
		}
		try {
			bStream = new BufferedInputStream(url.getInputStream());
			int i;
			while ((i = bStream.read()) >= 0) {
				resultString.append((char) i);
			}
		} catch (Exception ex) {
			return "Exception in Reading Result";
		}

		List<String> list = Util.searchURL(resultString.toString().replace("\""," "));
		if(list.size() == 2){
			return list.get(0);
		}
		
		return "";
	}


}
