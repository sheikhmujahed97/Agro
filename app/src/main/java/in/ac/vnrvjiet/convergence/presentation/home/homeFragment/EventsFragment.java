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
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.List;

import in.ac.vnrvjiet.convergence.BR;
import in.ac.vnrvjiet.convergence.R;
import in.ac.vnrvjiet.convergence.databinding.FragmentEventsBinding;
import in.ac.vnrvjiet.convergence.models.SingleEventItem;
import in.ac.vnrvjiet.convergence.presentation.home.MainActivity;
import in.ac.vnrvjiet.convergence.presentation.home.MainViewModel;

/**
 * Created by venkat on 14/1/18.
 */

public class EventsFragment extends Fragment {

    FragmentEventsBinding fragmentEventsBinding;
    MainViewModel mainViewModel;
    String[] eventsNameList, eventRoundList, eventRoomNumbers;
    List<SingleEventItem> singleEventItems;
    EventsRecyclerAdapter eventsRecyclerAdapter;
    private static EventsFragment eventsFragment;

    public static EventsFragment getInstance() {
        if (eventsFragment == null) {
            eventsFragment = new EventsFragment();
        }
        return eventsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventsNameList = getResources().getStringArray(R.array.events_names);
        eventRoundList = getResources().getStringArray(R.array.event_round_count);
        eventRoomNumbers = getResources().getStringArray(R.array.event_room_no);
        int eventsSize = eventsNameList.length;
        singleEventItems = new ArrayList<>(0);
        for (int i = 0; i < eventsSize; i++) {
            singleEventItems.add(new SingleEventItem(eventsNameList[i], eventRoomNumbers[i], eventRoundList[i]));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentEventsBinding = FragmentEventsBinding.inflate(inflater, container, false);
        mainViewModel = MainActivity.obtainViewModel(getActivity());
        fragmentEventsBinding.setMainViewModel(mainViewModel);
        eventsRecyclerAdapter = new EventsRecyclerAdapter(singleEventItems);
        fragmentEventsBinding.eventsRecycler.setAdapter(eventsRecyclerAdapter);
        return fragmentEventsBinding.getRoot();
    }

    class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.EventsRecyclerHolder> implements SectionIndexer {

        List<SingleEventItem> eventsList;
        private ArrayList<Integer> mSectionPositions;

        public EventsRecyclerAdapter(List<SingleEventItem> eventsList) {
            this.eventsList = eventsList;
        }

        @Override
        public EventsRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = null;
            viewHolder = new EventsRecyclerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_layout, parent, false));
            return (EventsRecyclerHolder) viewHolder;
        }

        @Override
        public void onBindViewHolder(EventsRecyclerHolder holder, int position) {
            holder.getViewDataBinding().setVariable(BR.event, eventsList.get(position));
            holder.getViewDataBinding().executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return (eventsList == null) ? 0 : eventsList.size();
        }

        @Override
        public Object[] getSections() {
            List<String> sections = new ArrayList<>(26);
            mSectionPositions = new ArrayList<>(26);
            for (int i = 0, size = eventsList.size(); i < size; i++) {
                String section = String.valueOf(eventsList.get(i).getEventName().charAt(0)).toUpperCase();
                if (!sections.contains(section)) {
                    sections.add(section);
                    mSectionPositions.add(i);
                }
            }
            return sections.toArray(new String[0]);
        }

        @Override
        public int getPositionForSection(int i) {
            return mSectionPositions.get(i);
        }

        @Override
        public int getSectionForPosition(int i) {
            return 0;
        }

        class EventsRecyclerHolder extends RecyclerView.ViewHolder {
            private final ViewDataBinding viewDataBinding;

            EventsRecyclerHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }

            ViewDataBinding getViewDataBinding() {
                return viewDataBinding;
            }
        }
    }
}
