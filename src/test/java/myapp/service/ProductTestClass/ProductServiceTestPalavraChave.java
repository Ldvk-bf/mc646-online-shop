package myapp.service.ProductTestClass;

import static myapp.service.ProductTestClass.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;
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
class ProductServiceTestPalavraChave {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    // TODO: Corrigir os
    // Codigo para PALAVRA CHAVEE

    // --- TC1: length = 0 (válido) | empty = true
    @Test
    void testKeywords_Length0_Valid() {
        Product p = productSample(1L, "Nest", "", null, 1, 1, null, BigDecimal.TEN, ProductStatus.IN_STOCK, null, Instant.now());
        var violations = validator().validate(p);
        assertTrue(violations.isEmpty(), "length=0 deve ser válido para keywords");
    }

    // --- TC2: length = 1 (válido) | empty = false
    @Test
    void testKeywords_Length1_Valid() {
        Product p = productSample(1L, "Nest", strOfLen(1), null, 1, 1, null, BigDecimal.TEN, ProductStatus.IN_STOCK, null, Instant.now());
        var violations = validator().validate(p);
        assertTrue(violations.isEmpty(), "length=1 deve ser válido para keywords");
    }

    // --- TC3: length = 199 (válido)
    @Test
    void testKeywords_Length199_Valid() {
        Product p = productSample(1L, "Nest", strOfLen(199), null, 1, 1, null, BigDecimal.TEN, ProductStatus.IN_STOCK, null, Instant.now());
        var violations = validator().validate(p);
        assertTrue(violations.isEmpty(), "length=199 deve ser válido para keywords");
    }

    // --- TC4: length = 200 (válido - limite superior)
    @Test
    void testKeywords_Length200_Valid() {
        Product p = productSample(1L, "Nest", strOfLen(200), null, 1, 1, null, BigDecimal.TEN, ProductStatus.IN_STOCK, null, Instant.now());
        var violations = validator().validate(p);
        assertTrue(violations.isEmpty(), "length=200 deve ser válido para keywords");
    }

    // --- TC5: length = 201 (inválido - estoura max)
    @Test
    void testKeywords_Length201_Invalid() {
        Product p = productSample(1L, "Nest", strOfLen(201), null, 1, 1, null, BigDecimal.TEN, ProductStatus.IN_STOCK, null, Instant.now());
        var violations = validator().validate(p);
        assertFalse(violations.isEmpty(), "length=201 deve violar @Size(max=200)");
        assertTrue(
            violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("keywords")),
            "deve haver violação especificamente em 'keywords'"
        );
    }

    // --- TC6: null (válido - campo opcional)
    @Test
    void testKeywords_Null_Valid() {
        Product p = productSample(1L, "Nest", null, null, 1, 1, null, BigDecimal.TEN, ProductStatus.IN_STOCK, null, Instant.now());
        var violations = validator().validate(p);
        assertTrue(violations.isEmpty(), "keywords=null deve ser permitido");
    }
}
