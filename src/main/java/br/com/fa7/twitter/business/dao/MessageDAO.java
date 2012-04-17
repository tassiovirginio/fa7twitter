package br.com.fa7.twitter.business.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import br.com.fa7.twitter.business.dao.util.HibernateDAOGenerico;
import br.com.fa7.twitter.entities.Message;
import br.com.fa7.twitter.entities.User;

@Component
public class MessageDAO extends HibernateDAOGenerico<Message, Long> {
	public List<Message> findAllFromUserViaHQL(User user){
		Session s = getSession();
		Query q = s.createQuery("FROM Message WHERE user = ?");
		q.setParameter(0, user);
		List<Message> msgLst = (List<Message>) q.list();
		return msgLst;
	}
}