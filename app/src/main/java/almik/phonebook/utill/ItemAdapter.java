package almik.phonebook.utill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import almik.phonebook.R;

/**
 * Created by alex on 30.09.16.
 */

public class ItemAdapter extends BaseAdapter {
    private Context ctx;
    LayoutInflater lInflater;
    private JSONArray jArray;


    public ItemAdapter(Context context){
        this.ctx=context;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        jArray = ContactUtill.getInstance().getList(ctx);
        if (jArray != null) {
        }
    }

    @Override
    public int getCount() {
        if (jArray!=null){
        return jArray.length();}
        else {return 0;}
    }

    @Override
    public Object getItem(int i) {
        try {
            return jArray.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int id) {
        return id;
    }



    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        try {
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

            JSONObject jsonObject=jArray.getJSONObject(i);
        // заполняем View
        ((TextView) view.findViewById(R.id.tvType)).setText(jsonObject.get(ctx.getString(R.string.type)).toString());
        ((TextView) view.findViewById(R.id.tVNumb)).setText(jsonObject.get(ctx.getString(R.string.number)).toString());
        ((TextView) view.findViewById(R.id.tVTime)).setText(jsonObject.get(ctx.getString(R.string.time)).toString());



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }




}
