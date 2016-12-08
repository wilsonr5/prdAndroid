package dicky.todolistjadi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dicky.todolistjadi.R;
import dicky.todolistjadi.StorageToDo;


public class tambah extends AppCompatActivity {


    private StorageToDo storage = new StorageToDo();
    private EditText editText;
    private Button buttonn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);


        editText = (EditText) findViewById(R.id.editText);
        buttonn = (Button) findViewById(R.id.buttonn);

        buttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
            }
        });
    }

    private void kirim() {
        if (editText.getText().toString().isEmpty() || editText.getText().toString().equals("")) {
            Toast.makeText(this, "Tuliskan todo..", Toast.LENGTH_LONG).show();
        } else {
            String[] oldTodoList = storage.loadTodo(this);
            String[] newTodoList = new String[oldTodoList.length + 1];

            if (oldTodoList.length > 0) {
                for (int i = 0; i < oldTodoList.length; i++) {
                    newTodoList[i] = oldTodoList[i];
                }
            }
            newTodoList[oldTodoList.length] = editText.getText().toString();

            storage.saveTodo(newTodoList, this);

            toMainActivity();
        }
    }

    private void toMainActivity() {
        Intent intent = new Intent(this, dicky.todolistjadi.MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
