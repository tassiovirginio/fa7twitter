package br.com.fa7.twitter.business.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.fa7.twitter.business.dao.util.HibernateDAOGenerico;
import br.com.fa7.twitter.entities.Message;
import br.com.fa7.twitter.entities.User;

@Component
public class MessageDAO extends HibernateDAOGenerico<Message, Long> {
	@SuppressWarnings("unchecked")
	public List<Message> findAllFromUserViaHQL(User user){
		return (List<Message>) getSession()
		.createQuery("FROM Message WHERE user = ? order by id desc")
		.setParameter(0, user)
		.list();
	}
}