package org.endcoronavirus.outreach.fragments;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.endcoronavirus.outreach.R;
import org.endcoronavirus.outreach.adapters.SelectContactsListAdapter;

public class SelectContactsFragment extends Fragment {
    private static final int REQUEST_READ_CONTACTS = 79;
    private View view;
    private SelectContactsListAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_select_contacts, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.contacts_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(getActivity());
        } else {
            startReadContacts();
        }

        return view;
    }

    private void requestPermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_CONTACTS)) {
            // show UI part if you want here to show some rationale !!!
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startReadContacts();
                } else {
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private void startReadContacts() {
        adapter = new SelectContactsListAdapter();
        adapter.startReadContacts(getActivity());
        recyclerView.setAdapter(adapter);
    }
}
