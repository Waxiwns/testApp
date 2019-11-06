// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package core;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:testing.properties")
public interface TestProperties extends Config {

    @Key("BASE_URL")
    String BASE_URL();

    @Key("loginPagUrl")
    String loginPagUrl();

    @Key("browserSize")
    String browserSize();

    @Key("timeout")
    Long timeout();

    @Key("headless")
    Boolean headless();

    @Key("selenideListener")
    String selenideListener();

    @Key("orderLink")
    String orderLink();

    @Key("errorLink")
    String errorLink();
}

