package com.mcssoft.racemeetings.interfaces;

/**
 * Used to provide an interface betweeen the MeetingsSearchFragment and MainActivity.
 */
public interface IDateSelect {
    /**
     * Date values (YYYY-MM-DD).
     * @param values [0] YYYY, [1] MM, [2] DD
     */
    void iDateValues(String[] values);
}
