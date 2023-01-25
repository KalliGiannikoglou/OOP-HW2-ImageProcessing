package ce326.hw2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class YUVImage {
    int width, height;
    YUVPixel[][] img;

    public YUVImage(int width, int height) {
        this.width = width;
        this.height = height;
        this.img = new YUVPixel[width][height];
    }

    public YUVImage(YUVImage copyImg){
        this.img = new YUVPixel[copyImg.width][copyImg.height];
        this.width = copyImg.width;
        this.height = copyImg.height;

        for(int w = 0; w < width; w++){
            for (int h = 0; h < height; h++){
                img[w][h] = new YUVPixel(copyImg.img[w][h]);
            }
        }
    }

    public YUVImage(RGBImage RGBImg){

        img = new YUVPixel[RGBImg.width][RGBImg.height];
        this.width = RGBImg.width;
        this.height = RGBImg.height;
        for(int i=0; i<width; i++){
            for (int j=0; j<height; j++){

                this.img[i][j].Y = (short) (((66*RGBImg.img[i][j].getRed() + 129* RGBImg.img[i][j].getGreen()+
                        25*RGBImg.img[i][j].getBlue() +128) >> 8) + 16);
                this.img[i][j].U = (short) (((-38*RGBImg.img[i][j].getRed() -74* RGBImg.img[i][j].getGreen()+
                        112*RGBImg.img[i][j].getBlue() +128) >> 8) + 128);
                this.img[i][j].V = (short) (((112*RGBImg.img[i][j].getRed() - 94* RGBImg.img[i][j].getGreen()-
                        18*RGBImg.img[i][j].getBlue() +128) >> 8) +128);
            }
        }

    }

    public YUVImage(java.io.File file) throws  java.io.FileNotFoundException, UnsupportedFileFormatException{

        String data;

        try {
            Scanner in = new Scanner(file);
            if(!file.exists()) { throw new FileNotFoundException(); }

            if(in.hasNext() && !Objects.equals(in.next(), "YUV3")){
                throw new UnsupportedFileFormatException("Incorrect YUV file form!");
            }

            if(in.hasNext()){
                this.width = Integer.parseInt(in.next());
                this.height = Integer.parseInt(in.next());
            }

            for(int r = 0; r < width; r++) {
                for (int c = 0; c < height; c++) {
                    //Read RGB for Pixel [r][c]
                    if(in.hasNextLine() ) {
                        data = in.next();
                        img[r][c].setY(Short.parseShort(data));
                        data = in.next();
                        img[r][c].setU(Short.parseShort(data));
                        data = in.next();
                        img[r][c].setV(Short.parseShort(data));
                    }
                    else{ throw new UnsupportedFileFormatException("Unsupported File Format: pixel format");}
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found Exception!");
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();

        str.append("YUV3 ");
        str.append(width).append(" ");
        str.append(height).append(" ");

        for(int r = 0; r < width; r++) {
            for (int c = 0; c < height; c++) {
                str.append(img[r][c].getY()).append(" ");
                str.append(img[r][c].getU()).append(" ");
                str.append(img[r][c].getV()).append(" ");
            }
            str.append("\n");
        }
        return String.valueOf(str);
    }

    public File toFile(java.io.File file){
        String str = toString();
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error at toFile in PPMImage class!");
            e.printStackTrace();
        }
        return file;
    }

    public void equalize(){}
}
