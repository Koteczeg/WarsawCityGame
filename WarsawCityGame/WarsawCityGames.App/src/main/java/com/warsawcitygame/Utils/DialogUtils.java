package com.warsawcitygame.Utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.ResponseBody;
import com.warsawcitygame.Activities.LoginActivity;
import com.warsawcitygame.Adapters.FriendListViewAdapter;
import com.warsawcitygame.Fragments.CurrentMissionFragment;
import com.warsawcitygame.R;
import com.warsawcitygames.models.UserMissionModel;
import com.warsawcitygames.models.friends_models.FriendModel;
import com.warsawcitygamescommunication.Services.FriendshipsService;

import java.io.IOError;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;

import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class DialogUtils
{
    public static void RaiseDialogEditTextView(Context context, Activity activity, String textToEdit, String description, final TextView oldText)
    {
        RaiseDialogEditTextView(context, activity, textToEdit, description, oldText, null);
    }

    public static void RaiseDialogEditTextView(Context context, Activity activity, String textToEdit, String description, final TextView oldText, final DelegateAction onSaveAction)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_text, null);
        builder.setView(dialogView);
        final EditText editField = (EditText)dialogView.findViewById(R.id.editable_text);
        editField.setText(textToEdit);
        final TextView descriptionTxt = (TextView)dialogView.findViewById(R.id.description);
        descriptionTxt.setText(description);
        final Button negativeButton = (Button)dialogView.findViewById(R.id.negativeButton);
        final Dialog dialog = builder.create();

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final Button positiveButton = (Button)dialogView.findViewById(R.id.positiveButton);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldText.setText(editField.getText().toString());
                dialog.dismiss();
                if(onSaveAction!=null)
                    onSaveAction.ExecuteAction();
            }
        });
        dialog.show();
    }

    public static void RaiseChangePasswordDialog(Context context, Activity activity, String[] descriptions, final TextView oldText, final DelegateActionParams<String> onSaveAction){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_password_change, null);
        builder.setView(dialogView);
        final EditText editField1 = (EditText)dialogView.findViewById(R.id.editable_text1);
        final TextView descriptionTxt1 = (TextView)dialogView.findViewById(R.id.description1);
        final EditText editField2 = (EditText)dialogView.findViewById(R.id.editable_text2);
        final TextView descriptionTxt2 = (TextView)dialogView.findViewById(R.id.description2);
        final Button negativeButton = (Button)dialogView.findViewById(R.id.negativeButton);
        final Dialog dialog = builder.create();

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final Button positiveButton = (Button)dialogView.findViewById(R.id.positiveButton);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(onSaveAction!=null)
                    onSaveAction.ExecuteAction(new String[]{editField1.getText().toString(), editField2.getText().toString()});
            }
        });
        dialog.show();
    }

    public static void RaiseDialogShowProfile(final Context context, Bitmap bitmap, final FriendModel modelObj, final FriendshipsService service, final List<FriendModel> friends, final List<FriendModel> searchResults, final FriendListViewAdapter adapter)
    {
        final Dialog dialog = new Dialog(context, R.style.TransparentStretchedDialog);
        dialog.setContentView(R.layout.dialog_profile);
        final TextView descriptionTxt = (TextView)dialog.findViewById(R.id.level);
        descriptionTxt.setText(modelObj.Username);
        final TextView usernameTxt = (TextView)dialog.findViewById(R.id.userNameTop);
        usernameTxt.setText(modelObj.Name);
        final ImageView imageView = (ImageView)dialog.findViewById(R.id.profilePic);
        if(bitmap!=null)
            imageView.setImageBitmap(bitmap);
        final Button negativeButton = (Button)dialog.findViewById(R.id.btn_dialog);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final Button friendActionButton = (Button)dialog.findViewById(R.id.btn_profile_action);
        if(modelObj.ActionType.equals("add"))
            friendActionButton.setText("Add friend");
        else if(modelObj.ActionType.equals("remove"))
            friendActionButton.setText("Remove friend");
        else
            friendActionButton.setVisibility(View.INVISIBLE);

        friendActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                final Dialog loading = DialogUtils.RaiseDialogLoading(context, true);
                Call<ResponseBody> call = null;
                if(modelObj.ActionType.equals("add"))
                    call = service.AssignFriend(modelObj.Id);
                else if(modelObj.ActionType.equals("remove"))
                    call = service.RemoveFriend(modelObj.Id);
                if (call != null)
                {
                    call.enqueue(new CustomCallback<ResponseBody>(context)
                    {
                        @Override
                        public void onSuccess(ResponseBody model)
                        {
                            if(modelObj.ActionType.equals("add"))
                            {
                                friends.add(modelObj);
                                searchResults.remove(modelObj);
                            }
                            else
                            {
                                friends.remove(modelObj);
                                searchResults.remove(modelObj);
                            }
                            adapter.notifyDataSetChanged();
                            DialogUtils.RaiseDialogShowError(context, "Success", "Successfully completed action.");
                            loading.dismiss();
                        }

                        @Override
                        public void onResponse(Response<ResponseBody> response, Retrofit retrofit)
                        {
                            if (!response.isSuccess())
                                DialogUtils.RaiseDialogShowError(context, "Error", "Something went wrong");
                            else
                            {
                                if(modelObj.ActionType.equals("add"))
                                {
                                    friends.add(modelObj);
                                    searchResults.remove(modelObj);
                                }
                                else
                                {
                                    friends.remove(modelObj);
                                    searchResults.remove(modelObj);
                                }
                                adapter.notifyDataSetChanged();
                                DialogUtils.RaiseDialogShowError(context, "Success", "Successfully completed action.");
                            }
                            loading.dismiss();
                        }

                        @Override
                        public void onFailure(Throwable t)
                        {
                            loading.dismiss();
                            DialogUtils.RaiseDialogShowError(context, "Error", "Something went wrong");
                        }
                    });
                }
            }
        });
        dialog.show();
    }

    public static Dialog RaiseDialogLoading(Context context)
    {
        return RaiseDialogLoading(context, true);
    }

    public static Dialog RaiseDialogLoading(Context context, boolean show)
    {
        final Dialog dialog = new Dialog(context, R.style.TransparentStretchedDialog);
        dialog.setContentView(R.layout.dialog_loading);
        if(show)dialog.show();
        return dialog;
    }

    public static Dialog RaiseDialogShowError(Context context, String title, String text)
    {
        final Dialog dialog = new Dialog(context, R.style.TransparentStretchedDialog);
        dialog.setContentView(R.layout.dialog_error);
        ((TextView)dialog.findViewById(R.id.error_title)).setText(title);
        ((TextView)dialog.findViewById(R.id.error_msg)).setText(text);
        Button ok = ((Button)dialog.findViewById(R.id.error_dismiss));
        ok.setText("OK");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public static Dialog RaiseDialogAbortMissionConfirmation(Context context, final Activity activity, final DelegateAction action)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirmation, null);
        builder.setView(dialogView);

        final Button negativeButton = (Button)dialogView.findViewById(R.id.negativeButton);
        final Dialog dialog = builder.create();

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final Button positiveButton = (Button)dialogView.findViewById(R.id.positiveButton);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(action != null) action.ExecuteAction();
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }



    public static void RaiseAchievementDialog(String msg, Context context)
    {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_popup_information);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showShortToast(String txt, Context context)
    {
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, txt, duration);
        toast.show();
    }
}
