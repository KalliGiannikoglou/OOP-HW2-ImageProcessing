package ce326.hw2;

public class RGBPixel {
    //RGB PIXEL:
    //        | 31-24 | 23-16 | 15-8  |  7-0  |
    //        |       |   R   |   G   |   B   |

    private  int rgb;
    private short red, green, blue;

    //set values red, green, blue of a pixel
    public RGBPixel(short red, short green, short blue){

        this.rgb = (red << 16) | (green << 8) | blue;
        this.red = (short) ((red << 16 ) | this.rgb);
        this.green = (short) ((green << 8 ) | this.rgb);
        this.blue = (short) (blue | this.rgb);
    }

    //make a pixel which is a copy of given pixel
    public RGBPixel(RGBPixel pixel){
        this.rgb = pixel.rgb;
        this.red = pixel.red;
        this.blue = pixel.blue;
        this.green = pixel.green;
    }

    //create an RGBPixel from a given YUVPixel
    public RGBPixel(YUVPixel pixel){
        short C = (short) (pixel.Y - 16);
        short D = (short) (pixel.U - 128);
        short E = (short) (pixel.V - 128);

        setRed((short) clip((298*C + 409*E + 128) >> 8));
        setGreen((short) clip((298*C - 100*D - 208*E + 128) >> 8));
        setBlue((short) clip((298*C + 516*D + 128) >> 8));

    }

    private int clip(int i) {

        if(i < 0){
            i = 0;
        }
        else if(i > 255){
            i = 255;
        }
        return i;
    }
    //Shifting to "isolate" different parts of the pixel and apply bitwise AND with 0xFF->255
    short getRed(){
        return this.red;
    }

    short getGreen(){
        return this.green;
    }

    short getBlue(){
        return this.blue;
    }

    void setRed(short red){

        this.rgb = red << 16 | this.rgb;
        this.red = red;
    }
    void setGreen(short green){

        this.rgb = green << 8 | this.rgb;
        this.red = green;
    }
    void setBlue(short blue){

        this.rgb = blue | this.rgb;
        this.blue = blue;
    }

    int getRGB(){
        return this.rgb;
    }
    void setRGB(int value){
        this.rgb = value;
    }

    final void setRGB(short red, short green, short blue){

        this.rgb = (red << 16) | (green << 8) | blue;
    }

    @Override
    public String toString(){
        return String.valueOf( getRed() + getGreen() + getBlue() );
    }

}

