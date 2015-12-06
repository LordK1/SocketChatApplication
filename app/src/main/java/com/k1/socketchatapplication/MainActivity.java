package com.k1.socketchatapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.k1.socketchatapplication.model.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

//    public static final String BASE_URL = "http://192.168.1.4:3000/";
    public static final String BASE_URL = "https://socketer.herokuapp.com/";

    private Socket mSocket;
    //    private Button button;
    private EditText editText;
    private Gson gson = new Gson();
    private TextView textView;
    private EditText userEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText = (EditText) findViewById(R.id.main_edit_text);
        userEditText = (EditText) findViewById(R.id.main_edit_user);
        textView = (TextView) findViewById(R.id.main_text_view);

//        button = (Button) findViewById(R.id.main_button_text);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(userEditText.getText().toString(), editText.getText().toString());
            }
        });


        try {
            mSocket = IO.socket(BASE_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mSocket.on("chat-update-messages", onUpdateMessagesListener);
        mSocket.connect();
        Log.i("LOG", String.valueOf(mSocket.connected()));

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    Log.i("LOG", String.valueOf(mSocket.connected()));
                    sendMessage(userEditText.getText().toString(), v.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    private Emitter.Listener onUpdateMessagesListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("LOG", "\n\n onUpdateMessagesListener : " + args[0]);
                    Message message = null;
                    try {
                        message = new Message((JSONObject) args[0]);
                        Toast.makeText(MainActivity.this, message.toString(), Toast.LENGTH_LONG).show();
                        textView.append(message.toString() + "\n");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private void sendMessage(String msg, String user) {
        try {
            JSONObject object = new JSONObject();
            object.put("username", user.isEmpty() ? "AndroidClientSide" : user);
            object.put("message", msg);
            mSocket.emit("chat-send-message", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
