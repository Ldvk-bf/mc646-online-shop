package myapp.service;

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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Nested
    class RatingTest {

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

    @Nested
    class PriceTest {

        @Test
        void price_1_ok() {
            var p = baseWithPrice(new BigDecimal("1"));
            var v = validator().validate(p);
            assertTrue(v.isEmpty(), "1 é válido (mínimo inclusivo)");
        }

        @Test
        void price_1_01_ok() {
            var p = baseWithPrice(new BigDecimal("1.01"));
            var v = validator().validate(p);
            assertTrue(v.isEmpty(), "1.01 é válido");
        }

        @Test
        void price_9999_ok() {
            var p = baseWithPrice(new BigDecimal("9999"));
            var v = validator().validate(p);
            assertTrue(v.isEmpty(), "9999 é válido (máximo inclusivo)");
        }

        @Test
        void price_9998_99_ok() {
            var p = baseWithPrice(new BigDecimal("9998.99"));
            var v = validator().validate(p);
            assertTrue(v.isEmpty(), "9998.99 é válido");
        }

        @Test
        void price_0_99_invalido() {
            var p = baseWithPrice(new BigDecimal("0.99"));
            var v = validator().validate(p);
            assertFalse(v.isEmpty(), "0.99 viola @DecimalMin(\"1\")");
            assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("price")));
        }

        @Test
        void price_9999_01_invalido() {
            var p = baseWithPrice(new BigDecimal("9999.01"));
            var v = validator().validate(p);
            assertFalse(v.isEmpty(), "9999.01 viola @DecimalMax(\"9999\")");
            assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("price")));
        }

        @Test
        void price_null_invalido() {
            var p = baseWithPrice(null);
            var v = validator().validate(p);
            assertFalse(v.isEmpty(), "price é @NotNull");
            assertTrue(v.stream().anyMatch(e -> e.getPropertyPath().toString().equals("price")));
        }
    }

    @Nested
    class QuantityInStockTest {

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

    @Nested
    class WeightTest {

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

    @Nested
    class DimensionsTest {

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

        @Test
        void dimensions_null_ok() {
            var p = baseWithDimensions(null); // campo opcional
            var v = validator().validate(p);
            assertTrue(v.isEmpty(), "dimensions=null deve ser permitido");
        }

        @Test
        void dimensions_vazio_ok() {
            var p = baseWithDimensions("");
            var v = validator().validate(p);
            assertTrue(v.isEmpty(), "string vazia (0) é válida");
        }
    }

    @Nested
    class DescriptionTest {

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

    @Nested
    class keywordsTest {

        @Test
        void testKeywords_Length0_Valid() {
            Product p = productSample(1L, "Nest", "", null, 1, 1, null, BigDecimal.TEN, ProductStatus.IN_STOCK, null, Instant.now());
            var violations = validator().validate(p);
            assertTrue(violations.isEmpty(), "length=0 deve ser válido para keywords");
        }

        // --- TC2: length = 1 (válido) | empty = false
        @Test
        void testKeywords_Length1_Valid() {
            Product p = productSample(
                1L,
                "Nest",
                strOfLen(1),
                null,
                1,
                1,
                null,
                BigDecimal.TEN,
                ProductStatus.IN_STOCK,
                null,
                Instant.now()
            );
            var violations = validator().validate(p);
            assertTrue(violations.isEmpty(), "length=1 deve ser válido para keywords");
        }

        // --- TC3: length = 199 (válido)
        @Test
        void testKeywords_Length199_Valid() {
            Product p = productSample(
                1L,
                "Nest",
                strOfLen(199),
                null,
                1,
                1,
                null,
                BigDecimal.TEN,
                ProductStatus.IN_STOCK,
                null,
                Instant.now()
            );
            var violations = validator().validate(p);
            assertTrue(violations.isEmpty(), "length=199 deve ser válido para keywords");
        }

        // --- TC4: length = 200 (válido - limite superior)
        @Test
        void testKeywords_Length200_Valid() {
            Product p = productSample(
                1L,
                "Nest",
                strOfLen(200),
                null,
                1,
                1,
                null,
                BigDecimal.TEN,
                ProductStatus.IN_STOCK,
                null,
                Instant.now()
            );
            var violations = validator().validate(p);
            assertTrue(violations.isEmpty(), "length=200 deve ser válido para keywords");
        }

        // --- TC5: length = 201 (inválido - estoura max)
        @Test
        void testKeywords_Length201_Invalid() {
            Product p = productSample(
                1L,
                "Nest",
                strOfLen(201),
                null,
                1,
                1,
                null,
                BigDecimal.TEN,
                ProductStatus.IN_STOCK,
                null,
                Instant.now()
            );
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

    @Nested
    class TitleTest {

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

    @Nested
    class StatusTest {

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

    @Nested
    class DataAddTest {

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

    @Nested
    class DataModTest {

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
}
