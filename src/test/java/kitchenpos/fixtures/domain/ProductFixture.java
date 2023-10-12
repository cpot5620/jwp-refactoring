package kitchenpos.fixtures.domain;

import kitchenpos.domain.Product;

import java.math.BigDecimal;

public class ProductFixture {

    public static Product createProduct(final String name,
                                        final BigDecimal price) {
        final Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return product;
    }

    public static Product createProductCreate(final String name,
                                              final BigDecimal price) {
        return createProduct(name, price);
    }

    public static class ProductRequestBuilder {

        private String name = "제육볶음";
        private BigDecimal price = new BigDecimal(10_000);

        public ProductRequestBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public ProductRequestBuilder price(final BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductRequestBuilder price(final int price) {
            this.price = new BigDecimal(price);
            return this;
        }

        public Product build() {
            final Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            return product;
        }
    }
}
