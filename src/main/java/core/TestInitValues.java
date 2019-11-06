// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package core;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:initValues.properties")
public interface TestInitValues extends Config {

    //    credentials
    @Key("adminEmail")
    String adminEmail();

    @Key("password")
    String password();

    @Key("adminId")
    String adminId();

    @Key("incorrectEmail")
    String incorrectEmail();

    @Key("incorrectPassword")
    String incorrectPassword();

    @Key("firstMerchantEmail")
    String firstMerchantEmail();

    @Key("firstMerchantFirstUserId")
    String firstMerchantFirstUserId();

    @Key("firstMerchantId")
    String firstMerchantId();

    @Key("firstMerchantKharkivPosId")
    String firstMerchantKharkivPosId();

    @Key("firstMerchantSmokePosId")
    String firstMerchantSmokePosId();

    @Key("firstMerchantDiscountedPizzaId")
    String firstMerchantDiscountedPizzaId();

    @Key("firstMerchantCheesePizzaId")
    String firstMerchantCheesePizzaId();

    @Key("firstDriverEmail")
    String firstDriverEmail();

    @Key("firstDriverId")
    String firstDriverId();

    @Key("secondDriverId")
    String secondDriverId();

    @Key("secondDriverEmail")
    String secondDriverEmail();

    @Key("incorrectDriverId")
    String incorrectDriverId();

    @Key("incorrectDriverEmail")
    String incorrectDriverEmail();

    @Key("firstCustomerEmail")
    String firstCustomerEmail();

    @Key("firstCustomerId")
    String firstCustomerId();

    @Key("secondCustomerId")
    String secondCustomerId();

    @Key("thirdCustomerId")
    String thirdCustomerId();

    @Key("secondCustomerEmail")
    String secondCustomerEmail();

    @Key("thirdCustomerEmail")
    String thirdCustomerEmail();

    @Key("firstCustomerCCId")
    String firstCustomerCCId();

    @Key("secondCustomerCCId")
    String secondCustomerCCId();

    //    locations

    @Key("lonKharkivPushkinska10")
    String lonKharkivPushkinska10();

    @Key("addressKharkivPushkinskaProvulok4")
    String addressKharkivPushkinskaProvulok4();

    @Key("latKharkivPushkinskaProvulok4")
    String latKharkivPushkinskaProvulok4();

    @Key("lonKharkivPushkinskaProvulok4")
    String lonKharkivPushkinskaProvulok4();

    //    promocodes
    @Key("promoSmokeDeliveryPromoCome")
    String promoSmokeDeliveryPromoCome();

    @Key("promoCourierDeliveryValue")
    String promoCourierDeliveryValue();

    @Key("promoCourierDeliveryPercent")
    String promoCourierDeliveryPercent();

    @Key("promoTaxiPercent")
    String promoTaxiPercent();

    @Key("promoCourierServicePercentDiscount")
    String promoCourierServicePercentDiscount();

    @Key("promoCourierServiceDiscount")
    String promoCourierServiceDiscount();

    @Key("promoCourier100Discount")
    String promoCourier100Discount();

    @Key("promoSmokeTaxi")
    String promoSmokeTaxi();

    @Key("promoTaxi100Discount")
    String promoTaxi100Discount();

    //    locations
    @Key("areaKharkiv")
    String areaKharkiv();

    @Key("addressKharkivShevchenka146")
    String addressKharkivShevchenka146();

    @Key("latKharkivShevchenka146")
    String latKharkivShevchenka146();

    @Key("lonKharkivShevchenka146")
    String lonKharkivShevchenka146();

    @Key("addressKharkivShevchenka334")
    String addressKharkivShevchenka334();

    @Key("latKharkivShevchenka334")
    String latKharkivShevchenka334();

    @Key("lonKharkivShevchenka334")
    String lonKharkivShevchenka334();

    @Key("addressKharkivPushkinska1")
    String addressKharkivPushkinska1();

    @Key("latKharkivPushkinska1")
    String latKharkivPushkinska1();

    @Key("lonKharkivPushkinska1")
    String lonKharkivPushkinska1();

    @Key("addressKharkivPushkinska10")
    String addressKharkivPushkinska10();

    @Key("latKharkivPushkinska10")
    String latKharkivPushkinska10();

    @Key("addressKharkivPushkinska100")
    String addressKharkivPushkinska100();

    @Key("latKharkivPushkinska100")
    String latKharkivPushkinska100();

    @Key("lonKharkivPushkinska100")
    String lonKharkivPushkinska100();

    //    orderKinds
    @Key("orderKindM2MEReg")
    String orderKindM2MEReg();

    @Key("orderKindServiceTaxi")
    String orderKindServiceTaxi();

    @Key("orderKindM2PReg")
    String orderKindM2PReg();

    @Key("orderKindCourierUser")
    String orderKindCourierUser();

    @Key("serviceIdTaxiEconom")
    String serviceIdTaxiEconom();
}
