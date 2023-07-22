package com.riftech.a99namesofallah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.ump.ConsentDebugSettings;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ListView listView ;
    private ProgressBar pgsBar;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private Intent intent;

    private ConsentInformation consentInformation;
    private ConsentForm consentForm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//                ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder(this)
//                .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
//                .addTestDeviceHashedId("C0C3585D7681D1732E0C5A43C804A1C6")
//                .build();
//
//
//        ConsentRequestParameters params = new ConsentRequestParameters
//                .Builder()
//                .setConsentDebugSettings(debugSettings)
//                .build();



        // Set tag for under age of consent. false means users are not under
        // age.
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();

        consentInformation = UserMessagingPlatform.getConsentInformation(this);
        consentInformation.requestConsentInfoUpdate(
                this,
                params,
                new ConsentInformation.OnConsentInfoUpdateSuccessListener() {
                    @Override
                    public void onConsentInfoUpdateSuccess() {
                        // The consent information state was updated.
                        // You are now ready to check if a form is available.

                        if (consentInformation.isConsentFormAvailable()) {

                            loadForm();
                        }
                    }
                },
                new ConsentInformation.OnConsentInfoUpdateFailureListener() {
                    @Override
                    public void onConsentInfoUpdateFailure(FormError formError) {
                        // Handle the error.
                    }
                });


       // SharedPreferences prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        /*SharedPreferences sharedPrefs = getSharedPreferences("sp_lang", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        if(!sharedPrefs.contains("initialized")) {
            editor = sharedPrefs.edit();
            editor.putBoolean("initialized", true);
            editor.putString("language", "English");
            editor.apply();
        }

        SharedPreferences myPrefs = this.getSharedPreferences("sp_lang", MODE_PRIVATE);
        String lang = myPrefs.getString("language",null);
*/

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

loadAd();



