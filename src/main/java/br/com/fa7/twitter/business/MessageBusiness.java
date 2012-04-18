package br.com.fa7.twitter.business;

import java.util.List;

import org.hibernate.criterion.Restrictions;
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
		//List<Message> retorno = messageDAO.findByCriteria(Restrictions.eq("user", user));
		List<Message> retorno = messageDAO.findAllFromUserViaHQL(user);
		return retorno;
	}

	public void clearAll() {
		List<Message> list = listAll();
		for (Message m : list) {
			messageDAO.delete(m);
		}
	}

}
