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

public class EditNameDialog extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText mEditText;

    public EditNameDialog() {
        // Empty constructor required for DialogFragment
    }

    //  interface for caller
    public interface EditNameDialogListener {
        void onFinishEditDialog(int id, String inputText);
    }

    public static EditNameDialog newInstance(int id, String name) {
        EditNameDialog frag = new EditNameDialog();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putInt("id", id);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_name, container);
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        String name = getArguments().getString("name", "");
        mEditText.setText(name);
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
            // Return input text to activity
            EditNameDialogListener listener = (EditNameDialogListener) getActivity();
            listener.onFinishEditDialog(getArguments().getInt("id"), mEditText.getText().toString());
            dismiss();
            return true;
        }
        return false;
    }

}
