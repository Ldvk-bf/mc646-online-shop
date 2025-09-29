package myapp.service.ProductTestClass;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.math.BigDecimal;
import java.time.Instant;
import myapp.domain.Product;
import myapp.domain.enumeration.ProductStatus;

public final class TestHelpers {

    private TestHelpers() {}

    // Cacheia um Validator para os testes
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static Validator validator() {
        return VALIDATOR;
    }

    // Fábrica de Product (o seu createProductSample)
    public static Product productSample(
        Long id,
        String title,
        String keywords,
        String description,
        Integer rating,
        Integer quantityInStock,
        String dimensions,
        BigDecimal price,
        ProductStatus status,
        Double weight,
        Instant dateAdded
    ) {
        return new Product()
            .id(id)
            .title(title)
            .keywords(keywords)
            .description(description)
            .rating(rating)
            .quantityInStock(quantityInStock)
            .dimensions(dimensions)
            .price(price)
            .status(status)
            .weight(weight)
            .dateAdded(dateAdded);
    }

    public static String strOfLen(int n) {
        return "a".repeat(Math.max(0, n));
    }

    public static Product baseWithDescription(String desc) {
        return productSample(1L, "Nest", null, desc, 1, 1, null, BigDecimal.TEN, ProductStatus.IN_STOCK, null, Instant.now());
    }

    public static Product baseWithRating(Integer rating) {
        return productSample(
            1L,
            "Título válido",
            null, // keywords opcional
            null, // description opcional
            rating, // <- varia aqui
            10, // quantityInStock >= 0
            null, // dimensions opcional
            new BigDecimal("50.00"),
            ProductStatus.IN_STOCK,
            null, // weight opcional
            Instant.now()
        );
    }

    public static Product baseWithPrice(BigDecimal price) {
        return productSample(
            1L,
            "Título válido",
            null, // keywords
            null, // description
            5, // rating
            10, // estoque
            null, // dimensions
            price, // <- varia aqui
            ProductStatus.IN_STOCK,
            null, // weight
            Instant.now()
        );
    }

    public static Product baseWithQty(Integer qty) {
        return productSample(
            1L,
            "Título válido",
            null, // keywords
            null, // description
            5, // rating
            qty, // <- varia aqui
            null, // dimensions
            new BigDecimal("50.00"),
            ProductStatus.IN_STOCK,
            null, // weight
            Instant.now()
        );
    }

    public static Product baseWithStatus(ProductStatus status) {
        return productSample(
            1L,
            "Título válido",
            null, // keywords
            null, // description
            5, // rating
            10, // estoque
            null, // dimensions
            new BigDecimal("50.00"),
            status, // <- varia aqui
            null, // weight
            Instant.now()
        );
    }

    public static Product baseWithWeight(Double weight) {
        return productSample(
            1L,
            "Título válido",
            null, // keywords
            null, // description
            5, // rating
            10, // quantityInStock
            null, // dimensions
            new BigDecimal("50.00"),
            ProductStatus.IN_STOCK,
            weight, // <- varia aqui
            Instant.now()
        );
    }

    public static Product baseWithDimensions(String dims) {
        return productSample(
            1L,
            "Título válido",
            null, // keywords
            null, // description
            5, // rating
            10, // quantityInStock
            dims, // <- varia aqui
            new BigDecimal("50.00"),
            ProductStatus.IN_STOCK,
            null, // weight
            Instant.now()
        );
    }

    public static Product baseWithDate(Instant date) {
        return productSample(
            1L,
            "Título válido",
            null, // keywords
            null, // description
            5, // rating
            10, // quantityInStock
            null, // dimensions
            new BigDecimal("50.00"),
            ProductStatus.IN_STOCK,
            null, // weight
            date // <- varia aqui
        );
    }

    public static Product baseWithDateModified(Instant dm) {
        return productSample(
            1L,
            "Título válido",
            null, // keywords
            null, // description
            5, // rating
            10, // estoque
            null, // dimensions
            new BigDecimal("50.00"),
            ProductStatus.IN_STOCK,
            null, // weight
            Instant.now() // dateAdded (obrigatório)
        ).dateModified(dm); // <- varia aqui
    }
}
