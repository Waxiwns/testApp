// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.users;

import core.TestInitValues;
import org.aeonbits.owner.ConfigFactory;
import utils.UseAPI;

import java.util.HashMap;
import java.util.Map;

import static core.TestStepLogger.log;
import static io.restassured.path.json.JsonPath.from;
import static utils.constants.ApiConstants.*;

public abstract class BaseUser {

    protected static final TestInitValues testInitValues = ConfigFactory.create(TestInitValues.class);

    protected UseAPI useAPI = new UseAPI();

    protected String id;

    protected String email;

    protected String password;

    protected String allInfo;


    protected Map<String, String> authToken = new HashMap<>();

    public BaseUser(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public BaseUser() {
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    protected abstract String getAllInfo();

    protected abstract String getAuthToken();

    protected String getKey(String key) {
        String result = getDataFromJson(getAllInfo(), key);

        if (result == null) {
            log("value: '" + key + "' is absent");
            return key;
        } else return result;
    }

    public String getFirstName() {
        return getKey(FIRST_NAME);
    }

    public String getLastName() {
        return getKey(LAST_NAME);
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getPhone() {
        return getKey(PHONE_NUMBER);
    }

    protected long currentTime() {
        return System.currentTimeMillis() / 1000L;
    }

    protected String getDataFromJson(String jsonBody, String key) {
        return from(jsonBody).getString(key);
    }

    protected Map<String, Object> getMapFromJson(String jsonBody, String key) {
        return from(jsonBody).getMap(key);
    }
}
