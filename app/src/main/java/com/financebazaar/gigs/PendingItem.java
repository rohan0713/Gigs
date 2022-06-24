package com.financebazaar.gigs;

public class PendingItem{
	private String referId;
	private String paytm_number;
	private String earnpay;
	private String id;
	private String campaign_id;
	private String status;
	private String campaignname;
	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCampaignname() {
		return campaignname;
	}

	public void setCampaignname(String campaignname) {
		this.campaignname = campaignname;
	}

	public void setReferId(String referId){
		this.referId = referId;
	}

	public String getReferId(){
		return referId;
	}

	public void setpaytm_number(String paytm_number){
		this.paytm_number = paytm_number;
	}

	public String getpaytm_number(){
		return paytm_number;
	}

	public void setEarnId(String earnId){
		this.earnpay = earnId;
	}

	public String getEarnId(){
		return earnpay;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setcampaign_id(String campaign_id){
		this.campaign_id = campaign_id;
	}

	public String getcampaign_id(){
		return campaign_id;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}
