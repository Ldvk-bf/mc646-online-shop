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
class ProductServiceTestDimensoes {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    // --- no ProductServiceTest
    @Test
    void dimensions_null_ok() {
        var p = baseWithDimensions(null);
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "null é permitido (campo opcional)");
    }

    @Test
    void dimensions_vazio_ok() {
        var p = baseWithDimensions("");
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "string vazia (0) é válida");
    }

    @Test
    void dimensions_1_ok() {
        var p = baseWithDimensions(strOfLen(1));
        var v = validator().validate(p);
        assertTrue(v.isEmpty());
    }

    @Test
    void dimensions_49_ok() {
        var p = baseWithDimensions(strOfLen(49));
        var v = validator().validate(p);
        assertTrue(v.isEmpty());
    }

    @Test
    void dimensions_50_ok() {
        var p = baseWithDimensions(strOfLen(50));
        var v = validator().validate(p);
        assertTrue(v.isEmpty());
    }

    @Test
    void dimensions_51_invalido() {
        var p = baseWithDimensions(strOfLen(51));
        var v = validator().validate(p);
        assertFalse(v.isEmpty(), "51 deve violar @Size(max=50)");
        assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("dimensions")));
    }
}
