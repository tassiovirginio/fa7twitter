package br.com.fa7.twitter;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.twitter.business.MessageBusiness;
import br.com.fa7.twitter.business.UserBusiness;
import br.com.fa7.twitter.business.exception.BusinessException;
import br.com.fa7.twitter.entities.Message;
import br.com.fa7.twitter.entities.User;
import br.com.fa7.twitter.pages.FindUserPage;
import br.com.fa7.twitter.pages.LoginPage;
import br.com.fa7.twitter.pages.PrincipalPage;
import br.com.fa7.twitter.pages.ProfilePage;
import br.com.fa7.twitter.pages.RegisterPage;

import com.google.code.jqwicket.JQComponentOnBeforeRenderListener;
import com.google.code.jqwicket.JQContributionConfig;

@Component
public class WicketApplication extends WebApplication{
	
	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private MessageBusiness messageBusiness;
	
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
		
		mountPage("/user/${login}", ProfilePage.class);
		mountPage("/find", FindUserPage.class);
		mountPage("/register", RegisterPage.class);
		mountPage("/login", LoginPage.class);
		
		populationDB();
	}
	
	@Transactional
	private void populationDB() {
		try {
			User tassio = new User("Tássio","tassio","123","tassio@fa7.org");
			userBusiness.newUser(tassio);
			User tiago = new User("Tiago","tiago","123","tiago@fa7.org");
			userBusiness.newUser(tiago);
			User luana = new User("Luana","luana","123","luana@fa7.org");
			userBusiness.newUser(luana);
			User juliana = new User("Juliana","juliana","123","juliana@fa7.org");
			userBusiness.newUser(juliana);
	
			messageBusiness.save(new Message("Olá do Tássio", tassio));
			messageBusiness.save(new Message("Mensagem do Tássio", tassio));
			messageBusiness.save(new Message("Olá do Tiago", tiago));
			messageBusiness.save(new Message("Tudo ok", tiago));
			messageBusiness.save(new Message("Tudo certinho?", luana));
			messageBusiness.save(new Message("Funciona?", juliana));
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	} 
}
