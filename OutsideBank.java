package layout;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.sunpreet.mbank.C0429R;
import com.example.sunpreet.mbank.SessionID;
import com.example.sunpreet.mbank.database;
import java.util.ArrayList;

public class OutsideBank extends Fragment implements OnItemSelectedListener {
    Button b1;
    database db;
    EditText e1;
    EditText e2;
    String fetchamt;
    Spinner spinner;

    class C19141 implements OnClickListener {
        C19141() {
        }

        public void onClick(View v) {
            if (OutsideBank.this.e1.getText().toString().trim().equals("") && OutsideBank.this.e2.getText().toString().trim().equals("")) {
                OutsideBank.this.e1.setError("Credit Account is required!");
                OutsideBank.this.e1.setBackgroundResource(C0429R.drawable.edittext_border);
                OutsideBank.this.e2.setError("Amount is required!");
                OutsideBank.this.e2.setBackgroundResource(C0429R.drawable.edittext_border);
            } else if (OutsideBank.this.e1.getText().toString().trim().equals("")) {
                OutsideBank.this.e1.setError("Credit Account is required!");
                OutsideBank.this.e1.setBackgroundResource(C0429R.drawable.edittext_border);
            } else if (OutsideBank.this.e2.getText().toString().trim().equals("")) {
                OutsideBank.this.e2.setError("Amount is required!");
                OutsideBank.this.e2.setBackgroundResource(C0429R.drawable.edittext_border);
            } else if (OutsideBank.this.spinner.getSelectedItem().toString().trim().equals("-- Select Account No. --")) {
                Toast.makeText(OutsideBank.this.getContext(), "Select Account No:", 0).show();
            } else {
                String acc = OutsideBank.this.spinner.getSelectedItem().toString().trim();
                String wamount = OutsideBank.this.e2.getText().toString();
                Cursor cs = OutsideBank.this.db.searchdata(acc);
                while (cs.moveToNext()) {
                    int amt = Integer.parseInt(cs.getString(2));
                    int transfer = Integer.parseInt(wamount);
                    int tot = amt - transfer;
                    OutsideBank.this.fetchamt = String.valueOf(tot);
                    if (amt >= transfer) {
                        OutsideBank.this.db.withdrawupdate(OutsideBank.this.e1.getText().toString(), OutsideBank.this.fetchamt);
                        Toast.makeText(OutsideBank.this.getContext(), "Successfully amount Fetched", 0).show();
                        if (OutsideBank.this.db.transferAmount(acc, OutsideBank.this.e1.getText().toString(), wamount)) {
                            ((InputMethodManager) OutsideBank.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(OutsideBank.this.getView().getWindowToken(), 2);
                            Snackbar.make(v, "Successfully Transferred", 0).setAction("Action", null).show();
                        } else {
                            Toast.makeText(OutsideBank.this.getContext(), "Try Again", 0).show();
                        }
                    } else {
                        ((InputMethodManager) OutsideBank.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(OutsideBank.this.getView().getWindowToken(), 2);
                        Snackbar.make(v, "Insufficient Amount", 0).setAction("Action", null).show();
                    }
                }
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String s = ((SessionID) getActivity().getApplicationContext()).getID();
        View view = inflater.inflate(C0429R.layout.fragment_outside_bank, container, false);
        this.db = new database(getContext());
        this.spinner = (Spinner) view.findViewById(C0429R.id.spinner);
        this.spinner.setOnItemSelectedListener(this);
        this.b1 = (Button) view.findViewById(C0429R.id.b1);
        this.e1 = (EditText) view.findViewById(C0429R.id.e1);
        this.e2 = (EditText) view.findViewById(C0429R.id.e2);
        ArrayList<String> spinnerContent = new ArrayList();
        spinnerContent.add("-- Select Account No. --");
        getActivity().getWindow().setSoftInputMode(32);
        Cursor cs = this.db.searchDataByEmail(s);
        while (cs.moveToNext()) {
            spinnerContent.add(cs.getString(1));
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(getContext(), 17367048, (String[]) spinnerContent.toArray(new String[spinnerContent.size()]));
        spinnerAdapter.setDropDownViewResource(17367049);
        this.spinner.setAdapter(spinnerAdapter);
        this.b1.setOnClickListener(new C19141());
        return view;
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
