package ce326.hw2;

public class RGBImage implements Image{
    static final int MAX_COLORDEPTH = 255;

    int width, height, colordepth;

    RGBPixel[][] img;

    public RGBImage(){ }

    public RGBImage(int width, int height, int colordepth){
        this.width = width;
        this.height = height;
        this.colordepth = colordepth;
    }

    public RGBImage(RGBImage copyImg){
        this.img = new RGBPixel[copyImg.width][copyImg.height];
        this.width = copyImg.width;
        this.height = copyImg.height;
        this.colordepth = copyImg.colordepth;

        for(int w = 0; w < width; w++){
            for (int h = 0; h < height; h++){
                img[w][h] = new RGBPixel(copyImg.img[w][h]);
            }
        }
    }

    public RGBImage(YUVImage YUVImg){
        short C,D,E;

        img = new RGBPixel[YUVImg.width][YUVImg.height];
        this.width = YUVImg.width;
        this.height = YUVImg.height;

        for(int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                C = (short) (YUVImg.img[i][j].Y - 16);
                D = (short) (YUVImg.img[i][j].U - 128);
                E = (short) (YUVImg.img[i][j].V - 128);

                this.img[i][j].setRed((short) clip((298*C + 409*E + 128) >> 8));
                this.img[i][j].setGreen((short) clip((298*C - 100*D - 208*E + 128) >> 8));
                this.img[i][j].setBlue((short) clip((298*C + 516*D + 128) >> 8));
            }
        }
    }

    protected int clip(int i) {

        if(i < 0){
            i = 0;
        }
        else if(i > 255){
            i = 255;
        }
        return i;
    }

    int getWidth(){ return  this.width; }

    int getHeight(){ return this.height; }

    int getColordepth(){ return this.colordepth; }

    RGBPixel getPixel(int row, int col){
        return this.img[row][col];
    }

    void setPixel(int row, int col, RGBPixel pixel){
        this.img[row][col] = pixel;
    }


    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setColordepth(int colordepth) {
        this.colordepth = colordepth;
    }

    //############################# INTERFACE IMPLEMENTATION #######################################
    public void grayscale() {
        RGBPixel[][] newImg = new RGBPixel[width][height];

        for(int r = 0; r < width; r++){
            for (int c = 0; c < height; c++){
              //  short gray = (short) (img[r][c].red *0.3 + img[r][c].green*0.59 + img[r][c].blue*0.11);
                newImg[r][c].setRed((short) (img[r][c].getRed() * 0.3));
                newImg[r][c].setBlue((short) (img[r][c].getBlue() * 0.59));
                newImg[r][c].setBlue((short) (img[r][c].getBlue() * 0.11));
                newImg[r][c].setRGB( img[r][c].getRed()<<16 | img[r][c].getGreen()<<8 | img[r][c].getBlue());
            }
        }
    }

    public void doublesize() {
        RGBPixel[][] newImg = new RGBPixel[2*width][2*height];

        for(int r = 0; r < width; r++){
            for (int c = 0; c < height; c++){

                newImg[2*r][2*c] = img[r][c];
                newImg[2*r+1][2*c] = img[r][c];
                newImg[2*r][2*c+1] = img[r][c];
                newImg[2*r+1][2*c+1] = img[r][c];
            }
        }

    }

    public void halfsize() {
        RGBPixel[][] newImg = new RGBPixel[2*width][2*height];

        for(int r = 0; r < width; r++){
            for (int c = 0; c < height; c++){

                //get the average value of the 4 pixels for each color separately
                int med_red = (img[2*r][2*c].getRed() + img[2*r+1][2*c].getRed()
                        + img[2*r][2*c+1].getRed() + img[2*r+1][2*c+1].getRed()) /4;
                int med_green = (img[2*r][2*c].getGreen() + img[2*r+1][2*c].getGreen()
                        + img[2*r][2*c+1].getGreen() + img[2*r+1][2*c+1].getGreen()) /4;
                int med_blue = (img[2*r][2*c].getBlue() + img[2*r+1][2*c].getBlue()
                        + img[2*r][2*c+1].getBlue() + img[2*r+1][2*c+1].getBlue()) /4;
                newImg[r][c].setRed((short) med_red);
                newImg[r][c].setGreen((short) med_green);
                newImg[r][c].setBlue((short) med_blue);
                newImg[r][c].setRGB((short) med_red, (short) med_green, (short) med_blue);
            }
        }
    }

    public void rotateClockwise() {

        //width is turned to height and height to width
        RGBPixel[][] newImg = new RGBPixel[height][width];

        for(int r=0; r<width; r++){
            for(int c=0; c<height; c++){
                //standard formula to rotate
                newImg[c][width - r - 1] = img[r][c];
            }
        }
    }
}
