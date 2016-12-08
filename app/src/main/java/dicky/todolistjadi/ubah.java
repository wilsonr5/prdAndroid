package dicky.todolistjadi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dicky.todolistjadi.R;
import dicky.todolistjadi.StorageToDo;

public class ubah extends AppCompatActivity {
    private String todoItem;
    private int position;
    private EditText editText;
    private Button ubahlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            position = intent.getIntExtra("position", 0);
            todoItem = intent.getStringExtra("todoItem");
        }


        editText = (EditText) findViewById(R.id.editText);
        editText.setText(todoItem);
        ubahlist = (Button) findViewById(R.id.ubahlist);
        ubahlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirim();
            }
        });
    }

    private void kirim() {
        StorageToDo storage = new StorageToDo();


        String[] oldList = storage.loadTodo(this);


        String[] newList = new String[oldList.length];

        for (int i = 0; i < oldList.length; i++) {
            if (i == position) {
                newList[i] = editText.getText().toString();
            } else {
                newList[i] = oldList[i];
            }
        }


        storage.saveTodo(newList, this);


        backMainActivity();
    }

    private void backMainActivity() {
        Intent intent = new Intent(this, dicky.todolistjadi.MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}