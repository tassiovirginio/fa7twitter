package br.com.fa7.twitter.business.dao;

import org.springframework.stereotype.Component;

import br.com.fa7.twitter.business.dao.util.HibernateDAOGenerico;
import br.com.fa7.twitter.entities.UserFollow;

@Component
public class UserFollowDAO extends HibernateDAOGenerico<UserFollow, Long> {}
