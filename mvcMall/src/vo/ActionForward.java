package vo;

// 작업후 최종적으로 뷰 페이지로 포워딩할 때의 방식을 정해주는 클래스
public class ActionForward {
	private String path;
	private boolean redirect;

	public ActionForward() {}
	// 매개변수 없이 인스턴스를 생성하기 위한 디폴트 생성자

	public ActionForward(String path, boolean redirect) {
		this.path = path;
		this.redirect = redirect;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}
}
