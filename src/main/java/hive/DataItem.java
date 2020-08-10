package hive;

public class DataItem {
	

		private String date;
		private Double open;
		private Double close;
		private Double highest;
		private Double lowest;
		
		public DataItem() {
			super();
		}
		
		
		
		public DataItem(String date, Double open, Double close, Double highest, Double lowest) {
			super();
			this.date = date;
			this.open = open;
			this.close = close;
			this.highest = highest;
			this.lowest = lowest;
		}


		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public Double getOpen() {
			return open;
		}
		public void setOpen(Double open) {
			this.open = open;
		}
		public Double getClose() {
			return close;
		}
		public void setClose(Double close) {
			this.close = close;
		}
		public Double getHighest() {
			return highest;
		}
		public void setHighest(Double highest) {
			this.highest = highest;
		}
		public Double getLowest() {
			return lowest;
		}
		public void setLowest(Double lowest) {
			this.lowest = lowest;
		}
		
	}



