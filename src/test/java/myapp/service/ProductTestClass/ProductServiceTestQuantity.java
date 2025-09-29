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
class ProductServiceTestQuantity {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void quantity_0_ok() {
        var p = baseWithQty(0);
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "quantityInStock=0 deve ser válido (mínimo inclusivo)");
    }

    @Test
    void quantity_1_ok() {
        var p = baseWithQty(1);
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "quantityInStock=1 deve ser válido");
    }

    @Test
    void quantity_menos1_invalido() {
        var p = baseWithQty(-1);
        var v = validator().validate(p);
        assertFalse(v.isEmpty(), "quantityInStock=-1 deve violar @Min(0)");
        assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("quantityInStock")));
    }

    @Test
    void quantity_null_ok() {
        var p = baseWithQty(null); // campo é opcional (sem @NotNull)
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "quantityInStock=null deve ser permitido");
    }
}
