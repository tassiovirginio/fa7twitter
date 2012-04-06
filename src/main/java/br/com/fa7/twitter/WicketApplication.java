package br.com.fa7.twitter;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.stereotype.Component;

import br.com.fa7.twitter.pages.PrincipalPage;

import com.google.code.jqwicket.JQComponentOnBeforeRenderListener;
import com.google.code.jqwicket.JQContributionConfig;

@Component
public class WicketApplication extends WebApplication{
	
	@Override
	public Class<PrincipalPage> getHomePage(){
		return PrincipalPage.class;
	}

	@Override
	public void init(){
		
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8"); 
        getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		
		JQContributionConfig config = new JQContributionConfig().withDefaultJQueryUi(); 
		getComponentPreOnBeforeRenderListeners().add(new JQComponentOnBeforeRenderListener(config));
		
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		getDebugSettings().setAjaxDebugModeEnabled(true);
	}
}