//        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
//        myFab.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                intent = new Intent(getBaseContext(), MainActivity2.class);
//                    startActivity(intent);
//            }
//        });
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        //String names[] = {"Ya-Rahman ","Ya-Raheem","Ya-Malik","Ya-Quddus","Ya-Salam","Ya-Mu min","Ya-Muhaymin","Ya-Aziz","Ya-Jabbar","Ya-Mutakabbir","Ya-Khaaliq","Ya-Baari","Ya-Musawwir","Ya-Ghaffar","Ya-Qahhaar","Ya-Wahhaab","Ya-Razzaaq","Ya-Fattaah","Ya- Alim","Ya-Qaabid","Ya-Basit","Ya-Khaafid","Ya-Raafi","Ya-Mu izz","Ya-Muzil","Ya-Sami","Ya-Basir","Ya-Hakam","Ya-Adl","Ya-Latif","Ya-Khabir","Ya-Halim","Ya- Azim","Ya-Ghafur","Ya-Shakur","Ya- Ali","Ya-Kabir","Ya-Hafiz","Ya-Muqit","Ya-Hasib","Ya-Jalil","Ya-Karim","Ya-Raqib","Ya-Mujib","Ya-Wasi","Ya-Hakim","Ya-Wadud","Ya-Majeed","Ya-Baa ith","Ya-Shahid","Ya-Haqq","Ya-Wakil","Ya-Qawiyy","Ya-Matin","Ya-Wali","Ya-Hamid","Ya-Muhsi","Ya-Mubdi","Ya-Mu id","Ya-Muhyi","Ya-Mumit","Ya-Hayy","Ya-Qayyum","Ya-Waajid","Ya-Maajid","Ya-Waahid","Ya-Ahad","Ya-Samad","Ya-Qadir","Ya-Muqtadir","Ya-Muqaddim","Ya-Mu akhkhir","Ya-Awwal","Ya-Akhir","Ya-Zahir","Ya-Batin","Ya-Wali","Ya-Muta ali","Ya-Barr","Ya-Tawwaab","Ya-Muntaqim","Ya-Afuww","Ya-Ra uf","Ya Malik Al-Mulk","Dhu Ya Jalal wa Ya Ikram","Ya-Muqsit","Ya-Jami","Ya-Ghani","Ya-Mughni","Ya-Mani","Ya-Darr","Ya-Nafi","Ya-Nur","Ya-Hadi","Ya-Badi","Ya-Baaqi","Ya-Waarith","Ya-Rashid","Ya-Sabur"};
        //String name[]={"1. Ya Rahman	-	الرحمن	","2.	Ya Raheem	-	الرحيم	","3.	Ya Malik	-	الملك	","4.	Ya Quddus	-	القدوس	","5.	Ya Salam	-	السلام	","6.	Ya Mu'min	-	المؤمن	","7.	Ya Muhaymin	-	المهيمن	","8.	Ya Aziz	-	العزيز	","9.	Ya Jabbaar	-	الجبار	","10.	Ya Mutakabbir	-	الْمُتَكَبِّرُ	","11.	Ya Khaaliq	-	الخالق	","12.	Ya Baari	-	البارئ	","13.	Ya Musawwir	-	المصور	","14.	Ya Ghaffaar	-	الغفار	","15.	Ya Qahhaar	-	القهار	","16.	Ya Wahhaab	-	الوهاب	","17.	Ya Razzaaq	-	الرزاق	","18.	Ya Fattaah	-	الفتاح	","19.	Ya Alim	-	العليم	","20.	Ya Qaabidh	-	القابض	","21.	Ya Baasit	-	الباسط	","22.	Ya Khaafidh	-	الخافض	","23.	Ya Raafi'	-	الرافع	","24.	Ya Mu'izz	-	المعز	","25.	Ya Muzil	-	المذل	","26.	Ya Sami'	-	السميع	","27.	Ya Basir	-	البصير	","28.	Ya Hakam	-	الحكم	","29.	Ya 'Adl	-	العدل	","30.	Ya Latif	-	اللطيف	","31.	Ya Khabir	-	الخبير	","32.	Ya Halim	-	الحليم	","33.	Ya-‘Adheem	-	العظيم	","34.	Ya Ghafur	-	الغفور	","35.	Ya Shakur	-	الشكور	","36.	Ya Ali	-	العلي	","37.	Ya Kabir	-	الكبير	","38.	Ya Hafidh	-	الحفيظ	","39.	Ya Muqit	-	المقيت	","40.	Ya Hasib	-	الحسيب	","41.	Ya Jalil	-	الجليل	","42.	Ya Karim	-	الكريم	","43.	Ya Raqib	-	الرقيب	","44.	Ya Mujib	-	المجيب	","45.	Ya Wasi'	-	الواسع	","46.	Ya Hakim	-	الحكيم	","47.	Ya Wadud	-	الودود	","48.	Ya Majid	-	المجيد	","49.	Ya Ba'ith	-	الباعث	","50.	Ya Shaheed	-	الشهيد	","51.	Ya Haqq	-	الحق	","52.	Ya Wakil	-	الوكيل	","53.	Ya Qawiyy	-	القوي	","54.	Ya Mateen	-	المتين	","55.	Ya Wali	-	الولي	","56.	Ya Hamidu	-	الحميد	","57.	Ya Muhsi	-	المحصي	","58.	Ya Mubdi	-	المبدئ	","59.	Ya Mu'id	-	المعيد	","60.	Ya Muhyi	-	المحيي	","61.	Ya Mumit	-	المميت	","62.	Ya Hayy	-	الحي	","63.	Ya Qayyum	-	القيوم	","64.	Ya Waajid	-	الواجد	","65.	Ya Maajid	-	الماجد	","66.	Ya Waahid	-	الواحد	","67.	Ya Ahad	-	الاحد	","68.	Ya Samad	-	الصمد	","69.	Ya Qaadir	-	القادر	","70.	Ya Muqtadir	-	المقتدر	","71.	Ya Muqaddim	-	المقدم	","72.	Ya Mu'akhkhir	-	المؤخر	","73.	Ya Awwal	-	الأول	","74.	Ya Aakhir	-	الآخر	","75.	Ya Dhaahir	-	الظاهر	","76.	Ya Baatin	-	الباطن	","77.	Ya Waali	-	الوالي	","78.	Ya Muta'ali	-	المتعالي	","79.	Ya Barr	-	البر	","80.	Ya Tawwaab	-	التواب	","81.	Ya Muntaqim	-	المنتقم	","82.	Ya 'Afuww	-	العفو	","83.	Ya Ra'uf	-	الرؤوف	","84.	Ya Malik Al Mulk	-	مالك الملك	","85.	Dhual Jalal wa Ya Ikram	-	ذو الجلال و الإكرام	","86.	Ya Muqsit	-	المقسط	","87.	Ya Jaami'	-	الجامع	","88.	Ya Ghani	-	الغني	","89.	Ya Mughni	-	المغني	","90.	Ya Mani'	-	المانع	","91.	Ya Dharr	-	الضآر	","92.	Ya Nafi'	-	النافع	","93.	Ya Nur	-	النور	","94.	Ya Hadi	-	الهادي	","95.	Ya Badi'i	-	البديع	","96.	Ya Baaqi	-	الباقي	","97.	Ya Waarith	-	الوارث	","98.	Ya Rashid	-	الرشيد	","99.	Ya Saboor	-	الصبور	"};
