package ce326.hw2;

public class YUVPixel {
    short Y,U,V;

    //CONSTANTS
    static final short Yy = 16;
    static final short Uu = 128;
    static final short Vv = 128;

    public YUVPixel(short Y, short U, short V){
        this.Y = Y;
        this.U = U;
        this.V = V;
    }

    public YUVPixel(YUVPixel pixel){
        this.Y = pixel.Y;
        this.U = pixel.U;
        this.V = pixel.V;
    }

    public YUVPixel(RGBPixel pixel){

    }

    public short getY() {
        return Y;
    }

    public short getU() {
        return U;
    }

    public short getV() {
        return V;
    }

    public void setY(short Y) {
        this.Y = Y;
    }

    public void setU(short U) {
        this.U = U;
    }

    public void setV(short V) {
        this.V = V;
    }
}
