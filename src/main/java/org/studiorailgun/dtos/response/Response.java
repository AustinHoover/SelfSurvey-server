package org.studiorailgun.dtos.response;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Response {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int id;

    int surveyId;

    Instant timeSubmitted;

    @OneToMany(mappedBy = "response")
    @JsonManagedReference
    List<ResponseValue> responseValues;

    public Response() {
        
    }

    public Response(Instant timeSubmitted, int surveyId){
        this.surveyId = surveyId;
        this.timeSubmitted = timeSubmitted;
    }

    public void addResponseValue(ResponseValue value){
        this.responseValues.add(value);
    }

    public int getId(){
        return id;
    }

    public int getSurveyId(){
        return surveyId;
    }

    public Instant getTime(){
        return timeSubmitted;
    }

    public List<ResponseValue> getResponseValues(){
        return responseValues;
    }
    
}
