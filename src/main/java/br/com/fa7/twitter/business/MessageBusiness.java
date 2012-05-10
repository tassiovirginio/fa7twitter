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
		final String HTML_LINK = "<a href=\"localhost:9999/user/&/\">^&</a>";
		//Iniciado por arroba e um caracter minusculo, seguido por caracteres minusculos ou numeros   
		final String REGEX_PATTERN = "@[a-z]+([a-z]|[0-9])*";
		
		Pattern userPattern = Pattern.compile(REGEX_PATTERN);
		String messageText = message.getMsg();
		Matcher match = userPattern.matcher(messageText);
		
		while (match.find()) {
			String userNameWithoutAt = match.group().substring(1);
			
			StringBuffer preparedMessage = new StringBuffer();
			preparedMessage.append(messageText.substring(0, match.start()));
			preparedMessage.append(HTML_LINK.replace("&", userNameWithoutAt));
			preparedMessage.append(messageText.substring(match.end()));
			messageText = preparedMessage.toString();
			
			match = userPattern.matcher(messageText);
		}
		return messageText.replace("^", "@");
	}

}
