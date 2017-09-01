package com.example.nicholasflod.slutprojekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class NyttInlagg_RubrikOUndertext extends AppCompatActivity
{
    private EditText rubrikText;
    private EditText underText;
    private String bildfil;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nytt_inlagg__rubrik_oundertext);
        rubrikText = (EditText) findViewById(R.id.nyttInlaggRubrikInmatning);
        underText = (EditText) findViewById(R.id.nyttInlaggUndertextInmatning);
    }


    //Går vidare till nästa sida
    public void NastaSida (View v)
    {
        Bundle extras = getIntent().getExtras();
        bildfil = extras.getString("bildfil");
        boolean gaVidare = TextEllerInte();
        if (gaVidare == true)
        {
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            i.putExtra("rubrik", rubrikText.getText().toString());
            i.putExtra("undertext", underText.getText().toString());
            i.putExtra("bildfil", bildfil);
            startActivity(i);
        }
        else
        {

        }
    }

    //Knappen för att gå tillbaka till tidigare sida
    public void Tillbaka (View v)
    {
        startActivity(new Intent(this, NyttInlagg_ValjBild.class));
    }

    //Kontrolerar om det finns en rubrik eller inte
    public boolean TextEllerInte ()
    {
        boolean finnsText = false;
        if (rubrikText.getText().toString().equals(""))
        {
            Toast.makeText(NyttInlagg_RubrikOUndertext.this,"Det måste finnas en rubrik", Toast.LENGTH_LONG).show();
        }
        else
        {
            finnsText = true;
        }
        return finnsText;
    }
}
