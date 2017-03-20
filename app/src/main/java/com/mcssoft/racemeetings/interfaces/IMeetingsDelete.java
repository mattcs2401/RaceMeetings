package com.mcssoft.racemeetings.interfaces;

/**
 * Interface between the MeetingsDeleteFragment and the MainActivity.
 */
public interface IMeetingsDelete {

    /**
     *
     * @param rows [0] The number of meetings rows deleted.
     *             [1] The number of races rows deleted.
     */
    void iMeetingsDelete(int[] rows);
}
