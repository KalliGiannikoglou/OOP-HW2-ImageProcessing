package ce326.hw2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class PPMImage extends RGBImage{

    //Standard format of PPM Image
    //"P3" Width Height Pixels[....]
    //We do not need to store any extra info about the file, just keep the format in toString and toFile

    public PPMImage(java.io.File file) throws java.io.FileNotFoundException, UnsupportedFileFormatException{
        String data;

        try{
            Scanner in = new Scanner(file);
            if(in.hasNext()){
                data = in.next();
                //check for the correct format
                if(!Objects.equals(data, "P3")){ throw new UnsupportedFileFormatException("Unsupported File Format: " +
                        "file does not start with P3"); }
            }

            //Width - Height - Colordepth
            if(in.hasNext()){
                data = in.next();
                super.width = Integer.parseInt(data);
                //a negative num is given as a width
                if(super.width <= 0){ throw new UnsupportedFileFormatException("Unsupported File Format: negative width");}
            }
            if(in.hasNext()){
                data = in.next();
                super.height = Integer.parseInt(data);
                //a negative num is given as a height
                if(super.height <= 0){ throw new UnsupportedFileFormatException("Unsupported File Format: negative height");}
            }

            if(in.hasNext()){
                data = in.next();
                super.colordepth = Integer.parseInt(data);
                //a negative num is given as a height
                if(super.colordepth <= 0){ throw new UnsupportedFileFormatException("Unsupported File Format: negative colordepth");}
            }
            for(int r = 0; r < width; r++) {
                for (int c = 0; c < height; c++) {
                    //Read RGB for Pixel [r][c]
                    if(in.hasNextLine()) {
                        data = in.next();
                        super.img[r][c].setRed(Short.parseShort(data));
                        data = in.next();
                        super.img[r][c].setGreen(Short.parseShort(data));
                        data = in.next();
                        super.img[r][c].setBlue(Short.parseShort(data));
                    }

                    else{ throw new UnsupportedFileFormatException("Unsupported File Format: pixel format");}
                }
            }
        }catch (java.io.FileNotFoundException e){
            System.out.println("File not found Exception!");
            e.printStackTrace();
        }
    }

    public PPMImage(RGBImage img){

        super.setWidth(img.width);
        super.setHeight(img.height);
        super.setColordepth(img.colordepth);
        for(int r = 0; r < width; r++) {
            for (int c = 0; c < height; c++) {
                super.setPixel(r,c, img.img[r][c]);
            }
        }
    }


    public PPMImage(YUVImage img){

        short C,D,E;
        super.img = new RGBPixel[img.width][img.height];
        this.width = img.width;
        this.height = img.height;

        for(int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                C = (short) (img.img[i][j].Y - 16);
                D = (short) (img.img[i][j].U - 128);
                E = (short) (img.img[i][j].V - 128);

                this.img[i][j].setRed((short) clip((298*C + 409*E + 128) >> 8));
                this.img[i][j].setGreen((short) clip((298*C - 100*D - 208*E + 128) >> 8));
                this.img[i][j].setBlue((short) clip((298*C + 516*D + 128) >> 8));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder ppmStr = new StringBuilder();

        ppmStr.append("P3 ");
        ppmStr.append(width).append(" ");
        ppmStr.append(height).append(" ");
        ppmStr.append(colordepth).append(" ");
        for(int r = 0; r < width; r++) {
            for (int c = 0; c < height; c++) {
                ppmStr.append(super.img[r][c].getRed()).append(" ");
                ppmStr.append(super.img[r][c].getGreen()).append(" ");
                ppmStr.append(super.img[r][c].getBlue()).append(" ");
            }
            ppmStr.append("\n");
        }

        return String.valueOf(ppmStr);
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
}
