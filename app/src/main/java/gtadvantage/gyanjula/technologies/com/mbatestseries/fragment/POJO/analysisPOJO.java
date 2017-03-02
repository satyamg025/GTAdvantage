package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.POJO;

/**
 * Created by satyam on 2/2/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class analysisPOJO {

    @SerializedName("BASIC")
    @Expose
    private Integer bASIC;
    @SerializedName("INTERMEDIATE")
    @Expose
    private Integer iNTERMEDIATE;
    @SerializedName("ADVANCED")
    @Expose
    private Integer aDVANCED;
    @SerializedName("MAX_TOTAL")
    @Expose
    private Integer mAXTOTAL;

    public Integer getBASIC() {
        return bASIC;
    }

    public void setBASIC(Integer bASIC) {
        this.bASIC = bASIC;
    }

    public Integer getINTERMEDIATE() {
        return iNTERMEDIATE;
    }

    public void setINTERMEDIATE(Integer iNTERMEDIATE) {
        this.iNTERMEDIATE = iNTERMEDIATE;
    }

    public Integer getADVANCED() {
        return aDVANCED;
    }

    public void setADVANCED(Integer aDVANCED) {
        this.aDVANCED = aDVANCED;
    }

    public Integer getMAXTOTAL() {
        return mAXTOTAL;
    }

    public void setMAXTOTAL(Integer mAXTOTAL) {
        this.mAXTOTAL = mAXTOTAL;
    }
}
