package in.ac.vnrvjiet.convergence.presentation.home.contactsFragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import in.ac.vnrvjiet.convergence.BR;
import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.models.ContactsModel;
import in.ac.vnrvjiet.convergence.presentation.home.MainViewModel;
import in.ac.vnrvjiet.convergence.presentation.util.TextDrawable;
import in.ac.vnrvjiet.convergence.presentation.util.Utils;

/**
 * Created by pinna on 12/30/2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private static final String TAG = "ContactsAdapter";

    List<ContactsModel> contactsModelList;

    private MainViewModel mainViewModel;

    public ContactsAdapter(List<ContactsModel> contactsModelList, MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        this.contactsModelList = contactsModelList;
        Log.i(TAG, "ContactsAdapter: constructor");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.person_contact_card, parent, false));
        return (ViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContactsModel contactsModel = contactsModelList.get(position);
        holder.getViewDataBinding().setVariable(BR.contactsModelPOJO, contactsModel);
        holder.getViewDataBinding().setVariable(BR.contactsMainViewModel, mainViewModel);
        Context context = holder.getViewDataBinding().getRoot().getContext();
        ImageView contactImage = holder.getViewDataBinding().getRoot().findViewById(R.id.contact_icon);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(contactsModel.getPersonName().toCharArray()[0]).toUpperCase(), context.getResources().getColor(R.color.material_green_convergence));
        contactImage.setImageDrawable(drawable);
        contactImage.setScaleType(ImageView.ScaleType.FIT_XY);
        contactImage.setBackground(null);
        holder.getViewDataBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return (contactsModelList == null) ? 0 : contactsModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding viewDataBinding;

        ViewHolder(View itemView) {
            super(itemView);
            viewDataBinding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getViewDataBinding() {
            return viewDataBinding;
        }
    }
}
