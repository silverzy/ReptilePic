package youtu;

import java.io.File;

public class MoveFile {

	public static void main(String[] args) {
		moveFile("test2.jpg","D:\\");
	}
	public static void moveFile(String re_path,String to_path){
		try {  
            File afile = new File(re_path);  
            System.out.println(to_path+ afile.getName());
            File tofile = new File(to_path);
            if(!tofile.exists()){
            	tofile.mkdirs();
            }
            if (afile.renameTo(new File(to_path+ afile.getName()))) {  
            	
                System.out.println("File is moved successful!");  
            } else {  
                System.out.println("File is failed to move!");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
}
