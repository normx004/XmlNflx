package nflx;



public class HistoryItemLine {
   public Integer getOptListLength() {
		return optListLength;
	}


	public void setOptListLength(Integer optListLength) {
		this.optListLength = optListLength;
	}


	public Integer getStartTime() {
		return startTime;
	}


	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}


	public Integer getStopTime() {
		return stopTime;
	}


	public void setStopTime(Integer stopTime) {
		this.stopTime = stopTime;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


private Integer optListLength = null;
   private Integer startTime = null; //start-time
   private Integer stopTime = null; // stop-time
   private String  id = null;
   private String  title = null;
   private String  location = null;
   private boolean videpOntop = false; // video-on-top
   private Float   rate = 0.0f;
   private Integer volume = 0;
   private Integer videoX = 0; // video-x
   private Integer videoY = 0; // video-y
   
   public HistoryItemLine() {
	   optListLength = 0;
	   startTime = 0;
	   stopTime = 0;
	   id = new String("");
	   title = new String("");
	   location = new String("");
   }
   
   
   private void out(String s) {
	   System.out.println("PlayListLine: " + s);
   }
}
