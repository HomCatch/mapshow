package com.iswater.map.pojos;


import java.util.Date;

public class Quality {
	
    public float intds;
    public float outtds;
    public float intemp;
    public float outtemp;
    public float inshades;
    public float outshades;
    public float inturbidity;
    public float outturbidity;
    public String date;
    public Long longdate;
    
    
	public float getIntds() {
		return intds;
	}
	public void setIntds(float intds) {
		this.intds = intds;
	}
	public float getOuttds() {
		return outtds;
	}
	public void setOuttds(float outtds) {
		this.outtds = outtds;
	}
	public float getIntemp() {
		return intemp;
	}
	public void setIntemp(float intemp) {
		this.intemp = intemp;
	}
	public float getOuttemp() {
		return outtemp;
	}
	public void setOuttemp(float outtemp) {
		this.outtemp = outtemp;
	}
	public float getInshades() {
		return inshades;
	}
	public void setInshades(float inshades) {
		this.inshades = inshades;
	}
	public float getOutshades() {
		return outshades;
	}
	public void setOutshades(float outshades) {
		this.outshades = outshades;
	}
	public float getInturbidity() {
		return inturbidity;
	}
	public void setInturbidity(float inturbidity) {
		this.inturbidity = inturbidity;
	}
	public float getOutturbidity() {
		return outturbidity;
	}
	public void setOutturbidity(float outturbidity) {
		this.outturbidity = outturbidity;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Long getLongdate() {
		return longdate;
	}
	public void setLongdate(Long longdate) {
		this.longdate = longdate;
	}
	
	
	@Override
	public String toString() {
		return "GetQuality [intds=" + intds + ", outtds=" + outtds + ", intemp=" + intemp + ", outtemp=" + outtemp
				+ ", inshades=" + inshades + ", outshades=" + outshades + ", inturbidity=" + inturbidity
				+ ", outturbidity=" + outturbidity + ", date=" + date + ", longdate=" + longdate + "]";
	}
    
    
    

}
