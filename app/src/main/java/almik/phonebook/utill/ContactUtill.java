package almik.phonebook.utill;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import almik.phonebook.MainActivity;
import almik.phonebook.R;


public class ContactUtill {
    private static ContactUtill contactUtill;
    static JSONArray array;

    private ContactUtill(){

    }

    public static ContactUtill getInstance(){
        if(contactUtill==null){
            contactUtill=new ContactUtill();
        }
        return contactUtill;
    }
    public String getNumb(int i,Context ctx){
        try {
            return array.getJSONObject(i).get(ctx.getString(R.string.number)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public JSONArray getList(Context context){
            // проверяем доступность SD
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                Log.d(context.getString(R.string.LogTag), "SD-карта не доступна: " + Environment.getExternalStorageState());
                return null;
            }
            // получаем путь к SD
            File sdPath = Environment.getExternalStorageDirectory();
            // добавляем свой каталог к пути
            sdPath = new File(sdPath.getAbsolutePath() + "/" + context.getString(R.string.NameDir));
            // формируем объект File, который содержит путь к файлу
            File sdFile = new File(sdPath,context.getString(R.string.NameFile));
            try {
                // открываем поток для чтения
                BufferedReader br = new BufferedReader(new FileReader(sdFile));
                String str = "";
                // читаем содержимое
                String res="";
                while ((str = br.readLine()) != null) {
                   res=res+str;
                }
                if(res!=null) {
                array=new JSONArray(res);
                return array;
                }else{return null;}
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


    }




    void addCall(String numb, String type, String time, Context context){
        JSONObject call=new JSONObject();
        try {
            call.put(context.getString(R.string.number),numb);
            call.put(context.getString(R.string.type),type);
            call.put(context.getString(R.string.time),time);
            array.put(call);
            MainActivity.itemAdapter.notifyDataSetChanged();

            // проверяем доступность SD
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                Log.d(context.getString(R.string.LogTag), "SD-карта не доступна: " + Environment.getExternalStorageState());
                return;
            }
            // получаем путь к SD
            File sdPath = Environment.getExternalStorageDirectory();
            // добавляем свой каталог к пути
            sdPath = new File(sdPath.getAbsolutePath() + "/" + context.getString(R.string.NameDir));
            // создаем каталог
            sdPath.mkdirs();
            // формируем объект File, который содержит путь к файлу
            File sdFile = new File(sdPath, context.getString(R.string.NameFile));

                // открываем поток для записи
                BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
                // пишем данные
                bw.append(array.toString());
                // закрываем поток
                bw.close();
                Log.d(context.getString(R.string.LogTag), "Файл записан на SD: " + sdFile.getAbsolutePath());

            getList(context);
        } catch (Exception e) {

        }
    }
}
