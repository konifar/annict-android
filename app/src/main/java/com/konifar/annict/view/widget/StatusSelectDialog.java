package com.konifar.annict.view.widget;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.konifar.annict.R;
import com.konifar.annict.model.Status;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.List;

public class StatusSelectDialog {

    private static final String TAG = StatusSelectDialog.class.getSimpleName();

    public static void show(Context context, final Status status, Callback cb) {
        List<Status> statuses = Status.all();
        int defaultItem = statuses.indexOf(status);

        String[] items = Stream.of(statuses)
            .map(value -> context.getString(value.stringRes))
            .collect(Collectors.toList())
            .toArray(new String[statuses.size()]);

        new AlertDialog.Builder(context).setTitle(R.string.status_select_message)
            .setSingleChoiceItems(items, defaultItem, (dialog, which) -> {
                Status selectedStatus = statuses.get(which);
                if (!status.equals(selectedStatus)) {
                    Log.d(TAG, "Selected status: " + selectedStatus);
                    cb.onSelectedItem(selectedStatus);
                    dialog.dismiss();
                }
            })
            .setNegativeButton(R.string.cancel, null)
            .show();
    }

    public interface Callback {

        void onSelectedItem(Status status);
    }
}
