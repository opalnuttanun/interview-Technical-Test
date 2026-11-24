package com.example.user;

import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.user.Service.UserService;
import com.example.user.Controller.*;

class UserControllerTest {

    private static final String VALID_USER = """
        {
            "name": "Jane Smith",
            "username": "jane",
            "email": "jane@example.com",
            "phone": "081-234-5678",
            "website": "example.com"
        }
        """;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(new UserService()))
            .setControllerAdvice(new ApiExceptionHandler())
            .build();
    }

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[2].id").value(3));
    }

    @Test
    void getUserDetails() throws Exception {
        mockMvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Leanne Graham"))
            .andExpect(jsonPath("$.username").value("Bret"))
            .andExpect(jsonPath("$.email").value("Sincere@april.biz"))
            .andExpect(jsonPath("$.phone").value("1-770-736-8031 x56442"))
            .andExpect(jsonPath("$.website").value("hildegard.org"));
    }

    @Test
    void postUser() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(VALID_USER))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(4))
            .andExpect(jsonPath("$.name").value("Jane Smith"))
            .andExpect(jsonPath("$.username").value("jane"))
            .andExpect(jsonPath("$.email").value("jane@example.com"))
            .andExpect(jsonPath("$.phone").value("081-234-5678"))
            .andExpect(jsonPath("$.website").value("example.com"));

        mockMvc.perform(get("/users/4"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("jane@example.com"));
    }
}