package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warsawcitygame.R;
import com.warsawcitygame.Utils.DialogUtils;

public class ProfileFragment extends Fragment {

	public ProfileFragment(){
        }
	private TextView userDescriptionEditable;
    private TextView userLoginEditable;
    private TextView userPasswordEditable;
    private TextView userEmailEditable;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeTextViews(rootView);
        setListeners();

        return rootView;
    }

    private void setListeners()
    {
        setDialogListener(userDescriptionEditable, "Enter new description");
        setDialogListener(userLoginEditable, "Enter new login");
        setDialogListener(userPasswordEditable, "Enter new password");
        setDialogListener(userEmailEditable, "Enter new email");

    }

    private void setDialogListener(final TextView textView,final String text)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.RaiseDialogEditTextView(getActivity(), getActivity(), ((TextView) v).getText().toString(), text, textView);
            }
        });
    }

    private void initializeTextViews(View root) {
        userDescriptionEditable = (TextView)root.findViewById(R.id.userDescriptionEditable);
        userLoginEditable = (TextView)root.findViewById(R.id.userNameEditable);
        userPasswordEditable = (TextView)root.findViewById(R.id.userPasswordEditable);
        userEmailEditable = (TextView)root.findViewById(R.id.userEmailEditable);
    }
}
