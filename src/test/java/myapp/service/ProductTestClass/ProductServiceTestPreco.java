package myapp.service.ProductTestClass;

import static myapp.service.ProductTestClass.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import myapp.repository.ProductRepository;
import myapp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTestPreco {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void price_1_ok() {
        var p = baseWithPrice(new BigDecimal("1"));
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "1 é válido (mínimo inclusivo)");
    }

    @Test
    void price_1_01_ok() {
        var p = baseWithPrice(new BigDecimal("1.01"));
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "1.01 é válido");
    }

    @Test
    void price_9999_ok() {
        var p = baseWithPrice(new BigDecimal("9999"));
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "9999 é válido (máximo inclusivo)");
    }

    @Test
    void price_9998_99_ok() {
        var p = baseWithPrice(new BigDecimal("9998.99"));
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "9998.99 é válido");
    }

    @Test
    void price_0_99_invalido() {
        var p = baseWithPrice(new BigDecimal("0.99"));
        var v = validator().validate(p);
        assertFalse(v.isEmpty(), "0.99 viola @DecimalMin(\"1\")");
        assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("price")));
    }

    @Test
    void price_9999_01_invalido() {
        var p = baseWithPrice(new BigDecimal("9999.01"));
        var v = validator().validate(p);
        assertFalse(v.isEmpty(), "9999.01 viola @DecimalMax(\"9999\")");
        assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("price")));
    }

    @Test
    void price_null_invalido() {
        var p = baseWithPrice(null);
        var v = validator().validate(p);
        assertFalse(v.isEmpty(), "price é @NotNull");
        assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("price")));
    }
}