//String name[]={"يا رحمانو 1 - Ya Rahmanu , يا رحيمو 2 - Ya Raheemu , يا ماليكو 3 - Ya Maliku , يا قدوسو 4 - Ya Quddusu , يا سلام 5 - Ya Salamu , يا مؤمنو 6 - Ya Mu'minu , يا محيمينو 7 - Ya Muhayminu , يا عزيزو 8 - Ya Azizu , يا جبارو 9 - Ya Jabbaaru , يا متاكبيرو 10 - Ya Mutakabbiru , يا خالق 11 - Ya Khaaliqu , يا بارو 12 - Ya Baariu , يا مصورويرو 13 - Ya Musawwiru , يا غفارو 14 - Ya Ghaffaaru , يا قهارو 15 - Ya Qahhaaru , يا وهابوا 16 - Ya Wahhaabu , يا رزاق 17 - Ya Razzaaqu , يا فتاحو 18 - Ya Fattaahu , يا عليمو 19 - Ya Alimu , يا قعبيدو 20 - Ya Qaabidhu , يا بعسيت 21 - Ya Baasitu , يا خافيدو 22 - Ya Khaafidhu , يا رافيو 23 - Ya Raafi'u , يا معتزو 24 - Ya Mu'izzu , يا موزيلو 25 - Ya Muzillu , يا سميع 26 - Ya Sami'u , يا بصيرو 27 - Ya Basiru , يا حكمو 28 - Ya Hakamu , يا عدلو 29 - Ya 'Adlu , يا لطيفو 30 - Ya Latifu , يا خبيرو 31 - Ya Khabiru , يا حليمو 32 - Ya Halimu , يا عذيبو 33 - Ya-‘Adheemu , يا غفور 34 - Ya Ghafuru , يا شكور 35 - Ya Shakuru , يا عليو 36 - Ya Aliu , يا كبيررو 37 - Ya Kabiru , يا حفيظو 38 - Ya Hafidhu , يا مقيتو 39 - Ya Muqitu , يا حاسبو 40 - Ya Hasibu , يا جليلو 41 - Ya Jalilu , يا كريمو 42 - Ya Karimu , يا راقيبو 43 - Ya Raqibu , يا موجيبو 44 - Ya Mujibu , يا واسيعو 45 - Ya Wasi'u , يا حكيمو 46 - Ya Hakimu , يا ودودو 47 - Ya Wadudu , يا مجيدو 48 - Ya Majidu , يا بعيثو 49 - Ya Ba'ithu , يا شهيدو 50 - Ya Shaheedu , يا حقو 51 - Ya Haqqu , يا وكيلو 52 - Ya Wakilu , يا قاوييو 53 - Ya Qawiyyu , يا ماتينو 54 - Ya Mateenu , يا وليو 55 - Ya Waliu , يا حميدو 56 - Ya Hamiduu , يا محسي 57 - Ya Muhsi , يا مبديو 58 - Ya Mubdiu , يا معييدو 59 - Ya Mu'idu , يا محيي 60 - Ya Muhyi , يا مميتو 61 - Ya Mumitu , يا حييو 62 - Ya Hayyu , يا قيومو 63 - Ya Qayyumu , يا واجيدو 64 - Ya Waajidu , يا مجيدو 65 - Ya Maajidu , يا وحيدو 66 - Ya Waahidu , يا أهادو 67 - Ya Ahadu , يا سامادو 68 - Ya Samadu , يا قادرو 69 - Ya Qaadiru , يا مقتدر 70 - Ya Muqtadiru , يا مقدمو 71 - Ya Muqaddimu , يا مؤخيرو 72 - Ya Mu'akhkhiru , يا أوالو 73 - Ya Awwalu , يا أخيرو 74 - Ya Aakhiru , يا ظاهر 75 - Ya Dhaahiru , يا باتينو 76 - Ya Baatinu , يا والي 77 - Ya Waaliu , يا متعالي 78 - Ya Muta'ali , يا برو 79 - Ya Barru , يا توابو 80 - Ya Tawwaabu , يا منتقمو 81 - Ya Muntaqimu , يا عفوا 82 - Ya 'Afuwwu , يا رؤوفو 83 - Ya Ra'ufu , يا مالك الملقي 84 - Ya Malik Al Mulki , يا جل جلالي الإكرام 85 - Ya Jal-Jalali Al Ikram , يا مقسيت 86 - Ya Muqsitu , يا جامعو 87 - Ya Jaami'u , يا غنيو 88 - Ya Ghaniu , يا مغنيو 89 - Ya Mughniu , يا مانيو 90 - Ya Mani'u , يا ضرو 91 - Ya Dharru , يا نافع 92 - Ya Nafi'u , يا نورو 93 - Ya Nuru , يا هاديو 94 - Ya Hadiu , يا بديع 95 - Ya Badi'iu , يا باكيو 96 - Ya Baaqiu , يا واريثو 97 - Ya Waarithu , يا رشيدو 98 - Ya Rashidu , يا سابورو - 99.Ya Sabooru"};
        String names[] = {"Ar-Rahman ","Ar-Raheem","Al-Malik","Al-Quddus","As-Salam","Al-Mu min","Al-Muhaymin","Al-Aziz","Al-Jabbar","Al-Mutakabbir","Al-Khaaliq","Al-Baari","Al-Musawwir","Al-Ghaffar","Al-Qahhaar","Al-Wahhaab","Ar-Razzaaq","Al-Fattaah","Al- Alim","Al-Qaabid","Al-Basit","Al-Khaafid","Ar-Raafi","Al-Mu izz","Al-Muzil","As-Sami","Al-Basir","Al-Hakam","Al-Adl","Al-Latif","Al-Khabir","Al-Halim","Al- Azim","Al-Ghafur","Ash-Shakur","Al- Ali","Al-Kabir","Al-Hafiz","Al-Muqit","Al-Hasib","Al-Jalil","Al-Karim","Ar-Raqib","Al-Mujib","Al-Wasi","Al-Hakim","Al-Wadud","Al-Majeed","Al-Baa ith","Ash-Shahid","Al-Haqq","Al-Wakil","Al-Qawiyy","Al-Matin","Al-Wali","Al-Hamid","Al-Muhsi","Al-Mubdi","Al-Mu id","Al-Muhyi","Al-Mumit","Al-Hayy","Al-Qayyum","Al-Waajid","Al-Maajid","Al-Waahid","Al-Ahad","As-Samad","Al-Qadir","Al-Muqtadir","Al-Muqaddim","Al-Mu akhkhir","Al-Awwal","Al-Akhir","Az-Zahir","Al-Batin","Al-Wali","Al-Muta ali","Al-Barr","At-Tawwaab","Al-Muntaqim","Al-Afuww","Ar-Ra uf","Malik al-Mulk","Dhu al Jalal wa al Ikram","Al-Muqsit","Al-Jami","Al-Ghani","Al-Mughni","Al-Mani","Ad-Darr","An-Nafi","An-Nur","Al-Hadi","Al-Badi","Al-Baaqi","Al-Waarith","Ar-Rashid","As-Sabur"};
        String name[]={"1.	Ar Rahman	-	الرحمن	","2.	Ar Raheem	-	الرحيم	","3.	Al Malik	-	الملك	","4.	Al Quddus	-	القدوس	","5.	As Salam	-	السلام	","6.	Al Mu'min	-	المؤمن	","7.	Al Muhaymin	-	المهيمن	","8.	Al Aziz	-	العزيز	","9.	Al Jabbaar	-	الجبار	","10.	Al Mutakabbir	-	الْمُتَكَبِّرُ	","11.	Al Khaaliq	-	الخالق	","12.	Al Baari	-	البارئ	","13.	Al Musawwir	-	المصور	","14.	Al Ghaffaar	-	الغفار	","15.	Al Qahhaar	-	القهار	","16.	Al Wahhaab	-	الوهاب	","17.	Ar Razzaaq	-	الرزاق	","18.	Al Fattaah	-	الفتاح	","19.	Al Alim	-	العليم	","20.	Al Qaabidh	-	القابض	","21.	Al Baasit	-	الباسط	","22.	Al Khaafidh	-	الخافض	","23.	Ar Raafi'	-	الرافع	","24.	Al Mu'izz	-	المعز	","25.	Al Muzil	-	المذل	","26.	As Sami'	-	السميع	","27.	Al Basir	-	البصير	","28.	Al Hakam	-	الحكم	","29.	Al 'Adl	-	العدل	","30.	Al Latif	-	اللطيف	","31.	Al Khabir	-	الخبير	","32.	Al Halim	-	الحليم	","33.	Al-‘Adheem	-	العظيم	","34.	Al Ghafur	-	الغفور	","35.	Ash Shakur	-	الشكور	","36.	Al Ali	-	العلي	","37.	Al Kabir	-	الكبير	","38.	Al Hafidh	-	الحفيظ	","39.	Al Muqit	-	المقيت	","40.	Al Hasib	-	الحسيب	","41.	Al Jalil	-	الجليل	","42.	Al Karim	-	الكريم	","43.	Ar Raqib	-	الرقيب	","44.	Al Mujib	-	المجيب	","45.	Al Wasi'	-	الواسع	","46.	Al Hakim	-	الحكيم	","47.	Al Wadud	-	الودود	","48.	Al Majid	-	المجيد	","49.	Al Ba'ith	-	الباعث	","50.	Ash Shaheed	-	الشهيد	","51.	Al Haqq	-	الحق	","52.	Al Wakil	-	الوكيل	","53.	Al Qawiyy	-	القوي	","54.	Al Mateen	-	المتين	","55.	Al Wali	-	الولي	","56.	Al Hamidu	-	الحميد	","57.	Al Muhsi	-	المحصي	","58.	Al Mubdi	-	المبدئ	","59.	Al Mu'id	-	المعيد	","60.	Al Muhyi	-	المحيي	","61.	Al Mumit	-	المميت	","62.	Al Hayy	-	الحي	","63.	Al Qayyum	-	القيوم	","64.	Al Waajid	-	الواجد	","65.	Al Maajid	-	الماجد	","66.	Al Waahid	-	الواحد	","67.	Al Ahad	-	الاحد	","68.	As Samad	-	الصمد	","69.	Al Qaadir	-	القادر	","70.	Al Muqtadir	-	المقتدر	","71.	Al Muqaddim	-	المقدم	","72.	Al Mu'akhkhir	-	المؤخر	","73.	Al Awwal	-	الأول	","74.	Al Aakhir	-	الآخر	","75.	Az Dhaahir	-	الظاهر	","76.	Al Baatin	-	الباطن	","77.	Al Waali	-	الوالي	","78.	Al Muta'ali	-	المتعالي	","79.	Al Barr	-	البر	","80.	At Tawwaab	-	التواب	","81.	Al Muntaqim	-	المنتقم	","82.	Al 'Afuww	-	العفو	","83.	Ar Ra'uf	-	الرؤوف	","84.	Malik Al Mulk	-	مالك الملك	","85.	Dhual Jalal wa Al Ikram	-	ذو الجلال و الإكرام	","86.	Al Muqsit	-	المقسط	","87.	Al Jaami'	-	الجامع	","88.	Al Ghani	-	الغني	","89.	Al Mughni	-	المغني	","90.	Al Mani'	-	المانع	","91.	Ad Dharr	-	الضآر	","92.	An Nafi'	-	النافع	","93.	An Nur	-	النور	","94.	Al Hadi	-	الهادي	","95.	Al Badi'i	-	البديع	","96.	Al Baaqi	-	الباقي	","97.	Al Waarith	-	الوارث	","98.	Ar Rashid	-	الرشيد	","99.	Al Saboor	-	الصبور	"};

       /* String st;



        st=getString(R.string.English);

String st2=getString(R.string.Arabic);

        String[] name2 =st2.split(",");

        String[] name =st.split(",");

        for (int i=0;i<name.length;i++){
            
        }*/

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
                    Log.d("TAG", "The interstitial Ya wasn't ready yet.");
                    loadAd();
                    startActivity(intent);
                }


            }

        });
    }

