package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warsawcitygame.R;
import com.warsawcitygame.Utils.DelegateAction;
import com.warsawcitygame.Utils.DelegateActionParams;
import com.warsawcitygame.Utils.DialogUtils;

import butterknife.ButterKnife;

public class ProfileFragment extends Fragment
{
    private TextView userDescriptionEditable;
    private TextView userLoginEditable;
    private TextView userPasswordEditable;
    private TextView userEmailEditable;

	public ProfileFragment(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initializeTextViews(rootView);
        setListeners();
        return rootView;
    }

    private void setListeners()
    {
        setDialogListener(userDescriptionEditable, "Enter new description", new ChangeDataAction());
        setDialogListener(userLoginEditable, "Enter new login", new ChangeDataAction());
        setDialogListener(userEmailEditable, "Enter new email", new ChangeDataAction());
        setPasswordDialogListener(userPasswordEditable, new ChangePasswordAction());
    }

    private void setPasswordDialogListener(final TextView textView, DelegateActionParams<String> action){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.RaiseChangePasswordDialog(getActivity(), getActivity(), new String[]{"Podaj stare hasło:", "Podaj nowe hasło"}, textView, new ChangePasswordAction());
            }
        });
    }

    private void setDialogListener(final TextView textView,final String text, final DelegateAction action)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.RaiseDialogEditTextView(getActivity(), getActivity(), ((TextView) v).getText().toString(), text, textView, action);
            }
        });
    }

    private void initializeTextViews(View root)
    {
        userDescriptionEditable=ButterKnife.findById(root,R.id.userDescriptionEditable);
        userLoginEditable=ButterKnife.findById(root,R.id.userNameEditable);
        userPasswordEditable=ButterKnife.findById(root,R.id.userPasswordEditable);
        userEmailEditable=ButterKnife.findById(root,R.id.userEmailEditable);
    }

    private void ChangeData(){

    }

    private void ChangePassword(String currentPassword, String newPassword){

    }

    class ChangeDataAction implements DelegateAction{
        public void ExecuteAction(){
            ChangeData();
        }
    }

    class ChangePasswordAction implements DelegateActionParams<String> {
        public void ExecuteAction(String[] params){
            ChangePassword(params[0], params[1]);
        }
    }
}
