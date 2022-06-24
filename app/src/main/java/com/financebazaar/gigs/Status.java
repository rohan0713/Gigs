package com.financebazaar.gigs;

import java.util.List;

public class Status{
	private List<SuccessItem> success;
	private List<PendingItem> pending;
	private List<FailedItem> failed;

	public void setSuccess(List<SuccessItem> success){
		this.success = success;
	}

	public List<SuccessItem> getSuccess(){
		return success;
	}

	public void setPending(List<PendingItem> pending){
		this.pending = pending;
	}

	public List<PendingItem> getPending(){
		return pending;
	}

	public void setFailed(List<FailedItem> failed){
		this.failed = failed;
	}

	public List<FailedItem> getFailed(){
		return failed;
	}
}