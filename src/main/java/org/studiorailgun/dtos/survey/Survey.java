
package org.studiorailgun.dtos.survey;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Survey {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int id;

    String name;

    @OneToMany(mappedBy = "survey")
    List<Question> questions;

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public List<Question> getQuestions(){
        return questions;
    }

    public void addQuestion(Question newQuestion){
        questions.add(newQuestion);
    }

    /**
     * Sets the name
     * @param name the name
     */
    public void setName(String name){
        this.name = name;
    }
    
}
