package org.example.genai.rag;

public class ChatMessage {
    private String question;
    private String answer;

    public ChatMessage(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // getters and setters

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}