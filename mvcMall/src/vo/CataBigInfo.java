package vo;

public class CataBigInfo {
// 대분류 정보를 저장할 클래스
	private String cb_name, cb_date;
	private int cb_idx;

	public String getCb_name() {
		return cb_name;
	}
	public void setCb_name(String cb_name) {
		this.cb_name = cb_name;
	}
	public String getCb_date() {
		return cb_date;
	}
	public void setCb_date(String cb_date) {
		this.cb_date = cb_date;
	}
	public int getCb_idx() {
		return cb_idx;
	}
	public void setCb_idx(int cb_idx) {
		this.cb_idx = cb_idx;
	}
}
