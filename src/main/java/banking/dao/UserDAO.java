package banking.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banking.entity.Users;

@Repository
public class UserDAO {
	@Autowired
	private EntityManager entityManager;

	public Users findUserAccount(String userName) {
		try {
			String sql = "Select e from users e where e.userName=:userName";
			Query query = entityManager.createQuery(sql);
			query.setParameter("userName", userName);
			return (Users) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
	}
}
