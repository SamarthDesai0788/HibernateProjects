package com.java.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import entity.Answers;
import entity.Question;

public class ConfigureClass {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory()) {

            Question question = new Question("What is Servlet");

            Answers answer = new Answers(question, "Servlet technology is used to create a web application");

            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();

                session.save(question);
                session.save(answer);

                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try (Session session = sessionFactory.openSession()) {
                Question retrievedQuestion = session.get(Question.class, question.getId());
                if (retrievedQuestion != null) {
                    System.out.println("Question: " + retrievedQuestion.getQuestionText());
                    System.out.println("Answers:");

                    for (Answers ans : retrievedQuestion.getAnswers()) {
                        System.out.println("- " + ans.getAnswerText());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
