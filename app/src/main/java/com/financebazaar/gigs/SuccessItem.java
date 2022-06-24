package com.financebazaar.gigs;

public class SuccessItem{
	private String amount;
	private String datetime;
	private String paytm_number;
	private String id;
	private String order_id;
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

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setDatetime(String datetime){
		this.datetime = datetime;
	}

	public String getDatetime(){
		return datetime;
	}

	public void setpaytm_number(String paytm_number){
		this.paytm_number = paytm_number;
	}

	public String getpaytm_number(){
		return paytm_number;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setorder_id(String order_id){
		this.order_id = order_id;
	}

	public String getorder_id(){
		return order_id;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}
