package br.com.fa7.twitter.pages.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.model.IModel;

public abstract class ListView<T> extends org.apache.wicket.markup.html.list.ListView<T>{
	private static final long serialVersionUID = -4574171478740012723L;
	
	public ListView(String id) {
		super(id);
	}

	public ListView(String id, IModel<? extends List<? extends T>> model) {
		super(id, model);
	}

	public ListView(String id, List<? extends T> list) {
		super(id, list);
	}

	public ListView(String id, Set<? extends T> setList) {
		super(id, new ArrayList<T>(setList));
	}

}
