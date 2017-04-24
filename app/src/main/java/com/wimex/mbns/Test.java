package com.wimex.mbns;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Test extends AppCompatActivity {
    EditText txtA;
    TextView txtB;
    Button btnC;
    ArrayList<String> l;

    public String fixNumnerToString(int number) {
        String num = "";
        String result = "";
        int turn = 1;
        num = String.valueOf(number);
        for (int i = (num.length() - 1); i >= 0; i--) {
            if ((turn % 3 == 0) && (turn != num.length())) {
                char c = num.charAt(i);
                result = "." + c + result;
            } else {
                char c = num.charAt(i);
                result = c + result;
            }
            turn++;
        }
        return result;
    }

    public int fixStringToNumber(String number) {
        return Integer.parseInt(number.replace(".", ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test);
        super.onCreate(savedInstanceState);
        l = new ArrayList<>();
        l.add("a1");
        l.add("a2");
        l.add("a3");
        l.add("a4");
        l.add("a5");
        ArrayList<String> m = new ArrayList<String>();
        for (String z:l){
          m.add(z);
        }
        //m=l;
        m.clear();
        String a = l.get(1);
        txtA = (EditText) findViewById(R.id.txtA);
        txtB = (TextView) findViewById(R.id.txtB);
        btnC = (Button) findViewById(R.id.btnC);
        int x = 7 / 3;
        float y = 7 / 3;
       // int a = 858858;

        //txtA.setText(fixNumnerToString(999999999));

       /* btnC.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    Toast.makeText(Test.this, "ok down", Toast.LENGTH_SHORT).show();
                    return true;

                }
                switch (keyCode) {
                    case KeyEvent.ACTION_UP:
                        Toast.makeText(Test.this, "ok c up", Toast.LENGTH_SHORT).show();
                        return true;
                    case KeyEvent.ACTION_DOWN:
                        Toast.makeText(Test.this, "ok c down", Toast.LENGTH_SHORT).show();
                        return true;

                    default:
                        break;
                }
                return false;
            }
        });
        txtB.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    Toast.makeText(Test.this, "ok down", Toast.LENGTH_SHORT).show();
                    return true;
                }
                switch (keyCode) {
                    case KeyEvent.ACTION_UP:
                        Toast.makeText(Test.this, "ok b up", Toast.LENGTH_SHORT).show();
                       return true;
                    case KeyEvent.ACTION_DOWN:
                        Toast.makeText(Test.this, "ok b down", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
        txtA.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()==KeyEvent.ACTION_DOWN){
                    if (keyCode==KeyEvent.KEYCODE_1){
                        Toast.makeText(Test.this, "ok down", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(Test.this, "ok down 2", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }else if (event.getAction()==KeyEvent.ACTION_UP){
                    Toast.makeText(Test.this,"out",Toast.LENGTH_SHORT).show();
                }

                switch (keyCode) {
                    case KeyEvent.ACTION_UP:
                        Toast.makeText(Test.this, "ok up", Toast.LENGTH_SHORT).show();
                        return true;
                    case KeyEvent.ACTION_DOWN:
                        Toast.makeText(Test.this, "ok down", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });*/
    }
}
