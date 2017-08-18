package youtu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.youtu.Youtu;

public class YoutuTest {
	public static final String APP_ID = "10093737";
	public static final String SECRET_ID = "AKIDXyFAzduYkmcLvKDcVzOcosmaGl8ODRI8";
	public static final String SECRET_KEY = "nSxVQFwbKvoz75kddu8IKFOTj8TdgLKD";
	public static final String USER_ID = "394622124";
	



	public static void main(String[] args) {

		try {
			Youtu faceYoutu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY,Youtu.API_YOUTU_END_POINT,USER_ID);
			JSONObject respose;
			List<String> myfaces = new ArrayList<String>();
			String path = "D://test2.png";
			myfaces.add("myface");
			//respose= faceYoutu.FaceCompareUrl("http://open.youtu.qq.com/content/img/slide-1.jpg","http://open.youtu.qq.com/content/img/slide-1.jpg");
			respose = faceYoutu.FaceIdentify(path, "myface");
			System.out.println(respose);
			//get respose
			JSONArray candidates= respose.getJSONArray("candidates");
			JSONObject date = candidates.getJSONObject(0);
			String faceName = date.get("person_id").toString();
			System.out.println(faceName);
			String to_path = "E:\\faces\\"+faceName+"\\";
			MoveFile.moveFile(path, to_path);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
