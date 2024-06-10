package com.example.salesIntel.service;

import com.example.salesIntel.model.Category;
import com.example.salesIntel.model.Product;
import com.example.salesIntel.model.User;
import com.example.salesIntel.model.dtos.ProductDTO;
import com.example.salesIntel.repository.ProductRepository;
import com.example.salesIntel.utils.SalesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProductService service;

    List<Product> products;

    @BeforeEach
    void setUp() throws SalesException {
        MockitoAnnotations.openMocks(this);
        products = List.of(
                Product.builder()
                        .id(1L)
                        .name("peito de frango")
                        .purchasePrice(20.0f)
                        .salePrice(20.0f)
                        .quantity(10)
                        .unit("1Kg")
                        .build(),
                Product.builder()
                        .id(2L)
                        .name("iogurte sabor mel natural Batavo")
                        .purchasePrice(2.59f)
                        .salePrice(2.59f)
                        .quantity(25)
                        .unit("170g")
                        .build(),
                Product.builder()
                        .id(3L)
                        .name("ovos")
                        .purchasePrice(12.59f)
                        .salePrice(12.59f)
                        .quantity(10)
                        .unit("cartela 30 unidades")
                        .build()
        );

        Category category = Category.builder().name("frios").products(products.subList(0, 2)).build();

        when(repository.findAll()).thenReturn(products);
        when(repository.findById(any())).thenReturn(Optional.empty());

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(products.get(0)));
        when(repository.findById(2L)).thenReturn(Optional.ofNullable(products.get(1)));
        when(repository.findById(3L)).thenReturn(Optional.ofNullable(products.get(2)));

        when(categoryService.getById(any())).thenReturn(category);
        when(userService.getUserById(any())).thenReturn(User.builder().company("Shadows").products(List.of(products.get(2))).build());
    }

    @Test
    void getAll() {
        List<Product> result = service.getAll();
        assertNotNull(result);
        assertEquals(products.size(), result.size());
        assertArrayEquals(result.toArray(), products.toArray());
    }

    @Test
    void getById() throws SalesException {
        assertEquals(service.getById(1L), products.get(0));
    }

    @Test
    void getProductByUserId() throws SalesException {
        List<Product> result = service.getProductByUserId(1L);
        assertArrayEquals(result.toArray(), List.of(products.get(2)).toArray());
    }

    @Test
    void getProductByCategoryId() throws SalesException {
        List<Product> result = service.getProductByCategoryId(1L);
        assertArrayEquals(result.toArray(), products.subList(0, 2).toArray());
    }

    @Test
    void createProduct() throws SalesException {
        Product product = Product.builder().id(4L).build();
        ProductDTO productDTO = new ProductDTO("test", 2.69f, 2.69f, 1, "1Kg", new Date(), 1, 1L, 1L);
        when(repository.save(any())).thenReturn(product);

        assertDoesNotThrow(() -> service.createProduct(productDTO));

        verify(userService, times(1)).getUserById(1L);
        verify(categoryService, times(1)).getById(1L);
        verify(repository, times(1)).save(any());
    }

    @Test
    void updateProduct() throws SalesException {
        Product product = Product.builder().id(1L).build();
        ProductDTO productDTO = new ProductDTO("test", 2.69f, 2.69f, 1, "1Kg", new Date(), 1, 1L, 1L);
        when(repository.save(any())).thenReturn(product);

        assertDoesNotThrow(() -> service.updateProduct(1L, productDTO));

        verify(userService, times(1)).getUserById(1L);
        verify(categoryService, times(1)).getById(1L);
        verify(repository, times(1)).save(any());
    }

    @Test
    void deleteProduct() throws SalesException {
        assertDoesNotThrow(() -> service.deleteProduct(1L));
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(any());
    }
}