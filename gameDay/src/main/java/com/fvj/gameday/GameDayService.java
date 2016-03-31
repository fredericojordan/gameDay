package com.fvj.gameday;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class GameDayService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory, AsyncResponse {

    private Date lastRefresh = new Date(0);
    private SimpleDateFormat refreshFormat = new SimpleDateFormat("HH:mm ");

    private List<MatchInfo> mWidgetItems = new ArrayList<>();
    private Context mContext;

    public StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    public void onCreate() {
        // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
        callForRefresh();
    }

    public void onDestroy() {
        // In onDestroy() you should tear down anything that was setup for your data source,
        // eg. cursors, connections, etc.
        mWidgetItems.clear();
    }

    public int getCount() {
        return mWidgetItems.size();
    }

    public RemoteViews getViewAt(int position) {
        // You can do heaving lifting in here, synchronously. For example, if you need to
        // process an image, fetch something from the network, etc., it is ok to do it here,
        // synchronously. A loading view will show up in lieu of the actual contents in the
        // interim.

        // position will always range from 0 to getCount() - 1.
        System.out.println("Loading view " + position);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.match_info);

        if (position >= mWidgetItems.size())
            return rv;

        String teams =  String.format("%s %s %s", mWidgetItems.get(position).team1, mWidgetItems.get(position).score, mWidgetItems.get(position).team2);
        rv.setTextViewText(R.id.match_teams, teams);
        rv.setTextViewText(R.id.match_time, mWidgetItems.get(position).time);
        rv.setTextViewText(R.id.match_league, mWidgetItems.get(position).league);
        rv.setTextViewText(R.id.refresh_timestamp, refreshFormat.format(lastRefresh));

        return rv;
    }

    public RemoteViews getLoadingView() {
        // You can create a custom loading view (for instance when getViewAt() is slow.) If you
        // return null here, you will get the default loading view.
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
        // on the collection view corresponding to this factory. You can do heaving lifting in
        // here, synchronously. For example, if you need to process an image, fetch something
        // from the network, etc., it is ok to do it here, synchronously. The widget will remain
        // in its current state while work is being done here, so you don't need to worry about
        // locking up the widget.
    }

    public void callForRefresh() {
        WebManager parser = new WebManager(mContext);
        parser.delegate = this;
        parser.execute("");
    }

    public void processFinish(Context context, ArrayList<MatchInfo> matches) {
        this.lastRefresh = new Date();
        this.mWidgetItems = matches;
        notifyDataChange();
    }

    private void notifyDataChange() {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(mContext);
        ComponentName component = new ComponentName(mContext, WidgetProvider.class);
        int[] widgetIds = widgetManager.getAppWidgetIds(component);
        widgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.stack_view);
    }
}