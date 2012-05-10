package br.com.fa7.twitter.util;

import java.io.BufferedInputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class Util {

	public static class UrlShortenerResult extends GenericJson {
		@Key("id")
		public String shortUrl;
	}

	public String shorten(String longUrl) {
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
		}

		catch (Exception ex) {
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

		List<String> list = searchURL(resultString.toString().replace("\""," "));
		if(list.size() == 2){
			return list.get(0);
		}
		
		return "";
	}


	public List<String> searchURL(String text) {
		List<String> list = new ArrayList<String>();
		Pattern p = Pattern
				.compile("(\\A|\\s)((http|https|ftp|mailto):\\S+)(\\s|\\z)");
		String[] texts = text.split(" ");
		for (String t : texts) {
			Matcher m = p.matcher(t);
			if (m.matches()) {
				list.add(t.substring(m.start(), m.end()));
			}
		}
		return list;
	}

	public static void main(String[] args) {
		Util util = new Util();

		String texto = "<a> http://tassiovirginio.com </a>";
		List<String> list = util.searchURL(texto);
		for (String s : list) {
			System.out.println(s);
			System.out.println(util.shorten(s));
		}

	}

}
