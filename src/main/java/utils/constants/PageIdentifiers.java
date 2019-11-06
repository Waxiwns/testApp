// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.constants;

public enum PageIdentifiers {
    PROFILE("Profile", "profile"),
    CART("Cart", "cart"),
    HISTORY("History", "history"),
    CHANGES("Changes", "changes"),
    COSTCALCULATION("Cost calculation", "calculation"),
    TRANSACTIONS("Transactions", "transactions"),
    TICKETS("Tickets", "tickets"),
    TRACK("Track", "track"),

    //messenger page
    MESSENGER("Messages", "messages");

    final String enValue;
    final String urlValue;

    PageIdentifiers(String enValue, String urlValue) {
        this.enValue = enValue;
        this.urlValue = urlValue;
    }

    public String getValue() {
        return enValue;
    }

    public String getUrlValue() {
        return urlValue;
    }
}
