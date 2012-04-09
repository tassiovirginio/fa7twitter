package br.com.fa7.twitter;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fa7.twitter.business.UserBusiness;
import br.com.fa7.twitter.entities.User;
import br.com.fa7.twitter.pages.PrincipalPage;

import com.google.code.jqwicket.JQComponentOnBeforeRenderListener;
import com.google.code.jqwicket.JQContributionConfig;

@Component
public class WicketApplication extends WebApplication{
	
	@Autowired
	private UserBusiness userBusiness;
	
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
		
		populationDB();
	}
	
	
	private void populationDB(){
		userBusiness.save(new User("Tassio","tassio","123","tassio@fa7.org"));
		userBusiness.save(new User("Tiago","tiago","123","tiago@fa7.org"));
		
	}
}
