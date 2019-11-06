// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.constants;

public enum ModalWindows {

    DELETE_ORDER_LINE_HEADER("Delete order line?"),
    DELETE_ORDER_LINE_BODY("You are going to delete order line. Are you sure?"),
    DISCARD_CHANGES_HEADER("Discard Changes"),
    DISCARD_CHANGES_BODY("Are you sure you want to leave this page?");

    private final String value;

    ModalWindows(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
