package com.sayali.springboot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sayali.springboot.models.RecipeSearch;
import com.sayali.springboot.models.AthenticationResponse;
import com.sayali.springboot.models.AuthenticationRequest;
import com.sayali.springboot.models.Recipe;
import com.sayali.springboot.repos.RecipeRepository;
import com.sayali.springboot.services.MyUserDetailsService;
import com.sayali.springboot.util.DemoUtil;

@RestController
//@RequestMapping("/api")
public class DemoController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private DemoUtil demoUtil;
	
	@Autowired
	RecipeRepository repo;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);
	
	@RequestMapping(value = "/recipes/", method = RequestMethod.GET)
	public List<Recipe> getRecipes() {
		List<Recipe> myList = repo.findAll();
		
		/* System.out.println("Here   "+myList.get(0).isVeg()); */
		
		return repo.findAll();
	}

	@RequestMapping({ "/hello" })
	public String hello() {
		return "Hello World!";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getUserpassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
			
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwtToken = demoUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AthenticationResponse(jwtToken));
	}
	
	@RequestMapping(value = "/recipes/{id}", method = RequestMethod.GET)
	public Optional<Recipe> getRecipe(@PathVariable("id") int id) {
		LOGGER.info("Finding recipe by id : "+ id);
		return repo.findById(id);
	}
	
	@RequestMapping(value = "/recipes/", method = RequestMethod.POST)
	public Recipe createRecipe(@RequestBody Recipe recipe) {
		return repo.save(recipe);
	}
	
	@RequestMapping(value = "/recipes/{id}", method = RequestMethod.PUT)
	public Recipe updateRecipe(@RequestBody Recipe recipe) {
		return repo.save(recipe);
	}
	
	@RequestMapping(value = "/recipes/{id}", method = RequestMethod.DELETE)
	public String deleteRecipe(@PathVariable("id") int id) {
		repo.deleteById(id);
		return "Deleted id : " + id;
	}
	
	@RequestMapping(value = "/recipes/search", method = RequestMethod.GET)
	public List<Recipe> searchRecipe(@RequestBody RecipeSearch recipe){
		
		System.out.println("Here");
		
		List<Recipe> recipes = repo.findAll();
		
		System.out.println("jjciijci"+recipes.size());
		
		List<Recipe> recipes1 = null;
		
		if(recipe.getName()!=null) {
			recipes1 = recipes.stream().filter( x -> x.getName().toLowerCase().contains(recipe.getName().toLowerCase())).collect(Collectors.toList());
			recipes = recipes1;
		}
		
		if(recipe.getIncludeIngredients()!=null) {
			recipes1 = recipes.stream().filter( x -> x.getIngredients().toLowerCase().contains(recipe.getIncludeIngredients())).collect(Collectors.toList());
			recipes = recipes1;
		}
		
		if(recipe.getServings()>0) {
			recipes1 = recipes.stream().filter( x -> x.getServings() == recipe.getServings()).collect(Collectors.toList());
			recipes = recipes1;
		}
		
		if(recipe.getExcludeIngredients()!=null) {
			System.out.println("Here nehggkerjlkgrlgrlrbjljb");
			recipes1 = recipes.stream().filter( x -> !(x.getIngredients().toLowerCase().contains(recipe.getExcludeIngredients().toLowerCase()))).collect(Collectors.toList());
			recipes = recipes1; 
		}
		
		if(recipe.getInstructions()!=null) {
			System.out.println("Here nehggkerjlkgrlgrlrbjljb");
			recipes1 = recipes.stream().filter( x -> x.getInstructions().toLowerCase().contains(recipe.getInstructions().toLowerCase())).collect(Collectors.toList());
			recipes = recipes1; 
		}
		
		if(recipe.getVeg()>=0) {
			System.out.println("Here  in Vegggg");
			recipes1 = recipes.stream().filter(x -> x.getVeg() == recipe.getVeg()).collect(Collectors.toList());
			System.out.println("After filter");
		}
		
		/*
		 * if(recipe.isVeg()) { System.out.println("isVeg criteria"); recipes1 =
		 * recipes.stream().filter( x ->
		 * x.getInstructions().toLowerCase().contains(recipe.getInstructions().
		 * toLowerCase())).collect(Collectors.toList()); recipes = recipes1; }
		 */
		
		//List<Recipe> recipeResult = recipes.stream().filter(x -> ((x.getIngredients().toLowerCase().contains(recipe.getIncludeIngredients().toLowerCase()))) && (x.getName().contains(recipe.getName()))).collect(Collectors.toList());
		
		//System.out.println("renceje"+recipeResult.size());
		
		if (recipes1!=null) {
			System.out.println("renceje"+recipes1.size());
		return recipes1;
		}
		return null;
		
	}
	
	

}
