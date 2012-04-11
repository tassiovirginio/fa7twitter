package br.com.fa7.twitter.business;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.twitter.business.dao.UserDAO;
import br.com.fa7.twitter.entities.User;

@Component
@Transactional 
public class UserBusiness {
	
	@Autowired
	private UserDAO userDAO; 
	
	public int size(){
		return userDAO.listAll().size();
	}
	
	public void save(User user){
		userDAO.save(user);
	}
	
	public List<User> listAll(){
		return userDAO.listAll();
	}
	
	public User findById(Long id){
		return userDAO.findById(id);
	}

	public List<User> findByName(String search) {
		return userDAO.findByCriteria(Restrictions.like("name", "%"+search+"%"));
	}

}
