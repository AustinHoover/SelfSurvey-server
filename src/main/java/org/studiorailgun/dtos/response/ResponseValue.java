package org.studiorailgun.dtos.response;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ResponseValue {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonBackReference
    Response response;

    int questionId;

    String value;

    public ResponseValue(){

    }

    public void setResponse(Response response){
        this.response = response;
    }

    public Response getResponse(){
        return response;
    }

    public int getQuestionId(){
        return questionId;
    }

    public void setQuestionId(int id){
        questionId = id;
    }

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value = value;
    }

    public int getId(){
        return id;
    }
    
}
