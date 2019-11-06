// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.constants;

public enum Statuses {
    //    Driver Statuses
    ANY("ANY", "0: ANY"),
    READY("READY", "1: READY"),
    ORDER_EXECUTION("ORDER_EXECUTION", "2: ORDER_EXECUTION"),
    OFFLINE("OFFLINE", "3: OFFLINE"),
    ORDER_OFFLINE("ORDER_OFFLINE", "4: ORDER_OFFLINE"),

    //    Order Statuses
    WAIT_MERCHANT_APPROVAL("WAIT MERCHANT APPROVAL", "WAIT_MERCHANT_APPROVAL"),
    WAIT_ACCEPTANCE("WAIT ACCEPTANCE", "WAIT_ACCEPTANCE"),
    READY_TO_EXECUTE("READY TO EXECUTE", "READY_TO_EXECUTE"),
    CANCELLED("CANCELLED", "CANCELLED"),
    NOT_CONFIRMED("NOT CONFIRMED", "NOT_CONFIRMED"),
    NEW("NEW", "NEW"),
    SUSPENDED("SUSPENDED", "SUSPENDED"),
    ON_THE_WAY_TO_PICK_UP("ON THE WAY TO PICK UP", "ON_THE_WAY_TO_PICK_UP"),
    AT_PICK_UP("AT PICK UP", "AT_PICK_UP"),
    ON_THE_WAY_TO_DROP_OFF("ON THE WAY TO DROP OFF", "ON_THE_WAY_TO_DROP_OFF"),
    AT_DROP_OFF("AT DROP OFF", "AT_DROP_OFF"),
    COMPLETED("COMPLETED", "COMPLETED"),
    GO_TO_PICK_UP("GO TO PICK UP", "GO_TO_PICK_UP"),
    GO_TO_DROP_OFF("GO TO DROP OFF", "GO_TO_DROP_OFF"),

    //    Merchant Statuses
    IN_PROGRESS("IN PROGRESS", ""),
    TAKEN("TAKEN", "");

    final String enValue;
    final String value;

    Statuses(String enValue, String value) {
        this.enValue = enValue;
        this.value = value;
    }

    public String getEnValue() {
        return enValue;
    }

    public String getValue() {
        return value;
    }
}
