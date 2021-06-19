package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeZone{

	@JsonProperty("olson_name")
	private String olsonName;

	@JsonProperty("offset")
	private String offset;

	@JsonProperty("kind")
	private String kind;

	public void setOlsonName(String olsonName){
		this.olsonName = olsonName;
	}

	public String getOlsonName(){
		return olsonName;
	}

	public void setOffset(String offset){
		this.offset = offset;
	}

	public String getOffset(){
		return offset;
	}

	public void setKind(String kind){
		this.kind = kind;
	}

	public String getKind(){
		return kind;
	}
}