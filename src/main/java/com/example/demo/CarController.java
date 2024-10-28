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
@RequestMapping("/car")
public class CarController {
	@Autowired
	private CarRepo userRepo;
	@GetMapping
	public List<Car> getAllUsers(){
		return userRepo.findAll();
	}
	@GetMapping("/{id}")
	public Car getByID(@PathVariable int id){
		return userRepo.findById(id).orElse(null);
	}
	@PostMapping
	public Car saveUser(@RequestBody Car user) {
		return userRepo.save(user);
	}
	@PutMapping("/{id}")
	public Car update(@PathVariable int id, @RequestBody Car user) {
		Car users=userRepo.findById(id).orElse(null);
		if(user.getName()!=null) {
			users.setName(user.getName());
		}
		if(user.getType()!=null) {
			users.setType(user.getType());
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
	public List<Car> getPaginated(@PathVariable int pageNo, @PathVariable int pageSize){
		Pageable pageable=PageRequest.of(pageNo, pageSize);
		Page<Car> result=userRepo.findAll(pageable);
		return result.hasContent()?result.getContent():new ArrayList<Car>();
	}
	@GetMapping("/sort")
	public List<Car> getSorted(@RequestParam String field, @RequestParam String direction){
		Direction sort=direction.equalsIgnoreCase("desc")?Direction.DESC:Direction.ASC;
		return userRepo.findAll(Sort.by(sort, field));
	}
}
