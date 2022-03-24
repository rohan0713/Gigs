package com.example.gigs;

public class ResponseItem{
	private String earnpay;
	private String referpay;
	private String olink;
	private String tC;
	private String id;
	private String steps;

	public ResponseItem(String earnpay, String referpay, String olink, String tC, String id, String steps, String campaignId, String campaignname) {
		this.earnpay = earnpay;
		this.referpay = referpay;
		this.olink = olink;
		this.tC = tC;
		this.id = id;
		this.steps = steps;
		this.campaignId = campaignId;
		this.campaignname = campaignname;
	}

	private String campaignId;
	private String campaignname;

	public void setEarnpay(String earnpay){
		this.earnpay = earnpay;
	}

	public String getEarnpay(){
		return earnpay;
	}

	public void setReferpay(String referpay){
		this.referpay = referpay;
	}

	public String getReferpay(){
		return referpay;
	}

	public void setOlink(String olink){
		this.olink = olink;
	}

	public String getOlink(){
		return olink;
	}

	public void setTC(String tC){
		this.tC = tC;
	}

	public String getTC(){
		return tC;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setSteps(String steps){
		this.steps = steps;
	}

	public String getSteps(){
		return steps;
	}

	public void setCampaignId(String campaignId){
		this.campaignId = campaignId;
	}

	public String getCampaignId(){
		return campaignId;
	}

	public void setCampaignname(String campaignname){
		this.campaignname = campaignname;
	}

	public String getCampaignname(){
		return campaignname;
	}
}
