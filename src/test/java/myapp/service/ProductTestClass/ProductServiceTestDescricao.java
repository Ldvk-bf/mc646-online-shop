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
class ProductServiceTestDescricao {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    // TC1: null -> válido
    @Test
    void description_null_valida() {
        var p = baseWithDescription(null);
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "description=null deve ser válido (campo opcional)");
    }

    // TC2: 50 -> válido (limite)
    @Test
    void description_50_valida() {
        var p = baseWithDescription(strOfLen(50));
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "50 chars deve ser válido");
    }

    // TC3: 51 -> válido
    @Test
    void description_51_valida() {
        var p = baseWithDescription(strOfLen(51));
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "51 chars deve ser válido");
    }

    // TC4: 49 -> inválido
    @Test
    void description_49_invalida() {
        var p = baseWithDescription(strOfLen(49));
        var v = validator().validate(p);
        assertFalse(v.isEmpty(), "49 chars deve violar @Size(min=50)");
        assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("description")));
    }

    // EXTRA: "" -> inválido (0 < 50)
    @Test
    void description_vazia_invalida() {
        var p = baseWithDescription("");
        var v = validator().validate(p);
        assertFalse(v.isEmpty(), "string vazia deve violar @Size(min=50)");
        assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("description")));
    }
}
