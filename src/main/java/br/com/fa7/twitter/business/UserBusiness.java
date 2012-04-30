package br.com.fa7.twitter.business;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.twitter.business.dao.UserDAO;
import br.com.fa7.twitter.business.exception.BusinessException;
import br.com.fa7.twitter.entities.User;

@Component
@Transactional
public class UserBusiness {

	@Autowired
	private UserDAO userDAO;

	public int size() {
		return userDAO.listAll().size();
	}

	public void newUser(User user) throws BusinessException {
		validarDadosNovoUsuario(user);
		save(user);
	}
	
	private void save(User user) {
		userDAO.save(user);
	}

	public List<User> listAll() {
		return userDAO.listAll();
	}

	public void delete(User user) {
		userDAO.delete(user);
	}

	public User findById(Long id) {
		return userDAO.findById(id);
	}
	
	public User findByEmail(String email) {
		List<User> result = userDAO.findByCriteria(Restrictions.eq("email",
				email));
		if (result.isEmpty())
			return null;
		return result.get(0);
	}

	public User findByLogin(String login) {
		List<User> result = userDAO.findByCriteria(Restrictions.like("login",
				login));
		if (result.isEmpty())
			return null;
		return result.get(0);
	}

	public List<User> findByName(String search) {
		return userDAO.findByCriteria(Restrictions.ilike("name", search, MatchMode.ANYWHERE));
	}

	public void clearAll() {
		List<User> list = listAll();
		for (User u : list) {
			delete(u);
		}
	}

	public User login(String login, String password) throws BusinessException {
		User user = this.findByLogin(login);
		if (user == null || !password.equals(user.getPassword()))
			throw new BusinessException("O login ou a senha inserido está incorreto.");
		return user;
	}

	public void follow(User follower, User userToFollow) {
		follower.getFollowing().add(userToFollow);
		save(follower);
	}

	public void unfollow(User follower, User userToUnfollow) {
		follower.getFollowing().remove(userToUnfollow);
		save(follower);
	}

	public void validarDadosNovoUsuario(User user) throws BusinessException {
		user.setEmail(user.getEmail().toLowerCase());
		user.setLogin(user.getLogin().toLowerCase());
		if (StringUtils.isEmpty(user.getName())) {
			throw new BusinessException("Nome obrigatório.");
		}
		if (StringUtils.isEmpty(user.getLogin())) {
			throw new BusinessException("Login obrigatório.");
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			throw new BusinessException("Senha obrigatória.");
		}
		if (!EmailValidator.getInstance().isValid(user.getEmail())) {
			throw new BusinessException("Email '" + user.getEmail()
					+ "' inválido.");
		}
		if (this.findByLogin(user.getLogin()) != null) {
			throw new BusinessException("Login '" + user.getLogin()
					+ "' indisponível.");
		}
		if (this.findByEmail(user.getEmail()) != null) {
			throw new BusinessException("Email '" + user.getEmail()
					+ "' já cadastrado.");
		}
	}

}
