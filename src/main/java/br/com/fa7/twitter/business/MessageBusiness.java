package br.com.fa7.twitter.business;

import java.util.List;
import java.util.regex.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.twitter.business.dao.MessageDAO;
import br.com.fa7.twitter.entities.Message;
import br.com.fa7.twitter.entities.User;

@Component
@Transactional
public class MessageBusiness {
	
	@Autowired
	private MessageDAO messageDAO; 
	
	public int size(){
		return messageDAO.listAll().size();
	}
	
	public void save(Message message){
		messageDAO.save(message);
	}
	
	public List<Message> listAll(){
		return messageDAO.listAll();
	}

	public List<Message> loadByUser(User user) {
		List<Message> retorno = messageDAO.findAllFromUserViaHQL(user);
		return retorno;
	}

	public void clearAll() {
		List<Message> list = listAll();
		for (Message m : list) {
			messageDAO.delete(m);
		}
	}

	public String toExibition(Message message) {
		String messageText = addHTMLLinkAtUsers(message.getMsg());
		return messageText.replace("^", "@");
	}
	
	private String addHTMLLinkAtUsers(String messageText){
		final String DOMAIN = "localhost:9999";
		final String HTML_LINK = "<a href=\"" + DOMAIN + "/user/&/\">^&</a>";
		//Iniciado por arroba e um caracter minusculo, seguido por caracteres minusculos ou numeros   
		final String REGEX_PATTERN = "@[a-z]+([a-z]|[0-9])*";
		
		Pattern userPattern = Pattern.compile(REGEX_PATTERN);
		Matcher matcher = userPattern.matcher(messageText);
		
		if (matcher.find()) {
			String userName = matcher.group();
			String userNameWithoutAt = userName.substring(1);
			
			StringBuffer preparedMessage = new StringBuffer();
			//Parte inicial da mensagem que não será alterada
			preparedMessage.append(messageText.substring(0, matcher.start()));
			//Inserindo o link no lugar do texto "@usuario"
			preparedMessage.append(HTML_LINK.replace("&", userNameWithoutAt));			
			//Parte final da mensagem que não será alterada
			preparedMessage.append(messageText.substring(matcher.end()));
			return messageText = addHTMLLinkAtUsers(preparedMessage.toString());
		}
		return messageText;
	}
}
