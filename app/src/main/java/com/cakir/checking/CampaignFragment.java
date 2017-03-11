package com.cakir.checking;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmet on 6.11.2016.
 */

public class CampaignFragment extends Fragment {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.kampanyalar_x, container, false);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(getContext(), albumList, new AlbumClick() {
            @Override
            public void OnClick(int id) {
                Intent intent=new Intent(getContext(), CampaignActivity.class);
                intent.putExtra("id",(long)id+1);
                startActivity(intent);
            }
        },"fırsat");

        prepareAlbums();

        return rootView;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.burger_logo,
                R.drawable.mavi,
                R.drawable.adidas_logo,
                R.drawable.koctas_logo,
                R.drawable.teknosa,
                R.drawable.sarar,
                R.drawable.altinyildiz,
                R.drawable.sabri_ozel,
                R.drawable.petrol_ofisi};

        Album a = new Album("Burger King", 4, covers[0]);
        albumList.add(a);

        a = new Album("Mavi", 3, covers[1]);
        albumList.add(a);

        a = new Album("Adidas", 2, covers[2]);
        albumList.add(a);

        a = new Album("Koçtaş", 2, covers[3]);
        albumList.add(a);

        a = new Album("Teknosa", 2, covers[4]);
        albumList.add(a);

        a = new Album("Sarar", 0, covers[5]);
        albumList.add(a);

        a = new Album("Altınyıldız", 0, covers[6]);
        albumList.add(a);

        a = new Album("Sabri Özel", 0, covers[7]);
        albumList.add(a);

        a = new Album("Petrol Ofisi", 0, covers[8]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}