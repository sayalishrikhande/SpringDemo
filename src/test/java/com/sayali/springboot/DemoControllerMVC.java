package com.sayali.springboot;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.sayali.springboot.models.Recipe;
import com.sayali.springboot.repos.RecipeRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
public class DemoControllerMVC {
	
	@Autowired
	private MockMvc mockMVC;
	
	@MockBean
	private RecipeRepository repo;

	@Test
	public void testFindAll() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(3);
		recipe.setIngredients("Rice");
		recipe.setIngredients("Rice and water");
		recipe.setInstructions("Boil water and rice");
		recipe.setServings(5);
		recipe.setUserID(1);
		
		List<Recipe> recipes = new ArrayList<>();
		
		recipes.add(recipe);
		
		when(repo.findAll()).thenReturn(recipes);
		
		try {
			
			mockMVC.perform(get("/recipes/")).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
