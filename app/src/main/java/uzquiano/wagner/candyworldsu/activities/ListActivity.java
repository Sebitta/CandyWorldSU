package uzquiano.wagner.candyworldsu.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uzquiano.wagner.candyworldsu.R;
import uzquiano.wagner.candyworldsu.adapter.MyAdapter;

public class ListActivity extends AppCompatActivity {
    private MyAdapter myAdapter;
    private int contador = 0;
    private int contar = 3;
    private ListView listView;
    private List<String> nombres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        nombres = new ArrayList<String>();
        nombres.add("diego");
        nombres.add("juan");
        nombres.add("jorge");
        nombres.add("matias");
        //forma visual que mostraremos los datos
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombres);
        //Enlazamos el adaptador con el listview
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListActivity.this, "clic en "+nombres.get(i), Toast.LENGTH_LONG).show();
            }
        });

        //Enlazamos con nuestro adaptador personalizado
        myAdapter  = new MyAdapter(this, R.layout.list_item, nombres);
        listView.setAdapter(myAdapter);
        registerForContextMenu(listView);



    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.add_item:
                //agregamos nuevo nombre

                if (contador < 0){
                    contador=0;
                }

                this.nombres.add("agregar nombres "+(++contador));
                contar++;
                //Notificamos al adaptador del cambio producido
                this.myAdapter.notifyDataSetChanged();
                return true;
            case R.id.delete_item:
                if (contar >= 0) {
                    this.nombres.remove(contar--);
                    contador--;

                }
                this.myAdapter.notifyDataSetChanged();
                return true;
            /*case R.id.List:
                Intent intent = new Intent(ListActivity.this, ListActivity.class);
                //intent.putExtra("sl2", Saludo);
                startActivity(intent);
                return true;*/
            case R.id.Grid:
                Intent intentt = new Intent(ListActivity.this, GridActivity.class);
                //intent.putExtra("sl2", Saludo);
                startActivity(intentt);
                return true;


            default:
                return super.onOptionsItemSelected(item);


        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(this.nombres.get(info.position));
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean  onContextItemSelected (MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete_item:

                this.nombres.remove(info.position);

                this.myAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        MenuItem itemToHide = menu.findItem(R.id.List);
        itemToHide.setVisible(false);
        return true;
    }
}
