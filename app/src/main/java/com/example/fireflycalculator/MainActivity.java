package com.example.fireflycalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView main_display, menu_history;

    List<String> history = new ArrayList<String>();

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = (DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                return false;
            }
        });

        main_display = (TextView) findViewById(R.id.display);

        menu_history = (TextView) findViewById(R.id.menu_history);

        ImageButton btn0 = (ImageButton) findViewById(R.id.btn0);
        ImageButton btn1 = (ImageButton) findViewById(R.id.btn1);
        ImageButton btn2 = (ImageButton) findViewById(R.id.btn2);
        ImageButton btn3 = (ImageButton) findViewById(R.id.btn3);
        ImageButton btn4 = (ImageButton) findViewById(R.id.btn4);
        ImageButton btn5 = (ImageButton) findViewById(R.id.btn5);
        ImageButton btn6 = (ImageButton) findViewById(R.id.btn6);
        ImageButton btn7 = (ImageButton) findViewById(R.id.btn7);
        ImageButton btn8 = (ImageButton) findViewById(R.id.btn8);
        ImageButton btn9 = (ImageButton) findViewById(R.id.btn9);

        ImageButton btnAC = (ImageButton) findViewById(R.id.btnAC);
        ImageButton btnDec = (ImageButton) findViewById(R.id.btnDec);
        ImageButton btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        ImageButton btnMin = (ImageButton) findViewById(R.id.btnMin);
        ImageButton btnMul = (ImageButton) findViewById(R.id.btnMul);
        ImageButton btnDiv = (ImageButton) findViewById(R.id.btnDiv);
        ImageButton btnEq = (ImageButton) findViewById(R.id.btnEq);

        btnAC.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                main_display.setText("");
            }
        });

        btnDec.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddDecimal();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                operator_insert("+");
            }
        });

        btnMin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                operator_insert("-");
            }
        });

        btnMul.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                operator_insert("*");
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                operator_insert("/");
            }
        });

        btnEq.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                calculate();
            }
        });


        btn0.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                main_display.setText(main_display.getText() + "0");
            }
        });

        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                main_display.setText(main_display.getText() + "1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                main_display.setText(main_display.getText() + "2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                main_display.setText(main_display.getText() + "3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                main_display.setText(main_display.getText() + "4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                main_display.setText(main_display.getText() + "5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                main_display.setText(main_display.getText() + "6");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                main_display.setText(main_display.getText() + "7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                main_display.setText(main_display.getText() + "8");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                main_display.setText(main_display.getText() + "9");
            }
        });
    }


    //calculates whatever is on the display
    private void calculate()
    {
        BigDecimal num1, num2;
        int op_index;
        char op;
        String cur = main_display.getText().toString();

        if(cur.isEmpty()) return; //return if null

        //regex matches num1 and num2
        Pattern p = Pattern.compile("(((?:^\\-?\\d*\\.?\\d*)|(?:(?<=[-+\\/*])(?:\\-?\\d*\\.?\\d*))))");
        Matcher m = p.matcher(cur);

        //get num1, num2, and operator
        if(m.find())
        {
            num1 = new BigDecimal(cur.substring(m.start(0),m.end(0)));
            op_index = m.end();

            if(m.find())
            {
                //return if number 2 doesn't exist
                if(cur.length() == (op_index + 1)) return;

                num2 = new BigDecimal(cur.substring(m.start(0),m.end(0)));
                op = cur.charAt(op_index);
            }
            else return;
        }
        else return;

        switch(op) {
            case '+':
                main_display.setText((num1.add(num2)).stripTrailingZeros().toPlainString());
                break;
            case '-':
                main_display.setText((num1.subtract(num2)).stripTrailingZeros().toPlainString());
                break;
            case '*':
                main_display.setText((num1.multiply(num2)).stripTrailingZeros().toPlainString());
                break;
            case '/':
                if(num2.compareTo(BigDecimal.ZERO) == 0) return; //don't divide by 0
                main_display.setText((num1.divide(num2, 9, RoundingMode.HALF_UP)).stripTrailingZeros().toPlainString()); //round after 9 digits
                break;
            default:
                return;
        }

        //update history
        history.add(num1.toPlainString() + op + num2.toPlainString() + "=" + main_display.getText());
        String temp = "";
        for(int i = history.size() - 1; i >= 0; i--)
        {
            temp = temp + history.get(i) + "\n\n";
        }
        menu_history.setText(temp);
    }

    private void operator_insert(String operator)
    {
        String s = main_display.getText().toString();

        if(s.isEmpty()) return;

        //regex finds operator
        Pattern p = Pattern.compile("(?!^\\-?\\d*\\.?\\d+)([-+\\/*])(?!^\\-?\\d*\\.?\\d+)");
        Matcher m = p.matcher(s);

        if(m.find())
        {
            //if operator already exists, replace operator
            main_display.setText(s.substring(0,m.start()) + operator + s.substring(m.end()));
        }
        else
        {
            //add operator
            main_display.setText(s + operator);
        }
    }

    private void AddDecimal()
    {
        String s = main_display.getText().toString();

        //regex finds operator
        Pattern p = Pattern.compile("(?!^\\-?\\d*\\.?\\d+)([-+\\/*])(?!^\\-?\\d*\\.?\\d+)");
        Matcher m = p.matcher(s);

        if(m.find()) //check for operator
        {
            //check second number for decimal
            if(!s.substring(m.end()).contains("."))
            {
                //if number is null, add 0 in front of decimal
                if(s.substring(m.end()).isEmpty())
                {
                    main_display.setText(s + "0.");
                }
                else
                {
                    main_display.setText(s + ".");
                }

            }
        }
        else
        {
            //check first number for decimal
            if(!s.contains("."))
            {
                //if number is null, add 0 in front of decimal
                if(s.isEmpty())
                {
                    main_display.setText(s + "0.");
                }
                else
                {
                    main_display.setText(s + ".");
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}