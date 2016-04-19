package com.warsawcitygame.Utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.warsawcitygame.R;

public class DialogUtils
{
    public static void RaiseDialogEditTextView(Context context, Activity activity, String textToEdit, String description, final TextView oldText)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
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

    public static void RaiseDialogShowProfile(Context context, Activity activity, String name, String levelDescription)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_profile_layout, null);
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

    public static Dialog RaiseDialogLoading(Context context, Activity activity)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_loading, null);
        builder.setView(dialogView);
        final Dialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static void RaiseDialogAbortMissionConfirmation(Context context, Activity activity)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirmation_layout, null);
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
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
