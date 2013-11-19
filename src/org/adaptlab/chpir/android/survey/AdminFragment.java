package org.adaptlab.chpir.android.survey;

import org.adaptlab.chpir.android.activerecordcloudsync.ActiveRecordCloudSync;
import org.adaptlab.chpir.android.activerecordcloudsync.PollService;
import org.adaptlab.chpir.android.survey.Models.AdminSettings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AdminFragment extends Fragment {

    private EditText mDeviceIdentifierEditText;
    private EditText mSyncIntervalEditText;
    private EditText mApiEndPointEditText;
    private Button mSaveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_settings, parent,
                false);
        mDeviceIdentifierEditText = (EditText) v
                .findViewById(R.id.device_identifier_edit_text);
        mDeviceIdentifierEditText.setText(AdminSettings.getInstance().getDeviceIdentifier());

        mSyncIntervalEditText = (EditText) v
                .findViewById(R.id.sync_interval_edit_text);
        mSyncIntervalEditText.setText(AdminSettings.getInstance().getSyncIntervalInMinutes() + "");
        
        mApiEndPointEditText = (EditText) v.findViewById(R.id.api_endpoint_edit_text);
        mApiEndPointEditText.setText(AdminSettings.getInstance().getApiUrl());

        mSaveButton = (Button) v.findViewById(R.id.save_admin_settings_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AdminSettings.getInstance().setDeviceIdentifier(mDeviceIdentifierEditText
                        .getText().toString());
                AdminSettings.getInstance().setSyncInterval(Integer
                        .parseInt(mSyncIntervalEditText.getText().toString()));
                AdminSettings.getInstance().setApiUrl(mApiEndPointEditText.getText().toString());
                PollService.setPollInterval(AdminSettings.getInstance().getSyncInterval());
                PollService.restartServiceAlarm(getActivity());
                ActiveRecordCloudSync.setEndPoint(AdminSettings.getInstance().getApiUrl());
                getActivity().finish();
            }
        });

        return v;
    }
}
