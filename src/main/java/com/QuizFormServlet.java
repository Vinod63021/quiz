package com;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/quiz")
public class QuizFormServlet extends HttpServlet {

    private final String[] questions = {
        "What is JVM?",
        "Which keyword is used to inherit a class in Java?",
        "Which method is the entry point of Java application?",
        "Which of these is not a Java keyword?",
        "Which collection class allows dynamic resizing?"
    };

    private final String[][] options = {
        {"Java Virtual Machine", "Java Version Manager", "Java Verified Method"},
        {"this", "super", "extends"},
        {"main()", "start()", "run()"},
        {"int", "goto", "implement"},
        {"ArrayList", "HashMap", "Array"}
    };

    private final int[] answers = {0, 2, 0, 2, 0}; // correct indexes

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Integer currentIndex = (Integer) session.getAttribute("currentIndex");
        Integer score = (Integer) session.getAttribute("score");
        Integer total = (Integer) session.getAttribute("total");

        if (currentIndex == null) {
            total = Integer.parseInt(request.getParameter("numQuestions"));
            if (total > questions.length) total = questions.length;

            session.setAttribute("currentIndex", 0);
            session.setAttribute("score", 0);
            session.setAttribute("total", total);
            currentIndex = 0;
            score = 0;
        } else {
            int selected = Integer.parseInt(request.getParameter("answer"));
            int prevIndex = currentIndex - 1;
            if (answers[prevIndex] == selected) {
                score++;
                session.setAttribute("score", score);
            }
        }

        if (currentIndex >= total) {
            response.sendRedirect("result");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Quiz</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("</head><body class='container mt-5'>");

        out.println("<h4>Question " + (currentIndex + 1) + " of " + total + "</h4>");
        out.println("<form action='quiz' method='post'>");
        out.println("<p><b>" + questions[currentIndex] + "</b></p>");

        for (int i = 0; i < options[currentIndex].length; i++) {
            out.println("<div class='form-check'>");
            out.println("<input class='form-check-input' type='radio' name='answer' value='" + i + "' required>");
            out.println("<label class='form-check-label'>" + options[currentIndex][i] + "</label>");
            out.println("</div>");
        }

        currentIndex++;
        session.setAttribute("currentIndex", currentIndex);

        out.println("<br><button type='submit' class='btn btn-success'>Next</button>");
        out.println("</form>");
        out.println("</body></html>");
    }
}
