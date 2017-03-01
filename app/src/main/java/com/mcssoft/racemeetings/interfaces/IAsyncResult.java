package com.mcssoft.racemeetings.interfaces;

/**
 * Used to provide an interface between the async task of DownloadData and the MainActivity.
 */
public interface IAsyncResult {
    /**
     * XML download results as a string
     * @param table Results relate to this table.
     * @param theResults Results of the download.
     */
    void download(String table, String theResults);
}
