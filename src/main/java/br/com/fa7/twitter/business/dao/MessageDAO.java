package br.com.fa7.twitter.business.dao;

import org.springframework.stereotype.Component;

import br.com.fa7.twitter.business.dao.util.HibernateDAOGenerico;
import br.com.fa7.twitter.entities.Message;

@Component
public class MessageDAO extends HibernateDAOGenerico<Message, Long> {}