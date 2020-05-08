/*******************************************************************************
 * Object-Orientated Programming CA5 [Stage 1] | Toll System | CA Value: 10%
 * Author: Matthew Waller | D00218004
 ******************************************************************************/

package DTOs;

public class TollEvent {
    private String tollBoothID;
    private String registration;
    private long imageId;
    private long timestamp;

    public TollEvent(String tollBoothID, String registration, long imageId, long timestamp)
    {
        this.tollBoothID = tollBoothID;
        this.registration = registration;
        this.imageId = imageId;
        this.timestamp = timestamp;
    }

    public String getTollBoothID()
    {
        return tollBoothID;
    }

    public void tollBoothID(String tollBoothID)
    {
        this.tollBoothID = tollBoothID;
    }
    
    public String getRegistration()
    {
        return registration;
    }

    public void setRegistration(String registration)
    {
        this.registration = registration;
    }

    public long getImageId()
    {
        return imageId;
    }

    public void setImageId(long imageId)
    {
        this.imageId = imageId;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    @Override
    public String toString()
    {
        return "TollEvent{" + "tollBoothID=" + tollBoothID + ", registration=" + registration + ", imageId=" + imageId + ", timestamp=" + timestamp + '}';
    }
}