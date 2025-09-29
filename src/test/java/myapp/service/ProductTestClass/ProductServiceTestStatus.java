package myapp.service.ProductTestClass;

import static myapp.service.ProductTestClass.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.*;

import myapp.domain.enumeration.ProductStatus;
import myapp.repository.ProductRepository;
import myapp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTestStatus {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void status_IN_STOCK_ok() {
        var p = baseWithStatus(ProductStatus.IN_STOCK);
        var v = validator().validate(p);
        assertTrue(v.isEmpty());
    }

    @Test
    void status_OUT_OF_STOCK_ok() {
        var p = baseWithStatus(ProductStatus.OUT_OF_STOCK);
        var v = validator().validate(p);
        assertTrue(v.isEmpty());
    }

    @Test
    void status_PREORDER_ok() {
        var p = baseWithStatus(ProductStatus.PREORDER);
        var v = validator().validate(p);
        assertTrue(v.isEmpty());
    }

    @Test
    void status_DISCONTINUED_ok() {
        var p = baseWithStatus(ProductStatus.DISCONTINUED);
        var v = validator().validate(p);
        assertTrue(v.isEmpty());
    }

    // inválido: null (por @NotNull)
    @Test
    void status_null_invalido() {
        var p = baseWithStatus(null);
        var v = validator().validate(p);
        assertFalse(v.isEmpty(), "status é @NotNull");
        assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("status")));
    }

    // opcional: “qualquer outra coisa” não mapeia para o enum
    @Test
    void status_string_invalida_dispara_valueOf() {
        assertThrows(IllegalArgumentException.class, () -> ProductStatus.valueOf("QUALQUER_OUTRA_COISA"));
    }
}
