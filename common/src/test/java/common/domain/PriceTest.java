package common.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {

    @DisplayName("메뉴의 가격이 null 이면 예외가 발생한다.")
    @Test
    void create_failNullPrice() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Price(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격이 음수면 예외가 발생한다.")
    @Test
    void create_failNegativePrice() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Price(BigDecimal.valueOf(-1000L)))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
