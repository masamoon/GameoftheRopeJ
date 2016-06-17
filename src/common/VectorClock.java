package common;

import java.io.Serializable;

/**
 * Implementation of the vector clock
 */

public class VectorClock implements Cloneable, Serializable{
    /**
     * @serial timeStamp
     */
    private int [] timeStamp;

    /**
     * @serial localIndex
     */
    private int localIndex;

    /**
     *  Constructor for the vector clock object
     * @param size vector clock size
     * @param localIndex local index of the vector clock
     */
    public VectorClock(int size, int localIndex)
    {
        this.timeStamp = new int[size];
        this.localIndex = localIndex;
    }

    /**
     *  Increments the current index of the local clock
     */
    public synchronized void increment()
    {
        this.timeStamp[this.localIndex]++;

    }

    /**
     *  Updates the vector clock
     * @param vector new vector clock object to update to
     */
    public synchronized VectorClock update(VectorClock vector)
    {
        for(int i = 0; i < vector.timeStamp.length; i++){
            timeStamp[i] = Math.max(this.timeStamp[i], vector.timeStamp[i]);
        }
        return getCopy();
    }

    /**
     * Gets a full copy (object clone) of the vector clock object
     * @return the clone of the vector clock
     */
    public synchronized VectorClock getCopy()
    {
        VectorClock copy = null;
        try {
            copy = (VectorClock) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    }

    /**
     * Gets the vector clock represented as an Integer Array.
     * @return the array of integers of the vector clock.
     */
    public synchronized int[] toIntArray()
    {
        return timeStamp;
    }
}
