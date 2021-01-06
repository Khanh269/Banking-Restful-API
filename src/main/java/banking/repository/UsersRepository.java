package banking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import banking.entity.Users;


public interface UsersRepository extends JpaRepository <Users,Integer>{
@Query ("SELECT t FROM Users t WHERE t.userName=:userName")
 Users getUserByName(String userName);
	


}
