package br.com.fa7.twitter.util;

public class FakeUrlShortener implements URLShortener {

	@Override
	public String shorten(String url) {
		return url;
	}

}
