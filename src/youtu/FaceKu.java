package youtu;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.youtu.Youtu;

public class FaceKu {

	public static final String APP_ID = "10093737";
	public static final String SECRET_ID = "AKIDXyFAzduYkmcLvKDcVzOcosmaGl8ODRI8";
	public static final String SECRET_KEY = "nSxVQFwbKvoz75kddu8IKFOTj8TdgLKD";
	public static final String USER_ID = "394622124";
	public static List<String> face_path = new ArrayList<String>();
	
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException, JSONException {
		GetSql("D:/downloadPic");
	}
	public void faceku() {
		
	}

	private static String GetSql(String path) throws KeyManagementException, NoSuchAlgorithmException, IOException, JSONException {
		Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);
		File rootDir = new File(path);
		if (!rootDir.isDirectory()) {
			//System.out.println("文件名" + rootDir.getAbsolutePath()+" "+time);
			face_path.add(rootDir.getAbsolutePath());
			
		} else {
			String[] fileList = rootDir.list();
		 
			for (int i = 0; i < fileList.length; i++) {
				if(i==0){
					String[] faceNames = path.split("\\\\");
				    if(faceNames.length>2) {
				    	String person_id = faceNames[2];
				    	JSONObject respose;
				    	String image_path = rootDir.getAbsolutePath() + "\\" + fileList[0];
				    	List<String> group_ids = new ArrayList<String>();
				    	group_ids.add("myface");
				    	respose=faceYoutu.NewPerson(image_path, person_id, group_ids);
				    	System.out.println("创建人："+respose);
				    }
				}
				path = rootDir.getAbsolutePath() + "\\" + fileList[i];
				GetSql(path);
			}
			if(!face_path.isEmpty()){
				String[] faceNames = path.split("\\\\");
			    String person_id = faceNames[2];
			    JSONObject respose;
			    respose=faceYoutu.AddFace(person_id, face_path);
				face_path.clear();
				System.out.println("添加人脸："+respose);
			}
		}
		return null;
	}

}
