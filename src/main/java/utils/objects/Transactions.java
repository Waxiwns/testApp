// ~ Copyright © 2019 Aram Meem Company Limited. All Rights Reserved.

package utils.objects;

import java.text.SimpleDateFormat;
import java.util.Date;

import static core.TestStepLogger.log;
import static io.restassured.path.json.JsonPath.from;
import static utils.constants.ApiConstants.*;

public class Transactions {

    private String allInfo;

    private int totalTransactionRecords = 0;

    private boolean isManualTx = true;

    protected String dateFormat = "MMM d, yyyy, h:mm:ss a";


    public Transactions(String allInfo) {
        this.allInfo = allInfo;
    }

    public String getId() {
        return getTxKey(ID);
    }

    public String getCreationDate() {
        return getTxDateKey(CREATION_DATE);
    }

    public String getBeneficiaryType() {
        return getTxKey(BENEFICIARY_TYPE);
    }

    public String getAccountType() {
        return getTxKey(ACCOUNT_TYPE);
    }

    public String getOperationType() {
        return getTxKey(TX_OPERATION_TYPE);
    }

    public String getRecordState() {
        return getTxKey(RECORD_STATE);
    }

    public String getHeadDescription() {
        return getTxKey(TX_HEAD_DESCRIPTION);
    }

    public String getAmount() {
        return getTxKey(AMOUNT);
    }

    public String getExtId() {
        return getExtTxKey(ID);
    }

    public String getExtState() {
        return getExtTxKey(STATE);
    }

    public String getExtCreationDate() {
        return getExtTxDateKey(CREATION_DATE);
    }

    public String getExtProcessingDate() {
        return getExtTxDateKey(PROCESSING_DATE);
    }

    public String getExtFinishedDate() {
        return getExtTxDateKey(FINISHED_DATE);
    }

    private String getAllInfo() {
        return allInfo;
    }

    private String getKey(String key) {
        String result;

        if (getDataFromJson(getAllInfo(), TOTAL) != null) {
            totalTransactionRecords = Integer.parseInt(getDataFromJson(getAllInfo(), TOTAL));
            result = getDataFromJson(getAllInfo(), CONTENT + "." + key);
            isManualTx = false;
        } else
            result = getDataFromJson(getAllInfo(), TRANSACTION_RECORDS + "." + key);

        if (result == null) {
            log("value: '" + key + "' is absent");
            return key;
        }

        return removeFirstEndScraping(result);
    }

    public String getTxKey(String key) {
        // TODO should can choose number of transaction
        String value = getKey(key);

        if (!value.equals(key)) {
            String[] values = getKey(key).split(", ");

            return isManualTx ? values[1] : values[0];
        } else
            return value;
    }

    public String getExtTxKey(String key) {
        // split if more than one transactions
        return getKey(EXT_TRANSACTIONS + "." + key).split(", ")[0];
    }

    public String getTxDateKey(String key) {
        if (key.contains(DATE)) {
            return convertDate(getTxKey(key));
        } else {
            log("value should have '" + DATE + "'");
            return key;
        }
    }

    public String getExtTxDateKey(String key) {
        if (key.contains(DATE)) {
            return convertDate(getExtTxKey(key));
        } else {
            log("value should have '" + DATE + "'");
            return key;
        }
    }

    public int getTransactionRecordsSize() {
        return totalTransactionRecords;
    }

    private String convertDate(Long time, String format) {
        return new SimpleDateFormat(format, java.util.Locale.ENGLISH).format(new Date(time));
    }

    private String convertDate(String time) {
        if (!time.equals("")) {
            long dateTime = Long.parseLong(time);

            dateTime = time.length() < 13 ? dateTime * 1000 : dateTime; // if long date is large (e.g creationDate": 1570042546 and processingDate": 1570042549535)

            return convertDate(dateTime, dateFormat);
        } else return time;
    }

    private String getDataFromJson(String jsonBody, String key) {
        return from(jsonBody).getString(key);
    }

    private String removeFirstEndScraping(String string) {
        string = string.startsWith("[") ? string.substring(1) : string;
        string = string.endsWith("]") ? string.substring(0, string.length() - 1) : string;

        if (string.startsWith("[") || string.endsWith("]")) string = removeFirstEndScraping(string);

        return string;
    }
}
