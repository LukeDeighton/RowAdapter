package com.lukedeighton.typedadapter.sample;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lukedeighton.typedadapter.adapter.CursorMapper;
import com.lukedeighton.typedadapter.adapter.CursorRowAdapter;
import com.lukedeighton.typedadapter.adapter.ExpandableRowAdapter;
import com.lukedeighton.typedadapter.adapter.ExpandableRowAdapter.GroupRow;
import com.lukedeighton.typedadapter.adapter.RecyclerRowAdapter;
import com.lukedeighton.typedadapter.adapter.RowAdapter;
import com.lukedeighton.typedadapter.row.ExpandableRowType;
import com.lukedeighton.typedadapter.row.RowType;
import com.lukedeighton.typedadapter.row.templates.InflatedRow;
import com.lukedeighton.typedadapter.row.templates.customview.CustomViewRow;
import com.lukedeighton.typedadapter.sample.data.MockedCursor;
import com.lukedeighton.typedadapter.sample.row.AnotherRow;
import com.lukedeighton.typedadapter.sample.row.DividerItemDecoration;
import com.lukedeighton.typedadapter.sample.row.HeaderRow;
import com.lukedeighton.typedadapter.sample.row.ImageResourceRow;
import com.lukedeighton.typedadapter.sample.row.ImageViewRow;
import com.lukedeighton.typedadapter.sample.row.NavRow;
import com.lukedeighton.typedadapter.sample.row.SampleExpandableRow;
import com.lukedeighton.typedadapter.sample.row.SampleRecyclerRow;
import com.lukedeighton.typedadapter.sample.row.SampleViewHolderRow;
import com.lukedeighton.typedadapter.sample.row.SimpleTextRow;
import com.lukedeighton.typedadapter.sample.row.customview.CustomData;
import com.lukedeighton.typedadapter.sample.row.customview.CustomView;
import com.lukedeighton.typedadapter.utils.OnRowClickListener;
import com.lukedeighton.typedadapter.utils.RowBuilder;
import com.lukedeighton.typedadapter.utils.RowClickHandler;

import java.util.ArrayList;
import java.util.List;

//TODO checklist:
// 1) Add a 'setText' Impl with multiple TextViews
// 2) Some Pre-made Rows - Like a divider one?
public class MainActivity extends Activity implements ActionBar.OnNavigationListener {
    private static final int LIST = 1;
    private static final int RECYCLER = 2;
    private static final int EXPANDABLE_LIST = 3;

    private ListView mListView;
    private ExpandableListView mExpandableListView;
    private RecyclerView mRecyclerView;
    private RowAdapter mAdapter;

    private Toast mToast;

    public enum NavType {
        TypedAdapter(LIST),
        ExpandableTypedAdapter(EXPANDABLE_LIST),
        TypedCursorAdapter(LIST),
        RecyclerAdapter(RECYCLER);

        NavType(int type) {
            this.type = type;
        }

        int type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initActionBarSpinner();
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        emulateLongTask();
    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.listview);
        mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        //RowClickHandler is a convenience class to handle click events. Alternatively you can still
        //provide an OnItemClickListener (ListView only) and rely on instanceof checks to figure out the row type
        RowClickHandler clickHandler = new RowClickHandler()
        .put(HeaderRow.class, new OnRowClickListener<HeaderRow>() {
            @Override
            public void onRowClick(HeaderRow typedRow, View view, int position) {
                mToast.setText("header: " + typedRow.getItem());
                mToast.show();
            }
        })
        .put(ImageResourceRow.class, new OnRowClickListener<ImageResourceRow>() {
            @Override
            public void onRowClick(ImageResourceRow typedRow, View view, int position) {
                mToast.setText("image: " + typedRow.getItem());
                mToast.show();
            }
        })
        .put(SimpleTextRow.class, new OnRowClickListener<SimpleTextRow>() {
            @Override
            public void onRowClick(SimpleTextRow typedRow, View view, int position) {
                mToast.setText("simple: " + typedRow.getItem());
                mToast.show();
            }
        })
        .put(SampleRecyclerRow.class, new OnRowClickListener<SampleRecyclerRow>() {
            @Override
            public void onRowClick(SampleRecyclerRow typedRow, View view, int position) {
                mToast.setText("SampleRecyclerRow, pos: " + position);
                mToast.show();
            }
        });

        //set listeners
        mListView.setOnItemClickListener(clickHandler);

