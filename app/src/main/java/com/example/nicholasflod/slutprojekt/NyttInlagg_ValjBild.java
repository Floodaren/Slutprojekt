package com.example.nicholasflod.slutprojekt;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class NyttInlagg_ValjBild extends AppCompatActivity
{

    ImageView visabild;
    Button valjBild;
    String bildfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nytt_inlagg__valj_bild);

        /*Kontrollera att man får tillgång till att läsa från galleriet direkt när man kommer
        * til denna aktivet. Detta är nödvändigt att kontrollera direkt då att använda ett foto
        * till varje inlägg är nödvändigt*/
        galleriBehorighet();
        kameraBehorighet();

        //Skapar en referens till "nyttInlaggValjBild"-knappen
        valjBild =(Button)findViewById(R.id.nyttInlaggValjBild);

        //Skapar en referens till min Imageview
        visabild=(ImageView)findViewById(R.id.nyttInlaggBild);

        //Använder metoden selectImage för att kunna ta ett foto eller välja ett ur biblioteket
        valjBild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anvandarVal();
            }
        });
    }

    public void NyttInlaggTillbaka (View v)
    {
        startActivity(new Intent(this,MainActivity.class));
    }

    public void NyttInlaggNasta (View v)
    {
        boolean bildVald = ValtBild();
            if (bildVald == true)
            {
                Bundle extra = getIntent().getExtras();
                //Skickar med filvägen för bilden som väljs som en sträng
                Intent i = new Intent(getApplicationContext(),NyttInlagg_RubrikOUndertext.class);
                i.putExtra("bildfil", bildfil);;
                startActivity(i);
            }
            else
            {

            }
    }

    //Metod för att kontrollera om en bild är vald och i annat fall ge ett felmeddelande
    public boolean ValtBild()
    {
        boolean arBildVald = false;
        if (bildfil == null)
        {
            Toast.makeText(NyttInlagg_ValjBild.this, "Du måste välja en bild för att gå vidare", Toast.LENGTH_SHORT).show();
        }
        else
        {
            arBildVald = true;
        }
        return arBildVald;
    }

    private void anvandarVal() {

        final CharSequence[] options = { "Ta foto", "Välj från galleriet","Avbryt" };

        AlertDialog.Builder builder = new AlertDialog.Builder(NyttInlagg_ValjBild.this);
        builder.setTitle("Lägg till ett foto");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Ta foto"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);
                }
                else if (options[item].equals("Välj från galleriet"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Avbryt")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int ingaendeVal, int resultCode, Intent data) {
        super.onActivityResult(ingaendeVal, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (ingaendeVal == 2)
            {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,filePath, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePath[0]);
                //Strängen för bilden som väljs, denna skall skickas till main för användning
                String filvag = cursor.getString(columnIndex);
                bildfil = filvag;
                cursor.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(filvag));
                visabild.setImageBitmap(thumbnail);
            }
        }
    }

    //Frågar användaren om applikationen får möjlighet att använda galleriet
    public  boolean galleriBehorighet()
    {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    //Frågar användaren om applikatione får använda kameran
    public  boolean kameraBehorighet()
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                return false;
            }
        }
        else
        {
            return true;
        }
    }
}





