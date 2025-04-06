package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {
	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement statement = null;
		
		//投稿リスト
		String[][] postList = {
				{"1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13"},
				{"1002", "2023-02-08", "お疲れ様です！", "12"},
				{"1003", "2023-02-09", "今日も頑張ります！", "18"},
				{"1001", "2023-02-09", "無理は禁物ですよ！", "17"},
				{"1002", "2023-02-10", "明日から連休ですね！", "20"}
		};
		
		try {
			//データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"pass"
				);
			
			    System.out.println("データベース接続成功:" + con );
			    
			    //SQLクエリを準備
			    String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?);";
			    statement = con.prepareStatement(sql);
			    
			    int rowCnt = 0;
			    for (int i = 0; i < postList.length; i++) {
			    	statement.setString(1, postList[i][0]);
			    	statement.setString(2, postList[i][1]);
			    	statement.setString(3, postList[i][2]);
			    	statement.setString(4, postList[i][3]);
			    	rowCnt += statement.executeUpdate();
			    }
			    	
			    	System.out.println("レコード追加を実行します");
			    	System.out.println(rowCnt + "件のレコードが追加されました");
			    
			    String selectSql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002;";
			    statement = con.prepareStatement(selectSql);
			    
			    ResultSet result = statement.executeQuery();
			    
			    int recordNumber = 0;
			    while (result.next()) {
			    	Date postedAt = result.getDate("posted_at");
			    	String postedContent = result.getString("post_content");
			    	int likes = result.getInt("likes");
			    	recordNumber++;
			    	
			    	System.out.println("ユーザーIDが1002のレコードを検索しました");
			    	System.out.println(recordNumber + "件目：投稿日時＝" + postedAt + "/投稿内容＝" + postedContent + "/いいね数＝" + likes);
			    }
			    
		} catch (SQLException e) {
			System.out.println("エラー発生；" + e.getMessage());
		} finally {
			if(statement != null) {
				try {statement.close(); } catch (SQLException ignore) {}
			}
			if(con != null) {
				try {con.close(); } catch(SQLException ignore) {}
				
			}
		}
		

	}

}
