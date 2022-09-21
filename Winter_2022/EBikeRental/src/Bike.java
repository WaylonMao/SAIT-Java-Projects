/**
 * Specification of a Bike class suited to keeping track of bike rentals. This class is complete
 * and intended for use in the Winter 2022 CMPP269 Assignment #3 e-bike rental application.
 *
 * @author D. Shier
 * @version March 2022
 */
import java.util.Date;
public class Bike
{
    // constants
    public static final double LOW_BATTERY_CHARGE = 0.2;

    // instance variables
    private String bikeId;
    private double batteryCharge;
    private boolean inUse;
    private long rentalStart;
    private String userName;

    /**
     * Full-arg constructor for objects of class Bike
     */
    public Bike(String bikeId, double batteryCharge, boolean inUse, long rentalStart, String userName)
    {
        // assign parameter values to instance variables
        this.bikeId = bikeId;
        setBatteryCharge(batteryCharge);
        this.inUse = inUse;
        this.rentalStart = rentalStart;
        this.userName = userName;
    }
    
    /**
     * Single-arg constructor for objects of class Bike
     */
    public Bike(String bikeId)
    {
        // assign parameter values to instance variables
        this.bikeId = bikeId;
        setBatteryCharge(1.0);
        this.inUse = false;
        this.rentalStart = 0L;
        this.userName = null;
    }

    /**
     * getBikeId method - returns current value of bikeId
     *
     * @return            current value of the bikeId instance variable
     */
    public String getBikeId()
    {
        return bikeId;
    }

    /**
     * setBatteryCharge method - assigns the parameter value to batteryCharge
     *
     * @param  batteryCharge     new value to be stored in the batteryCharge instance variable, which must be
     *                           between 0.0 and 1.0 (inclusive)
     */
    public void setBatteryCharge(double batteryCharge)
    {
        if (batteryCharge >= 0.0 && batteryCharge <= 1.0)
        {
            this.batteryCharge = batteryCharge;
        }
    }

    /**
     * getBatteryCharge method - returns current value of batteryCharge
     *
     * @return                   current value of the batteryCharge instance variable
     */
    public double getBatteryCharge()
    {
        return batteryCharge;
    }

    /**
     * isBatteryLow method - returns a boolean indicating whether batteryCharge is considered low
     *
     * @return               true if current value of batteryCharge instance variable is at or below
     *                       the LOW_BATTERY_CHARGE constant value, false otherwise
     */
    public boolean isBatteryLow()
    {
        return batteryCharge <= LOW_BATTERY_CHARGE;
    }

    /**
     * isInUse method - returns a boolean indicating whether Bike is rented (i.e. in use)
     *
     * @return          current value of the inUse instance variable
     */
    public boolean isInUse()
    {
        return inUse;
    }

    /**
     * getRentalStart method - returns current value of rentalStart (expressed as milliseconds)
     *
     * @return                 current value of the rentalStart instance variable
     */
    public long getRentalStart()
    {
        return rentalStart;
    }

    /**
     * getUserName method - returns current value of userName
     *
     * @return              current value of the userName instance variable
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * rent method        - assigns values to inUse, userName and rentalStart indicating that the Bike
     *                      is now rented. Note that inUse MUST be false to begin with.
     *
     * @param  userName     user name or ID to be stored in the userName instance variable
     */
    public void rent(String userName)
    {
        if (!inUse)
        {
            inUse = true;
            this.userName = userName;
            rentalStart = System.currentTimeMillis(); // the rental clock starts now
        }
    }

    /**
     * unrent method      - resets inUse, userName and rentalStart values indicating that the Bike is
     *                      no longer rented. Note that inUse MUST be true to begin with.
     *
     * @return              elapsed milliseconds from rentalStart up to the time this method is invoked
     */
    public long unrent()
    {
        long elapsed = 0;
        if (inUse)
        {
            elapsed = System.currentTimeMillis() - rentalStart;
            inUse = false;
            userName = "";
            rentalStart = 0L;
        }
        return elapsed;
    }

    /**
     * equals() - overrides Objects.equals to determine Bike equality by comparing bikeId strings
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }

        if (!Bike.class.isAssignableFrom(obj.getClass()))
        {
            return false;
        }

        final Bike other = (Bike) obj;
        if ((this.bikeId == null) ? (other.bikeId != null) : !this.bikeId.equals(other.bikeId))
        {
            return false;
        }

        return true;
    }

    /**
     * hashCode() - overrides Objects.hashCode to derive a Bike hashcode using bikeId
     */
    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 53 * hash + (this.bikeId != null ? this.bikeId.hashCode() : 0);
        return hash;
    }

    /**
     * toString method - returns a readable representation of Bike state
     *
     * @return           a String containing nicely formatted Bike instance variable values
     */
    @Override
    public String toString()
    {
        String result = String.format("%7s  %6.0f%%", getBikeId(), getBatteryCharge() * 100.0);
        if (isInUse())
        {
            result += String.format("  Rented at %1$tH:%1$tM to %2$s", new Date(getRentalStart()), getUserName());
        }
        return result;
    }
}
