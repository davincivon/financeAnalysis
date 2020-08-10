package hive;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class MyS
 */
@WebServlet("/MyS")
public class MyS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			// 初始化链接
			HiveConn.init();
			// 获取基本数据
			//List<DataItem> list = this.chartMyS();
			List<DataItem> list = HiveConn.getBaseData();
			// 获取开盘比例
			HiveConn.updateOpen(list);
			// 获取收盘比例
			HiveConn.updateClose(list);
			// json
			Gson gson = new Gson();
			String str = gson.toJson(list);
			request.setAttribute("list", str);
			// 渲染
			request.getRequestDispatcher("ShowDatas.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private List<DataItem> chartMyS() {
		DataItem dd = new DataItem();
		dd.setDate("2018/1/23");
		dd.setOpen(61.31);
		dd.setClose(61.24);
		dd.setLowest(61.37);
		dd.setHighest(61.24);
		
		DataItem d = new DataItem();
		d.setDate("2018/1/24");
		d.setOpen(62.31);
		d.setClose(62.24);
		d.setLowest(62.37);
		d.setHighest(62.24);

		DataItem d1 = new DataItem();
		d1.setDate("2018/1/25");
		d1.setOpen(62.24);
		d1.setClose(62.72);
		d1.setLowest(62.59);
		d1.setHighest(62.79);

		DataItem d2 = new DataItem();
		d2.setDate("2018/1/28");
		d2.setOpen(62.72);
		d2.setClose(63.26);
		d2.setLowest(62.63);
		d2.setHighest(63.29);

		DataItem d3 = new DataItem();
		d3.setDate("2018/1/29");
		d3.setOpen(63.10);
		d3.setClose(63.02);
		d3.setLowest(63.02);
		d3.setHighest(63.11);

		DataItem d4 = new DataItem();
		d4.setDate("2018/1/30");
		d4.setOpen(63.02);
		d4.setClose(63.45);
		d4.setLowest(62.99);
		d4.setHighest(63.49);

		DataItem d5 = new DataItem();
		d5.setDate("2018/1/31");
		d5.setOpen(63.46);
		d5.setClose(63.75);
		d5.setLowest(63.37);
		d5.setHighest(63.94);

		List<DataItem> al = new ArrayList<>();
		al.add(dd);
		al.add(d);
		al.add(d1);
		al.add(d2);
		al.add(d3);
		al.add(d4);
		al.add(d5);
		
		return al;
	}

}
