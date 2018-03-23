package in.ac.vnrvjiet.convergence.presentation.home.profileFragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.models.UserEventParticipationPojo;

/**
 * Created by venkat on 2/1/18.
 */

public class EventsAdapter extends FirebaseRecyclerAdapter<UserEventParticipationPojo,EventsAdapter.ViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EventsAdapter(FirebaseRecyclerOptions<UserEventParticipationPojo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, UserEventParticipationPojo model) {

        holder.getViewDataBinding().executePendingBindings();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.person_contact_card, parent, false));
        return (ViewHolder) viewHolder;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding viewDataBinding;

        ViewHolder(View itemView) {
            super(itemView);
            viewDataBinding = DataBindingUtil.bind(itemView);
        }

        ViewDataBinding getViewDataBinding() {
            return viewDataBinding;
        }
    }
}
