RowAdapter
==========

Yet another adapter library?
----------------------------
RowAdapter...
* Can be used as a replacement to `RecyclerView.Adapter`, `SpinnerAdapter`, `CursorAdapter`, `ExpandableListAdapter`, `ListAdapter`, all while using a common interface.
* Aims to provide more flexibility than other adapter libraries by letting the adapter automatically handle ViewType information, simplifying your code and making it reusable
* Is compatible with both `ListView` and `RecyclerView` implementations

Basic Usage
-----------
1) Create a Row Class which represents an Adapter View and handles binding of your model to the view. You have a choice of sub classes to extend from that remove boiler plate code, such as inflating from a layout resource.
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
2) Instantiate a list of Rows of any type (View Type information is handled by RowAdapter):
```java
List<SampleRecyclerRow> rows = new ArrayList<SampleRecyclerRow>();
for (int i = 0; i < 17; i++) {
    String text = "Extra Info: " + String.valueOf(i);
    rows.add(new SampleRecyclerRow(text));
}
```
3) Instantiate an Adapter and provide it to either a `ListView` `RecyclerView` or `ExpandableListView`
```java
mRecyclerView.setAdapter(new RecyclerRowAdapter(this, rows));
```
4) Handle click events with a `RowClickHandler` to support multiple RowType click events or using `OnRowClickListener` for single click events.
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

