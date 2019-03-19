package it.bz.beacon.sdkandroid;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import it.bz.beacon.beaconsuedtirolsdk.NearbyBeaconManager;
import it.bz.beacon.beaconsuedtirolsdk.data.entity.Beacon;
import it.bz.beacon.beaconsuedtirolsdk.exception.MissingLocationPermissionException;
import it.bz.beacon.beaconsuedtirolsdk.exception.NoBluetoothException;
import it.bz.beacon.beaconsuedtirolsdk.listener.EddystoneListener;
import it.bz.beacon.beaconsuedtirolsdk.listener.IBeaconListener;
import it.bz.beacon.sdkandroid.adapter.BeaconAdapter;

/**
 * An activity representing a list of Beacons. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BeaconDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class BeaconListActivity extends AppCompatActivity implements IBeaconListener, EddystoneListener {

    private static final int LOCATION_PERMISSION_REQUEST = 1;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet device.
     */
    private boolean isTablet;
    private BeaconAdapter adapter;
    private NearbyBeaconManager manager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.beacon_detail_container) != null) {
            isTablet = true;
        }

        RecyclerView recyclerView = findViewById(R.id.beacon_list);
        assert recyclerView != null;
        adapter = new BeaconAdapter(this, isTablet);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        manager = new NearbyBeaconManager(this);
        manager.setIBeaconListener(this);
        manager.setEddystoneListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startScanningIfLocationPermissionGranted();
    }

    private void startScanningIfLocationPermissionGranted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        }
        else {
            tryStartScanning();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tryStartScanning();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void tryStartScanning() {
        try {
            manager.startScanning();
        }
        catch (NoBluetoothException e) {
            e.printStackTrace();
            showSnackbarWithRetry(getString(R.string.no_bluetooth));
        }
        catch (MissingLocationPermissionException e) {
            e.printStackTrace();
            showSnackbarWithRetry(getString(R.string.missing_location_permission));
        }
    }

    private void showSnackbarWithRetry(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tryStartScanning();
                    }
                })
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.stopScanning();
    }

    @Override
    public void onEddystoneDiscovered(final Beacon beacon) {
        runOnUiThread(new Runnable() {
            public void run() {
                adapter.addItem(beacon);
                setTitle();
            }
        });
    }

    @Override
    public void onEddystoneLost(Beacon beacon) {
        // TODO: remove lost beacon from list
    }

    @Override
    public void onIBeaconDiscovered(final Beacon beacon) {
        runOnUiThread(new Runnable() {
            public void run() {
                adapter.addItem(beacon);
                setTitle();
            }
        });
    }

    @Override
    public void onIBeaconLost(Beacon beacon) {
        // TODO: remove lost beacon from list
    }

    private void setTitle() {
        toolbar.setTitle(String.format(Locale.getDefault(), "%s (%d)", getString(R.string.app_name), adapter.getItemCount()));
    }
}