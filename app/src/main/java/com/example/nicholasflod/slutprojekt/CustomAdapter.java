package com.example.nicholasflod.slutprojekt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

class CustomAdapter extends ArrayAdapter<String>
{
    //Klassvariabler
    private List<String> underTexterLista = new ArrayList<String>();
    private List<String> bildfilvagLista = new ArrayList<String>();
    private List<Integer> iDLista = new ArrayList<Integer>();


    //Konstruktor
    public CustomAdapter(Context context, List<String> rubrikListaFranMain, List<String> underTexterFranMain, List<String> bildFilVagFranMain, List<Integer> idn)
    {
        super(context,R.layout.enlistrad, rubrikListaFranMain);
        this.underTexterLista = underTexterFranMain;
        this.bildfilvagLista = bildFilVagFranMain;
        this.iDLista = idn;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        LayoutInflater nicholasInflater = LayoutInflater.from(getContext());
        View customView = nicholasInflater.inflate(R.layout.enlistrad, parent, false);

        //Referenser till de olika ID:na i enlistrad.xml
        TextView rubrik = (TextView) customView.findViewById(R.id.storText);
        TextView underTexten = (TextView) customView.findViewById(R.id.litenText);
        ImageView bilden = (ImageView) customView.findViewById(R.id.visaBild);
        Button flervalsknapp = (Button) customView.findViewById(R.id.inlaggMeny);
        Button kartKnapp = (Button) customView.findViewById(R.id.mapsKnapp);


        //Sätter text och bild till widget:sen i enlistrad.xml
        final int id = iDLista.get(position);
        flervalsknapp.setTag(id);
        kartKnapp.setTag(id);
        String enRubrik = getItem(position);
        rubrik.setText(enRubrik);
        underTexten.setText(underTexterLista.get(position));
        Bitmap forminskadbild = decodeSampledBitmapFromString(200,200,position);
        bilden.setImageBitmap(forminskadbild);
        return customView;
    }


    /*Metoderna nedan är tagna från länk:
     * http://www.past5.com/tutorials/2014/10/24/bitmaps-in-android-listview/
      * Det metoderna gör är att minska/komprimera storleken på den bitmapsen/bilderna för att
      * undvika att applikationen Kraschar. Problemet är att vanliga bilder är stora i både utrymme
      * och bildstorlek. Detta skapar problem för applikationen då bilderna tar upp för mycket minne
      * än vad telefonen klarar av. Modiferade inparametrar lite för att den ska fungera för mig,
      * jag skickar alltså med positionen samtidigt (positionen för inlägget i listan)*/
    private Bitmap decodeSampledBitmapFromString(int reqWidth, int reqHeight, int position)
    {
        Bitmap bitmap;

        //decode File and set inSampleSize
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(bildfilvagLista.get(position), options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // decode File with inSampleSize set
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(bildfilvagLista.get(position), options);
        return bitmap;
    }
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }
}
