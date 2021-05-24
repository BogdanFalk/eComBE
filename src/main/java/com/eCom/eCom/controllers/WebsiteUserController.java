package com.eCom.eCom.controllers;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.eCom.eCom.entities.WebsiteUser;
import com.eCom.eCom.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.querydsl.core.types.Predicate;

@RestController
public class WebsiteUserController {

  private final UserRepository userRepository;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  public WebsiteUserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/users/{id}")
  public WebsiteUser findUserById(@PathVariable("id") WebsiteUser user) {
    return user;
  }

  @GetMapping("/users")
  public Page<WebsiteUser> findAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  @GetMapping("/sortedusers")
  public Page<WebsiteUser> findAllUsersSortedByName() {
    Pageable pageable = PageRequest.of(0, 5, Sort.by("name"));
    return userRepository.findAll(pageable);
  }

  @PostMapping("/filterByEmail")
  public Page<WebsiteUser> findByEmail(@RequestParam("email") String email, Pageable pageable) {

    if (email == null) {
      return userRepository.findAll(pageable);
    } else {
      return userRepository.findByEmail(email, pageable);
    }
  }

  @PostMapping("/users/addUser")
  public String addUser(@RequestBody WebsiteUser user) {
    jdbcTemplate
        .execute("INSERT INTO WebsiteUser (email,name) VALUES ('" + user.getEmail() + "','" + user.getName() + "')");
    System.console().printf(user.getEmail());
    return ("ok");
  }
}