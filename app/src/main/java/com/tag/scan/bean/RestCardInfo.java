package com.tag.scan.bean;

import java.io.Serializable;

public class RestCardInfo  extends BaseResp implements Serializable {
	private static final long serialVersionUID = 1L;
	private String cardInfo;
	private String cardCount;
	public String getCardInfo() {
		return cardInfo;
	}
	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}
	public String getCardCount() {
		return cardCount;
	}
	public void setCardCount(String cardCount) {
		this.cardCount = cardCount;
	}

}
