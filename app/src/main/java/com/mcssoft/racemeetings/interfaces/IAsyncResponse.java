package com.mcssoft.racemeetings.interfaces;

/**
 * Used to provide an interface between the async task (network downloader) and the main activity.
 */
public interface IAsyncResponse {
    void processFinish(String theResults);
}
