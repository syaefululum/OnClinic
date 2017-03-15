package com.example.posmedicine.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.posmedicine.R;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.pixplicity.easyprefs.library.Prefs;

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected LinearLayout fullLayout;
    protected FrameLayout actContent;

    @Override
    public void setContentView(final int layoutResID) {
        fullLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.base_layout, null);
        actContent = (FrameLayout) fullLayout.findViewById(R.id.act_content);
        getLayoutInflater().inflate(layoutResID, actContent, true);
        super.setContentView(fullLayout);

        Iconify.with(new FontAwesomeModule());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setNavigationViewIcons(navigationView.getMenu());
        setNavigationViewMenu(navigationView);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_medicine) {
            startActivity(new Intent(this, MedicineActivity.class));
        } else if (id == R.id.nav_unit) {
            startActivity(new Intent(this, UnitActivity.class));
        } else if (id == R.id.nav_appointment) {
            startActivity(new Intent(this, AppointmentActivity.class));
        } else if (id == R.id.nav_pharmacy_chasier) {
            startActivity(new Intent(this, PharmacyChasierActivity.class));
        } else if (id == R.id.nav_cashier_transaction_data) {
            startActivity(new Intent(this, CashierTransactionActivity.class));
        } else if (id == R.id.nav_complaint) {
            startActivity(new Intent(this, ComplaintHeaderActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationViewIcons(Menu menu) {
        menu.findItem(R.id.nav_complaint).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_heartbeat)
                        .actionBarSize());
        menu.findItem(R.id.nav_appointment_doctor).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_calendar)
                        .actionBarSize());
        menu.findItem(R.id.nav_appointment).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_calendar)
                        .actionBarSize());
        menu.findItem(R.id.nav_medicine).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_medkit)
                        .actionBarSize());
        menu.findItem(R.id.nav_pharmacy_chasier).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_money)
                        .actionBarSize());
        menu.findItem(R.id.nav_cashier_transaction_data).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_money)
                        .actionBarSize());
        menu.findItem(R.id.nav_unit).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_heart)
                        .actionBarSize());
        menu.findItem(R.id.logout).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_sign_out)
                        .actionBarSize());
    }

    private void setNavigationViewMenu(NavigationView navigationView) {
        ImageView iv = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageViewProfilePict);
        Menu menu = navigationView.getMenu();
        TextView username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userLoginName);
        TextView userrole = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userLoginRole);
        String role = Prefs.getString("USERROLE", "Not Set");
        username.setText(Prefs.getString("USERNAME", "Not Set"));
        userrole.setText(Prefs.getString("USERROLE", "Not Set"));
        
        if (role.equals("Doctor")) {
            iv.setImageResource(R.drawable.doctor);
            menu.findItem(R.id.nav_group_patient).setVisible(false);
            menu.findItem(R.id.nav_group_nurse).setVisible(false);
        } else if (role.equals("Patient")) {
            iv.setImageResource(R.drawable.user);
            menu.findItem(R.id.nav_group_doctor).setVisible(false);
            menu.findItem(R.id.nav_group_nurse).setVisible(false);
        } else {
            iv.setImageResource(R.drawable.nurse);
            menu.findItem(R.id.nav_group_doctor).setVisible(false);
            menu.findItem(R.id.nav_group_patient).setVisible(false);
        }
    }
}
