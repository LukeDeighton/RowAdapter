RowAdapter
==========

Yet another adapter library?
----------------------------
RowAdapter...
* aims to provide more flexibility than other adapter libraries
* handles ViewType information, simplifying your code and making it reusable
* supports RecyclerView and is also compatible with existing ListView implementations
* can be used as a `RecyclerView.Aadapter`, `SpinnerAdapter`, `CursorAdapter`, `ExpandableListAdapter`, `ListAdapter`

Basic Usage
-----------
1) Declare a Row Class extending a Row Subtype or implementing `ViewHolderRowType` or `RowType` 
```java
public class SampleRecyclerRow extends ViewHolderRow<String, SampleRecyclerRow.ViewHolder> {
    public SampleRecyclerRow(String item) {
        super(item, R.layout.row_header);
    }

    @Override
    public ViewHolder createViewHolder(View view, int position) {
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder viewHolder, String item, int position) {
        viewHolder.mTextView.setText(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.textview);
        }
    }
}
```
2) Instantiate a list of Rows:
```java
List<SampleRecyclerRow> rows = new ArrayList<SampleRecyclerRow>();
for (int i = 0; i < 17; i++) {
    String text = "Extra Info: " + String.valueOf(i);
    rows.add(new SampleRecyclerRow(text));
}
```
3) Instantiate an Adapter and supply it to either a `ListView` `RecyclerView` or `ExpandableListView`
```java
mRecyclerView.setAdapter(new RecyclerRowAdapter(this, rows));
```
4) Handle click events with
```java
RowClickHandler clickHandler = new RowClickHandler()
.put(SampleRecyclerRow.class, new OnRowClickListener<SampleRecyclerRow>() {
    @Override
    public void onRowClick(SampleRecyclerRow typedRow, View view, int position) {
        mToast.setText("SampleRecyclerRow, pos: " + position);
        mToast.show();
    }
})
...
```
Further Usage
-------------
Please refer to this blog post or the sample module for a complete set of usages.
