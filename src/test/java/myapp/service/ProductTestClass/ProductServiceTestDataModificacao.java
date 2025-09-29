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
class ProductServiceTestDataModificacao {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void dateModified_null_ok() {
        var p = baseWithDateModified(null);
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "dateModified=null deve ser válido");
    }

    // TC2: passado -> válido
    @Test
    void dateModified_past_ok() {
        var p = baseWithDateModified(Instant.now().minusSeconds(3600));
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "passado é válido");
    }

    // TC3: presente -> válido
    @Test
    void dateModified_now_ok() {
        var p = baseWithDateModified(Instant.now());
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "agora é válido");
    }

    // TC4: futuro -> válido
    @Test
    void dateModified_future_ok() {
        var p = baseWithDateModified(Instant.now().plusSeconds(3600));
        var v = validator().validate(p);
        assertTrue(v.isEmpty(), "futuro é válido (sem @Past/@PastOrPresent)");
    }
}
