package com.fvj.gameday;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetProvider extends AppWidgetProvider {
    public static final String TOAST_ACTION = "com.fvj.gameday.TOAST_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(TOAST_ACTION)) {
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; ++i) {
            setupIntent(context, appWidgetManager, appWidgetIds[i]);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void setupIntent(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Here we setup the intent which points to the StackViewService which will
        // provide the views for this collection.
        Intent intent = new Intent(context, GameDayService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        // When intents are compared, the extras are ignored, so we need to embed the extras
        // into the data so that the extras will not be ignored.
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.game_day_widget);
        rv.setRemoteAdapter(appWidgetId, R.id.stack_view, intent);

        // The empty view is displayed when the collection has no items. It should be a sibling
        // of the collection view.
        rv.setEmptyView(R.id.stack_view, R.id.empty_view);

        // Here we setup the a pending intent template. Individuals items of a collection
        // cannot setup their own pending intents, instead, the collection as a whole can
        // setup a pending intent template, and the individual items can set a fillInIntent
        // to create unique before on an item to item basis.
        Intent toastIntent = new Intent(context, WidgetProvider.class);
        toastIntent.setAction(WidgetProvider.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, rv);

    }
}