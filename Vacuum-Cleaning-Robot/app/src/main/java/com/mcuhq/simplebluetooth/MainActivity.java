package com.mcuhq.simplebluetooth;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier

    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    public final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status

    // GUI Components
    private TextView mBluetoothStatus;
    private TextView mReadBuffer;
    private Button mListPairedDevicesBtn;
    private ToggleButton btn_vacuum;
    private ToggleButton btn_clean;
    private ToggleButton btn_vacuum_auto;
    private ToggleButton btn_clean_auto;
    private ToggleButton btn_auto;
    private ToggleButton btn_mix;
    private ToggleButton btn_toggle;
    private ListView mDevicesListView;
    private ImageView btn_forward;
    private ImageView btn_back;
    private ImageView btn_left;
    private ImageView btn_right;
    private BluetoothAdapter mBTAdapter;
    private Set<BluetoothDevice> mPairedDevices;
    private ArrayAdapter<String> mBTArrayAdapter;

    private Handler mHandler;
    private ConnectedThread mConnectedThread; // bluetooth background worker thread to send and receive data
    private BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path

    private static final String[] PERMISSIONS_BLUETOOTH = {
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_ADMIN
    };

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_toggle = findViewById(R.id.btn_toggle);
        btn_forward = findViewById(R.id.up);
        btn_back = findViewById(R.id.down);
        btn_left = findViewById(R.id.left);
        btn_right = findViewById(R.id.right);
        btn_vacuum = findViewById(R.id.vakum);
        btn_clean = findViewById(R.id.pel);
        btn_mix = findViewById(R.id.mix);
        btn_vacuum_auto = findViewById(R.id.vakum_auto);
        btn_clean_auto = findViewById(R.id.pel_auto);
        btn_auto = findViewById(R.id.automatic);
        mListPairedDevicesBtn = (Button) findViewById(R.id.paired_btn);

        mBTArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio

        mDevicesListView = (ListView) findViewById(R.id.devices_list_view);
        mDevicesListView.setAdapter(mBTArrayAdapter); // assign model to view
        mDevicesListView.setOnItemClickListener(mDeviceClickListener);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_READ) {
                    String readMessage = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        readMessage = new String((byte[]) msg.obj, StandardCharsets.UTF_8);
                    }
                }

                if (msg.what == CONNECTING_STATUS) {
                    char[] sConnected;
                    String message = "S";
                    if (msg.arg1 == 1) {
                        Toast.makeText(getApplicationContext(), "Connected to " + msg.obj, Toast.LENGTH_SHORT).show();
                        comand(message);
                    } else {
                        Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };


        btn_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    bluetoothOn();
                    compoundButton.setBackgroundColor(Color.BLUE);
                } else {
                    bluetoothOff();
                    compoundButton.setBackgroundColor(Color.RED);
                }
            }
        });

        btn_clean.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    compoundButton.setBackgroundColor(Color.BLUE);
                    String message = "C";
                    comand(message);
                } else {
                    compoundButton.setBackgroundColor(Color.RED);
                    String message = "c";
                    comand(message);
                }
            }
        });

        btn_vacuum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    compoundButton.setBackgroundColor(Color.BLUE);
                    String message = "V";
                    comand(message);
                } else {
                    compoundButton.setBackgroundColor(Color.RED);
                    String message = "v";
                    comand(message);
                }
            }
        });

        btn_mix.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    compoundButton.setBackgroundColor(Color.BLUE);
                    String message = "X";
                    comand(message);
                } else {
                    compoundButton.setBackgroundColor(Color.RED);
                    String message = "x";
                    comand(message);
                }
            }
        });

        btn_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    compoundButton.setBackgroundColor(Color.BLUE);
                    String message = "A";
                    btn_clean.setVisibility(View.GONE);
                    btn_vacuum.setVisibility(View.GONE);
                    btn_clean_auto.setVisibility(View.VISIBLE);
                    btn_vacuum_auto.setVisibility(View.VISIBLE);
                    comand(message);
                } else {
                    compoundButton.setBackgroundColor(Color.RED);
                    String message = "a";
                    btn_clean.setVisibility(View.VISIBLE);
                    btn_vacuum.setVisibility(View.VISIBLE);
                    btn_clean_auto.setVisibility(View.GONE);
                    btn_vacuum_auto.setVisibility(View.GONE);
                    comand(message);
                }
            }
        });

        btn_clean_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    compoundButton.setBackgroundColor(Color.BLUE);
                    String message = "CA";
                    comand(message);
                } else {
                    compoundButton.setBackgroundColor(Color.RED);
                    String message = "cA";
                    comand(message);
                }
            }
        });

        btn_vacuum_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    compoundButton.setBackgroundColor(Color.BLUE);
                    String message = "VA";
                    comand(message);
                } else {
                    compoundButton.setBackgroundColor(Color.RED);
                    String message = "vA";
                    comand(message);
                }
            }
        });

        mListPairedDevicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPairedDevices();

            }
        });


        btn_forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.postDelayed(mForward, 10);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mForward);
                        String message = "S";
                        comand(message);
                        break;
                }
                return true;
            }

            Runnable mForward = new Runnable() {
                @Override
                public void run() {
                    String message = "F";
                    comand(message);
                    mHandler.postDelayed(this, 100);
                }
            };
        });

        btn_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.postDelayed(mBack, 10);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mBack);
                        String message = "S";
                        comand(message);
                        break;
                }
                return true;
            }

            Runnable mBack = new Runnable() {
                @Override
                public void run() {
                    String message = "B";
                    comand(message);
                    mHandler.postDelayed(this, 100);
                }
            };
        });

        btn_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.postDelayed(mLeft, 10);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mLeft);
                        String message = "S";
                        comand(message);
                        break;
                }
                return true;
            }

            Runnable mLeft = new Runnable() {
                @Override
                public void run() {
                    String message = "L";
                    comand(message);
                    mHandler.postDelayed(this, 100);
                }
            };
        });

        btn_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.postDelayed(mRight, 10);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mRight);
                        String message = "S";
                        comand(message);
                        break;
                }
                return true;
            }

            Runnable mRight = new Runnable() {
                @Override
                public void run() {
                    String message = "R";
                    comand(message);
                    mHandler.postDelayed(this, 100);
                }

            };
        });
    }

    private void comand(String Message) {
        if (mBTSocket != null) {
            try {
                // Create the command that will be sent to arduino.
                // String must be converted in its bytes to be sent on serial
                // communication
                mBTSocket.getOutputStream().write(Message.getBytes());
            } catch (IOException e) {

            }
        }
    }

    private void bluetoothOn() {
        checkPermission();
        if (!mBTAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            Toast.makeText(getApplicationContext(), getString(R.string.sBTturON), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.BTisON), Toast.LENGTH_SHORT).show();
        }
    }

    // Enter here after user selects "yes" or "no" to enabling radio


    private void bluetoothOff() {
        checkPermission();
        mBTAdapter.disable(); // turn off
        Toast.makeText(getApplicationContext(), "Bluetooth turned Off", Toast.LENGTH_SHORT).show();
    }

    final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkPermission();
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // add the name to the list
                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                mBTArrayAdapter.notifyDataSetChanged();
            }
        }
    };

    private void listPairedDevices() {
        if (mBTAdapter.isEnabled()) {
            checkPermission();
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
            builderSingle.setIcon(R.drawable.ic_android_black_24dp);
            builderSingle.setTitle("Select One Name:-");
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
            mBTArrayAdapter.clear();
            mPairedDevices = mBTAdapter.getBondedDevices();
            for (BluetoothDevice device : mPairedDevices)
                arrayAdapter.add(device.getName() + "\n" + device.getAddress());

            builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });


            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!mBTAdapter.isEnabled()) {
                        Toast.makeText(getBaseContext(), getString(R.string.BTnotOn), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String strName = arrayAdapter.getItem(which);
                    final String address = strName.substring(strName.length() - 17);
                    final String name = strName.substring(0,strName.length() - 17);
                    new Thread(){
                        @Override
                        public void run() {
                            boolean fail = false;
                            BluetoothDevice device = mBTAdapter.getRemoteDevice(address);
                            try {
                                mBTSocket = createBluetoothSocket(device);
                            } catch (IOException e) {
                                fail = true;
                                Toast.makeText(getBaseContext(), getString(R.string.ErrSockCrea), Toast.LENGTH_SHORT).show();
                            }
                            try {
                                checkPermission();
                                mBTSocket.connect();
                            } catch (IOException e) {
                                try {
                                    fail = true;
                                    mBTSocket.close();
                                    mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                            .sendToTarget();
                                } catch (IOException e2) {
                                    //insert code to deal with this
                                    Toast.makeText(getBaseContext(), getString(R.string.ErrSockCrea), Toast.LENGTH_SHORT).show();
                                }
                            }
                            if(!fail) {
                                mConnectedThread = new ConnectedThread(mBTSocket, mHandler);
                                mConnectedThread.start();

                                mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name)
                                        .sendToTarget();

                            }
                        }
                    }.start();
                }

            });
            builderSingle.show();
        }else{
            Toast.makeText(getBaseContext(), getString(R.string.BTnotOn), Toast.LENGTH_SHORT).show();

        }
    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if(!mBTAdapter.isEnabled()) {
                Toast.makeText(getBaseContext(), getString(R.string.BTnotOn), Toast.LENGTH_SHORT).show();
                return;
            }

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) view).getText().toString();
            final String address = info.substring(info.length() - 17);
            final String name = info.substring(0,info.length() - 17);

            // Spawn a new thread to avoid blocking the GUI one
            new Thread()
            {
                @Override
                public void run() {
                    boolean fail = false;

                    BluetoothDevice device = mBTAdapter.getRemoteDevice(address);

                    try {
                        mBTSocket = createBluetoothSocket(device);
                    } catch (IOException e) {
                        fail = true;
                        Toast.makeText(getBaseContext(), getString(R.string.ErrSockCrea), Toast.LENGTH_SHORT).show();
                    }
                    // Establish the Bluetooth socket connection.
                    try {
                        checkPermission();
                        mBTSocket.connect();
                    } catch (IOException e) {
                        try {
                            fail = true;
                            mBTSocket.close();
                            mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                    .sendToTarget();
                        } catch (IOException e2) {
                            //insert code to deal with this
                            Toast.makeText(getBaseContext(), getString(R.string.ErrSockCrea), Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(!fail) {
                        mConnectedThread = new ConnectedThread(mBTSocket, mHandler);
                        mConnectedThread.start();

                        mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name)
                                .sendToTarget();
                    }
                }
            }.start();
        }
    };

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        checkPermission();
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BT_MODULE_UUID);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
        }
        return  device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                return;
            }
        }
    }
}