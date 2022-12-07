package org.studiorailgun.dtos.survey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int id;

    static final int TYPE_TRUE_FALSE = 0;
    static final int TYPE_RATING = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    Survey survey;

    String prompt;

    int type;

    int ratingMax;
    int ratingMin;

    public Question(){
        this.type = TYPE_TRUE_FALSE;
    }

    public Question(String prompt, int ratingMax, int ratingMin){
        this.type = TYPE_RATING;
        this.ratingMax = ratingMax;
        this.ratingMin = ratingMin;
    }

    public void setSurvey(Survey survey){
        this.survey = survey;
    }

    public String getPrompt(){
        return prompt;
    }

    public int getId(){
        return id;
    }

    public int getType(){
        return type;
    }

    public int getRatingMax(){
        return ratingMax;
    }

    public int getRatingMin(){
        return ratingMin;
    }
    
}
