package com.warsawcitygame.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.ResponseBody;
import com.warsawcitygame.Activities.CropUserImageActivity;
import com.warsawcitygame.Activities.LoginActivity;
import com.warsawcitygame.R;
import com.warsawcitygame.Utils.CustomCallback;
import com.warsawcitygame.Utils.DelegateAction;
import com.warsawcitygame.Utils.DelegateActionParams;
import com.warsawcitygame.Utils.DialogUtils;
import com.warsawcitygame.Utils.MyApplication;
import com.warsawcitygames.models.PlayerProfileDataModel;
import com.warsawcitygamescommunication.Services.UserProfileService;

import javax.inject.Inject;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class ProfileFragment extends Fragment
{
    private TextView userDescriptionEditable;
    private TextView userLoginEditable;
    private TextView userPasswordEditable;
    private TextView userEmailEditable;
    private TextView userLevelEditable;
    private TextView userExpEditable;
    private Dialog dialog;
    private CircleImageView profilePic;
    @Inject UserProfileService service;
    @Inject SharedPreferences preferences;

    public ProfileFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ((MyApplication) getActivity().getApplication()).getServicesComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initializeTextViews(rootView);
        setListeners();
        getData(rootView);
        return rootView;
    }

    private void setListeners()
    {
        setDialogListener(userDescriptionEditable, getActivity().getString(R.string.enterNewDescription), new ChangeDataAction());
        setDialogListener(userLoginEditable, getActivity().getString(R.string.enterLoginText), new ChangeDataAction());
        setDialogListener(userEmailEditable, getActivity().getString(R.string.enterEmailText), new ChangeDataAction());
        setPasswordDialogListener(userPasswordEditable, new ChangePasswordAction());
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CropUserImageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setPasswordDialogListener(final TextView textView, DelegateActionParams<String> action)
    {
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogUtils.RaiseChangePasswordDialog(getActivity(), getActivity(), new String[]{getActivity().getString(R.string.writeOldPasswordText), getActivity().getString(R.string.writeNewPasswordText)}, textView, new ChangePasswordAction());
            }
        });
    }

    private void setDialogListener(final TextView textView, final String text, final DelegateAction action)
    {
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogUtils.RaiseDialogEditTextView(getActivity(), getActivity(), ((TextView) v).getText().toString(), text, textView, action);
            }
        });
    }

    private void initializeTextViews(View root)
    {
        userDescriptionEditable = ButterKnife.findById(root, R.id.userDescriptionEditable);
        userLoginEditable = ButterKnife.findById(root, R.id.userNameEditable);
        userPasswordEditable = ButterKnife.findById(root, R.id.userPasswordEditable);
        userEmailEditable = ButterKnife.findById(root, R.id.userEmailEditable);
        userLevelEditable = ButterKnife.findById(root, R.id.userRank);
        userExpEditable = ButterKnife.findById(root, R.id.userExp);
        profilePic = ButterKnife.findById(root, R.id.profilePic);
    }

    @Override
    public void onResume()
    {
        String encoded = preferences.getString(CropUserImageActivity.USER_IMAGE, null);
        byte[] imageAsBytes=null;
        if(encoded!=null)
        imageAsBytes = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
        if(imageAsBytes==null)
        {
            profilePic.setImageResource(R.drawable.default_image);
            super.onResume();
            return;
        }
        Bitmap bm = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        if(bm!=null)
        {
            profilePic.setImageBitmap(bm);
        }
        super.onResume();
    }

    private void getData(View rootView)
    {
        TextView t = (TextView)rootView.findViewById(R.id.userNameTop);
        t.setText(preferences.getString(LoginActivity.USERNAME_KEY, null));
        final String username = preferences.getString(LoginActivity.USERNAME_KEY, null);
        showLoadingDialog();
        Call<PlayerProfileDataModel> callData = service.GetProfileData(username);
        callData.enqueue(new CustomCallback<PlayerProfileDataModel>(getActivity())
        {
            @Override
            public void onSuccess(PlayerProfileDataModel model)
            {
            }

            @Override
            public void onResponse(Response<PlayerProfileDataModel> response, Retrofit retrofit)
            {
                setProfileView(response.body());
                Call<String> call = service.GetUserImage();
                call.enqueue(new CustomCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(String model) {
                    }

                    @Override
                    public void onResponse(Response<String> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            if (response.body() != null) {
                                byte[] imageAsBytes = Base64.decode(response.body().getBytes(), Base64.DEFAULT);
                                Bitmap bm = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                                if (bm != null) {
                                    profilePic.setImageBitmap(bm);
                                }
                            }
                        } else {
                            //DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Cannot load user image");
                        }
                        if (dialog != null) {
                            dialog.dismiss();
                            dialog = null;
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Error " + t.getMessage());
                        super.onFailure(t);
                    }
                });
            }

            @Override
            public void onFailure(Throwable t)
            {
                DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Error " + t.getMessage());
                super.onFailure(t);
            }
        });
    }

    private void setProfileView(PlayerProfileDataModel model)
    {
        userDescriptionEditable.setText(model.Description.isEmpty() ? "Type some quote or sth about you." : model.Description);
        userEmailEditable.setText(model.Email);
        userLoginEditable.setText(model.Name);
        userLevelEditable.setText(model.Level);
        userExpEditable.setText(model.Exp + " exp");
    }

    private void ChangeData()
    {
        Call<ResponseBody> call = service.ChangeUserData(userEmailEditable.getText().toString(), userLoginEditable.getText().toString(), userDescriptionEditable.getText().toString());
        showLoadingDialog();
        call.enqueue(new CustomCallback<ResponseBody>(getActivity())
        {
            @Override
            public void onSuccess(ResponseBody model)
            {
                if (dialog != null)
                {
                    dialog.dismiss();
                    dialog = null;
                }
                Toast toast = Toast.makeText(getActivity(), getActivity().getString(R.string.successChangedUserDataText), Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit)
            {
                if (!response.isSuccess())
                    DialogUtils.RaiseDialogShowError(getActivity(), "Error", getActivity().getString(R.string.errorChangingUserData));
                else
                {
                    DialogUtils.RaiseDialogShowError(getActivity(), "Success", getActivity().getString(R.string.successChangingUserDatatext));
                    if (dialog != null)
                    {
                        dialog.dismiss();
                        dialog = null;
                    }
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                if (dialog != null)
                {
                    dialog.dismiss();
                    dialog = null;
                }
                DialogUtils.RaiseDialogShowError(getActivity(), "Error", getActivity().getString(R.string.errorChangingUserDataText));
            }
        });
    }

    private void ChangePassword(String currentPassword, String newPassword)
    {
        Call<ResponseBody> call = service.ChangePassword(currentPassword, newPassword);
        showLoadingDialog();
        call.enqueue(new CustomCallback<ResponseBody>(getActivity())
        {
            @Override
            public void onSuccess(ResponseBody model)
            {
                if (dialog != null)
                {
                    dialog.dismiss();
                    dialog = null;
                }
                Toast toast = Toast.makeText(getActivity(), getActivity().getString(R.string.successChangedPasswordText), Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit)
            {
                if (!response.isSuccess())
                    DialogUtils.RaiseDialogShowError(getActivity(), "Error", getActivity().getString(R.string.errorChangingPasswordText));
                else
                {
                    if (dialog != null)
                    {
                        dialog.dismiss();
                        dialog = null;
                    }
                    Toast toast = Toast.makeText(getActivity(), getActivity().getString(R.string.successChangingPasswordText), Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                if (dialog != null)
                {
                    dialog.dismiss();
                    dialog = null;
                }
                Toast toast = Toast.makeText(getActivity(), getActivity().getString(R.string.changePasswordFailedText), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void showLoadingDialog()
    {
        dialog = DialogUtils.RaiseDialogLoading(getActivity(), false);
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                dialog.show();
            }
        };
        Thread thread = new Thread(runnable);
        thread.run();
    }

    class ChangeDataAction implements DelegateAction
    {
        public void ExecuteAction()
        {
            ChangeData();
        }
    }

    class ChangePasswordAction implements DelegateActionParams<String>
    {
        public void ExecuteAction(String[] params)
        {
            ChangePassword(params[0], params[1]);
        }
    }
}
