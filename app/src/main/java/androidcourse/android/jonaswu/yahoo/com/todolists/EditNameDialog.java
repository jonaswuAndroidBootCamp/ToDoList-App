package androidcourse.android.jonaswu.yahoo.com.todolists;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import de.greenrobot.event.EventBus;

public class EditNameDialog extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText mEditText;
    private MainActivity.UpdateItem updateItem;

    public EditNameDialog() {
        // Empty constructor required for DialogFragment
    }

    public MainActivity.UpdateItem getUpdateItem() {
        return updateItem;
    }

    public void setUpdateItem(MainActivity.UpdateItem updateItem) {
        this.updateItem = updateItem;
    }

    public static EditNameDialog newInstance(MainActivity.UpdateItem updateItem) {
        EditNameDialog frag = new EditNameDialog();
        frag.setUpdateItem(updateItem);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_name, container);
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        mEditText.setText(updateItem.name);
        getDialog().setTitle("Edit name");
        // Show soft keyboard automatically
        mEditText.requestFocus();
        mEditText.setOnEditorActionListener(this);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            updateItem.name = mEditText.getText().toString();
            EventBus.getDefault().post(updateItem);
            dismiss();
            return true;
        }
        return false;
    }

}
