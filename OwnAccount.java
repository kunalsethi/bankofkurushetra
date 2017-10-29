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

public class OwnAccount extends Fragment implements OnItemSelectedListener {
    Button b1;
    database db;
    EditText e1;
    String fetchamt;
    Spinner spinner;
    Spinner spinner2;

    class C19151 implements OnClickListener {
        C19151() {
        }

        public void onClick(View v) {
            if (OwnAccount.this.e1.getText().toString().trim().equals("")) {
                OwnAccount.this.e1.setError("Amount is required!");
                OwnAccount.this.e1.setBackgroundResource(C0429R.drawable.edittext_border);
            }
            String acc = OwnAccount.this.spinner.getSelectedItem().toString().trim();
            String acc2 = OwnAccount.this.spinner2.getSelectedItem().toString().trim();
            if ((acc.equals(acc2) || acc2.equals(acc)) && !(acc.equals("-- Select Account No. --") && acc2.equals("-- Select Account No. --"))) {
                Toast.makeText(OwnAccount.this.getContext(), "Can't transfer into the same account", 0).show();
            } else if (acc.equals("-- Select Account No. --") || acc2.equals("-- Select Account No. --")) {
                Toast.makeText(OwnAccount.this.getContext(), "Select Account First!! ", 0).show();
            } else {
                Cursor cs = OwnAccount.this.db.searchdata(acc);
                while (cs.moveToNext()) {
                    Cursor cs2 = OwnAccount.this.db.searchdata(acc2);
                    while (cs2.moveToNext()) {
                        int amt = Integer.parseInt(cs.getString(2));
                        int transfer = Integer.parseInt(OwnAccount.this.e1.getText().toString());
                        int tot = amt - transfer;
                        OwnAccount.this.fetchamt = String.valueOf(tot);
                        if (amt >= transfer) {
                            OwnAccount.this.db.withdrawupdate(acc, OwnAccount.this.fetchamt);
                            int tot2 = Integer.parseInt(cs2.getString(2)) + Integer.parseInt(OwnAccount.this.e1.getText().toString());
                            OwnAccount.this.fetchamt = String.valueOf(tot2);
                            if (OwnAccount.this.db.withdrawupdate(acc2, OwnAccount.this.fetchamt)) {
                                ((InputMethodManager) OwnAccount.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(OwnAccount.this.getView().getWindowToken(), 2);
                                Snackbar.make(v, "Successfully Transferred", 0).setAction("Action", null).show();
                            } else {
                                Toast.makeText(OwnAccount.this.getContext(), "Try Again", 0).show();
                            }
                        } else {
                            ((InputMethodManager) OwnAccount.this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(OwnAccount.this.getView().getWindowToken(), 2);
                            Snackbar.make(v, "Insufficient Amount", 0).setAction("Action", null).show();
                        }
                    }
                }
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String s = ((SessionID) getActivity().getApplicationContext()).getID();
        View view = inflater.inflate(C0429R.layout.fragment_own_account, container, false);
        this.db = new database(getContext());
        this.b1 = (Button) view.findViewById(C0429R.id.b1);
        this.e1 = (EditText) view.findViewById(C0429R.id.e1);
        this.spinner = (Spinner) view.findViewById(C0429R.id.spinner);
        this.spinner.setOnItemSelectedListener(this);
        this.spinner2 = (Spinner) view.findViewById(C0429R.id.spinner2);
        this.spinner2.setOnItemSelectedListener(this);
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
        this.spinner2.setAdapter(spinnerAdapter);
        this.b1.setOnClickListener(new C19151());
        return view;
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
