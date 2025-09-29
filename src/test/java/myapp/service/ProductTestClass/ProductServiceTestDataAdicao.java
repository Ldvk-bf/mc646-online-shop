package myapp.service.ProductTestClass;

import static myapp.service.ProductTestClass.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import myapp.repository.ProductRepository;
import myapp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTestDataAdicao {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    // --- no ProductServiceTest

    @Test
    void dateAdded_now_ok() {
        var p = baseWithDate(Instant.now());
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "Instant.now() deve ser válido");
    }

    // TC2: passado -> válido
    @Test
    void dateAdded_past_ok() {
        var p = baseWithDate(Instant.now().minusSeconds(3600)); // 1h atrás
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "passado é válido (não há @Past/@PastOrPresent)");
    }

    // TC3: futuro -> válido (não há restrição temporal)
    @Test
    void dateAdded_future_ok() {
        var p = baseWithDate(Instant.now().plusSeconds(3600)); // 1h à frente
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "futuro também é válido sem @Past/@PastOrPresent");
    }

    // TC4: null -> inválido (por @NotNull)
    @Test
    void dateAdded_null_invalido() {
        var p = baseWithDate(null);
        var v = validator().validate(p);
        assertFalse(v.isEmpty(), "dateAdded é obrigatório (@NotNull)");
        assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("dateAdded")));
    }
}
