package hive;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HiveConn {
	// Hiveserver1 和hiveserver2的JDBC区别：
		// HiveServer version Connection URL Driver Class
		// HiveServer2 jdbc:hive2://: org.apache.hive.jdbc.HiveDriver
		// HiveServer1 jdbc:hive://: org.apache.hadoop.hive.jdbc.HiveDriver
		// private final static Logger logger = LoggerFactory.getLogger(HiveJDBC.class);
		private static String driverName = "org.apache.hive.jdbc.HiveDriver";
		private static String url = "jdbc:hive2://localhost:10000/eucny";
		private static String user = "hive";
		private static String password = "113531";
		private static String sql = "select er.date,max(er.rate) as max_rate,min(er.rate) as min_rate from exchangerate er group by er.date";
		private static String sql_open_close = "select er.time, er.rate from exchangerate er where er.date = '";
		private static String sql_order_high = "' order by er.time desc limit 1";
		private static String sql_order_low = "' order by er.time limit 1";
		private static ResultSet res;

		private static Connection conn;
		private static Statement stmt;

		// 加载驱动、创建连接
		public static void init() throws ClassNotFoundException, SQLException {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);
		}
		
		// 获取基本信息
		public static List<DataItem> getBaseData() throws ClassNotFoundException, SQLException {
			List<DataItem> list = new ArrayList<>();
			stmt = conn.createStatement();
			res = stmt.executeQuery(sql);
			while(res.next()) {
				list.add(new DataItem(res.getString(2), 0D, 0D,res.getDouble(3), res.getDouble(4)));
			}
			return list;
		}
		
		// 获取每天的收盘比率
		public static void updateClose(List<DataItem> list) throws ClassNotFoundException, SQLException {
			for(DataItem temp : list) {
				stmt = conn.createStatement();
				res = stmt.executeQuery(sql_open_close + temp.getDate() + sql_order_high);
				while(res.next()) {
					temp.setClose(res.getDouble(4));
				}
			}
		}
		
		// 获取每天的开盘比率
		public static void updateOpen(List<DataItem> list) throws ClassNotFoundException, SQLException {
			for(DataItem temp : list) {
				stmt = conn.createStatement();
				res = stmt.executeQuery(sql_open_close + temp.getDate() + sql_order_low);
				while(res.next()) {
					temp.setOpen(res.getDouble(3));
				}
			}
		}

		public static void main(String[] args) throws Exception {
			init();
			System.out.print("end");
		}

}
