package ce326.hw2;

public class UnsupportedFileFormatException extends java.lang.Exception {
    String msg;

    public UnsupportedFileFormatException(){

    }
    public UnsupportedFileFormatException(String msg){
        this.msg = msg;
    }

}
