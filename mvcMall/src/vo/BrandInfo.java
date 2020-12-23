package vo;

public class BrandInfo {
// 브랜드 정보를 저장할 클래스
	private int bl_idx;
	private String bl_name ,bl_date;
	public int getBl_idx() {
		return bl_idx;
	}
	public void setBl_idx(int bl_idx) {
		this.bl_idx = bl_idx;
	}
	public String getBl_name() {
		return bl_name;
	}
	public void setBl_name(String bl_name) {
		this.bl_name = bl_name;
	}
	public String getBl_date() {
		return bl_date;
	}
	public void setBl_date(String bl_date) {
		this.bl_date = bl_date;
	}
	
}
