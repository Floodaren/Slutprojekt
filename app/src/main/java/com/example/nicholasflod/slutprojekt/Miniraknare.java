package com.example.nicholasflod.slutprojekt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
public class Miniraknare extends AppCompatActivity
{

    private double tal1 = 0;
    private double tal2 = 0;
    private String operator = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miniraknare);
    }

    public void Clear (View v)
    {
        TextView resultat = (TextView)findViewById(R.id.resultat);
        resultat.setText("0");
    }

    public void PlusMinus (View v)
    {
        TextView resultat = (TextView)findViewById(R.id.resultat);
        double resultatTal = Double.parseDouble(resultat.getText().toString());
        double negativtEllerPositivt = resultatTal * -1;
        String utfortResultat = Double.toString(negativtEllerPositivt);
        resultat.setText(utfortResultat);
    }

    public void Procent (View v)
    {
        TextView resultat = (TextView)findViewById(R.id.resultat);
        double resultatTal = Double.parseDouble(resultat.getText().toString());
        double procentTal = (resultatTal / 100);
        String utfortResultat = Double.toString(procentTal);
        resultat.setText(utfortResultat);

    }

    public void Sjuan (View v)
    {
        TextView resultat = (TextView)findViewById(R.id.resultat);
        String resultatStrang = resultat.getText().toString();
        String resultatStrang2 = "";
        if (resultatStrang.equals("0"))
        {
            resultatStrang2 = "7";
        }
        else
        {
            resultatStrang2 = resultatStrang+"7";
        }
        resultat.setText(resultatStrang2);

    }

    public void Attan (View v)
    {
        TextView resultat = (TextView)findViewById(R.id.resultat);
        String resultatStrang = resultat.getText().toString();
        String resultatStrang2 = "";
        if (resultatStrang.equals("0"))
        {
            resultatStrang2 = "8";
        }
        else
        {
            resultatStrang2 = resultatStrang+"8";
        }
        resultat.setText(resultatStrang2);
    }

    public void Nian (View v)
    {
        TextView resultat = (TextView)findViewById(R.id.resultat);
        String resultatStrang = resultat.getText().toString();
        String resultatStrang2 = "";
        if (resultatStrang.equals("0"))
        {
            resultatStrang2 = "9";
        }
        else
        {
            resultatStrang2 = resultatStrang+"9";
        }
        resultat.setText(resultatStrang2);
    }

    public void Sexan (View v)
    {
        TextView resultat = (TextView)findViewById(R.id.resultat);
        String resultatStrang = resultat.getText().toString();
        String resultatStrang2 = "";
        if (resultatStrang.equals("0"))
        {
            resultatStrang2 = "6";
        }
        else
        {
            resultatStrang2 = resultatStrang+"6";
        }
        resultat.setText(resultatStrang2);
    }

    public void Femman (View v)
    {
        TextView resultat = (TextView)findViewById(R.id.resultat);
        String resultatStrang = resultat.getText().toString();
        String resultatStrang2 = "";
        if (resultatStrang.equals("0"))
        {
            resultatStrang2 = "5";
        }
        else
        {
            resultatStrang2 = resultatStrang+"5";
        }
        resultat.setText(resultatStrang2);
    }

    public void Fyran (View v)
    {
        TextView resultat = (TextView)findViewById(R.id.resultat);
        String resultatStrang = resultat.getText().toString();
        String resultatStrang2 = "";
        if (resultatStrang.equals("0"))
        {
            resultatStrang2 = "4";
        }
        else
        {
            resultatStrang2 = resultatStrang+"4";
        }
        resultat.setText(resultatStrang2);
    }

    public void Ettan (View v)
    {
        TextView resultat = (TextView)findViewById(R.id.resultat);
        String resultatStrang = resultat.getText().toString();
        String resultatStrang2 = "";
        if (resultatStrang.equals("0"))
        {
            resultatStrang2 = "1";
        }
        else
        {
            resultatStrang2 = resultatStrang+"1";
        }
        resultat.setText(resultatStrang2);
    }

    public void Tvaan (View v)
    {
        TextView resultat = (TextView)findViewById(R.id.resultat);
        String resultatStrang = resultat.getText().toString();
        String resultatStrang2 = "";
        if (resultatStrang.equals("0"))
        {
            resultatStrang2 = "2";
        }
        else
        {
            resultatStrang2 = resultatStrang+"2";
        }
        resultat.setText(resultatStrang2);
    }

    public void Trean (View v)
    {
        TextView resultat = (TextView)findViewById(R.id.resultat);
        String resultatStrang = resultat.getText().toString();
        String resultatStrang2 = "";
        if (resultatStrang.equals("0"))
        {
            resultatStrang2 = "3";
        }
        else
        {
            resultatStrang2 = resultatStrang+"3";
        }
        resultat.setText(resultatStrang2);
    }

    public void Nollan (View v)
    {
        TextView resultat = (TextView)findViewById(R.id.resultat);
        String resultatStrang = resultat.getText().toString();
        String resultatStrang2 = "";
        if (resultatStrang.equals("0"))
        {
            resultatStrang2 = "0";
        }
        else
        {
            resultatStrang2 = resultatStrang+"0";
        }
        resultat.setText(resultatStrang2);
    }

    public void Komman (View v)
    {
        TextView resultat = (TextView)findViewById(R.id.resultat);
        String resultatStrang = resultat.getText().toString();
        String resultatStrang2 = "";
        if (resultatStrang.contains("."))
        {
            resultatStrang2 = resultatStrang;
        }
        else
        {
            resultatStrang2 = resultatStrang+".";
        }
        resultat.setText(resultatStrang2);
    }

    public void Division (View v)
    {
        operator = "1";
        TextView resultat = (TextView)findViewById(R.id.resultat);
        tal1 = Double.parseDouble(resultat.getText().toString());
        resultat.setText("");
    }

    public void Addition (View v)
    {
        operator = "2";
        TextView resultat = (TextView)findViewById(R.id.resultat);
        tal1 = Double.parseDouble(resultat.getText().toString());
        resultat.setText("");
    }

    public void Minus (View v)
    {
        operator = "3";
        TextView resultat = (TextView)findViewById(R.id.resultat);
        tal1 = Double.parseDouble(resultat.getText().toString());
        resultat.setText("");
    }

    public void Xet (View v)
    {
        operator = "4";
        TextView resultat = (TextView)findViewById(R.id.resultat);
        tal1 = Double.parseDouble(resultat.getText().toString());
        resultat.setText("");
    }

    public void Likamedan (View v)
    {
        switch (operator)
        {
            case "0":
                break;
            case "1":
                TextView resultat1 = (TextView)findViewById(R.id.resultat);
                tal2 = Double.parseDouble(resultat1.getText().toString());
                String resultatet1 = Double.toString(tal1/tal2);
                resultat1.setText(resultatet1);
                break;
            case "2":
                TextView resultat2 = (TextView)findViewById(R.id.resultat);
                tal2 = Double.parseDouble(resultat2.getText().toString());
                String resultatet2 = Double.toString(tal1+tal2);
                resultat2.setText(resultatet2);
                break;
            case "3":
                TextView resultat3 = (TextView)findViewById(R.id.resultat);
                tal2 = Double.parseDouble(resultat3.getText().toString());
                String resultatet3 = Double.toString(tal1-tal2);
                resultat3.setText(resultatet3);
                break;
            case "4":
                TextView resultat4 = (TextView)findViewById(R.id.resultat);
                tal2 = Double.parseDouble(resultat4.getText().toString());
                String resultatet4 = Double.toString(tal1*tal2);
                resultat4.setText(resultatet4);
                break;
        }
    }
}
