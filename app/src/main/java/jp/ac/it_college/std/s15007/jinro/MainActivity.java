package jp.ac.it_college.std.s15007.jinro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button findVillage = (Button) findViewById(R.id.btn_find_village);
        Button makeVillage = (Button) findViewById(R.id.btn_make_village);
        Button goToVillage = (Button) findViewById(R.id.btn_go_into_village);

        findVillage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), FindVillage.class);
                startActivity(intent);
            }
        });


        makeVillage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MakeVillage.class);
                startActivity(intent);
            }
        });

        goToVillage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), GoIntoVillage.class);
                startActivity(intent);
            }
        });
    }
}
