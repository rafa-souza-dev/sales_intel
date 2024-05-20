package com.example.salesIntel.service;

import com.example.salesIntel.model.Category;
import com.example.salesIntel.model.dtos.CategoryDTO;
import com.example.salesIntel.repository.CategoryRepository;
import com.example.salesIntel.utils.SalesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    private List<Category> categories;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categories = List.of(
                Category.builder().id(1).name("legumes").products(List.of()).build(),
                Category.builder().id(2).name("frios").products(List.of()).build(),
                Category.builder().id(3).name("carnes").products(List.of()).build()
        );

        when(repository.findAll()).thenReturn(categories);
        when(repository.findById(any())).thenReturn(Optional.empty());
        when(repository.findByName(any())).thenReturn(Optional.empty());

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(categories.get(0)));
        when(repository.findById(2L)).thenReturn(Optional.ofNullable(categories.get(1)));
        when(repository.findById(3L)).thenReturn(Optional.ofNullable(categories.get(2)));

        when(repository.findByName("legumes")).thenReturn(Optional.ofNullable(categories.get(0)));
        when(repository.findByName("frios")).thenReturn(Optional.ofNullable(categories.get(1)));
        when(repository.findByName("carnes")).thenReturn(Optional.ofNullable(categories.get(2)));

    }

    @Test
    void getAll() {
        List<Category> result = service.getAll();

        assertNotNull(result);
        assertEquals(categories.size(), result.size());
        assertArrayEquals(result.toArray(), categories.toArray());
    }

    @Test
    void getById() throws SalesException {
        assertEquals(service.getById(1L), categories.get(0));
    }

    @Test
    void getByName() throws SalesException {
        assertEquals(service.getByName("carnes"), categories.get(2));
    }

    @Test
    void createCategory() throws SalesException {
        CategoryDTO categoryDTO = new CategoryDTO("bebidas");
        Category category = Category.builder().id(4).name("bebidas").build();
        when(repository.save(any())).thenReturn(category);

        service.createCategory(categoryDTO);

        verify(repository, times(1)).findByName("bebidas");
        verify(repository, times(1)).save(any());
    }

    @Test
    void updateCategory() throws SalesException {
        CategoryDTO categoryDTO = new CategoryDTO("bebidas");
        Category category = Category.builder().id(2).name("bebidas").build();
        when(repository.save(any())).thenReturn(category);

        service.createCategory(categoryDTO);

        verify(repository, times(1)).findByName("bebidas");
        verify(repository, times(1)).save(any());
    }

    @Test
    void deleteCategory() throws SalesException {
        service.deleteCategory(1L);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}