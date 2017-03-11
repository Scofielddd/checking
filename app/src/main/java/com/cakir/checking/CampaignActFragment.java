package com.cakir.checking;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ahmet on 6.11.2016.
 */

public class CampaignActFragment extends Fragment {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    private SweetAlertDialog sweet;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_campaignact, container, false);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(getContext(), albumList, new AlbumClick() {
            @Override
            public void OnClick(int id) {
                sweet = new SweetAlertDialog(getContext(),SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                sweet.setTitleText("Emin Misin ? ");
                sweet.setContentText("Onaylarsan 10000 coin karşılığında bu kampanyayı kartına tanımlayacaksın");
                sweet.setConfirmText("Evet");
                sweet.setCancelText("Hayır");

                sweet.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });

                sweet.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sDialog) {
                        sDialog.setTitleText("");
                        sDialog.showCancelButton(false);
                        sDialog.setContentText("İndiriminiz ING kartınıza tanımlandı").changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));

                        sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        });
                    }
                })
                        .show();
            }
        }, "coin karşılığında");



        return  rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        Bundle extras = getActivity().getIntent().getExtras();
        long id = extras.getLong("id");

        Log.d("Kampanya",":"+id);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.cmp_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new CampaignActFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        switch ((int)id)
        {
            case 1:
                prepareBurger();
                break;
            case 2:
                prepareMavi();
                break;
            case 3:
                prepareAdidas();
                break;
            case 4:
                prepareKoctas();
                break;
            case 5:
                prepareTeknosa();
                break;
        }
    }
    //////  BURGER TEXT /////////

    private void prepareBurger() {
        int[] covers = new int[]{
                R.drawable.burger1,
                R.drawable.burger2,
                R.drawable.burger3,
                R.drawable.burger4,
        };

        Album a = new Album("Menü 1", 10000, covers[0]);
        albumList.add(a);

        a = new Album("Menü 2", 10000, covers[1]);
        albumList.add(a);

        a = new Album("Menü 3", 10000, covers[2]);
        albumList.add(a);

        a = new Album("Menü 4", 10000, covers[3]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

///////// MAVİ ////////////

    private void prepareMavi() {
        int[] covers = new int[]{
                R.drawable.mavi_gomlek,
                R.drawable.mavi_kravat,
                R.drawable.mavi_pant,
        };

        Album a = new Album("Fırsat 1", 10000, covers[0]);
        albumList.add(a);

        a = new Album("Fırsat 2", 10000, covers[1]);
        albumList.add(a);

        a = new Album("Fırsat 3", 10000, covers[2]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

///////// ADİDAS //////////////

    private void prepareAdidas() {
        int[] covers = new int[]{
                R.drawable.adidas1,
                R.drawable.adidas2,
        };

        Album a = new Album("Kampanya 1", 10000, covers[0]);
        albumList.add(a);

        a = new Album("Kampanya 2", 10000, covers[1]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

///////// KOCTAS ///////////////

    private void prepareKoctas() {
        int[] covers = new int[]{
                R.drawable.koctas1,
                R.drawable.koctas2,
        };

        Album a = new Album("Kampanya 1", 10000, covers[0]);
        albumList.add(a);

        a = new Album("Kampanya 2", 10000, covers[1]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }


////// TEKNOSA ////////////

    private void prepareTeknosa() {
        int[] covers = new int[]{
                R.drawable.tekno1,
                R.drawable.tekno2,
                R.drawable.tekno3,
        };

        Album a = new Album("Hediye 1", 10000, covers[0]);
        albumList.add(a);

        a = new Album("Hediye 2", 10000, covers[1]);
        albumList.add(a);

        a = new Album("Hediye 3", 10000, covers[2]);
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
