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
	
	public void delete(User user){
		userDAO.delete(user);
	}
	
	public User findById(Long id){
		return userDAO.findById(id); 
	}

	public User findByLogin(String login) {
		List<User> result = userDAO.findByCriteria(Restrictions.like("login", login));
		if (result.isEmpty())
		  return null;
		return result.get(0);
	}

	public List<User> findByName(String search) {
		return userDAO.findByCriteria(Restrictions.like("name", "%"+search+"%"));
	}
	
	public void clearAll() {
		List<User> list = listAll();
		for (User u : list) {
			userDAO.delete(u);
		}
	}

	public User login(String login, String password) throws Exception {
		User user = this.findByLogin(login);
		if (user == null)
			throw new Exception("Login nao cadastrado");
		if (password.equals(user.getPassword()))
			return user;
		else 
			throw new Exception("Usuario e senha invalidos");
	}
	
	public void follow(User follower, User userToFollow) {
		follower.getFollowing().add(userToFollow);
		userDAO.save(follower);
	}
	
//	public void unfollow(User follower, User userToUnfollow) {
//		List<UserFollow> ufList = userFollowDAO.findByCriteria(
//				Restrictions.and(
//						Restrictions.eq("user", follower), 
//						Restrictions.eq("follow", userToUnfollow)
//						));
//		UserFollow uf = ufList.get(0);
//		userFollowDAO.delete(uf);
//	}
	
}
