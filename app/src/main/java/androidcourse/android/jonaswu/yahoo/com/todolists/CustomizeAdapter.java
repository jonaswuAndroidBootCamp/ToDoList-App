package androidcourse.android.jonaswu.yahoo.com.todolists;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jonaswu on 2015/7/23.
 */
public class CustomizeAdapter extends BaseAdapter {

    private ArrayList<String> listItem = new ArrayList<String>();
    private Context context;

    public CustomizeAdapter(Context context) {
        super();
        this.context = context;
    }

    public CustomizeAdapter(Context context, ArrayList<String> listItem) {
        this(context);
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public String getItem(int position) {
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
        String p = this.getItem(position);
        EditText edit = (EditText) v.findViewById(R.id.edit);
        edit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EditText me = ((EditText) v);
                me.setClickable(true);
                me.setCursorVisible(true);
                me.setFocusable(true);
                me.setFocusableInTouchMode(true);
                me.setBackgroundResource(R.drawable.focus_item);
                return true;
            }
        });
        edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    EditText me = ((EditText) v);
                    me.setClickable(false);
                    me.setCursorVisible(false);
                    me.setFocusable(false);
                    me.setFocusableInTouchMode(false);
                    me.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });
        edit.setText(p);
        return v;
    }
}
