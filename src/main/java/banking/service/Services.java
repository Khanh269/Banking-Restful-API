package banking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banking.entity.TransHistory;
import banking.entity.Users;
import banking.repository.TransHistoryRepository;
import banking.repository.UsersRepository;

@Service
public class Services {
	@Autowired
	private UsersRepository repository;
	@Autowired
	private TransHistoryRepository tRepository;

	public long getBalance(int userId) {
		Users user = repository.getOne(userId);
		return user.getBalance();
	}

	public Users saveUser(Users user) {
		return repository.save(user);

	}

	public List<Users> getUsers() {
		return repository.findAll();
	}

	public Users getUserById(int userId) {
		return repository.findById(userId).orElse(null);
	}

	public String deleteUser(int userId) {
		repository.deleteById(userId);
		return "Users removed !! " + userId;
	}

	public Users updateUser(Users user) {
		Users existingUsers = repository.findById(user.getUserId()).orElse(null);
		existingUsers.setUserName(user.getUserName());
		existingUsers.setBalance(user.getBalance());
		return repository.save(existingUsers);
	}

	public String deposit(int userId, int amount) {
		try {
			Users user = getUserById(userId);
			long newBalance = user.getBalance() + amount;
			user.setBalance(newBalance);
			repository.save(user);

			TransHistory tHistory = new TransHistory();
			tHistory.setUserId(userId);
			tHistory.setAmount(amount);
			tHistory.setTransTime(LocalDateTime.now());
			tRepository.save(tHistory);

			return ("Giao dich thanh cong, so du cua quy khach la:" + user.getBalance());
		} catch (Exception e) {
			e.printStackTrace();
			return "Giao dich that bai, xin thu lai";
		}
	}

	public String withdraw(int userId, int amount) {
		Users user = getUserById(userId);
		TransHistory tHistory;
		long newBalance;
		if (user.getBalance() >= amount) {
			tHistory = new TransHistory();
			newBalance = user.getBalance() - amount;
			user.setBalance(newBalance);
			repository.save(user);

			tHistory = new TransHistory();
			tHistory.setUserId(userId);
			tHistory.setAmount(-amount);
			tHistory.setTransTime(LocalDateTime.now());
			tRepository.save(tHistory);

			return ("Giao dich thanh cong, so du cua quy khach la:" + user.getBalance());
		} else {
			return "So du khong du";
		}
	}

	public String transfer(int userId1, int userId2, int amount) {
		Users userTransfer = getUserById(userId1);
		try {
			withdraw(userId1, amount);
			deposit(userId2, amount);
			return ("Giao dich thanh cong, so du cua quy khach la:" + userTransfer.getBalance());
		} catch (Exception e) {
			e.printStackTrace();
			return "So du khong du";
		}

	}

	public List<TransHistory> transHistory(int userId) {
		return tRepository.getTransHistory(userId);
	}
}
