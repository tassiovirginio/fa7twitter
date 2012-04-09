package br.com.fa7.twitter.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public User(String name, String login, String password, String email) {
		super();
		this.name = name;
		this.login = login;
		this.password = password;
		this.email = email;
	}
	
	public User() {
	}

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	
	private String login;
	
	private String password;
	
	private String email;
	
	//Seguidores
	@OneToMany
	private Set<User> listFollower;
	
	//Seguidos
	@OneToMany
	private Set<User> listFollowed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<User> getListFollower() {
		return listFollower;
	}

	public void setListFollower(Set<User> listFollower) {
		this.listFollower = listFollower;
	}

	public Set<User> getListFollowed() {
		return listFollowed;
	}

	public void setListFollowed(Set<User> listFollowed) {
		this.listFollowed = listFollowed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
