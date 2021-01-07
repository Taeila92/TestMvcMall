package vo;

public class CartInfo {
// 장바구니 정보를 저장할 클래스로 장바구니에서 보여줄 데이터들도 저장함
	private int cl_idx, cl_cnt, pl_stock, price;
	private String cl_buyer, cl_ismember, pl_id, cl_opt, cl_date, pl_name, pl_img1, pl_opt;

	public int getCl_idx() {
		return cl_idx;
	}
	public void setCl_idx(int cl_idx) {
		this.cl_idx = cl_idx;
	}
	public int getCl_cnt() {
		return cl_cnt;
	}
	public void setCl_cnt(int cl_cnt) {
		this.cl_cnt = cl_cnt;
	}
	public int getPl_stock() {
		return pl_stock;
	}
	public void setPl_stock(int pl_stock) {
		this.pl_stock = pl_stock;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCl_buyer() {
		return cl_buyer;
	}
	public void setCl_buyer(String cl_buyer) {
		this.cl_buyer = cl_buyer;
	}
	public String getCl_ismember() {
		return cl_ismember;
	}
	public void setCl_ismember(String cl_ismember) {
		this.cl_ismember = cl_ismember;
	}
	public String getPl_id() {
		return pl_id;
	}
	public void setPl_id(String pl_id) {
		this.pl_id = pl_id;
	}
	public String getCl_opt() {
		return cl_opt;
	}
	public void setCl_opt(String cl_opt) {
		this.cl_opt = cl_opt;
	}
	public String getCl_date() {
		return cl_date;
	}
	public void setCl_date(String cl_date) {
		this.cl_date = cl_date;
	}
	public String getPl_name() {
		return pl_name;
	}
	public void setPl_name(String pl_name) {
		this.pl_name = pl_name;
	}
	public String getPl_img1() {
		return pl_img1;
	}
	public void setPl_img1(String pl_img1) {
		this.pl_img1 = pl_img1;
	}
	public String getPl_opt() {
		return pl_opt;
	}
	public void setPl_opt(String pl_opt) {
		this.pl_opt = pl_opt;
	}
}
