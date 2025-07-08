package com.riftech.a99namesofallah;


import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import androidx.annotation.Nullable;


public class MainActivity extends AppCompatActivity {
    private static final int UPDATE_REQUEST_CODE = 123;
    private AppUpdateManager appUpdateManager;
    private static final String TAG = "MainActivity";
    ListView listView ;
    private ProgressBar pgsBar;

    private Intent intent;


    AlertDialog.Builder builder;
    AlertDialog customAlertDialog;
    SharedPreferences sharedPreferences;
    String names[];
    int selected_index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        // Storing data into SharedPreferences
        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        if(!sharedPreferences.contains("index")) {
// Creating an Editor object to edit(write to the file)
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
            myEdit.putInt("index",0);

// Once the changes have been made, we need to commit to apply those changes made,
// otherwise, it will throw an error
            myEdit.apply();
        }







        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        pgsBar = (ProgressBar) findViewById(R.id.pBar);

//        loadAd();
        changelang();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, names){
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
//                int selected_index = sharedPreferences.getInt("interval", 0);

                // ListView Clicked item value
                final String  itemValue    = (String) listView.getItemAtPosition(position);
                 intent = new Intent(getBaseContext(), Main2Activity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("value", itemValue);
                startActivity(intent);
//


            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Always check for updates when user returns to the app
        checkForUpdate();
    }

    private void checkForUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this);

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.FLEXIBLE,
                            this,
                            UPDATE_REQUEST_CODE
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                // Update is downloaded but not installed
                showCompleteUpdateSnackbar();
            }
        });
    }

    private void showCompleteUpdateSnackbar() {
        Snackbar snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                "Update ready! Restart to apply.",
                Snackbar.LENGTH_INDEFINITE
        );
        snackbar.setAction("Restart", view -> appUpdateManager.completeUpdate());
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(this, "Update canceled. You will be reminded again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.change, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorite) {
            // do something here
            // single item array instance to store which element is selected by user initially
            // it should be set to zero meaning none of the element is selected by default
            selected_index = sharedPreferences.getInt("index", 0);
            final int[] checkedItem = {selected_index};

            // AlertDialog builder instance to build the alert dialog
            builder = new AlertDialog.Builder(MainActivity.this);

            // set the custom icon to the alert dialog
            builder.setIcon(R.drawable.change);

            // title of the alert dialog
            builder.setTitle("Change Language:");

            // list of the items to be displayed to the user in the
            // form of list so that user can select the item from
            final String[] listItems = new String[]{"English","العربية", "Indonesian","বাংলা","हिंदी", "Français", "Italiano","Español","Deutsch","Português","Русский"};


            // the function setSingleChoiceItems is the function which
            // builds the alert dialog with the single item selection
            builder.setSingleChoiceItems(listItems, checkedItem[0], (dialog, which) -> {
                // update the selected item which is selected by the user so that it should be selected
                // when user opens the dialog next time and pass the instance to setSingleChoiceItems method
                checkedItem[0] = which;

                // now also update the TextView which previews the selected item
                //tvSelectedItemPreview.setText("Selected Item is : " + listItems[which]);




                SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                myEdit.putInt("index",which);

// Once the changes have been made, we need to commit to apply those changes made,
// otherwise, it will throw an error
                myEdit.apply();

                changelang();
                // when selected an item the dialog should be closed with the dismiss method
                dialog.dismiss();
                recreate();

            });

            // set the negative button if the user is not interested to select or change already selected item
            builder.setNegativeButton("Cancel", (dialog, which) -> {

            });

            // create and build the AlertDialog instance with the AlertDialog builder instance
            customAlertDialog = builder.create();

            // show the alert dialog when the button is clicked
            customAlertDialog.show();
        }
        if (id == R.id.action_share) {
            // do something here
            // single item array instance to store which element is selected by user initially
            // it should be set to zero meaning none of the element is selected by default
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Love Calculator");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName() +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void changelang() {
        selected_index = sharedPreferences.getInt("index", 0);
        switch (selected_index){
            case 0:

                names=getString(R.string.names_en).split(",");
                this.setTitle(getString(R.string.app_name));
                break;
            case 1:

                names=getString(R.string.names_arabic).split(",");
                this.setTitle(getString(R.string.app_name_arabic));
                break;
            case 2:

                names=getString(R.string.names_ind).split(",");
                this.setTitle(getString(R.string.app_name_ind));
                break;
            case 7:

                names=getString(R.string.names_sp).split(",");
                this.setTitle(getString(R.string.app_name_sp));
                break;
            case 4:

                names=getString(R.string.names_hi).split(",");
                this.setTitle(getString(R.string.app_name_hi));
                break;
            case 5:
                names=getString(R.string.names_fr).split(",");
                this.setTitle(getString(R.string.app_name_fr));
                break;
            case 6:
                names=getString(R.string.names_it).split(",");
                this.setTitle(getString(R.string.app_name_it));
                break;
            case 3:

                names=getString(R.string.names_bn).split(",");
                this.setTitle(getString(R.string.app_name_bn));
                break;
            case 8:
                names=getString(R.string.names_de).split(",");
                this.setTitle(getString(R.string.app_name_de));
                break;
            case 9:
                names=getString(R.string.names_pr).split(",");
                this.setTitle(getString(R.string.app_name_pr));
                break;
            case 10:
                names=getString(R.string.names_ru).split(",");
                this.setTitle(getString(R.string.app_name_ru));
                break;

            default:

                names=getString(R.string.names_en).split(",");
                this.setTitle(getString(R.string.app_name));
                break;
        }

    }



    public void start_match(final View view){



    }

//    public void loadForm() {
//        // Loads a consent form. Must be called on the main thread.
//        UserMessagingPlatform.loadConsentForm(
//                this,
//                new UserMessagingPlatform.OnConsentFormLoadSuccessListener() {
//                    @Override
//                    public void onConsentFormLoadSuccess(ConsentForm consentForm) {
//                        MainActivity.this.consentForm = consentForm;
//                        if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
//                            consentForm.show(
//                                    MainActivity.this,
//                                    new ConsentForm.OnConsentFormDismissedListener() {
//                                        @Override
//                                        public void onConsentFormDismissed(@Nullable FormError formError) {
//                                            if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.OBTAINED) {
//                                                // App can start requesting ads.
//                                            }
//
//                                            // Handle dismissal by reloading form.
//                                            loadForm();
//                                        }
//                                    });
//                        }
//                    }
//                },
//                new UserMessagingPlatform.OnConsentFormLoadFailureListener() {
//                    @Override
//                    public void onConsentFormLoadFailure(FormError formError) {
//                        // Handle the error.
//                    }
//                }
//        );
//    }

}
