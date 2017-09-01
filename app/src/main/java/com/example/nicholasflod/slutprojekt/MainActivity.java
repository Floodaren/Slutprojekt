package com.example.nicholasflod.slutprojekt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.content.Intent;
import android.widget.ListView;
import android.database.Cursor;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
public class MainActivity extends AppCompatActivity {

    private ListView innehallslista;
    private String rubriktext;
    private String undertext;
    private String bildfil;
    private boolean finnsDatabasEllerInte;
    private ListAdapter nicholasAdapter;
    private static final String TAG = "MyActivity";
    private List<Integer> idn = new ArrayList<Integer>();
    private List<String> rubriker = new ArrayList<String>();
    private List<String> underTexter = new ArrayList<String>();
    private List<String> filvagforbilder = new ArrayList<String>();
    private List<String> positionerlista = new ArrayList<String>();
    private MySQLiteHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        innehallslista = (ListView) findViewById(R.id.innehallsLista);
        myDb = new MySQLiteHelper(this);
        setSupportActionBar(toolbar);

        //Hanterar data från javaklasserna NyttInlagg_ValjBild & NyttInlagg_RubrikOUndertext
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            rubriktext = extras.getString("rubrik");
            undertext = extras.getString("undertext");
            bildfil = extras.getString("bildfil");
            if (rubriktext != null && bildfil != null)
            {
                myDb.infogaData(rubriktext,undertext,bildfil);
            }
        }
        hamtaFranDatabas();
        nicholasAdapter = new CustomAdapter(this, rubriker, underTexter, filvagforbilder, idn);
        innehallslista.setAdapter(nicholasAdapter);

    }

    //Hämtar data från databasen
    public void hamtaFranDatabas()
    {
        //Anropar metoden för att hämta datan
        Cursor allInformation = myDb.HamtaData();
        finnsDatabasEllerInte = allInformation.moveToFirst();
        if (finnsDatabasEllerInte == true)
        {
            //Loppar igenom alla rader i databasen
            while (!allInformation.isAfterLast())
            {
                //Tar ut Int från kolumn 0 (Id:n)
                int idFranDB = allInformation.getInt(0);
                //Tar ut strängen ur kolumn 1 (Rubrikerna)
                String rubrikFranDB = allInformation.getString(1);

                //Tar ut strängen ur kolumn 2 (Undertexterna)
                String undertextFranDB = allInformation.getString(2);

                //Tar ut strängen ur kolumn 3 (Bildfilvägen)
                String bildfilvagDB = allInformation.getString(3);


                idn.add(idFranDB);
                rubriker.add(rubrikFranDB);
                underTexter.add(undertextFranDB);
                filvagforbilder.add(bildfilvagDB);

                //Går vidare till nästa rad i listan
                allInformation.moveToNext();
            }
        }
        else
        {

            Toast.makeText(MainActivity.this,"Det finns inga inlägg, tryck på knapparna uppe i högra" +
                    " hörnet för att skapa ett nytt inlägg", Toast.LENGTH_LONG).show();
            RelativeLayout bakgrund = (RelativeLayout) findViewById(R.id.mainBakgrund);
            bakgrund.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Tryck på knapparna i högra hörnet för att navigera", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //Åtgärd som används när användaren trycker på menyknappen på ett inlägg
    public void InlaggMenyKlick (View v)
    {
        final int rowId = (int)v.getTag();
        final CharSequence[] options = { "Ja","Nej" };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Vill du ta bort inlägget?");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Ja"))
                {
                    myDb.deleteSingleRow(rowId);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    dialog.dismiss();

                }

                else if (options[item].equals("Nej")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    /*Öppnar google maps när en användare trycker på kartknappen på ett inlägg och beroende
    * på om det finns en position eller inte i databasen för inlägget, kommer den att visa
    * göteborg som startpunkt första gången annars den position användaren tryckte i senast
    * för inlägget*/
    public void OppnaKartorKnapp (View v)
    {
        final int rowid = (int)v.getTag();
        Cursor positioninformation = myDb.HamtaData();
        boolean finnsPositionEllerInte = positioninformation.moveToPosition(rowid-1);
        String position = positioninformation.getString(4);
        if (position != null)
        {

            String[] latlong =  position.split(",");
            String latitude = latlong[0];
            String longitude = latlong[1];
            StringBuilder sb = new StringBuilder(latitude);
            sb.delete(0,10);
            StringBuilder sb2 = new StringBuilder(longitude);
            sb2.setLength(sb.length() - 1);
            String rattLatitude = sb.toString();
            String rattLongitude = sb2.toString();

            Intent i = new Intent(getApplicationContext(), MapsActivity.class);
            i.putExtra("latitude", rattLatitude);
            i.putExtra("longitude", rattLongitude);
            startActivity(i);


        }
        else
        {
            Intent i = new Intent(getApplicationContext(), MapsActivity.class);
            i.putExtra("radID", rowid);
            startActivity(i);
        }
    }

    //Skapar menyn i main
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Case-sats för att hantera de olika alternativen i menyn
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.camera_button:
                isCameraPermissionGranted();
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(i);
                return true;
            case R.id.miniraknare_knapp:
                startActivity(new Intent(getApplication(), Miniraknare.class));
                return true;
            case R.id.nytt_inlagg:
                startActivity(new Intent(this, NyttInlagg_ValjBild.class));
                return true;
            case R.id.Radera_alla_inlagg:
                taBortAllaInlagg();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*Skapar en varningsruta innan alla inlägg tas bort och om användaren svarar jag kommer databasen
    * att rensas på alla data och således töms listviewn*/
    private void taBortAllaInlagg() {

        /*If-sats för att kolla om det finns inlägg eller inte (om en databas existerar eller inte).
        * Finns ingen databas finns alltså inga inlägg*/
        if (finnsDatabasEllerInte == true)
        {
            final CharSequence[] options = { "Ja","Nej" };
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Är du säker på att du vill radera allt?");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Ja"))
                    {
                        myDb.deleteAllNotes();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }

                    else if (options[item].equals("Nej")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
        else
        {
            Toast.makeText(MainActivity.this,"Det finns inga inlägg, skapa nya genom att trycka: Nytt Inlägg ",Toast.LENGTH_SHORT).show();
        }
    }

    //Metod som frågar om applikationen får access till kameran. Används vid kameravalet i menyn.
    public  boolean isCameraPermissionGranted()
    {
        /*Kontrolerar om sdk >= 23. Detta måste göras vid denna versionen men i tidigare versioner
        * godkäns detta i Google Play*/
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkSelfPermission(android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            }
            else
            {
                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 1);
                return false;
            }
        }
        else
        //Automatiskt godkänd om sdk<API23.
        {
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

}

