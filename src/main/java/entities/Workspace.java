package entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Workspace{
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("kind")
	private String kind;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("name")
	private String name;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("id")
	private int id;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("project_ids")
	private List<Object> projectIds;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@JsonProperty("person_id")
	private int personId;

	public void setKind(String kind){
		this.kind = kind;
	}

	public String getKind(){
		return kind;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setProjectIds(List<Object> projectIds){
		this.projectIds = projectIds;
	}

	public List<Object> getProjectIds(){
		return projectIds;
	}

	public void setPersonId(int personId){
		this.personId = personId;
	}

	public int getPersonId(){
		return personId;
	}
}