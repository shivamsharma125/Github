package in.co.softwaresolution.github;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartingActivity extends AppCompatActivity {

    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        editText = findViewById(R.id.editTextStartingActivity);
        button = findViewById(R.id.searchButtonStartingActivity);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = editText.getText().toString();
                string = string.trim();

                if(!string.isEmpty())
                {
                    Intent intent = new Intent(StartingActivity.this,MainActivity.class);
                    intent.putExtra(MainActivity.USERNAME,string);
                    startActivity(intent);
                }

            }
        });

    }
}
