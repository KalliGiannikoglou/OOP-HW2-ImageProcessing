package ce326.hw2;

import java.io.FileNotFoundException;

public class PPMImageStacker {


    public PPMImageStacker(java.io.File dir) throws java.io.FileNotFoundException, UnsupportedFileFormatException{

        try {
            if(!dir.exists()){
                throw new FileNotFoundException("[ERROR] directory" + dir + " does not exist!");
            }
            if(!dir.isDirectory()){
                throw new FileNotFoundException("[ERROR] directory" + dir + " is not a directory!");
            }

          
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void stack(){

    }

    public PPMImage getStackedImage(){

        return null;
    }

}
