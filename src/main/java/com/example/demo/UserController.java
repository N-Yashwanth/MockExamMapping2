package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepo userRepo;
	@GetMapping
	public List<User> getAllUsers(){
		return userRepo.findAll();
	}
	@GetMapping("/{id}")
	public User getByID(@PathVariable int id){
		return userRepo.findById(id).orElse(null);
	}
	@PostMapping
	public User saveUser(@RequestBody User user) {
		return userRepo.save(user);
	}
	@PutMapping("/{id}")
	public User update(@PathVariable int id, @RequestBody User user) {
		User users=userRepo.findById(id).orElse(null);
		if(user.getFirst()!=null) {
			users.setFirst(user.getFirst());
		}
		if(user.getLast()!=null) {
			users.setLast(user.getLast());
		}
		if(user.getCar()!=null) {
			users.setCar(user.getCar());
		}
		return userRepo.save(users);
	}
	@DeleteMapping("/{id}")
	public String delete(@PathVariable int id) {
		if(userRepo.existsById(id)) {
			userRepo.deleteById(id);
			return "Deleted Successfully";
		}
		else {
			return "Not present";
		}
	}
	@GetMapping("/page/{pageNo}/{pageSize}")
	public List<User> getPaginated(@PathVariable int pageNo, @PathVariable int pageSize){
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<User> result=userRepo.findAll(pageable);
		return result.hasContent()?result.getContent():new ArrayList<User>();
	}
	@GetMapping("/sort")
	public List<User> getSorted(@RequestParam String field, @RequestParam String direction){
		Direction sort=direction.equalsIgnoreCase("desc")?Direction.DESC:Direction.ASC;
		return userRepo.findAll(Sort.by(sort, field));
	}
}
