package gtadvantage.gyanjula.technologies.com.mbatestseries.fragment.POJO;

/**
 * Created by satyam on 2/2/17.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class leaderboardPOJO {

    @SerializedName("NAME")
    @Expose
    private List<String> nAME = new ArrayList<>();
    @SerializedName("BASIC")
    @Expose
    private List<Integer> bASIC = new ArrayList<>();
    @SerializedName("INTERMEDIATE")
    @Expose
    private List<Integer> iNTERMEDIATE = new ArrayList<>();
    @SerializedName("ADVANCED")
    @Expose
    private List<Integer> aDVANCED = new ArrayList<>();
    @SerializedName("max_total")
    @Expose
    private Integer maxTotal;
    @SerializedName("max_name")
    @Expose
    private String maxName;

    public List<String> getNAME() {
        return nAME;
    }

    public void setNAME(List<String> nAME) {
        this.nAME = nAME;
    }

    public List<Integer> getBASIC() {
        return bASIC;
    }

    public void setBASIC(List<Integer> bASIC) {
        this.bASIC = bASIC;
    }

    public List<Integer> getINTERMEDIATE() {
        return iNTERMEDIATE;
    }

    public void setINTERMEDIATE(List<Integer> iNTERMEDIATE) {
        this.iNTERMEDIATE = iNTERMEDIATE;
    }

    public List<Integer> getADVANCED() {
        return aDVANCED;
    }

    public void setADVANCED(List<Integer> aDVANCED) {
        this.aDVANCED = aDVANCED;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public String getMaxName() {
        return maxName;
    }

    public void setMaxName(String maxName) {
        this.maxName = maxName;
    }

}