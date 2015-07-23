package androidcourse.android.jonaswu.yahoo.com.todolists;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;


public class MainActivity extends FragmentActivity implements EditNameDialog.EditNameDialogListener {

    private CustomizeAdapter customizeAdapter;
    private ListView listView;
    private Button button;
    private EditText edit;
    private todolistDao todoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDb();
        initView();
    }

    private void initDb() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "greendao", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        todoDao = daoSession.getTodolistDao();

    }

    private void initView() {

        customizeAdapter = new CustomizeAdapter(this);
        listView = (ListView) findViewById(R.id.mainListView);
        listView.setAdapter(customizeAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoItem item = customizeAdapter.getItem(position);
                showEditDialog(position, item.name);
                return true;
            }
        });

        // initial item list
        QueryBuilder qb = todoDao.queryBuilder();
        List result = qb.list();
        for (int i = 0; i < result.size(); i++) {
            todolist item = (todolist) result.get(i);
            customizeAdapter.addItem(new ToDoItem(Integer.valueOf(item.getId().toString()), item.getName()));
        }

        // initial panel
        button = (Button) findViewById(R.id.addButton);
        edit = (EditText) findViewById(R.id.addEdit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit.getText().toString();
                if (content.length() > 0) {
                    // insert to sql and get id first
                    todolist newItem = new todolist();
                    newItem.setName(content);
                    todoDao.insertInTx(newItem);
                    Long id = newItem.getId();
                    // then add item
                    customizeAdapter.addItem(new ToDoItem(Integer.valueOf(id.toString()), content));
                    edit.setText(""); // better user experience
                }
            }
        });

    }

    private void showEditDialog(int id, String name) {
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialog editNameDialog = EditNameDialog.newInstance(id, name);
        editNameDialog.show(fm, "fragment_edit_name");
    }

    @Override
    public void onFinishEditDialog(int position, String inputText) {
        // get item id from position
        ToDoItem item = customizeAdapter.getItem(position);
        item.name = inputText;
        // update item
        customizeAdapter.setItem(position, item);

        // update sql
        int id = item.id;
        Query query = todoDao.queryBuilder().where(
                todolistDao.Properties.Id.eq(id))
                .build();
        todolist result = (todolist) query.list().get(0);
        result.setName(inputText);

    }

}
