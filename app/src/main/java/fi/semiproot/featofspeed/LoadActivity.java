package fi.semiproot.featofspeed;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class LoadActivity extends AppCompatActivity {

    private boolean isHost;
    private String code;
    private Thread t;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        Log.d("FOS", "LoadActivity onCreate");

        // Get extras:
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            isHost = bundle.getBoolean("host", false);
            code = bundle.getString("code", "0000");
        }

        handler = new Handler();
    }

    @Override
    protected void onStart() {
        super.onStart();

        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t.sleep(1000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(LoadActivity.this, LobbyActivity.class);
                            intent.putExtra("host", isHost);
                            intent.putExtra("code", code);
                            startActivity(intent);
                            LoadActivity.this.finish();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}
