package br.com.fa7.twitter.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.twitter.business.dao.MessageDAO;
import br.com.fa7.twitter.entities.Message;

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

}
