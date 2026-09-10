package kr.it.code.main.common.vo;


public class PageVO {

	private int totalPage; // 전체 페이지 개수
	private int nowPage; // 현재 페이지 0부터 시작하도록 한다.
	private int blockPerPageCnt; // 한블럭에 표시할 페이지 수
	private int pagePerRows; // 한페이지에 보여줄 게시글 개수 
	private int totalRows;  // 전체 데이터 수 
	private int nowBlock; // 현재 블럭 위치
	private int totalBlock; // 전체 블럭 개수 
	
	
	//페이징을 처리할 기본데이터 입력 메서드
	public void setData(int nowPage, int totalRows) {
		this.nowPage = nowPage;
		this.totalRows = totalRows;
		this.pagePerRows = 10; // 한페이지에 10개씩 보도록 설정
		this.blockPerPageCnt = 10; // 한블럭에 보여질 페이지 개수 
	}
	
	
	//sql에서 사용할 데이터 시작위치
	public int getOffset() {
		return this.nowPage * this.pagePerRows;
	}
	
	
	//sql에서 사용할 데이터 개수 
	public int getRowCount() {
		return this.pagePerRows;
	}
	
	
	public int getTotalPage() {
		double val = (double)this.totalRows / this.pagePerRows;
		//올림
		 int totalPage = (int)(Math.ceil(val));
		return totalPage;
		
	}
	
	//현재 블럭 위치
	public void getNowBlock() {
		double val = (double)this.nowPage / this.blockPerPageCnt;
		this.nowBlock  = (int)(Math.floor(val));
	}
	
	//전체 블럭 개수
	public void getTotalBlock() {
		double val = (double)this.getTotalPage() / this.blockPerPageCnt;
		this.totalBlock =  (int)(Math.ceil(val));
	}
	
	public String pageHTML() {
		
		StringBuilder sb = new StringBuilder();
	
		//계산된 값 처리 
		this.totalPage = this.getTotalPage();
		this.getNowBlock();
		this.getTotalBlock();
		
		int pageNum = 0;
		String isDiasbled = " disabled";
		String isActive = "";

		//첫페이지가 아님 
		if(this.nowPage > 0) {
			isDiasbled = "";
		}
		
		sb.append("<li class=\"page-item" + isDiasbled+ "\">");
		sb.append(" <a class=\"page-link\" href=\"javascript:void(0)\" onclick=\"movePage(0)\">처음</a>");
		sb.append("</li>");
		
		//이전페이지 - 전 블록의 마지막페이지로 이동 
		if(this.nowBlock > 0) {
			pageNum = (this.nowBlock * this.blockPerPageCnt) -1;
			sb.append("<li class=\"page-item\">");
			sb.append(" <a class=\"page-link\" href=\"javascript:void(0)\" onclick=\"movePage(" + pageNum + ")\">이전</a>");
			sb.append("</li>");			
		}
		
		//페이지 번호 그리기(현재 블록에서 번호 그리기)
		for(int i =0; i < this.blockPerPageCnt ; i++) {
			isActive =  "";
			pageNum =  (this.nowBlock * this.blockPerPageCnt) + i;
			
			if(pageNum == this.nowPage) {
				isActive = " active";
			}
			
			sb.append("<li class=\"page-item" + isActive+ "\">");
			sb.append(" <a class=\"page-link\" href=\"javascript:void(0)\" onclick=\"movePage(" + pageNum + ")\">");
			sb.append( (pageNum+1) + "</a></li>");		
			
			//페이지가 10페이지 이내일 경우, 마지막 페이지 번호에서 멈추도록 한다.
			if(this.totalPage == 0 || this.totalPage == (pageNum+1)) {
				break;
			}
			
		}

		//다음
		if( (this.nowBlock+ 1) < this.totalBlock) {
			pageNum = ( (this.nowBlock+1) * this.blockPerPageCnt);
			sb.append("<li class=\"page-item\">");
			sb.append(" <a class=\"page-link\" href=\"javascript:void(0)\" onclick=\"movePage(" + pageNum + ")\">다음</a>");
			sb.append("</li>");		
		}
		
		//마지막페이지
		 isDiasbled = "";
		 
		 if(this.totalPage == ( this.nowPage+1 )) {
			 isDiasbled = " disabled";
		 }
		 
		 pageNum = this.totalPage - 1;
		 
		 sb.append("<li class=\"page-item" + isDiasbled+ "\">");
		 sb.append(" <a class=\"page-link\" href=\"javascript:void(0)\" onclick=\"movePage(" + pageNum + ")\">마지막</a>");
		 sb.append("</li>");		
		
		
		return sb.toString();
	}
}
