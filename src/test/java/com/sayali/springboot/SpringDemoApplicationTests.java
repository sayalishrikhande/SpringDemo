package com.sayali.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.sayali.springboot.models.*;

@SpringBootTest
class SpringDemoApplicationTests {

	RestTemplate restTemplate = new RestTemplate();
	
	@Value("${springdemo.services.url}")
	private String baseURL;
	
	
	public SpringDemoApplicationTests() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void testGetRecipe() {
		System.out.println(baseURL);
		Recipe recipe =  restTemplate.getForObject(baseURL+"1", Recipe.class);
		System.out.println("Name "+recipe.getName());
		assertNotNull(recipe);
		System.out.println("Name "+recipe.getName());
		assertEquals("Tea", recipe.getName());
	}
	
	@Test
	public void testCreateRecipe() {
		Recipe recipe = new Recipe();
		recipe.setName("Rice");
		recipe.setIngredients("Rice and water");
		recipe.setInstructions("Boil water and rice");
		recipe.setServings(5);
		recipe.setUserID(1);
		recipe.setVeg(1);;
		Recipe newRecipe = restTemplate.postForObject(baseURL, recipe,Recipe.class);
		assertNotNull(newRecipe);
		assertNotNull(newRecipe.getId());
		assertEquals("Rice", newRecipe.getName());
	}
	
	@Test
	public void testUpdateRecipe() {
		
		RestTemplate restTemplate1 = new RestTemplate();

		Recipe recipe1 =  restTemplate1.getForObject(baseURL+"2", Recipe.class);
		
		recipe1.setServings(4);

		//restTemplate1.put("http://localhost:8080/recipes/", recipe1);
		restTemplate1.put(baseURL+"{id}", recipe1, recipe1.getId());
		
	}
	
	/*
	 * @Test public void searchRecipe() { Recipe recipe = restTemplate. }
	 */

}
