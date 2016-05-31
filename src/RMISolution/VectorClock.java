package RMISolution;

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
    public synchronized void update(VectorClock vector)
    {
        for(int i = 0; i < vector.timeStamp.length; i++){
            timeStamp[i] = Math.max(this.timeStamp[i], vector.timeStamp[i]);
        }
    }

    /**
     * Gets a full copy (object clone) of the vector clock object
     * @return the clone of the vector clock
     * @throws java.lang.CloneNotSupportedException
     */
    public synchronized VectorClock getCopy() throws CloneNotSupportedException
    {
        VectorClock copy = (VectorClock) super.clone();

        copy.localIndex = localIndex;
        copy.timeStamp = timeStamp.clone();

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
