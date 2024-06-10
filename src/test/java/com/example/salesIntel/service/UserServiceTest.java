package com.example.salesIntel.service;

import com.example.salesIntel.model.User;
import com.example.salesIntel.model.dtos.UserDTO;
import com.example.salesIntel.repository.UserRepository;
import com.example.salesIntel.utils.SalesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;

    private List<User> users;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.encode(any())).thenReturn("passwordEncode");
        users = List.of(
                User.builder()
                        .id(1L)
                        .company("Sales Intel")
                        .email("admin@salesintel.com")
                        .password("admin")
                        .build(),
                User.builder()
                        .id(2L)
                        .company("Wet Water")
                        .email("admin@wetwater.com")
                        .password("admin")
                        .build(),
                User.builder()
                        .id(3L)
                        .company("Phosphet")
                        .email("admin@phosphet.com")
                        .password("admin")
                        .build());
        when(repository.findAll()).thenReturn(users);
        when(repository.findById(any())).thenReturn(Optional.empty());
        when(repository.findByEmail(any())).thenReturn(Optional.empty());

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(users.get(0)));
        when(repository.findById(2L)).thenReturn(Optional.ofNullable(users.get(1)));
        when(repository.findById(3L)).thenReturn(Optional.ofNullable(users.get(2)));

        when(repository.findByEmail("admin@salesintel.com")).thenReturn(Optional.ofNullable(users.get(0)));
        when(repository.findByEmail("admin@wetwater.com")).thenReturn(Optional.ofNullable(users.get(1)));
        when(repository.findByEmail("admin@phosphet.com")).thenReturn(Optional.ofNullable(users.get(2)));

        when(repository.findByCompany("Sales Intel")).thenReturn(Optional.ofNullable(users.get(0)));
        when(repository.findByCompany("Wet Water")).thenReturn(Optional.ofNullable(users.get(1)));
        when(repository.findByCompany("Phosphet")).thenReturn(Optional.ofNullable(users.get(2)));
    }

    @Test
    void getAllUsers() {
        List<User> result = service.getAllUsers();

        verify(repository, times(1)).findAll();
        assertArrayEquals(result.toArray(), users.toArray());
    }

    @Test
    void getUserById() throws SalesException {
        assertEquals(service.getUserById(1L), users.get(0));
    }
    @Test
    void getUserByIdException() {
        Long id = Math.subtractExact(4L, 100L);
        assertThrows(SalesException.class, () -> service.getUserById(id));
    }

    @Test
    void getUserByEmail() throws SalesException {
        assertEquals(service.getUserByEmail("admin@phosphet.com"), users.get(2));
    }

    @Test
    void getUserByEmailException() {
        assertThrows(SalesException.class, () -> service.getUserByEmail("admin@admin.com"));
    }

    @Test
    void getUserByCompany() throws SalesException {
        assertEquals(service.getUserByCompany("Sales Intel"), users.get(0));
    }

    @Test
    void getUserByCompanyException() {
        assertThrows(SalesException.class, () -> service.getUserByCompany("Aguas Norte"));
    }

    @Test
    void createUser() throws SalesException {
        UserDTO userDTO = new UserDTO("Marquinhos Finacimentos", "admin@marfin.br", "admin");
        User user = User.builder()
                .company("Marquinhos Finacimentos")
                .email("admin@marfin.br")
                .password("admin")
                .build();
        when(repository.save(any())).thenReturn(user);

        service.createUser(userDTO);

        verify(repository, times(1)).findByEmail(user.getEmail());
        verify(repository, times(1)).save(any());

    }

    @Test
    void updateUser() throws SalesException {
        UserDTO userDTO = new UserDTO("Marquinhos Finacimentos", "admin@marfin.br", "admin");
        User user = User.builder()
                .company("Marquinhos Finacimentos")
                .email("admin@marfin.br")
                .password("admin")
                .build();
        when(repository.save(any())).thenReturn(user);

        service.updateUser(1L, userDTO);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any());
    }

    @Test
    void deleteUser() {
        service.deleteUser(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}