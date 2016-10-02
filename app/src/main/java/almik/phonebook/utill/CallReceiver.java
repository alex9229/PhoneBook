package almik.phonebook.utill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class CallReceiver extends BroadcastReceiver {
    private String phoneNumber = "";
    ContactUtill contactUtill= ContactUtill.getInstance();
    Date currentDate = new Date(System.currentTimeMillis());
    SimpleDateFormat dateForm = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
    static String testDate="";
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            //получаем исходящий номер
            phoneNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
            contactUtill.addCall(phoneNumber,"out",dateForm.format(currentDate),context);
        } else if (intent.getAction().equals("android.intent.action.PHONE_STATE")){
            String phone_state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (phone_state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                if (!Objects.equals(testDate, dateForm.format(currentDate))){
                testDate=dateForm.format(currentDate);
                //телефон звонит, получаем входящий номер
                phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                contactUtill.addCall(phoneNumber,"in",dateForm.format(currentDate),context);}
            } else if (phone_state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                //телефон находится в режиме звонка (набор номера / разговор)
            } else if (phone_state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                //телефон находиться в ждущем режиме. Это событие наступает по окончанию разговора, когда мы уже знаем номер и факт звонка
            }
        }
    }
}