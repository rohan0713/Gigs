package com.financebazaar.gigs;

public class ResponseItem{
	private String earnpay;
	private String referpay;
	private String olink;
	private String t_c;
	private String id;
	private String steps;
	private String campaign_id;
	private String campaignname;
	private String type;
	private String image;
	private String status;
	private String ticket_id;

	public String getT_c() {
		return t_c;
	}

	public void setT_c(String t_c) {
		this.t_c = t_c;
	}

	public String getCampaign_id() {
		return campaign_id;
	}

	public void setCampaign_id(String campaign_id) {
		this.campaign_id = campaign_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(String ticket_id) {
		this.ticket_id = ticket_id;
	}

	public ResponseItem(String earnpay, String referpay, String olink, String t_c, String id, String steps, String campaign_id, String campaignname, String type, String image, String status, String ticket_id) {
		this.earnpay = earnpay;
		this.referpay = referpay;
		this.olink = olink;
		this.t_c = t_c;
		this.id = id;
		this.steps = steps;
		this.campaign_id = campaign_id;
		this.campaignname = campaignname;
		this.type = type;
		this.image = image;
		this.status = status;
		this.ticket_id = ticket_id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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
		this.t_c = tC;
	}

	public String getTC(){
		return t_c;
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
		this.campaign_id = campaignId;
	}

	public String getCampaignId(){
		return campaign_id;
	}

	public void setCampaignname(String campaignname){
		this.campaignname = campaignname;
	}

	public String getCampaignname(){
		return campaignname;
	}
}
