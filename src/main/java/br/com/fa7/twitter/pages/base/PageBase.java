package br.com.fa7.twitter.pages.base;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import br.com.fa7.twitter.entities.Message;
import br.com.fa7.twitter.pages.PrincipalPage;

public class PageBase extends WebPage {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "rawtypes", "serial" })
	public PageBase() {

		Link linkKanban = new Link("lkKanban") {
			@Override
			public void onClick() {
			}
		};
		add(linkKanban);

		Link lkManager = new Link("lkManager") {
			@Override
			public void onClick() {
			}
		};
		add(lkManager);

		Link lkSobre = new Link("lkSobre") {
			@Override
			public void onClick() {
				setResponsePage(new PrincipalPage());
			}
		};
		add(lkSobre);
		
		AjaxLink ajaxLink = new AjaxLink("lkNewActivity") {
			@Override
			public void onClick(AjaxRequestTarget target) {
			}
		};
		add(ajaxLink);

	}
	
}
