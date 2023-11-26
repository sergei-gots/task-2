package org.task2.task22;

public interface TokenConstants {
    /** Default expiration time
     * = 15 min
     */
    long EXPIRATION_MILLIS = 900_000L;
    /** Max time left to allow the token be refreshed
     *  = 5 min
     */
    long REFRESH_ALLOWANCE_THRESHOLD = 300_000L;
    /** Min time left when there is necessity to refresh token when it is requested on server
     * = 1 min
     */
    long REFRESH_NECESSITY_THRESHOLD = 60_000L;

}
