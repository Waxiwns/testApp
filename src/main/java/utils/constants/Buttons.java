// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.constants;

public enum Buttons {

    GO("Go"),
    CREATE_TICKET("Create Ticket"),
    CHANGE("CHANGE"),
    DETAILS("DETAILS"),
    ANALYZE("ANALYZE"),
    EDIT("EDIT"),
    CHAT("CHAT"),
    CALL("CALL"),
    SAVE("SAVE"),
    CANCEL("CANCEL"),
    CANCEL_lower("Cancel"),
    TO_SUPPORT("To Support"),
    COMPLETE("Complete"),
    RESTART_ORDER("Restart Order"),
    SELECT("Select"),
    ADD_TRANSACTION("Add transaction"),
    EXPORT_TO_EXCEL("Export to Excel"),
    FILTER("Filter");

    final String value;

    Buttons(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
