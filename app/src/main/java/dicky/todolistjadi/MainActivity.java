package dicky.todolistjadi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import dicky.todolistjadi.R;
import dicky.todolistjadi.StorageToDo;
import dicky.todolistjadi.tambah;
import dicky.todolistjadi.ubah;

public class MainActivity extends AppCompatActivity {
    private ListView lvItems;
    private String[] Itemstodo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)

                findViewById(R.id.lvItems);
        populateTodoList();
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                                               public boolean onItemLongClick(AdapterView<?> parent, View v,
                                                                              int Position, long id) {
                                                   openDialog(Position);
                                                   return false;

                                               }
                                           }

        );


        ImageButton btn = (ImageButton) findViewById(R.id.btnAddItem);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), tambah.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }


    private void openDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apa yang ingin Anda lakukan dengan List yang dipilih?")
                .setCancelable(true)
                .setPositiveButton("UBAH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ubahActivity(position);
                    }
                })
                .setNegativeButton("HAPUS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hapusList(position);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void populateTodoList() {
        StorageToDo storage = new StorageToDo();
        Itemstodo = storage.loadTodo(this);
        if (Itemstodo.length > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Itemstodo);
            lvItems.setAdapter(adapter);

        }
    }

    private void ubahActivity(int position) {

        String todoItem = Itemstodo[position];

        Intent intent = new Intent(this, ubah.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("position", position);
        intent.putExtra("todoItem", todoItem);
        startActivity(intent);
    }

    private void hapusList(int position) {
        String[] newTodoList = new String[Itemstodo.length - 1];

        int j = 0;
        for (int i = 0; i < newTodoList.length; i++) {
            if (i == position) {
                j++;
            }
            newTodoList[i] = Itemstodo[j++];
        }


        StorageToDo storage = new StorageToDo();
        storage.saveTodo(newTodoList, this);


        populateTodoList();
    }
}