package myapp.service.ProductTestClass;

import static myapp.service.ProductTestClass.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.*;

import myapp.repository.ProductRepository;
import myapp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTestAvaliacao {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void rating_1_ok() {
        var p = baseWithRating(1);
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "rating=1 deve ser válido");
    }

    @Test
    void rating_2_ok() {
        var p = baseWithRating(2);
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "rating=2 deve ser válido");
    }

    @Test
    void rating_9_ok() {
        var p = baseWithRating(9);
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "rating=9 deve ser válido");
    }

    @Test
    void rating_10_ok() {
        var p = baseWithRating(10);
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "rating=10 deve ser válido");
    }

    @Test
    void rating_0_invalido() {
        var p = baseWithRating(0);
        var v = validator().validate(p);
        assertFalse(v.isEmpty(), "rating=0 deve violar @Min(1)");
        assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("rating")));
    }

    @Test
    void rating_11_invalido() {
        var p = baseWithRating(11);
        var v = validator().validate(p);
        assertFalse(v.isEmpty(), "rating=11 deve violar @Max(10)");
        assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("rating")));
    }

    @Test
    void rating_null_ok() {
        var p = baseWithRating(null); // campo é opcional
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "rating=null deve ser válido (opcional)");
    }
}
