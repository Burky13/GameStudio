package sk.tsystems.gamestudio.service.userService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import sk.tsystems.gamestudio.entity.User;

@Transactional
public class UserServiceJPA implements UserService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public boolean register(User user) {
		try {
			entityManager.persist(user);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User login(String username, String password) {
		try {
		return entityManager
				.createQuery("select u from User u where u.username = :username and u.password = :password", User.class)
				.setParameter("username", username)
				.setParameter("password", password)
				.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}
	
}
