package com.fvj.gameday;

import android.content.Context;

import java.util.ArrayList;

public interface AsyncResponse {
    void processFinish(Context context, ArrayList<MatchInfo> matches);
}