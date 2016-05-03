package com.warsawcitygame.Utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.warsawcitygame.R;

import java.io.IOError;
import java.util.InputMismatchException;

public class DialogUtils
{
    public static void RaiseDialogEditTextView(Context context, Activity activity, String textToEdit, String description, final TextView oldText)
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
            }
        });
          dialog.show();
    }

    public static void RaiseDialogEditTextView(Context context, Activity activity, String textToEdit, String description, final TextView oldText, final DelegateAction onSaveAction)
    {
        if(onSaveAction==null) RaiseDialogEditTextView(context, activity, textToEdit, description, oldText);
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
                onSaveAction.ExecuteAction();
            }
        });
        dialog.show();
    }

    public static void RaiseDialogShowProfile(Context context, Activity activity, String name, String levelDescription)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_profile, null);
        builder.setView(dialogView);
        final TextView descriptionTxt = (TextView)dialogView.findViewById(R.id.level);
        descriptionTxt.setText(levelDescription);
        final TextView usernameTxt = (TextView)dialogView.findViewById(R.id.userNameTop);
        usernameTxt.setText(name);
        final ImageView imageView = (ImageView)dialogView.findViewById(R.id.profilePic);
        imageView.setImageResource(R.drawable.tiger);
        final Button negativeButton = (Button)dialogView.findViewById(R.id.btn_dialog);
        final Dialog dialog = builder.create();
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static Dialog RaiseDialogLoading(Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_loading, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static Dialog RaiseDialogShowError(Context context, String title, String text)
    {
        final Dialog dialog= new Dialog(context);
        dialog.setContentView(R.layout.dialog_error);
        ((TextView)dialog.findViewById(R.id.error_title)).setText(title);
        ((TextView)dialog.findViewById(R.id.error_msg)).setText(text);
        Button ok = ((Button)dialog.findViewById(R.id.error_dismiss));
        ok.setText("OK");
        ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public static Dialog RaiseDialogAbortMissionConfirmation(Context context, Activity activity)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirmation, null);
        builder.setView(dialogView);

        final Button negativeButton = (Button)dialogView.findViewById(R.id.negativeButton);
        final Dialog dialog = builder.create();

        negativeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        final Button positiveButton = (Button)dialogView.findViewById(R.id.positiveButton);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO abort mission
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
