package kitchenpos.application.exception;

public class NotFoundMenuException extends IllegalArgumentException {

    public NotFoundMenuException(final String message) {
        super(message);
    }
}
