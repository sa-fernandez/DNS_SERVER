/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.List;

/**
 *
 * @author Santiago Fernandez - Fabian Olarte - Andres Vasquez
 */
public class MessageDns {
    
    private HeaderDns header;
    private QuestionDns question;
    private List<AnswerDns> answer;

    public MessageDns() {
    }

    public MessageDns(HeaderDns header, QuestionDns question, List<AnswerDns> answer) {
        this.header = header;
        this.question = question;
        this.answer = answer;
    }

    public HeaderDns getHeader() {
        return header;
    }

    public void setHeader(HeaderDns header) {
        this.header = header;
    }

    public QuestionDns getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDns question) {
        this.question = question;
    }

    public List<AnswerDns> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerDns> answer) {
        this.answer = answer;
    }
    
}
