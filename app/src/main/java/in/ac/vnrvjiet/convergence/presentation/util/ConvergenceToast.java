package in.ac.vnrvjiet.convergence.presentation.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import in.ac.vnrvjiet.convergence.R;

/**
 * Created by pinna on 12/30/2017.
 */

public class ConvergenceToast {
    public static void create(Context context, int icon, String text,int duration) {
        Activity activity = (Activity) context;
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.convergence_toast, (ViewGroup) activity.findViewById(R.id.toast_container));

        TextView textView = (TextView) layout.findViewById(R.id.convergence_toast_text);
        textView.setText(text);

        ImageView imageView = (ImageView) layout.findViewById(R.id.convergence_toast_icon);

        if (icon != 0) {
            imageView.setImageResource(icon);
        } else {
            imageView.setVisibility(View.GONE);
        }

        Toast toast = new Toast(activity);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(layout);
        toast.show();
    }
}