package com;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/result")
public class QuizSubmitServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        int total = (Integer) session.getAttribute("total");
        int score = (Integer) session.getAttribute("score");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Quiz Result</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("</head><body class='container mt-5'>");

        out.println("<div class='card p-4 shadow'>");
        out.println("<h3>🎉 Quiz Completed!</h3>");
        out.println("<p>Total Questions: " + total + "</p>");
        out.println("<p>Correct Answers: " + score + "</p>");
        out.println("<p>Your Score: <strong>" + (score * 100 / total) + "%</strong></p>");
        out.println("<a href='index.html' class='btn btn-primary mt-3'>Try Again</a>");
        out.println("</div>");

        out.println("</body></html>");

        // Clear session after quiz
        session.invalidate();
    }
}
