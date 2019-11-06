// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package pages.orderPageTabs;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class OrderCreateTicketPage {

    public SelenideElement ticketSaveButtonIcon = $(".save-cancel-btn-wrapper .btn-primary");
    public SelenideElement newTicketPanel = $(".card-header");


    //    page text
    public static final String TITLE = "New Ticket";
}
