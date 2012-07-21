package br.com.fa7.twitter.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	public static List<String> searchURL(String text) {
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

}
