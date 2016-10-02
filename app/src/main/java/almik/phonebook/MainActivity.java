package almik.phonebook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import almik.phonebook.utill.ContactUtill;
import almik.phonebook.utill.ItemAdapter;

public class MainActivity extends AppCompatActivity {

    private ContactUtill contactUtill;
    public static ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactUtill = ContactUtill.getInstance();
        itemAdapter = new ItemAdapter(this);


        // находим список
        ListView lvMain = (ListView) findViewById(R.id.list_Call);
        // присваиваем адаптер списку
        lvMain.setAdapter(itemAdapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String number = contactUtill.getNumb(i, getBaseContext());
                if (number != null) {
                    Uri call = Uri.parse("tel:" + number);
                    Intent surf = new Intent(Intent.ACTION_CALL, call);
                    if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                       ///check permission
                        return;
                    }
                    startActivity(surf);
                }
            }});
//

    }




}
