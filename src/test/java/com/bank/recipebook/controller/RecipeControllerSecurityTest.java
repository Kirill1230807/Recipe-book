package com.bank.recipebook.controller;


import com.bank.recipebook.security.SecurityConfig;
import com.bank.recipebook.service.RecipeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
@Import(SecurityConfig.class)
class RecipeControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecipeService recipeService;

    private static final String URL = "/api/recipes";

    private static final String CREATE_JSON = """
        {
          "title": "Тестовий рецепт",
          "description": "Опис",
          "instructions": "Приготувати страву",
          "prepTime": 30,
          "authorId": 1,
          "categoryId": 1,
          "ingredients": []
        }
        """;

    private static final String UPDATE_JSON = """
        {
          "title": "Оновлений рецепт",
          "description": "Оновлений опис",
          "instructions": "Оновлені інструкції",
          "prepTime": 40,
          "categoryId": 1,
          "ingredients": []
        }
        """;

    @Test
    void adminCanGetAllRecipes() throws Exception {
        when(recipeService.findAll()).thenReturn(List.of());

        mockMvc.perform(get(URL).with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanGetRecipeById() throws Exception {
        when(recipeService.findById(1L)).thenReturn(null);

        mockMvc.perform(get(URL + "/1").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanCreateRecipe() throws Exception {
        mockMvc.perform(post(URL)
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREATE_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void adminCanUpdateRecipe() throws Exception {
        mockMvc.perform(put(URL + "/1")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanDeleteRecipe() throws Exception {
        mockMvc.perform(delete(URL + "/1")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void userCanGetAllRecipes() throws Exception {
        when(recipeService.findAll()).thenReturn(List.of());

        mockMvc.perform(get(URL).with(user("user").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void userCanGetRecipeById() throws Exception {
        when(recipeService.findById(1L)).thenReturn(null);

        mockMvc.perform(get(URL + "/1").with(user("user").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void userCannotCreateRecipe() throws Exception {
        mockMvc.perform(post(URL)
                        .with(user("user").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREATE_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void userCannotUpdateRecipe() throws Exception {
        mockMvc.perform(put(URL + "/1")
                        .with(user("user").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void userCannotDeleteRecipe() throws Exception {
        mockMvc.perform(delete(URL + "/1")
                        .with(user("user").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}
