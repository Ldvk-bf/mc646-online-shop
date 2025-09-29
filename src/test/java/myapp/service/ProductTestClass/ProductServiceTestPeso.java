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
class ProductServiceTestPeso {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    // --- no ProductServiceTest

    @Test
    void weight_0_001_ok() {
        var p = baseWithWeight(0.001);
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "weight=0.001 deve ser válido (>= 0)");
    }

    @Test
    void weight_0_ok() {
        var p = baseWithWeight(0.0);
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "weight=0 deve ser válido (limite inclusivo)");
    }

    @Test
    void weight_menos0_001_invalido() {
        var p = baseWithWeight(-0.001);
        var v = validator().validate(p);
        assertFalse(v.isEmpty(), "weight negativo deve violar @DecimalMin(\"0\")");
        assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("weight")));
    }

    @Test
    void weight_null_ok() {
        var p = baseWithWeight(null); // campo opcional
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "weight=null deve ser permitido");
    }
}
