package androidcourse.android.jonaswu.yahoo.com.todolists;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import androidcourse.android.jonaswu.yahoo.com.todolists.base.BaseFragmentActivity;
import androidcourse.android.jonaswu.yahoo.com.todolists.dao_helper.DaoMaster;
import androidcourse.android.jonaswu.yahoo.com.todolists.dao_helper.DaoSession;
import androidcourse.android.jonaswu.yahoo.com.todolists.dao_helper.todolist;
import androidcourse.android.jonaswu.yahoo.com.todolists.dao_helper.todolistDao;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;


public class MainActivity extends BaseFragmentActivity {

    private CustomizeAdapter customizeAdapter;
    private ListView listView;
    private Button button;
    private EditText edit;
    private todolistDao todoDao;
    private boolean doubleBackToExitPressedOnce;

    public class UpdateItem extends ToDoItem {

        public int position;

        public UpdateItem(int id, String name, int position) {
            super(id, name);
            this.position = position;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDb();
        // initial item list
        QueryBuilder qb = todoDao.queryBuilder();
        List result = qb.list();
        for (int i = 0; i < result.size(); i++) {
            todolist item = (todolist) result.get(i);
            customizeAdapter.addItemWithoutNotifyChange(new ToDoItem(Integer.valueOf(item.getId().toString()), item.getName()));
        }
        customizeAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
                showEditDialog(new UpdateItem(item.id, item.name, position));
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

    private void showEditDialog(UpdateItem updateItem) {
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialog editNameDialog = EditNameDialog.newInstance(updateItem);
        editNameDialog.show(fm, "fragment_edit_name");
    }

    @Subscribe
    public void onEventMainThread(UpdateItem updateItem) {

        int id = updateItem.id;
        Query query = todoDao.queryBuilder().where(
                todolistDao.Properties.Id.eq(id))
                .build();
        todolist dbItem = (todolist) query.list().get(0);
        if (updateItem.name.length() > 0) {
            // get item id from position
            // update item
            customizeAdapter.setItem(updateItem.position, updateItem);
            // update sql
            dbItem.setName(updateItem.name);
            todoDao.insertOrReplaceInTx(dbItem);
        } else {
            todoDao.delete(dbItem);
            customizeAdapter.deleteItem(updateItem.position);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
