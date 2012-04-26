package br.com.fa7.twitter.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public User() {
	}
	
	public User(String name, String login, String password, String email) {
		super();
		this.name = name;
		this.login = login;
		this.password = password;
		this.email = email;
	}

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String login;

	private String password;

	private String email;
		
	//Usuarios seguidos
	@ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="user_follow",
		joinColumns={
			@JoinColumn(name="user")}, 
			inverseJoinColumns={@JoinColumn(name = "follow")}
		)
	private Set<User> following = new HashSet<User>();
	
	//Usuarios seguidores
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="following")
	private Set<User> followers = new HashSet<User>();

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

	/**
	 * Lista de usuarios que seguem este usuario
	 */
	public Set<User> getFollowers() {
		return followers;
	}

	/**
	 * Lista de usuarios que este usuario segue
	 */
	public Set<User> getFollowing() {
		return following;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", login=" + login
				+ ", password=" + password + ", email=" + email + "]";
	}

	public boolean isFollowing(User user) {
		return this.following.contains(user);
	}
}


