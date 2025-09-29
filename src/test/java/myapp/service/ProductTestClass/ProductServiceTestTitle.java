package myapp.service.ProductTestClass;

import static myapp.service.ProductTestClass.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import myapp.domain.Product;
import myapp.domain.enumeration.ProductStatus;
import myapp.repository.ProductRepository;
import myapp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTestTitle {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testTitleEquivalencePartitionTitle() {
        // válido (3 chars)
        Product ok = productSample(1L, "Nes", null, null, 1, 1, null, BigDecimal.TEN, ProductStatus.IN_STOCK, null, Instant.now());

        Set<ConstraintViolation<Product>> vOk = validator().validate(ok);
        assertTrue(vOk.isEmpty());
        when(productRepository.save(ok)).thenReturn(ok);
        assertEquals(ok, productService.save(ok));

        // inválido (< 3 chars)
        Product bad = productSample(1L, "NE", null, null, 1, 1, null, BigDecimal.TEN, ProductStatus.IN_STOCK, null, Instant.now());

        Set<ConstraintViolation<Product>> vBad = validator().validate(bad);
        assertEquals("title", vBad.iterator().next().getPropertyPath().toString());
    }
}