//    public void changeConstrain() {
//        ConstraintSet set = new ConstraintSet();
//        ConstraintLayout layout;
//        layout = (ConstraintLayout) findViewById(R.id.layout);
//        set.clone(layout);
//// The following breaks the connection.
//        set.clear(R.id.floatingActionButton, ConstraintSet.END);
//// Comment out line above and uncomment line below to make the connection.
// set.connect(R.id.floatingActionButton, ConstraintSet.START, R.id.layout, ConstraintSet.BOTTOM, 0);
//        set.applyTo(layout);
//    }
    public void loadAd() {
        AdRequest adRequest2 = new AdRequest.Builder().build();
//ca-app-pub-3940256099942544/1033173712
        //ca-app-pub-7831928589958637/6266654826
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

    public void loadForm() {
        // Loads a consent form. Must be called on the main thread.
        UserMessagingPlatform.loadConsentForm(
                this,
                new UserMessagingPlatform.OnConsentFormLoadSuccessListener() {
                    @Override
                    public void onConsentFormLoadSuccess(ConsentForm consentForm) {
                        MainActivity.this.consentForm = consentForm;
                        if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                            consentForm.show(
                                    MainActivity.this,
                                    new ConsentForm.OnConsentFormDismissedListener() {
                                        @Override
                                        public void onConsentFormDismissed(@Nullable FormError formError) {
                                            if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.OBTAINED) {
                                                // App can start requesting ads.
                                            }

                                            // Handle dismissal by reloading form.
                                            loadForm();
                                        }
                                    });
                        }
                    }
                },
                new UserMessagingPlatform.OnConsentFormLoadFailureListener() {
                    @Override
                    public void onConsentFormLoadFailure(FormError formError) {
                        // Handle the error.
                    }
                }
        );
    }

}
