package banking.controller;

import banking.entity.TransHistory;
import banking.entity.Users;
import banking.service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class Controllers {

	@Autowired
	private Services service;

	@PostMapping("/addUser")
	public Users addUser(@RequestBody Users user) {
		return service.saveUser(user);
	}

	@GetMapping("/users")
	public List<Users> findAllUserss() {
		return service.getUsers();
	}

	@GetMapping("/userById/{userId}")
	public Users findUsersById(@PathVariable int userId) {
		return service.getUserById(userId);
	}

	@PutMapping("/update")
	public Users updateUser(@RequestBody Users user) {
		return service.updateUser(user);
	}

	@DeleteMapping("/delete/{userId}")
	public String deleteUsers(@PathVariable int userId) {
		return service.deleteUser(userId);
	}

	@PostMapping("/deposit")
	public String deposit(@RequestParam int userId, int amount) {
		return service.deposit(userId, amount);
	}

	@PostMapping("/withdraw")
	public String withdraw(@RequestParam int userId, int amount) {
		return service.withdraw(userId, amount);
	}

	@PostMapping("/transfer")
	public String withdraw(@RequestParam int userId1, int userId2, int amount) {
		return service.transfer(userId1, userId2, amount);
	}

	@GetMapping("/transferHistory")
	public List<TransHistory> transHistory(@RequestParam int userId) {
		return service.transHistory(userId);
	}

	@PostMapping("/login")
	public String login(HttpServletRequest request, HttpSession session) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		Users user = service.gestUserByName(userName);
		boolean isValid = service.checkLogin(userName, password);
		if (isValid) {
			session.setAttribute("user", user);
			return ("Dang nhap thanh cong, User: "+ user.getUserName()+", Password: "+ user.getPassword());
		} else {
			return "Tai khoan hoac mat khau khong dung";
		}
	}

	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpSession session) {
		session.invalidate();
		return "Ban da dang xuat";
	}

	@GetMapping(value = { "/", "/home" })
	public String homepage() {
		return "home";
	}

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
}
