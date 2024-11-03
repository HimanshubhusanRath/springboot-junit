package com.hr.microservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr.microservices.SpringBootMain;
import com.hr.microservices.controller.UserController;
import com.hr.microservices.domain.User;
import com.hr.microservices.exceptions.UserNotFoundException;
import com.hr.microservices.exceptions.handler.GlobalExceptionHandler;
import com.hr.microservices.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpringBootMain.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("REST API Tests")
public class UserControllerTest {

    private static MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    final User u1 = new User(100L, "Himanshu", "Bangalore");
    final User u2 = new User(200L, "Rajesh", "Hyderabad");
    final User u3 = new User(300L, "Amit", "Bolangir");

    @BeforeAll
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    public void getAllUsers_Success() throws Exception {
        final List<User> users = new ArrayList<>(Arrays.asList(u1, u2, u3));
        Mockito.when(userRepository.findAll()).thenReturn(users);

        // Check if the api response status is 200 (ok), the result contains 3 elements and the name of the 3rd user is 'Amit'
        mockMvc.perform(MockMvcRequestBuilders.get("/user/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("Amit")));
    }

    @Test
    @Tag("dev")
    public void getUserById_Success() throws Exception {
        Mockito.when(userRepository.findById(u2.getId())).thenReturn(Optional.of(u2));

        // Check if the api response status is 200 (ok), the result is not null and the name of the user is 'Himanshu'
        mockMvc.perform(MockMvcRequestBuilders.get("/user/200").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Rajesh")));
    }

    @Test
    public void saveUser_success() throws Exception {
        final User user = new User(null, "Ashok", "Chennai");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // As the user DTO can not be used directly, we need to convert it to Json string
        final String requestBody = objectMapper.writeValueAsString(user);
        System.out.println("Request : " + requestBody);

        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(requestBody);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Ashok")));
    }

    @Test
    public void updateUser_success() throws Exception {
        // Mock the find by id method to return the user by id
        Mockito.when(userRepository.findById(u1.getId())).thenReturn(Optional.of(u1));
        // Update the user
        final User updatedUser = new User(u1.getId(), "Mahesh", "Puri");
        // Mock the save method to return the updated user
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(updatedUser);

        final String requestBody = objectMapper.writeValueAsString(updatedUser);
        final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/user").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(requestBody);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Mahesh")));
    }

    @Test
    public void deleteUser_success() throws Exception {
        Mockito.when(userRepository.findById(u1.getId())).thenReturn(Optional.of(u1));
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/100").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteUser_failure_notFound() throws Exception {
        Mockito.when(userRepository.findById(u1.getId())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/101")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> Assertions.assertTrue(
                        result.getResolvedException() instanceof UserNotFoundException
                ))
                .andExpect(result -> Assertions.assertEquals("User not found",
                        result.getResolvedException().getMessage()));
    }

}
