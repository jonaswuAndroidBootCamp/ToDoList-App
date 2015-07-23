package androidcourse.android.jonaswu.yahoo.com.todolists;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayList<String> listItems;
    private CustomizeAdapter customizeAdapter;
    private ListView listView;
    private Button button;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        // initial listview
        listItems = new ArrayList<>();
        customizeAdapter = new CustomizeAdapter(this, listItems);
        listView = (ListView) findViewById(R.id.mainListView);
        listView.setAdapter(customizeAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                View v = listView.getChildAt(position);
                EditText edit = (EditText) v.findViewById(R.id.edit);
                edit.setClickable(true);
                edit.setCursorVisible(true);
                edit.setFocusable(true);
                edit.setFocusableInTouchMode(true);
                edit.setBackgroundResource(R.drawable.focus_item);
                // customizeAdapter.getItem(position);
                Toast.makeText(MainActivity.this, "HAHA", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // initial panel
        button = (Button) findViewById(R.id.addButton);
        edit = (EditText) findViewById(R.id.addEdit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit.getText().toString();
                if (content.length() > 0) {
                    listItems.add(content);
                    edit.setText(""); // better user experience
                }
                customizeAdapter.notifyDataSetChanged();
            }
        });

    }

/*    @Override
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
*/
}
