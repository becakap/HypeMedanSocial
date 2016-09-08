package br.com.hype.medan;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by IQBAL on 9/9/2016.
 */
public class CategoryAdapter extends FragmentPagerAdapter{

    /** Context of the app */
    private Context mContext;

    /**
     * Create a new {@link CategoryAdapter} object.
     *
     * @param context is the context of the app
     * @param fm is the fragment manager that will keep each fragment's state in the adapter
     *           across swipes.
     */
    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ThisHappeningFragment();
        } else if (position == 1){
            return new ThisTodayFragment();
        } else if (position == 2){
            return new ThisWeekFragment();
        } else if (position == 3){
            return new ThisMonthFragment();
        } else if (position == 4){
            return new ThisNewestFragment();
        } else {
            return new ThisFreeFragment();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_happening);
        } else if (position == 1) {
            return mContext.getString(R.string.category_today);
        } else if (position == 2) {
            return mContext.getString(R.string.category_this_week);
        } else if (position == 3) {
            return mContext.getString(R.string.category_this_month);
        } else if (position == 4) {
            return mContext.getString(R.string.category_this_newest);
        } else  {
            return mContext.getString(R.string.category_this_free);
        }
    }
}