        //This uses TwoWayView's ItemClickSupport which sets one listener per RecyclerView.
        //Therefore if you use ItemClickSupport it will replace this listener (and vice versa)
        RecyclerRowAdapter.setRowClickHandler(mRecyclerView, clickHandler);
    }

    private void initActionBarSpinner() {
        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

            RowBuilder rowBuilder = new RowBuilder().add(NavType.values(), NavRow.class);
            RowAdapter adapter = new RowAdapter(this, rowBuilder.build());
            actionBar.setListNavigationCallbacks(adapter, this);
        }
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        NavType navType = NavType.values()[itemPosition];
        switch (navType.type) {
            case LIST:
                mListView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                mExpandableListView.setVisibility(View.GONE);
                break;
            case EXPANDABLE_LIST:
                mListView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                mExpandableListView.setVisibility(View.VISIBLE);
                break;
            case RECYCLER:
                mListView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mExpandableListView.setVisibility(View.GONE);
                break;
        }
        switch (navType) {
            case TypedAdapter:
                populateTypedAdapter();
                return true;
            case ExpandableTypedAdapter:
                populateExpandableRowAdapter();
                return true;
            case TypedCursorAdapter:
                populateCursorAdapter();
                return true;
            case RecyclerAdapter:
                populateRecyclerAdapter();
                return true;
        }
        return false;
    }

    private void emulateLongTask() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                //emulate a long task
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                onLongTaskComplete();
            }
        }.execute();
    }

    private void onLongTaskComplete() {
        //the underlying data has changed, lets update the listview
        List<RowType> rows = mAdapter.getRows();
        rows.remove(3);
        rows.add(3, new ImageViewRow(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        for (int i = 0; i < 7; i++) {
            rows.add(new HeaderRow("Extra Info: " + String.valueOf(i)));
        }

        //notify changes to data
        mAdapter.notifyDataSetChanged();
    }

    private void populateRecyclerAdapter() {
        List<SampleRecyclerRow> rows = new ArrayList<SampleRecyclerRow>();
        for (int i = 0; i < 17; i++) {
            rows.add(new SampleRecyclerRow("Extra Info: " + String.valueOf(i)));
        }
        mRecyclerView.setAdapter(new RecyclerRowAdapter(this, rows));
    }

    private void populateExpandableRowAdapter() {
        List<GroupRow> groupRows = new ArrayList<GroupRow>();
        for (int i = 0; i < 15; i++) {
            ExpandableRowType expandableRowType = new SampleExpandableRow("Group " + i);
            List<RowType> childRows = new ArrayList<RowType>();
            for (int j = 0; j < 7; j++) {
                childRows.add(new AnotherRow("Child " + j));
            }
            GroupRow groupRow = new GroupRow(expandableRowType, childRows);
            groupRows.add(groupRow);
        }
        mExpandableListView.setAdapter(new ExpandableRowAdapter(this, groupRows));
    }

    private void populateTypedAdapter() {
        //it is a little more tricky to distinguish the row
        //click event due to type erasure (same goes for any generic class).
        InflatedRow<String> anonymousRow = new InflatedRow<String>("test", R.layout.row_header) {
            @Override
            public void bindView(View view, int position) {
                TextView textView = (TextView) view.findViewById(R.id.textview);
                textView.setTextColor(getResources().getColor(android.R.color.tertiary_text_dark));
                textView.setText(getItem());
            }
        };

        //Sample using the ViewHolder pattern
        SampleViewHolderRow viewHolderRow = new SampleViewHolderRow("ViewHolderRow");

        //Sample using the CustomView pattern
        CustomData customData = new CustomData(R.drawable.abc_ab_share_pack_holo_dark,
                R.string.abc_action_mode_done);
        CustomViewRow<CustomData, CustomView> customViewRow =
                new CustomViewRow<CustomData, CustomView>(CustomView.class, customData);

        //TypedAdapter takes a list of rows. Either Construct the list yourself or use the
        //RowBuilder which is slightly more convenient
        RowBuilder rowBuilder = new RowBuilder()
                .add(new HeaderRow("hello"))
                .add(new SimpleTextRow("simple", R.layout.row_header, R.id.textview))
                .add(new ImageResourceRow(R.drawable.ic_launcher))
                .add(customViewRow)
                .add(viewHolderRow)
                .add(anonymousRow);

        //Note - viewTypeCount is optional and is only required if you intend to add more ViewTypes
        //later on using notifyDataSetChange() - else ignore this option and let the TypedAdapter
        //infer the viewTypeCount itself
        int viewTypeCount = 7;

        //Create a TypedAdapter and supply it to the ListView
        mAdapter = new RowAdapter(this, rowBuilder.build(), viewTypeCount);
        mListView.setAdapter(mAdapter);
    }

    private void populateCursorAdapter() {
        CursorRowAdapter<HeaderRow, String> adapter = new CursorRowAdapter<HeaderRow, String>(
                this, new MockedCursor(20), new CursorMapper<HeaderRow, String>() {
            @Override
            public String map(Cursor c) {
                return String.valueOf(c.getString(1));
            }

            @Override
            public HeaderRow createRow(Context context, String item, int position) {
                return new HeaderRow(item);
            }
        });
        mListView.setAdapter(adapter);
    }
}
