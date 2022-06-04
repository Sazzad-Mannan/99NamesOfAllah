package com.riftech.a99namesofallah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ListView listView ;
    private ProgressBar pgsBar;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private Intent intent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

loadAd();




        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        String names[] = {"Ar-Rahman ","Ar-Raheem","Al-Malik","Al-Quddus","As-Salam","Al-Mu min","Al-Muhaymin","Al-Aziz","Al-Jabbar","Al-Mutakabbir","Al-Khaaliq","Al-Baari","Al-Musawwir","Al-Ghaffar","Al-Qahhaar","Al-Wahhaab","Ar-Razzaaq","Al-Fattaah","Al- Alim","Al-Qaabid","Al-Basit","Al-Khaafid","Ar-Raafi","Al-Mu izz","Al-Muzil","As-Sami","Al-Basir","Al-Hakam","Al-Adl","Al-Latif","Al-Khabir","Al-Halim","Al- Azim","Al-Ghafur","Ash-Shakur","Al- Ali","Al-Kabir","Al-Hafiz","Al-Muqit","Al-Hasib","Al-Jalil","Al-Karim","Ar-Raqib","Al-Mujib","Al-Wasi","Al-Hakim","Al-Wadud","Al-Majeed","Al-Baa ith","Ash-Shahid","Al-Haqq","Al-Wakil","Al-Qawiyy","Al-Matin","Al-Wali","Al-Hamid","Al-Muhsi","Al-Mubdi","Al-Mu id","Al-Muhyi","Al-Mumit","Al-Hayy","Al-Qayyum","Al-Waajid","Al-Maajid","Al-Waahid","Al-Ahad","As-Samad","Al-Qadir","Al-Muqtadir","Al-Muqaddim","Al-Mu akhkhir","Al-Awwal","Al-Akhir","Az-Zahir","Al-Batin","Al-Wali","Al-Muta ali","Al-Barr","At-Tawwaab","Al-Muntaqim","Al-Afuww","Ar-Ra uf","Malik al-Mulk","Dhu al Jalal wa al Ikram","Al-Muqsit","Al-Jami","Al-Ghani","Al-Mughni","Al-Mani","Ad-Darr","An-Nafi","An-Nur","Al-Hadi","Al-Badi","Al-Baaqi","Al-Waarith","Ar-Rashid","As-Sabur"};
        String name[]={"1.	Ar Rahman	-	الرحمن	","2.	Ar Raheem	-	الرحيم	","3.	Al Malik	-	الملك	","4.	Al Quddus	-	القدوس	","5.	As Salam	-	السلام	","6.	Al Mu'min	-	المؤمن	","7.	Al Muhaymin	-	المهيمن	","8.	Al Aziz	-	العزيز	","9.	Al Jabbaar	-	الجبار	","10.	Al Mutakabbir	-	الْمُتَكَبِّرُ	","11.	Al Khaaliq	-	الخالق	","12.	Al Baari	-	البارئ	","13.	Al Musawwir	-	المصور	","14.	Al Ghaffaar	-	الغفار	","15.	Al Qahhaar	-	القهار	","16.	Al Wahhaab	-	الوهاب	","17.	Ar Razzaaq	-	الرزاق	","18.	Al Fattaah	-	الفتاح	","19.	Al Alim	-	العليم	","20.	Al Qaabidh	-	القابض	","21.	Al Baasit	-	الباسط	","22.	Al Khaafidh	-	الخافض	","23.	Ar Raafi'	-	الرافع	","24.	Al Mu'izz	-	المعز	","25.	Al Muzil	-	المذل	","26.	As Sami'	-	السميع	","27.	Al Basir	-	البصير	","28.	Al Hakam	-	الحكم	","29.	Al 'Adl	-	العدل	","30.	Al Latif	-	اللطيف	","31.	Al Khabir	-	الخبير	","32.	Al Halim	-	الحليم	","33.	Al-‘Adheem	-	العظيم	","34.	Al Ghafur	-	الغفور	","35.	Ash Shakur	-	الشكور	","36.	Al Ali	-	العلي	","37.	Al Kabir	-	الكبير	","38.	Al Hafidh	-	الحفيظ	","39.	Al Muqit	-	المقيت	","40.	Al Hasib	-	الحسيب	","41.	Al Jalil	-	الجليل	","42.	Al Karim	-	الكريم	","43.	Ar Raqib	-	الرقيب	","44.	Al Mujib	-	المجيب	","45.	Al Wasi'	-	الواسع	","46.	Al Hakim	-	الحكيم	","47.	Al Wadud	-	الودود	","48.	Al Majid	-	المجيد	","49.	Al Ba'ith	-	الباعث	","50.	Ash Shaheed	-	الشهيد	","51.	Al Haqq	-	الحق	","52.	Al Wakil	-	الوكيل	","53.	Al Qawiyy	-	القوي	","54.	Al Mateen	-	المتين	","55.	Al Wali	-	الولي	","56.	Al Hamidu	-	الحميد	","57.	Al Muhsi	-	المحصي	","58.	Al Mubdi	-	المبدئ	","59.	Al Mu'id	-	المعيد	","60.	Al Muhyi	-	المحيي	","61.	Al Mumit	-	المميت	","62.	Al Hayy	-	الحي	","63.	Al Qayyum	-	القيوم	","64.	Al Waajid	-	الواجد	","65.	Al Maajid	-	الماجد	","66.	Al Waahid	-	الواحد	","67.	Al Ahad	-	الاحد	","68.	As Samad	-	الصمد	","69.	Al Qaadir	-	القادر	","70.	Al Muqtadir	-	المقتدر	","71.	Al Muqaddim	-	المقدم	","72.	Al Mu'akhkhir	-	المؤخر	","73.	Al Awwal	-	الأول	","74.	Al Aakhir	-	الآخر	","75.	Az Dhaahir	-	الظاهر	","76.	Al Baatin	-	الباطن	","77.	Al Waali	-	الوالي	","78.	Al Muta'ali	-	المتعالي	","79.	Al Barr	-	البر	","80.	At Tawwaab	-	التواب	","81.	Al Muntaqim	-	المنتقم	","82.	Al 'Afuww	-	العفو	","83.	Ar Ra'uf	-	الرؤوف	","84.	Malik Al Mulk	-	مالك الملك	","85.	Dhual Jalal wa Al Ikram	-	ذو الجلال و الإكرام	","86.	Al Muqsit	-	المقسط	","87.	Al Jaami'	-	الجامع	","88.	Al Ghani	-	الغني	","89.	Al Mughni	-	المغني	","90.	Al Mani'	-	المانع	","91.	Ad Dharr	-	الضآر	","92.	An Nafi'	-	النافع	","93.	An Nur	-	النور	","94.	Al Hadi	-	الهادي	","95.	Al Badi'i	-	البديع	","96.	Al Baaqi	-	الباقي	","97.	Al Waarith	-	الوارث	","98.	Ar Rashid	-	الرشيد	","99.	Al Saboor	-	الصبور	"};


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, name){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.BLACK);

                // Generate ListView Item using TextView
                return view;
            }
        };


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                // ListView Clicked item index
                final int itemPosition     = position;

                // ListView Clicked item value
                final String  itemValue    = (String) listView.getItemAtPosition(position);
                 intent = new Intent(getBaseContext(), Main2Activity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("value", itemValue);
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    loadAd();
                    startActivity(intent);
                }


            }

        });
    }

    public void loadAd() {
        AdRequest adRequest2 = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-7831928589958637/6266654826", adRequest2,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        //Toast.makeText(MainActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        MainActivity.this.mInterstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");

                                       startActivity(intent);
                                        //dismissed();

                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        MainActivity.this.mInterstitialAd = null;
                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                       /* String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
                        Toast.makeText(
                                MainActivity.this, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
                                .show();*/
                    }

                });
    }

    public void start_match(final View view){



    }
}
