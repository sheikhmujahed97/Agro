package in.ac.vnrvjiet.convergence.presentation.home.homeFragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.ac.vnrvjiet.convergence.BR;
import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.databinding.FragmentWorkshopBinding;
import in.ac.vnrvjiet.convergence.models.WorkshopPojo;
import in.ac.vnrvjiet.convergence.presentation.home.MainActivity;
import in.ac.vnrvjiet.convergence.presentation.home.MainViewModel;
import in.ac.vnrvjiet.convergence.presentation.util.carousellayoutmanager.CarouselLayoutManager;
import in.ac.vnrvjiet.convergence.presentation.util.carousellayoutmanager.CarouselZoomPostLayoutListener;
import in.ac.vnrvjiet.convergence.presentation.util.carousellayoutmanager.CenterScrollListener;

/**
 * Created by venkat on 16/1/18.
 */

public class WorkShopFragment extends Fragment {

    static WorkShopFragment workShopFragment;
    FragmentWorkshopBinding fragmentWorkshopBinding;
    MainViewModel mainViewModel;

    String[] workShopNames, noOfDays, registrationFees, venue;
    List<WorkshopPojo> workshopPojoList;

    public static WorkShopFragment getInstance() {
        if (workShopFragment == null) {
            workShopFragment = new WorkShopFragment();
        }
        return workShopFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        workShopNames = getResources().getStringArray(R.array.workshop_names);
        noOfDays = getResources().getStringArray(R.array.workshop_days);
        registrationFees = getResources().getStringArray(R.array.workshop_registration_fees);
        venue = getResources().getStringArray(R.array.workshop_venue);
        workshopPojoList = new ArrayList<>(0);
        int workShopCount = workShopNames.length;
        for (int i = 0; i < workShopCount; i++) {
            workshopPojoList.add(new WorkshopPojo(workShopNames[i], noOfDays[i], registrationFees[i], venue[i]));
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentWorkshopBinding = FragmentWorkshopBinding.inflate(inflater, container, false);
        mainViewModel = MainActivity.obtainViewModel(getActivity());
        fragmentWorkshopBinding.setMainViewModel(mainViewModel);
        CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL,true);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        RecyclerView workshopRecycler = fragmentWorkshopBinding.workshopRecycler;
        workshopRecycler.setLayoutManager(layoutManager);
        workshopRecycler.setHasFixedSize(true);
        workshopRecycler.setAdapter(new WorkshopAdapter(workshopPojoList));
        workshopRecycler.addOnScrollListener(new CenterScrollListener());
        return fragmentWorkshopBinding.getRoot();
    }

    class WorkshopAdapter extends RecyclerView.Adapter<WorkshopViewHolder> {

        List<WorkshopPojo> workshopPojoList;

        WorkshopAdapter(List<WorkshopPojo> workshopPojoList) {
            this.workshopPojoList = workshopPojoList;
        }

        @Override
        public WorkshopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = null;
            viewHolder = new WorkshopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.workshop_item_layout, parent, false));
            return (WorkshopViewHolder) viewHolder;
        }

        @Override
        public void onBindViewHolder(WorkshopViewHolder holder, int position) {
            holder.getViewDataBinding().setVariable(BR.workshop, workshopPojoList.get(position));
            holder.getViewDataBinding().executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return (workshopPojoList == null) ? 0 : workshopPojoList.size();
        }
    }

    class WorkshopViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding viewDataBinding;

        WorkshopViewHolder(View itemView) {
            super(itemView);
            viewDataBinding = DataBindingUtil.bind(itemView);
        }

        ViewDataBinding getViewDataBinding() {
            return viewDataBinding;
        }
    }
}
