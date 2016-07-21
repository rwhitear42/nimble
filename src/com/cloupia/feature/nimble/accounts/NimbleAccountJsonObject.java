/*******************************************************************************
 * Copyright (c) 2016 Russ Whitear, Cisco and others
 *
 * Unless explicitly stated otherwise all files in this repository are licensed
 * under the Apache Software License 2.0
 *******************************************************************************/
package com.cloupia.feature.nimble.accounts;

public class NimbleAccountJsonObject {
	
	private String account;
	private String deviceIp;
	private String dnDetachedState;
	private String isCredentialPolicy;
	private String login;
	private String password;
	private String port;
	private String protocol;
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getDeviceIp() {
		return deviceIp;
	}
	
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	
	public String getDnDetachedState() {
		return dnDetachedState;
	}
	
	public void setDnDetachedState(String dnDetachedState) {
		this.dnDetachedState = dnDetachedState;
	}
	
	public String getIsCredentialPolicy() {
		return isCredentialPolicy;
	}
	
	public void setIsCredentialPolicy(String isCredentialPolicy) {
		this.isCredentialPolicy = isCredentialPolicy;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPort() {
		return port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getProtocol() {
		return protocol;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
}
