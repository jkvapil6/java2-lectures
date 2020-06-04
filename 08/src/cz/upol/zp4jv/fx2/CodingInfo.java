package cz.upol.zp4jv.fx2;

public class CodingInfo {

    private String encoded;
    private String decoded;
    private int shift;

    public CodingInfo(String encoded, String decoded, int shift) {
        this.encoded = encoded;
        this.decoded = decoded;
        this.shift = shift;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public String getDecoded() {
        return decoded;
    }

    public void setDecoded(String decoded) {
        this.decoded = decoded;
    }

    public String getEncoded() {
        return encoded;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }
}
