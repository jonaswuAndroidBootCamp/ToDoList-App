package androidcourse.android.jonaswu.yahoo.com.todolists;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jonaswu on 2015/7/23.
 */
public class CustomizeAdapter extends BaseAdapter {

    private ArrayList<ToDoItem> listItem = new ArrayList<ToDoItem>();
    private Context context;

    public CustomizeAdapter(Context context) {
        super();
        this.context = context;
    }

    public void setItem(int id, ToDoItem value) {
        listItem.set(id, value);
        this.notifyDataSetChanged();
    }

    public void addItem(ToDoItem value) {
        this.addItemWithoutNotifyChange(value);
        this.notifyDataSetChanged();
    }

    public void addItemWithoutNotifyChange(ToDoItem value) {
        listItem.add(value);
    }


    public void deleteItem(int id) {
        listItem.remove(id);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public ToDoItem getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(R.layout.item_view, null);
        }
        ToDoItem p = this.getItem(position);
        TextView name = (TextView) v.findViewById(R.id.name);
        name.setText(p.name);
        return v;
    }
}
