package kitchenpos.exception;

public class NotFoundOrDuplicateMenuToOrderExcpetion extends IllegalArgumentException {

    public NotFoundOrDuplicateMenuToOrderExcpetion(final String message) {
        super(message);
    }
}
