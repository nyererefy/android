package com.konektedi.vs.other;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.konektedi.vs.R;

public class SupportActivityMain extends AppCompatActivity {

    Button emailUsBtn, whatsAppUsBtn, rateAppBtn;
    ImageButton kInstagramBtn, sInstagramBtn, sBlogBtn, kFacebookBtn, sFacebookBtn, kWebBtn,kTwitterBtn,sTwitterBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        emailUsBtn = findViewById(R.id.emailUsBtn);
        whatsAppUsBtn = findViewById(R.id.whatsAppUsBtn);
        rateAppBtn = findViewById(R.id.rateAppBtn);
        sBlogBtn = findViewById(R.id.sBlogBtn);
        kInstagramBtn = findViewById(R.id.kInstagramBtn);
        sInstagramBtn = findViewById(R.id.sInstagramBtn);
        kFacebookBtn = findViewById(R.id.kFacebookBtn);
        sFacebookBtn = findViewById(R.id.sFacebookBtn);
        kWebBtn = findViewById(R.id.kWebBtn);
        kTwitterBtn = findViewById(R.id.kTwitterBtn);
        sTwitterBtn = findViewById(R.id.sTwitterBtn);

        whatsAppUsBtn.setOnClickListener(this::openWhatsApp);
        emailUsBtn.setOnClickListener(view -> openGmail());
        rateAppBtn.setOnClickListener(view -> rateThisApp());
        kInstagramBtn.setOnClickListener(view -> openInstagram("konektedi"));
        sInstagramBtn.setOnClickListener(view -> openInstagram("sylvanuskateile"));
        sBlogBtn.setOnClickListener(view -> openBrowser("https://kateile.blogspot.com"));
        kWebBtn.setOnClickListener(view -> openBrowser("https://konektedi.com"));
        kFacebookBtn.setOnClickListener(view -> openFacebookPage("konektedi", "1967247010153586"));
        sFacebookBtn.setOnClickListener(view -> openFacebookProfile("sylvanuskateile", "100008373461967"));
        sTwitterBtn.setOnClickListener(view -> openTwitter("sylvanuskateile"));
        kTwitterBtn.setOnClickListener(view -> openTwitter("konektedi"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }

    public void openFacebookPage(final String username, final String id) {
        try {
            this.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + id)));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + username)));
        }
    }

    public void openFacebookProfile(final String username, final String id) {
        try {
            this.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + id)));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + username)));
        }
    }

    private void openBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void openWhatsApp(View view) {
        String path = null;
        if (isAppInstalled("com.whatsapp", "WhatsApp")) {
            path = "com.whatsapp";
        }

        //TODO change number
        String smsNumber = "255753535059"; //without '+'
        try {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Android Feedback.\n");
            sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net");
            sendIntent.setPackage(path);
            startActivity(sendIntent);
        } catch (Exception e) {
            Snackbar.make(view, "Error\n" + e.toString(), Snackbar.LENGTH_LONG).show();
        }
    }

    private void openGmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] recipients = {"konektedi@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Android feedback.");
        intent.putExtra(Intent.EXTRA_TEXT, "");
//        intent.putExtra(Intent.EXTRA_CC, "mailcc@gmail.com");
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }

    private void rateThisApp() {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }

    private void openInstagram(String username) {
        PackageManager pm = getPackageManager();
        Intent intent = newInstagramProfileIntent(pm, username);
        startActivity(intent);
    }

    private void openTwitter(String twitter_user_name){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twitter_user_name)));
        }catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + twitter_user_name)));
        }
    }
    public static Intent newInstagramProfileIntent(PackageManager pm, String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length() - 1);
                }
                final String username = url.substring(url.lastIndexOf("/") + 1);
                // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
                intent.setData(Uri.parse("http://instagram.com/_u/" + username));
                intent.setPackage("com.instagram.android");
                return intent;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        intent.setData(Uri.parse(url));
        return intent;
    }

    public boolean isAppInstalled(String package_name, String app_name) {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo info = pm.getPackageInfo("" + package_name, PackageManager.GET_META_DATA);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Your device has not installed " + app_name, Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
    }


}
